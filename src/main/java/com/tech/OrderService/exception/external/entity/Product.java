package com.tech.OrderService.exception.external.entity;


import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="PRODUCT_ID")
    private  Long productId;
    @Column(name="PRODUCT_NAME")
    private String productName;
    @Column(name="PRICE")
    private Long price;
    @Column(name="QUANTITY")
    private Long quantity;
	public long getProductId() {
		
		return productId;
	}


}
