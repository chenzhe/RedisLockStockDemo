package cn.startom.redisStock.api;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import cn.startom.redisStock.dto.CustomerAddCmd;
import cn.startom.redisStock.dto.CustomerListByNameQry;
import cn.startom.redisStock.dto.domainmodel.Customer;

public interface CustomerServiceI {

    public Response addCustomer(CustomerAddCmd customerAddCmd);

    public MultiResponse<Customer> listByName(CustomerListByNameQry customerListByNameQry);
}
