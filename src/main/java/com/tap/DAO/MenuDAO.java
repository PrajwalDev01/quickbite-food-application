package com.tap.DAO;

import java.util.List;
import com.tap.model.Menu;

public interface MenuDAO {
    void addMenu(Menu m);
    void updateMenu(Menu m);
    void deleteMenu(int id);
    Menu getMenu(int id);
    List<Menu> getAllMenus();
    List<Menu> getMenusByRestaurant(int restaurantId);
}
