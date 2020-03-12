package cn.startom.redisStock.executor;

import cn.startom.redisStock.domain.gateway.StockGateway;
import cn.startom.redisStock.domain.stock.StockInfo;
import cn.startom.redisStock.dto.StockAddCmd;
import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.CommandExecutorI;
import com.alibaba.cola.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
@Command
public class StockAddCmdExe implements CommandExecutorI<Response, StockAddCmd> {

    @Autowired
    private StockGateway stockGateway;
    @Override
    public Response execute(StockAddCmd cmd) {
        StockInfo stock=new StockInfo();
        stock.setProduct_id(cmd.getProduct_id());
        stock.setStock(cmd.getStock());
        boolean isok= stockGateway.addStock(stock,cmd.getStock());
        if(isok)
             return Response.buildSuccess();
        else
            return Response.buildFailure("999","新增库存失败！");
    }
}
