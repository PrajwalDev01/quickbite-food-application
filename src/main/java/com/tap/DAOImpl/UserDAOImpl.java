package com.tap.DAOImpl;


import java.net.UnknownServiceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.UserDAO;
import com.tap.model.User;
import com.tap.utility.DBConnection;

public class UserDAOImpl implements UserDAO {
	private static final String INSERT_QUERY= "INSERT INTO user(userName,password,email,address,role,"
			+ "createddate,lastlogindate) VALUES(?,?,?,?,?,?,?)";
private static final String SELECT_QUERY="SELECT *FROM user WHERE userid=? ";
	private static final String UPDATE_QUERY="UPDATE USER  SET userName =?, password=?, "
			+ "email=?,address=?, lastlogindate=? WHERE userid=?";
private static final String SELECT_QUERY02="SELECT * FROM user";
private static final String DELECT_QUERY="DELETE FROM user"
		+ " WHERE userid = ?";



@Override
public int addUser(User u) {
    int i = 0;
    try (Connection con = DBConnection.getConnection();
         PreparedStatement pstmt = con.prepareStatement(INSERT_QUERY)) {

        pstmt.setString(1, u.getUserName());
        pstmt.setString(2, u.getPassword()); // hashed password
        pstmt.setString(3, u.getEmail());
        pstmt.setString(4, u.getAddress());
        pstmt.setString(5, u.getRole());

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        pstmt.setTimestamp(6, currentTimestamp);
        pstmt.setTimestamp(7, currentTimestamp);

        i = pstmt.executeUpdate();

        // Commit only if auto-commit is disabled
        if (!con.getAutoCommit()) {
            con.commit();
        }

        System.out.println("Inserted rows: " + i);

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return i;
}





	@Override
	public void updateUser(User u) {
	Connection con=	DBConnection.getConnection();
		try {
			PreparedStatement pstmt=con.prepareStatement(UPDATE_QUERY);
		
			 pstmt.setString(1,u.getUserName());
			 pstmt.setString(2,u.getPassword());
			 pstmt.setString(3,u.getEmail());
			 pstmt.setString(4,u.getAddress());
			 pstmt.setTimestamp(5,  new Timestamp(System.currentTimeMillis()));
		     pstmt.setInt(6,u.getUserid() );
		int i=     pstmt.executeUpdate();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void deleteUser(int id) {
		User u=null;
		
		
		Connection con=DBConnection.getConnection();
		try {
			PreparedStatement pstmt=con.prepareStatement(DELECT_QUERY);
			 
		     pstmt.setInt(1,id );
		int i=     pstmt.executeUpdate();
		System.out.println(i);
		con.commit();
		
		
		
//		return u;
//			
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
	public User getUser(int id) {
		User u = null;
		Connection con=	DBConnection.getConnection();
		try {
			PreparedStatement pstmt=con.prepareStatement(SELECT_QUERY);
			pstmt.setInt(1,id);
			
		ResultSet res=	pstmt.executeQuery();
		while(res.next())
		{			
		 u=	getUserBtResultSet(res);
		}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return u;
	}

	

	@Override
	public List<User> getAllUser() {
//		User u=null;
	List<User> list=	new ArrayList<User>();
	
	
	Connection con=	DBConnection.getConnection();

		try {
			Statement stmt	= con.createStatement();
		ResultSet res= stmt.executeQuery(SELECT_QUERY02);
		while(res.next()) {
			User u=getUserBtResultSet(res);
			list.add(u);
		}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	

		return list;
	}


	@Override
	public User getUserByUserName(String userName) {
		User u = null;
		Connection con = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet res = null;
		try {
			String sql = "SELECT * FROM user WHERE userName = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userName);
			res = pstmt.executeQuery();
			if (res.next()) {
				u = getUserBtResultSet(res);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { if (res != null) res.close(); } catch (Exception e) {}
			try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
		}
		return u;
	}

 private static User getUserBtResultSet(ResultSet res) throws SQLException{
	  int userid =res.getInt("userid");
		String userName=res.getString("userName");
		String password=res.getString("password");
		String  email=res.getString("email");
		String address=res.getString("address");
		String role=res.getString("role");
		Timestamp createddate= res.getTimestamp("createddate");
		Timestamp lastloginDate= res.getTimestamp("lastlogindate");
	User	 u=new User(userid,userName, password, email, address, role, createddate, lastloginDate);
return u;
  }

  
  
}
