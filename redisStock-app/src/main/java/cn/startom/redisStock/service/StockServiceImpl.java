package cn.startom.redisStock.service;

import cn.startom.redisStock.api.StockServiceI;
import cn.startom.redisStock.dto.StockAddCmd;
import cn.startom.redisStock.dto.StockSingleQry;
import cn.startom.redisStock.dto.StockSubCmd;
import cn.startom.redisStock.dto.domainmodel.StockInfo;
import com.alibaba.cola.command.CommandBusI;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StockServiceImpl implements StockServiceI {
    @Autowired
    private CommandBusI commandBus;
    @Override
    public Response addStock(StockAddCmd cmd) {
        return (Response)commandBus.send(cmd);
    }

    @Override
    public Response subStock(StockSubCmd cmd) {
        return (Response)commandBus.send(cmd);
    }
    @Override
    public SingleResponse<StockInfo> getStock(StockSingleQry stockSingleQry) {
        return (SingleResponse<StockInfo>)commandBus.send(stockSingleQry);
    }
}
