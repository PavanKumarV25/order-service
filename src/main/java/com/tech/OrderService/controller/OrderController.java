package com.tech.OrderService.controller;

import com.tech.OrderService.model.OrderRequest;
import com.tech.OrderService.model.OrderResponse;
import com.tech.OrderService.service.OrderService;

import lombok.extern.slf4j.Slf4j;

import org.hibernate.annotations.common.util.impl.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.event.HyperlinkEvent;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest)
    {
    	Logger log=LoggerFactory.getLogger(OrderController.class);
        Long orderId=orderService.placeOrder(orderRequest);
       log.info("Order Id: {}",orderId);
       
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/getOrderDetails/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetials(@PathVariable long orderId){

        OrderResponse orderResponse=orderService.getOrderDetails(orderId);
        return new ResponseEntity<>(orderResponse,HttpStatus.OK);
    }
}
