package cn.startom.redisStock.dto.domainevent;

import com.alibaba.cola.event.DomainEventI;
import lombok.Data;

@Data
public class StockSubEvent implements DomainEventI {
    private String reqId;
    private int product_id;
    private  int stock;
}
