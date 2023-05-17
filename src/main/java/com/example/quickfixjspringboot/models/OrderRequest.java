package com.example.quickfixjspringboot.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project: quickfixj-spring-boot
 * Author: Joniyed Bhuiyan
 * Email: joniyed.bhuiyan@gmail.com
 * Date: ৮/৫/২৩
 * Day & Time: Monday, ৭:০৪ AM
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequest {
    private String clientOrderId;
    private char side;
    private String symbol;
    private double quantity;
    private char orderType;
    private double price;
}
