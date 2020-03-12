package cn.startom.redisStock.common;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class RedissonConnector {
    RedissonClient redisson;
    @PostConstruct
    public void init(){
        Config config = new Config();
        config.useSingleServer().setAddress("192.168.100.175:6379");
//        config.useClusterServers()
//                // use "rediss://" for SSL connection
//                .addNodeAddress("redis://192.168.100.175:6379");
        redisson= Redisson.create(config);
    }

    public RedissonClient getClient(){
        return redisson;
    }

    @PreDestroy
    public void destroy(){
        redisson.shutdown();
    }
}
