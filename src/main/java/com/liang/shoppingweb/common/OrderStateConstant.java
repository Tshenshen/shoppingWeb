package com.liang.shoppingweb.common;

public class OrderStateConstant {

    /**
     * 订单状态，1为待付款，2为待发货，3为待收货，4为完成，0为取消
     */
    public static final int cancel = 0;
    public static final int unPay = 1;
    public static final int unSend = 2;
    public static final int unReceive = 3;
    public static final int refund = 4;
    public static final int finish = 5;
    public static final int refundAccept = 6;
    public static final int refundRefuse = 7;
}
