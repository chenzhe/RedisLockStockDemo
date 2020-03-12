package cn.startom.redisStock.domain.gateway;

import cn.startom.redisStock.domain.customer.Customer;
import cn.startom.redisStock.domain.customer.Credit;

//Assume that the credit info is in antoher distributed Service
public interface CreditGateway {
    public Credit getCredit(String customerId);
}
