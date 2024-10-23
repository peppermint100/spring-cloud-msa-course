package com.peppermint100.orderservice.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peppermint100.orderservice.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class OrderProducer {

    private KafkaTemplate<String, String> kafkaTemplate;
    List<OrderField> fields = Arrays.asList(
            new OrderField("string", true, "order_id"),
            new OrderField("string", true, "user_id"),
            new OrderField("string", true, "product_id"),
            new OrderField("int32", true, "qty"),
            new OrderField("int32", true, "unit_price"),
            new OrderField("int32", true, "total_price")
    );

    Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("orders")
            .build();

    @Autowired
    public OrderProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public OrderDto send(String topic, OrderDto orderDto) {
        Payload payload = Payload.builder()
                .user_id(orderDto.getUserId())
                .order_id(orderDto.getOrderId())
                .qty(orderDto.getQty())
                .product_id(orderDto.getProductId())
                .unit_price(orderDto.getUnitPrice())
                .total_price(orderDto.getTotalPrice())
                .build();

        KafkaOrderDto kafkaOrderDto = new KafkaOrderDto(schema, payload);

        ObjectMapper mapper = new ObjectMapper();
        String jsonInputString = "";
        try {
            jsonInputString = mapper.writeValueAsString(kafkaOrderDto);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        kafkaTemplate.send(topic, jsonInputString);
        log.info("Order Producer sent data from order microservice" + kafkaOrderDto);
        return orderDto;
    }
}
