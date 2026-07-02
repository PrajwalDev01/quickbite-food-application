package com.tap.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.MenuDAO;
import com.tap.model.Menu;
import com.tap.utility.DBConnection;

public class MenuDAOImpl implements MenuDAO {
	private static final String INSERT_QUERY = "INSERT INTO menu(restaurantId, itemName, description, price, isAvailable, imagePath) VALUES(?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_QUERY = "UPDATE menu SET restaurantId=?, itemName=?, description=?, price=?, isAvailable=?, imagePath=? WHERE menuId=?";
	private static final String DELETE_QUERY = "DELETE FROM menu WHERE menuId=?";
	private static final String SELECT_QUERY = "SELECT * FROM menu WHERE menuId=?";
	private static final String SELECT_ALL_QUERY = "SELECT * FROM menu";
	private static final String SELECT_BY_RESTAURANT_QUERY = "SELECT * FROM menu WHERE restaurantId=?";

	@Override
	public void addMenu(Menu m) {
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(INSERT_QUERY);
			pstmt.setInt(1, m.getRestaurantId());
			pstmt.setString(2, m.getItemName());
			pstmt.setString(3, m.getDescription());
			pstmt.setDouble(4, m.getPrice());
			pstmt.setBoolean(5, m.getIsAvailable());
			pstmt.setString(6, m.getImagePath());

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
	public void updateMenu(Menu m) {
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(UPDATE_QUERY);
			pstmt.setInt(1, m.getRestaurantId());
			pstmt.setString(2, m.getItemName());
			pstmt.setString(3, m.getDescription());
			pstmt.setDouble(4, m.getPrice());
			pstmt.setBoolean(5, m.getIsAvailable());
			pstmt.setString(6, m.getImagePath());
			pstmt.setInt(7, m.getMenuId());

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
	public void deleteMenu(int id) {
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
	public Menu getMenu(int id) {
		Menu m = null;
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(SELECT_QUERY);
			pstmt.setInt(1, id);

			ResultSet res = pstmt.executeQuery();
			if (res.next()) {
				m = getMenuFromResultSet(res);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}

	@Override
	public List<Menu> getAllMenus() {
		List<Menu> list = new ArrayList<>();
		Connection con = DBConnection.getConnection();
		try {
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery(SELECT_ALL_QUERY);
			while (res.next()) {
				Menu m = getMenuFromResultSet(res);
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Menu> getMenusByRestaurant(int restaurantId) {
		List<Menu> list = new ArrayList<>();
		Connection con = DBConnection.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(SELECT_BY_RESTAURANT_QUERY);
			pstmt.setInt(1, restaurantId);

			ResultSet res = pstmt.executeQuery();
			while (res.next()) {
				Menu m = getMenuFromResultSet(res);
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	private Menu getMenuFromResultSet(ResultSet res) throws SQLException {
		int menuId = res.getInt("menuId");
		int restaurantId = res.getInt("restaurantId");
		String itemName = res.getString("itemName");
		String description = res.getString("description");
		double price = res.getDouble("price");
		boolean isAvailable = res.getBoolean("isAvailable");
		String imagePath = res.getString("imagePath");
		return new Menu(menuId, restaurantId, itemName, description, price, isAvailable, imagePath);
	}
}
