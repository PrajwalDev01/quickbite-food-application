package com.tap.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.OrderTableDAO;
import com.tap.model.Order;
import com.tap.utility.DBConnection;

public class OrderTableDAOImpl implements OrderTableDAO {
	private static final String INSERT_QUERY = "INSERT INTO ordertable(userId, restaurantId, orderDate, totalAmount, status, paymentMethod) VALUES(?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_QUERY = "UPDATE ordertable SET userId=?, restaurantId=?, orderDate=?, totalAmount=?, status=?, paymentMethod=? WHERE orderId=?";
	private static final String DELETE_QUERY = "DELETE FROM ordertable WHERE orderId=?";
	private static final String SELECT_QUERY = "SELECT * FROM ordertable WHERE orderId=?";
	private static final String SELECT_ALL_QUERY = "SELECT * FROM ordertable";
	private static final String SELECT_BY_USER_QUERY = "SELECT * FROM ordertable WHERE userId=?";

	@Override
	public void addOrder(Order o) {
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, o.getUserId());
			pstmt.setInt(2, o.getRestaurantId());
			pstmt.setTimestamp(3, o.getOrderDate());
			pstmt.setDouble(4, o.getTotalAmount());
			pstmt.setString(5, o.getStatus());
			pstmt.setString(6, o.getPaymentMode());

			int i = pstmt.executeUpdate();
			System.out.println(i);
			ResultSet keys = pstmt.getGeneratedKeys();
			if (keys.next()) {
				o.setOrderId(keys.getInt(1));
			}
			con.commit();
		} catch (SQLException e) {
			try {
				if (con != null) {
					con.rollback();
				}
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	@Override
	public void updateOrder(Order o) {
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(UPDATE_QUERY);
			pstmt.setInt(1, o.getUserId());
			pstmt.setInt(2, o.getRestaurantId());
			pstmt.setTimestamp(3, o.getOrderDate());
			pstmt.setDouble(4, o.getTotalAmount());
			pstmt.setString(5, o.getStatus());
			pstmt.setString(6, o.getPaymentMode());
			pstmt.setInt(7, o.getOrderId());

			int i = pstmt.executeUpdate();
			System.out.println(i);
			con.commit();
		} catch (SQLException e) {
			try {
				if (con != null) {
					con.rollback();
				}
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	@Override
	public void deleteOrder(int id) {
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(DELETE_QUERY);
			pstmt.setInt(1, id);

			int i = pstmt.executeUpdate();
			System.out.println(i);
			con.commit();
		} catch (SQLException e) {
			try {
				if (con != null) {
					con.rollback();
				}
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	@Override
	public Order getOrder(int id) {
		Order o = null;
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(SELECT_QUERY);
			pstmt.setInt(1, id);

			ResultSet res = pstmt.executeQuery();
			if (res.next()) {
				o = getOrderFromResultSet(res);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return o;
	}

	@Override
	public List<Order> getAllOrders() {
		List<Order> list = new ArrayList<>();
		Connection con = DBConnection.getConnection();
		try {
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery(SELECT_ALL_QUERY);
			while (res.next()) {
				Order o = getOrderFromResultSet(res);
				list.add(o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Order> getOrdersByUser(int userId) {
		List<Order> list = new ArrayList<>();
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(SELECT_BY_USER_QUERY);
			pstmt.setInt(1, userId);

			ResultSet res = pstmt.executeQuery();
			while (res.next()) {
				Order o = getOrderFromResultSet(res);
				list.add(o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	private Order getOrderFromResultSet(ResultSet res) throws SQLException {
		int orderId = res.getInt("orderId");
		int userId = res.getInt("userId");
		int restaurantId = res.getInt("restaurantId");
		Timestamp orderDate = res.getTimestamp("orderDate");
		double totalAmount = res.getDouble("totalAmount");
		String status = res.getString("status");
		String paymentMethod = res.getString("paymentMethod");

		return new Order(orderId, userId, restaurantId, orderDate, totalAmount, status, paymentMethod);
	}
}
