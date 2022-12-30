package com.adolesce.common.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单类型
 */
public enum OrderType {
    FREE (1, "免费订单！"),
    HALF (2, "半价订单！"),
    DISCOUT (3, "打折订单！"),
    SEVEN_DISCOUNT (4, "七折订单！"),
    BA_DISCOUNT (5, "霸王餐订单！");


    public Integer key;
    public String value;

    OrderType(int key, String value) {
        this.key = key;
        this.value = value;
    }

    private static final Map<Integer, OrderType> RESULT_CODE_MAP = new HashMap<>();
    static {
        OrderType[] values = OrderType.values();
        for (OrderType resultCode : values) {
            RESULT_CODE_MAP.put(resultCode.key,resultCode);
        }
    }

    public static OrderType getByKey(String key) {
        return RESULT_CODE_MAP.get(key);
    }

    public boolean eq(String key) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        return this.toString().equals(key);
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.valueOf(key);
    }
}