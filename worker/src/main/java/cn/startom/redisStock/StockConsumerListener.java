package cn.startom.redisStock;

import cn.startom.redisStock.domain.gateway.StockGateway;
import cn.startom.redisStock.dto.domainevent.StockAddEvent;
import cn.startom.redisStock.dto.domainevent.StockSubEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.sql.SQLIntegrityConstraintViolationException;


@Component
@Slf4j
public class StockConsumerListener {
    @Autowired
    private StockGateway stockGateway;

    @KafkaListener(topics="stock.add",groupId = "stockServiceGroup")
    public void addStock(StockAddEvent event){
        log.info("消费消息新增库存"+event.getStock());
        stockGateway.addStockToDatabase(event.getReqId(),event.getProduct_id(),event.getStock());

    }
    @KafkaListener(topics="stock.sub",groupId = "stockServiceGroup")
    public void subStock(StockSubEvent event){
        log.info("消费消息扣减库存"+event.getStock());
        //这里需要判断去重，增加幂等性支持
        stockGateway.subStockToDatabase(event.getReqId(), event.getProduct_id(), event.getStock());

    }
}

