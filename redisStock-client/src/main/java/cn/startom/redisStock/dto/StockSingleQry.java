package cn.startom.redisStock.dto;

import com.alibaba.cola.dto.Query;
import lombok.Data;

@Data
public class StockSingleQry extends Query {
    public int product_id;
}
