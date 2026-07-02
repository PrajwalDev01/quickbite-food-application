package com.tap.DAO;




import java.util.List;

import com.tap.model.User;

public interface UserDAO {
    int addUser(User u);
    void updateUser(User u);
    void deleteUser(int id);
    User getUser(int id);
    List<User> getAllUser();
    User getUserByUserName(String userName);
}
