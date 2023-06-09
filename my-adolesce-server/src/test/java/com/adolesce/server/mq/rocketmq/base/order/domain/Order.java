package com.adolesce.server.mq.rocketmq.base.order.domain;

import lombok.Data;

@Data
public class Order {
    private String id;
    private String msg;

    @Override
    public String toString() {
        return "Order{ id='" + id + ", msg='" + msg + '}';
    }
}
