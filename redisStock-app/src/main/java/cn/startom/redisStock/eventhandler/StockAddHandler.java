package cn.startom.redisStock.eventhandler;

import cn.startom.redisStock.dto.domainevent.StockAddEvent;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.event.EventHandler;
import com.alibaba.cola.event.EventHandlerI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.StringUtils;

@EventHandler
@Slf4j
public class StockAddHandler implements EventHandlerI<Response, StockAddEvent> {

    @Override
    public Response execute(StockAddEvent stockAddEvent) {
        log.info("新增库存"+stockAddEvent.getStock());
        return Response.buildSuccess();
    }
}
