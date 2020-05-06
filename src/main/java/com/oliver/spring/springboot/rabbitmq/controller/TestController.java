package com.oliver.spring.springboot.rabbitmq.controller;

import com.oliver.spring.springboot.rabbitmq.config.RabbitConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public TestController(){
    }
    
    @RequestMapping("/{name}")
    public String doSendMessageToRabbitMQ(@PathVariable("name") String name) {
    	rabbitTemplate.convertAndSend(RabbitConfiguration.QUEUE_NAME, name);
    	
    	return "Hola " + name;
    }
    

    @RabbitListener(queues = RabbitConfiguration.QUEUE_NAME)
    public void onMessageFromRabbitMQ(final String messageFromRabbitMQ){
    	System.out.println("Recibiendo mensaje ...");
        LOGGER.info("{}", messageFromRabbitMQ);
    }
}
