package cn.startom.redisStock.eventhandler;

import cn.startom.redisStock.dto.domainevent.StockSubEvent;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.event.EventHandler;
import com.alibaba.cola.event.EventHandlerI;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

@EventHandler
@Slf4j
public class StockSubHandler implements EventHandlerI<Response, StockSubEvent> {

    @Override
    public Response execute(StockSubEvent stockSubEvent) {
        log.info("扣减库存"+stockSubEvent.getStock());

        return Response.buildSuccess();
    }
}
