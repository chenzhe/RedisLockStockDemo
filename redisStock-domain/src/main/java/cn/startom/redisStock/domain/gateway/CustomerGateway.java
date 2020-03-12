package cn.startom.redisStock.domain.gateway;

import cn.startom.redisStock.domain.customer.Customer;

public interface CustomerGateway {
    public Customer getByById(String customerId);
}
