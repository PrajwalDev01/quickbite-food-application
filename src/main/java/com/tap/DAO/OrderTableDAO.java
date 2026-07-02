package com.tap.DAO;

import java.util.List;
import com.tap.model.Order;

public interface OrderTableDAO {
    void addOrder(Order o);
    void updateOrder(Order o);
    void deleteOrder(int id);
    Order getOrder(int id);
    List<Order> getAllOrders();
    List<Order> getOrdersByUser(int userId);
}
