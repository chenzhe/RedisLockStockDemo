package cn.startom.redisStock.domain.stock;

import com.alibaba.cola.domain.Entity;
import lombok.Data;

@Data
@Entity
public class StockInfo extends cn.startom.redisStock.dto.domainmodel.StockInfo {

    /**
     * 判断是否有库存
     * @param saleNum
     * @return
     */
    public boolean canSale(int saleNum){
        if(this.stock>=saleNum) {
           return true;
        }
        else{
            return false;
        }
    }
}
