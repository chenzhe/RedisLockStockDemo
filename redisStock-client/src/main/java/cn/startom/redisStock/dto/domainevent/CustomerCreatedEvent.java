package cn.startom.redisStock.dto.domainevent;

import com.alibaba.cola.event.DomainEventI;

import static cn.startom.redisStock.dto.domainevent.DomainEventConstant.CUSTOMER_CREATED_TOPIC;

/**
 * CustomerCreatedEvent
 *
 * @author Frank Zhang
 * @date 2019-01-04 10:32 AM
 */
public class CustomerCreatedEvent implements DomainEventI {

    private String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

}
