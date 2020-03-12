package cn.startom.redisStock.repository;

import cn.startom.redisStock.common.RedissonConnector;
import cn.startom.redisStock.domain.gateway.StockGateway;
import cn.startom.redisStock.domain.stock.StockInfo;
import cn.startom.redisStock.dto.domainevent.StockAddEvent;
import cn.startom.redisStock.dto.domainevent.StockSubEvent;
import cn.startom.redisStock.message.DomainEventPublisher;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.redisson.remote.RemoteServiceTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
@Repository
public class StockRepository implements StockGateway {

    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private RedissonConnector redissonConnector;
    @Autowired
    private DomainEventPublisher domainEventPublisher;
    @Autowired
    private KafkaTemplate<Object, Object> template;

    @Override
    public StockInfo getById(int productId) {
        String cacheKey="stockinfo:"+productId;
        String stockKey="stock:"+productId;
        RedissonClient client= redissonConnector.getClient();
        RBucket<StockDO> stockCache= client.getBucket(cacheKey);
        RAtomicLong atomic= client.getAtomicLong(stockKey);
        StockDO stock= stockCache.get();
        if(stock==null){
            //第一次没有数据的时候添加数据到缓存中
            stock= stockMapper.getById(productId);
            stockCache.set(stock);
            atomic.addAndGet(stock.getStock());
        }
        else{
            stock.setStock((int)atomic.get());
        }

        StockInfo stockInfo=new StockInfo();
        stockInfo.setProduct_id(stock.getProduct_id());
        stockInfo.setStock(stock.getStock());
        return stockInfo;
    }

    @Override
    public boolean addStock(StockInfo stock, int saleNum) {
        //保证发送消息和redis操作在一个事物中
        boolean isok= this.template.executeInTransaction(kafkaTemplate -> {
            StockAddEvent event=new StockAddEvent();
            event.setProduct_id(stock.getProduct_id());
            event.setStock(saleNum);
            event.setReqId(UUID.randomUUID().toString());
            kafkaTemplate.send("stock.add", event);
            String stockKey="stock:"+stock.getProduct_id();
            RedissonClient client= redissonConnector.getClient();
            RAtomicLong atomic= client.getAtomicLong(stockKey);
            if(saleNum==1){
                atomic.incrementAndGet();
            }else {
                atomic.addAndGet(saleNum);
            }
            domainEventPublisher.publish(event);
            return true;
        });
        return isok;
    }

    @Override
    public boolean subStock(StockInfo stock, int num) {
        //发送到MQ
        boolean isok= this.template.executeInTransaction(kafkaTemplate -> {
            StockSubEvent event=new StockSubEvent();
            event.setProduct_id(stock.getProduct_id());
            event.setStock(num);
            event.setReqId(UUID.randomUUID().toString());
            kafkaTemplate.send("stock.sub", event);
            String stockKey="stock:"+stock.getProduct_id();
            RedissonClient client= redissonConnector.getClient();
            RAtomicLong atomic= client.getAtomicLong(stockKey);
            if(num==1){
                atomic.decrementAndGet();
            }
            else {
                atomic.addAndGet(num * -1);
            }
            domainEventPublisher.publish(event);
            return true;
        });
        return isok;
    }

    @Override
    @Transactional
    public boolean addStockToDatabase(String id,int productId, int num) {
        StockLogsDO sl=new StockLogsDO();
        sl.setId(id);
        sl.setOut_stock(num*-1);
        int rows=stockMapper.updateStockLogs(sl);
        if(rows==0) {
            //如果不存在则更新总库存
            rows = stockMapper.insertStockLogs(sl);
            if (rows > 0) {
                rows = stockMapper.addStock(productId, num);
            }
        }
        return rows>0;
    }

    @Override
    @Transactional
    public boolean subStockToDatabase(String id,int productId, int num) {
        StockLogsDO sl=new StockLogsDO();
        sl.setId(id);
        sl.setOut_stock(num);
        int rows=stockMapper.updateStockLogs(sl);
        if(rows==0) {
            //如果不存在则更新总库存
            rows = stockMapper.insertStockLogs(sl);
            if (rows > 0) {
                rows = stockMapper.subStock(productId, num);
            }
        }
        return rows>0;
    }
}
