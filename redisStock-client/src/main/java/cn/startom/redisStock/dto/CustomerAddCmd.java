package cn.startom.redisStock.dto;

import com.alibaba.cola.dto.Command;
import cn.startom.redisStock.dto.domainmodel.Customer;
import lombok.Data;

@Data
public class CustomerAddCmd extends Command{

    private Customer customer;

}
