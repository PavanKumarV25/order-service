package com.tech.OrderService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

    private long orderId;

    private Instant orderDate;

    private String orderStaus;

    private long amount;

    private ProductDetails productDetails;

    private PaymentDetails paymentDetails;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductDetails {

        private String productName;
        private Long productId;
        private Long quantity;
        private Long price;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PaymentDetails {

        private long paymentId;

        private String status;

        private long amount;

        private PaymentMode paymentMode;

        private Instant paymentDate;

        private long orderId;


    }


}
