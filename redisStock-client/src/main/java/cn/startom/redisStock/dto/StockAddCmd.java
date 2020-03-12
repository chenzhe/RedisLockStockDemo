package cn.startom.redisStock.dto;

import com.alibaba.cola.dto.Command;
import lombok.Data;

@Data
public class StockAddCmd extends Command {
    private  int product_id;
    private int stock;

    public String toString(){
        return String.valueOf(product_id);
    }
}
