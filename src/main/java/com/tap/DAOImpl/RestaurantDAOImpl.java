package com.tap.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.RestaurantDAO;
import com.tap.model.Restaurant;
import com.tap.utility.DBConnection;

public class RestaurantDAOImpl implements RestaurantDAO {
	private static final String INSERT_QUERY = "INSERT INTO restaurant(name, cuisineType, deliveryTime, address, rating, isActive, imagePath) VALUES(?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_QUERY = "UPDATE restaurant SET name=?, cuisineType=?, deliveryTime=?, address=?, rating=?, isActive=?, imagePath=? WHERE restaurantId=?";
	private static final String DELETE_QUERY = "DELETE FROM restaurant WHERE restaurantId=?";
	private static final String SELECT_QUERY = "SELECT * FROM restaurant WHERE restaurantId=?";
	private static final String SELECT_ALL_QUERY = "SELECT * FROM restaurant";

	@Override
	public void addRestaurant(Restaurant r) {
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(INSERT_QUERY);
			pstmt.setString(1, r.getName());
			pstmt.setString(2, r.getCuisineType());
			pstmt.setInt(3, r.getDeliveryTime());
			pstmt.setString(4, r.getAddress());
			pstmt.setDouble(5, r.getRating());
			pstmt.setBoolean(6, r.getIsActive());
			pstmt.setString(7, r.getImagePath());

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
	public void updateRestaurant(Restaurant r) {
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(UPDATE_QUERY);
			pstmt.setString(1, r.getName());
			pstmt.setString(2, r.getCuisineType());
			pstmt.setInt(3, r.getDeliveryTime());
			pstmt.setString(4, r.getAddress());
			pstmt.setDouble(5, r.getRating());
			pstmt.setBoolean(6, r.getIsActive());
			pstmt.setString(7, r.getImagePath());
			pstmt.setInt(8, r.getRestaurantId());

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
	public void deleteRestaurant(int id) {
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
	public Restaurant getRestaurant(int id) {
		Restaurant r = null;
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(SELECT_QUERY);
			pstmt.setInt(1, id);

			ResultSet res = pstmt.executeQuery();
			if (res.next()) {
				r = getRestaurantFromResultSet(res);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public List<Restaurant> getAllRestaurants() {
		List<Restaurant> list = new ArrayList<>();
		Connection con = DBConnection.getConnection();
		try {
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery(SELECT_ALL_QUERY);
			while (res.next()) {
				Restaurant r = getRestaurantFromResultSet(res);
				list.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	private Restaurant getRestaurantFromResultSet(ResultSet res) throws SQLException {
		int restaurantId = res.getInt("restaurantId");
		String name = res.getString("name");
		String cuisineType = res.getString("cuisineType");
		int deliveryTime = res.getInt("deliveryTime");
		String address = res.getString("address");
		double rating = res.getDouble("rating");
		boolean isActive = res.getBoolean("isActive");
		String imagePath = res.getString("imagePath");
		return new Restaurant(restaurantId, name, cuisineType, deliveryTime, address, rating, isActive, imagePath);
	}
}
