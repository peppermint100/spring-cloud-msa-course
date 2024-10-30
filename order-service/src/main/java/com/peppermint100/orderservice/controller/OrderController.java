package com.peppermint100.orderservice.controller;

import com.peppermint100.orderservice.dto.OrderDto;
import com.peppermint100.orderservice.jpa.OrderEntity;
import com.peppermint100.orderservice.messagequeue.KafkaProducer;
import com.peppermint100.orderservice.messagequeue.OrderProducer;
import com.peppermint100.orderservice.service.OrderService;
import com.peppermint100.orderservice.vo.RequestOrder;
import com.peppermint100.orderservice.vo.ResponseOrder;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order-service")
@Slf4j
public class OrderController {

    private Environment env;
    private OrderService orderService;
    private KafkaProducer kafkaProducer;
    private OrderProducer orderProducer;

    @Autowired
    public OrderController(Environment env, OrderService orderService, KafkaProducer kafkaProducer, OrderProducer orderProducer) {
        this.env = env;
        this.orderService = orderService;
        this.kafkaProducer = kafkaProducer;
        this.orderProducer = orderProducer;
    }



    @GetMapping("/health_check")
    public String status() {
        return String.format("Order Service Health Check OK on PORT %s", env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(
            @RequestBody RequestOrder orderDetails,
            @PathVariable("userId") String userId
    ) {
        log.info("Before added order data");
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = mapper.map(orderDetails, OrderDto.class);
        orderDto.setUserId(userId);

        // jpa
        OrderDto createdOrder = orderService.createOrder(orderDto);
        ResponseOrder responseOrder = mapper.map(createdOrder, ResponseOrder.class);

        log.info("After added order data");

        // kafka
//        orderDto.setOrderId(UUID.randomUUID().toString());
//        orderDto.setTotalPrice(orderDetails.getQty() * orderDetails.getUnitPrice());
//        ResponseOrder responseOrder = mapper.map(orderDto, ResponseOrder.class);

        // send order via kafka
        kafkaProducer.send("example-catalog-topic", orderDto);
//        orderProducer.send("orders", orderDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(
            @PathVariable("userId") String userId
    ) throws Exception {
        log.info("Before retrieve order data");
        Iterable<OrderEntity> orderList = orderService.getOrdersByUserId(userId);
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<ResponseOrder> result = new ArrayList<>();

        orderList.forEach(v -> {
            result.add(mapper.map(v, ResponseOrder.class));
        });

//        try {
//            Thread.sleep(1000);
//            throw new Exception("장애 발생");
//        } catch (InterruptedException ex) {
//            log.error(ex.getMessage());
//        }

        log.info("after retrieve order data");

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
