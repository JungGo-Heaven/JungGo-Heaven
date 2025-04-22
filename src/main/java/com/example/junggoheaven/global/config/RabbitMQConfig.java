package com.example.junggoheaven.global.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Binding;


@Configuration
public class RabbitMQConfig {
    public static final String BID_QUEUE = "bid.queue";
    public static final String BID_EXCHANGE = "bid.exchange";
    public static final String BID_ROUTING_KEY = "bid.key";

    @Bean
    public Queue bidQueue() {
        return new Queue(BID_QUEUE, true);
    }

    @Bean
    public DirectExchange bidExchange() {
        return new DirectExchange(BID_EXCHANGE);
    }

    @Bean
    public Binding bidBinding() {
        return BindingBuilder.bind(bidQueue()).to(bidExchange()).with(BID_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
