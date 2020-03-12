package cn.startom.redisStock.executor.query;

import cn.startom.redisStock.domain.gateway.StockGateway;
import cn.startom.redisStock.dto.StockSingleQry;
import cn.startom.redisStock.dto.domainmodel.StockInfo;
import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.QueryExecutorI;
import com.alibaba.cola.dto.SingleResponse;
import org.springframework.beans.factory.annotation.Autowired;
@Command
public class StockSingleQryExe implements QueryExecutorI<SingleResponse<StockInfo>, StockSingleQry> {

    @Autowired
    private StockGateway stockGateway;
    @Override
    public SingleResponse<StockInfo> execute(StockSingleQry cmd) {

        StockInfo stock= stockGateway.getById(cmd.getProduct_id());

        return SingleResponse.of(stock);
    }
}
