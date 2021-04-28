package br.com.loja.ecommerce;

import java.math.BigDecimal;

public class Order {

    private final String userId;
    private final String orderId;
    private final BigDecimal amount;

    public Order(String userId, String orderid, BigDecimal amount) {
        this.userId = userId;
        this.orderId = orderid;
        this.amount = amount;
    }
}
