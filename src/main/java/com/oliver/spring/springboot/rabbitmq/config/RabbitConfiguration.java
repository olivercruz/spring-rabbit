package com.oliver.spring.springboot.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.CacheMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.Connection;

@Configuration
public class RabbitConfiguration {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitConfiguration.class);

    public static final String QUEUE_NAME = "prueba-queue";

    private static final String EXCHANGE_NAME = "prueba-exchange";

    private static final String ROUTING_KEY = "prueba-routing-key";

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private Integer port;

    @Value("${spring.rabbitmq.username}")
    private String user;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtualhost}")
    private String virtualhost;

    @Bean
    public ConnectionFactory connectionFactory() {
    	CachingConnectionFactory connectionFactory = null;
    	connectionFactory = new CachingConnectionFactory();
    	connectionFactory.setAddresses("172.25.0.2:5672,movistar.westus2.cloudapp.azure.com:5672");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setCacheMode(CacheMode.CONNECTION);
        connectionFactory.setConnectionCacheSize(5);
        connectionFactory.setPublisherConfirms(true);
        LOGGER.info("nombre");
    	return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() throws Exception {
    	return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}
