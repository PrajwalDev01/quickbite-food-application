package com.tap.DAO;

import java.util.List;
import com.tap.model.OrderItem;

public interface OrderItemDAO {
    void addOrderItem(OrderItem oi);
    void updateOrderItem(OrderItem oi);
    void deleteOrderItem(int id);
    OrderItem getOrderItem(int id);
    List<OrderItem> getAllOrderItems();
    List<OrderItem> getOrderItemsByOrder(int orderId);
}
