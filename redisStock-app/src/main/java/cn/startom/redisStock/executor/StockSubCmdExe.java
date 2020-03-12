package cn.startom.redisStock.executor;

import cn.startom.redisStock.domain.gateway.StockGateway;
import cn.startom.redisStock.domain.stock.StockInfo;
import cn.startom.redisStock.dto.StockSubCmd;
import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.CommandExecutorI;
import com.alibaba.cola.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
@Command
public class StockSubCmdExe implements CommandExecutorI<Response, StockSubCmd> {
    @Autowired
    private StockGateway stockGateway;
    @Override
    public Response execute(StockSubCmd cmd) {
        StockInfo stock=new StockInfo();
        stock.setProduct_id(cmd.getProduct_id());
        stock.setStock(cmd.getStock());
        StockInfo s= stockGateway.getById(stock.getProduct_id());
       if( s.canSale(cmd.getStock())) {
           boolean isok = stockGateway.subStock(stock, cmd.getStock());
           if (isok)
               return Response.buildSuccess();
           else
               return Response.buildFailure("999", "扣减库存失败！");
       }else{
           return Response.buildFailure("789","库存不足");
       }
    }
}
