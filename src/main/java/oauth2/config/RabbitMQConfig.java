package oauth2.config;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import oauth2.common.RedisKeyConfig;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/10/9 13:50
 */
@Configuration
@Slf4j
public class RabbitMQConfig {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RedisKeyConfig redisKeyConfig;

    /**
     * 交换器
     * @return
     */
    @Bean
    TopicExchange exchange(){
        return new TopicExchange("ExpiredTokenExchange");
    }

    /**
     * 添加过期token的队列
     */
    @Bean
    public Queue addExpiredTokenQueue(){
        return new Queue("addExpiredToken");
    }

    /**
     * 删除过期token的队列
     */
//    @Bean
//    public Queue deleteExpiredTokenQueue(){
//        return new Queue("deleteExpiredToken");
//    }

    /**
     * 绑定队列和交换器
     */
    @Bean
    Binding bindingAddExpiredTokenQueue(){
        return BindingBuilder.bind(addExpiredTokenQueue()).to(exchange()).with("expiredToken.addExpiredToken");
    }

//    @Bean
//    Binding bindingDeleteExpiredTokenQueue(){
//        return BindingBuilder.bind(deleteExpiredTokenQueue()).to(exchange()).with("expiredToken.deleteExpiredToken");
//    }



    /**
     * 队列处理逻辑
     */
    @RabbitListener(queues = "addExpiredToken")
    public void addExpiredToken2Redis(List<String> info, Channel channel, Message message){
        log.info("添加过期的用户和时间到redis中");

        String userId = info.get(0);
        String timestamp = info.get(1);

        redisTemplate.opsForHash().put(redisKeyConfig.getExpiredTokenKey(),userId,Long.valueOf(timestamp));

        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            log.error("添加过期的token,在确认消息时抛出异常:" + e.getMessage());
        }
    }

//    @RabbitListener(queues = "deleteExpiredToken")
//    public void deleteExpiredToken2Redis(String userId,Channel channel, Message message){
//        log.info("删除过期的用户和时间到redis中:" + userId);
//
//        redisTemplate.opsForHash().delete(redisKeyConfig.getExpiredTokenKey(),userId);
//
//        try {
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//        } catch (IOException e) {
//            log.error("删除过期的token,在确认消息时抛出异常:" + e.getMessage());
//        }
//    }

}
