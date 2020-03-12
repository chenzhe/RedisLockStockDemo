package cn.startom.redisStock.api;

import cn.startom.redisStock.dto.StockAddCmd;
import cn.startom.redisStock.dto.StockSingleQry;
import cn.startom.redisStock.dto.StockSubCmd;
import cn.startom.redisStock.dto.domainmodel.StockInfo;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import sun.security.provider.certpath.OCSPResponse;


/**
 * 库存服务
 */
public interface StockServiceI {
    Response addStock(StockAddCmd cmd);
    Response subStock(StockSubCmd cmd);
    SingleResponse<StockInfo> getStock(StockSingleQry qry);

}
