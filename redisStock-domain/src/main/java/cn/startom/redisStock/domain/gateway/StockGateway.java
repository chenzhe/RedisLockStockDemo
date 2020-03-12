package cn.startom.redisStock.domain.gateway;

import cn.startom.redisStock.domain.stock.StockInfo;

public interface StockGateway {
    StockInfo getById(int productId);
    boolean addStock(StockInfo stock,int saleNum);
    boolean subStock(StockInfo stock,int num);
    boolean addStockToDatabase(String id,int productId,int num);
    boolean subStockToDatabase(String id,int productId,int num);

}
