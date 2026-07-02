package com.tap.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.OrderItemDAO;
import com.tap.model.OrderItem;
import com.tap.utility.DBConnection;

public class OrderDAOImpl implements OrderItemDAO {
	private static final String INSERT_QUERY = "INSERT INTO orderitem(orderId, menuId, quantity, itemTotal) VALUES(?, ?, ?, ?)";
	private static final String UPDATE_QUERY = "UPDATE orderitem SET orderId=?, menuId=?, quantity=?, itemTotal=? WHERE orderItemId=?";
	private static final String DELETE_QUERY = "DELETE FROM orderitem WHERE orderItemId=?";
	private static final String SELECT_QUERY = "SELECT * FROM orderitem WHERE orderItemId=?";
	private static final String SELECT_ALL_QUERY = "SELECT * FROM orderitem";
	private static final String SELECT_BY_ORDER_QUERY = "SELECT * FROM orderitem WHERE orderId=?";

	@Override
	public void addOrderItem(OrderItem oi) {
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(INSERT_QUERY);
			pstmt.setInt(1, oi.getOrderId());
			pstmt.setInt(2, oi.getMenuId());
			pstmt.setInt(3, oi.getQuantity());
			pstmt.setDouble(4, oi.getItemTotal());

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
	public void updateOrderItem(OrderItem oi) {
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(UPDATE_QUERY);
			pstmt.setInt(1, oi.getOrderId());
			pstmt.setInt(2, oi.getMenuId());
			pstmt.setInt(3, oi.getQuantity());
			pstmt.setDouble(4, oi.getItemTotal());
			pstmt.setInt(5, oi.getOrderItemId());

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
	public void deleteOrderItem(int id) {
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
	public OrderItem getOrderItem(int id) {
		OrderItem oi = null;
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(SELECT_QUERY);
			pstmt.setInt(1, id);

			ResultSet res = pstmt.executeQuery();
			if (res.next()) {
				oi = getOrderItemFromResultSet(res);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return oi;
	}

	@Override
	public List<OrderItem> getAllOrderItems() {
		List<OrderItem> list = new ArrayList<>();
		Connection con = DBConnection.getConnection();
		try {
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery(SELECT_ALL_QUERY);
			while (res.next()) {
				OrderItem oi = getOrderItemFromResultSet(res);
				list.add(oi);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<OrderItem> getOrderItemsByOrder(int orderId) {
		List<OrderItem> list = new ArrayList<>();
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(SELECT_BY_ORDER_QUERY);
			pstmt.setInt(1, orderId);

			ResultSet res = pstmt.executeQuery();
			while (res.next()) {
				OrderItem oi = getOrderItemFromResultSet(res);
				list.add(oi);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	private OrderItem getOrderItemFromResultSet(ResultSet res) throws SQLException {
		int orderItemId = res.getInt("orderItemId");
		int orderId = res.getInt("orderId");
		int menuId = res.getInt("menuId");
		int quantity = res.getInt("quantity");
		double itemTotal = res.getDouble("itemTotal");

		return new OrderItem(orderItemId, orderId, menuId, quantity, itemTotal);
	}
}
