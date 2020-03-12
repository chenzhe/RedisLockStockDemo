package cn.startom.redisStock.controller;

import cn.startom.redisStock.annotation.CacheLock;
import cn.startom.redisStock.annotation.CacheParam;
import cn.startom.redisStock.api.CustomerServiceI;
import cn.startom.redisStock.api.StockServiceI;
import cn.startom.redisStock.dto.*;
import cn.startom.redisStock.dto.domainevent.StockSubEvent;
import cn.startom.redisStock.dto.domainmodel.Customer;
import cn.startom.redisStock.dto.domainmodel.StockInfo;
import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StockController {
    @Autowired
    private StockServiceI serviceI;

    @GetMapping(value = "/stock")
    public SingleResponse<StockInfo> findById(@RequestParam int pid){
        StockSingleQry qry=new StockSingleQry();
        qry.setProduct_id(pid);
        return serviceI.getStock(qry);
    }

    @PostMapping(value="/stock/add")
    @CacheLock(prefix = "stock-lock")
    public Response addStock(@CacheParam(name = "cmd") @RequestBody  StockAddCmd cmd){
       return serviceI.addStock(cmd);
    }
    @PostMapping(value="/stock/sub")
    @CacheLock(prefix = "stock-lock")
    public Response subStock(@CacheParam(name = "cmd")@RequestBody  StockSubCmd cmd){
        return serviceI.subStock(cmd);
    }

}
