package cn.startom.redisStock.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StockMapper {
     StockDO getById(@Param("id")int productId);

    int addStock(@Param("product_id")int productId,@Param("stock") int num);

    int subStock(@Param("product_id")int productId,@Param("stock") int num);

    int insertStockLogs(StockLogsDO obj);
    int updateStockLogs(StockLogsDO obj);
}
