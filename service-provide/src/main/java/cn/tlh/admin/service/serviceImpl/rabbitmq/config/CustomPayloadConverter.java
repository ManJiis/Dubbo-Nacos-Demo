//package cn.tlh.admin.service.serviceImpl.rabbitmq.config;
//
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageHeaders;
//import org.springframework.messaging.converter.MessageConverter;
//import org.springframework.messaging.support.MessageBuilder;
//
///**
// * @author musui
// */
//public class CustomPayloadConverter implements MessageConverter {
//
//    @Override
//    public Object fromMessage(Message<?> message, Class<?> targetClass) {
//        Object payLoad = message.getPayload();
//        //write your logic here.
//        return payLoad;
//    }
//
//    @Override
//    public Message<?> toMessage(Object payload, MessageHeaders headers) {
//        // write your logic.
//        return MessageBuilder.withPayload(payload).copyHeaders(headers).build();
//    }
//}