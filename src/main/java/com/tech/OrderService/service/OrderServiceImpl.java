package com.tech.OrderService.service;

import com.tech.OrderService.entity.Order;
import com.tech.OrderService.exception.CustomException;
import com.tech.OrderService.exception.external.client.PaymentService;
import com.tech.OrderService.exception.external.client.ProductService;
import com.tech.OrderService.exception.external.request.PaymentRequest;
import com.tech.OrderService.model.OrderRequest;
import com.tech.OrderService.model.OrderResponse;
import com.tech.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductService productService;

    private PaymentService paymentService;
    @Override
    public long placeOrder(OrderRequest orderRequest) {

        //Order Entity-> save the data with status  order created
        //Product Service-> Block Products(Reduce the quantity)
        //Payment Service->Payments -> Success Else Cancelled
       productService.reduceQuantity(orderRequest.getProductId(),orderRequest.getQuantity());
//        String url="http://localhost:8181/product/reduceQuantity/{id}?quantity={quantity}";
//        HttpHeaders h=new HttpHeaders();
//        Map<String, Long> params=new HashMap<>();
//        params.put("id",orderRequest.getProductId());
//        params.put("quantity",orderRequest.getQuantity());
//        restTemplate.put(url,Product.class,params);
        log.info("Creating Order With Status CREATED");

        Order order= Order.builder()
                .amount(orderRequest.getTotalamount())
                .orderStatus("CREATED")
                .orderDate(Instant.now())
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity())
                .build();
        orderRepository.save(order);
        log.info("Calling Payment Service to complete the payment");
        PaymentRequest paymentRequest=new PaymentRequest().builder()
                        .orderId(order.getId())
                        .paymentMode(orderRequest.getPaymentMode())
                         .amount(order.getAmount()).build();
        String orderStatus=null;
        try{
            restTemplate.postForEntity("http://PAYMENT-SERVICE/payment",paymentRequest,long.class);
            orderStatus="PLACED";
            log.info("Payment Success and Order Status changed to PLACED");
        }
        catch (Exception e)
        {
            log.error("error->"+e);
            orderStatus="PAYMENT_FAILED";
            log.info("Error Occured In payment Changing Order Status to Payment Failed");

        }
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        log.info("Order Placed Successfully with Order Id: {}",order.getId());

        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        log.info("get order detials based on order id");
        Order order=orderRepository.findById(orderId).orElseThrow(()->new CustomException("Order not found for the Id","NOT_FOUND",404));

        OrderResponse.ProductDetails productDetais=restTemplate.getForObject("http://PRODUCT-SERVICE/product/getProduct/"+order.getProductId(), OrderResponse.ProductDetails.class);
        OrderResponse.ProductDetails productDetails1=OrderResponse.ProductDetails.builder()
                .productName(productDetais.getProductName())
                .productId(productDetais.getProductId())
                .quantity(productDetais.getQuantity())
                .price(productDetais.getPrice()).build();

        log.info("Get Payment Details using order Id");
        OrderResponse.PaymentDetails paymentDetails=restTemplate.getForObject("http://PAYMENT-SERVICE/payment/getDetails/"+order.getId(),OrderResponse.PaymentDetails.class);
        OrderResponse.PaymentDetails paymentDetails1=OrderResponse.PaymentDetails.builder()
                .paymentId(paymentDetails.getPaymentId())
                .paymentDate(paymentDetails.getPaymentDate())
                .paymentMode(paymentDetails.getPaymentMode())
                .status(paymentDetails.getStatus())
                .orderId(paymentDetails.getOrderId())
                .amount(paymentDetails.getAmount())
                .build();
        OrderResponse orderResponse=OrderResponse.builder()
                .orderId(order.getId())
                .orderStaus(order.getOrderStatus())
                .orderDate(order.getOrderDate())
                .amount(order.getAmount())
                .productDetails(productDetails1)
                .paymentDetails(paymentDetails1)
                .build();

        return orderResponse;
    }
}
