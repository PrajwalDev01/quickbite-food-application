package com.tap;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import com.tap.DAO.MenuDAO;
import com.tap.DAO.OrderItemDAO;
import com.tap.DAO.OrderTableDAO;
import com.tap.DAO.RestaurantDAO;
import com.tap.DAO.UserDAO;
import com.tap.DAOImpl.MenuDAOImpl;
import com.tap.DAOImpl.OrderDAOImpl;
import com.tap.DAOImpl.OrderTableDAOImpl;
import com.tap.DAOImpl.RestaurantDAOImpl;
import com.tap.DAOImpl.UserDAOImpl;
import com.tap.model.Menu;
import com.tap.model.OrderItem;
import com.tap.model.Order;
import com.tap.model.Restaurant;
import com.tap.model.User;
import com.tap.utility.DBConnection;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== QuickBite Bengaluru Restaurants Seeder ===");

        Connection con = DBConnection.getConnection();
        if (con == null) {
            System.err.println("CRITICAL: Database connection could not be established!");
            return;
        }

        try {
            // 1. Clear existing data in correct dependency order (EXCEPT user table)
            System.out.println("\nClearing old records from database (excluding user table)...");
            Statement stmt = con.createStatement();
            
            // Temporarily disable foreign key constraints to clean tables
            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            stmt.executeUpdate("TRUNCATE TABLE orderitem");
            stmt.executeUpdate("TRUNCATE TABLE ordertable");
            stmt.executeUpdate("TRUNCATE TABLE menu");
            stmt.executeUpdate("TRUNCATE TABLE restaurant");
            stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
            con.commit();
            System.out.println("Tables cleared (user table preserved).");

            // 2. Initialize DAOs
            UserDAO userDAO = new UserDAOImpl();
            RestaurantDAO restaurantDAO = new RestaurantDAOImpl();
            MenuDAO menuDAO = new MenuDAOImpl();
            OrderTableDAO orderDAO = new OrderTableDAOImpl();
            OrderItemDAO orderItemDAO = new OrderDAOImpl();

            // 3. Fetch/Create User to link orders
            List<User> users = userDAO.getAllUser();
            if (users.isEmpty()) {
                System.out.println("\nNo users found in database. Creating a default user...");
                User defaultUser = new User("Bengaluru Foodie", "pass123", "foodie@bengaluru.com", "Indiranagar, Bengaluru", "CUSTOMER");
                userDAO.addUser(defaultUser);
                users = userDAO.getAllUser();
            }
            User activeUser = users.get(0);
            System.out.println("Using User for sample orders: " + activeUser.getUserName() + " (ID: " + activeUser.getUserid() + ")");

            // 4. Insert Real Bengaluru Restaurants
            System.out.println("\nInserting Bengaluru Restaurants...");
            
            Restaurant mtr = new Restaurant("MTR - Mavalli Tiffin Room", "South Indian", 25, "Lalbagh Road, Richmond Town, Bengaluru", 4.7, true, "Images/mtr.jpg");
            Restaurant truffles = new Restaurant("Truffles", "Burgers & Cafe", 30, "Koramangala 4th Block, Bengaluru", 4.5, true, "Images/Tuffles.png");
            Restaurant nagarjuna = new Restaurant("Nagarjuna", "Andhra", 35, "Double Road, Indiranagar, Bengaluru", 4.6, true, "Images/Nagarjun.png");
            Restaurant cornerHouse = new Restaurant("Corner House Ice Creams", "Desserts", 15, "Jayanagar 4th Block, Bengaluru", 4.8, true, "Images/cornerhouse.png");
            Restaurant toit = new Restaurant("Toit Brewpub", "Pizza & Italian", 40, "100 Feet Road, Indiranagar, Bengaluru", 4.7, true, "Images/toit.png");
            Restaurant karavalli = new Restaurant("Karavalli @ Taj", "Coastal & Indian", 50, "Residency Road, Bengaluru", 4.8, true, "Images/Karavalli.png");
            Restaurant bygBrewski = new Restaurant("Byg Brewski Brewing Company", "Multi-Cuisine & Brewery", 45, "Sarjapur Road, Bengaluru", 4.6, true, "Images/BYG.png");
            Restaurant ctr = new Restaurant("CTR (Shri Sagar)", "South Indian", 20, "Malleshwaram, Bengaluru", 4.7, true, "Images/CTR.png");
            Restaurant shiro = new Restaurant("Shiro", "Japanese & Asian", 55, "UB City, Bengaluru", 4.5, true, "Images/Shro.png");
            Restaurant hardRock = new Restaurant("Hard Rock Cafe", "American & Bar", 40, "St. Marks Road, Bengaluru", 4.3, true, "Images/HArdROCK.png");

            restaurantDAO.addRestaurant(mtr);
            restaurantDAO.addRestaurant(truffles);
            restaurantDAO.addRestaurant(nagarjuna);
            restaurantDAO.addRestaurant(cornerHouse);
            restaurantDAO.addRestaurant(toit);
            restaurantDAO.addRestaurant(karavalli);
            restaurantDAO.addRestaurant(bygBrewski);
            restaurantDAO.addRestaurant(ctr);
            restaurantDAO.addRestaurant(shiro);
            restaurantDAO.addRestaurant(hardRock);

            // Fetch generated Restaurant IDs
            List<Restaurant> dbRestaurants = restaurantDAO.getAllRestaurants();
            Restaurant dbMtr = findRestaurantByName(dbRestaurants, "MTR - Mavalli Tiffin Room");
            Restaurant dbTruffles = findRestaurantByName(dbRestaurants, "Truffles");
            Restaurant dbNagarjuna = findRestaurantByName(dbRestaurants, "Nagarjuna");
            Restaurant dbCornerHouse = findRestaurantByName(dbRestaurants, "Corner House Ice Creams");
            Restaurant dbToit = findRestaurantByName(dbRestaurants, "Toit Brewpub");
            Restaurant dbKaravalli = findRestaurantByName(dbRestaurants, "Karavalli @ Taj");
            Restaurant dbByg = findRestaurantByName(dbRestaurants, "Byg Brewski Brewing Company");
            Restaurant dbCtr = findRestaurantByName(dbRestaurants, "CTR (Shri Sagar)");
            Restaurant dbShiro = findRestaurantByName(dbRestaurants, "Shiro");
            Restaurant dbHardRock = findRestaurantByName(dbRestaurants, "Hard Rock Cafe");

            System.out.println("Bengaluru Restaurants inserted.");

            // 5. Insert Menus for Bengaluru Restaurants
            System.out.println("\nInserting Menu Items...");
            
            // Restaurant 1: Byg Brewski menus (under dbMtr / ID 1)
            menuDAO.addMenu(new Menu(dbMtr.getRestaurantId(), "Craft Beer Sampler", "Four house-brewed craft beers served in tasting glasses", 499.00, true, "images/byg_brewski_beer_sampler.jpg"));
            menuDAO.addMenu(new Menu(dbMtr.getRestaurantId(), "Chicken Wings", "Crispy wings tossed in spicy sauce", 349.00, true, "images/byg_brewski_chicken_wings.jpg"));
            menuDAO.addMenu(new Menu(dbMtr.getRestaurantId(), "Paneer Tikka", "Marinated paneer cubes grilled to perfection", 299.00, true, "images/byg_brewski_paneer_tikka.jpg"));
            menuDAO.addMenu(new Menu(dbMtr.getRestaurantId(), "Loaded Fries", "French fries topped with cheese, jalapeños, and salsa", 249.00, true, "images/byg_brewski_loaded_fries.jpg"));
            menuDAO.addMenu(new Menu(dbMtr.getRestaurantId(), "Veg Pizza", "Thin-crust pizza with fresh vegetables and mozzarella", 399.00, true, "images/byg_brewski_veg_pizza.jpg"));
            menuDAO.addMenu(new Menu(dbMtr.getRestaurantId(), "BBQ Chicken Pizza", "Smoky BBQ chicken with onions and peppers", 459.00, true, "images/byg_brewski_bbq_chicken_pizza.jpg"));
            menuDAO.addMenu(new Menu(dbMtr.getRestaurantId(), "Caesar Salad", "Romaine lettuce with Caesar dressing and croutons", 279.00, true, "images/byg_brewski_caesar_salad.jpg"));
            menuDAO.addMenu(new Menu(dbMtr.getRestaurantId(), "Grilled Fish", "Seasoned fish fillet served with lemon butter sauce", 499.00, true, "images/byg_brewski_grilled_fish.jpg"));
            menuDAO.addMenu(new Menu(dbMtr.getRestaurantId(), "Brownie with Ice Cream", "Warm brownie topped with vanilla ice cream", 229.00, true, "images/byg_brewski_brownie.jpg"));
            menuDAO.addMenu(new Menu(dbMtr.getRestaurantId(), "Mojito", "Refreshing cocktail with mint, lime, and soda", 299.00, true, "images/byg_brewski_mojito.jpg"));

            // Restaurant 2: CTR (Shri Sagar) menus (under dbTruffles / ID 2)
            menuDAO.addMenu(new Menu(dbTruffles.getRestaurantId(), "Masala Dosa", "Crispy dosa filled with spiced potato masala", 80.00, true, "images/ctr_masala_dosa.jpg"));
            menuDAO.addMenu(new Menu(dbTruffles.getRestaurantId(), "Plain Dosa", "Classic dosa served with chutney and sambar", 70.00, true, "images/ctr_plain_dosa.jpg"));
            menuDAO.addMenu(new Menu(dbTruffles.getRestaurantId(), "Idli Vada Combo", "Soft idlis and crispy vada served with chutney", 60.00, true, "images/ctr_idli_vada.jpg"));
            menuDAO.addMenu(new Menu(dbTruffles.getRestaurantId(), "Kesari Bath", "Sweet semolina dish flavored with saffron", 50.00, true, "images/ctr_kesari_bath.jpg"));
            menuDAO.addMenu(new Menu(dbTruffles.getRestaurantId(), "Khara Bath", "Savory semolina upma with spices", 50.00, true, "images/ctr_khara_bath.jpg"));
            menuDAO.addMenu(new Menu(dbTruffles.getRestaurantId(), "Filter Coffee", "Strong South Indian filter coffee", 40.00, true, "images/ctr_filter_coffee.jpg"));
            menuDAO.addMenu(new Menu(dbTruffles.getRestaurantId(), "Rava Idli", "Steamed semolina idlis served with chutney", 55.00, true, "images/ctr_rava_idli.jpg"));
            menuDAO.addMenu(new Menu(dbTruffles.getRestaurantId(), "Set Dosa", "Soft, fluffy dosas served in a set of three", 65.00, true, "images/ctr_set_dosa.jpg"));
            menuDAO.addMenu(new Menu(dbTruffles.getRestaurantId(), "Bisi Bele Bath", "Spiced rice and lentil dish", 70.00, true, "images/ctr_bisi_bele_bath.jpg"));
            menuDAO.addMenu(new Menu(dbTruffles.getRestaurantId(), "Poori Sagu", "Fluffy pooris served with vegetable curry", 75.00, true, "images/ctr_poori_sagu.jpg"));

            // Restaurant 3: Shiro Japanese & Asian menus (under dbNagarjuna / ID 3)
            menuDAO.addMenu(new Menu(dbNagarjuna.getRestaurantId(), "Sushi Platter", "Assorted sushi rolls with wasabi and soy sauce", 699.00, true, "images/shiro_sushi_platter.jpg"));
            menuDAO.addMenu(new Menu(dbNagarjuna.getRestaurantId(), "Ramen Bowl", "Noodles in broth with pork, egg, and vegetables", 499.00, true, "images/shiro_ramen.jpg"));
            menuDAO.addMenu(new Menu(dbNagarjuna.getRestaurantId(), "Dim Sum Basket", "Steamed dumplings and bao buns", 399.00, true, "images/shiro_dim_sum.jpg"));
            menuDAO.addMenu(new Menu(dbNagarjuna.getRestaurantId(), "Tempura Prawns", "Crispy battered prawns with dipping sauce", 459.00, true, "images/shiro_tempura.jpg"));
            menuDAO.addMenu(new Menu(dbNagarjuna.getRestaurantId(), "Matcha Dessert", "Matcha ice cream and cake with red bean topping", 349.00, true, "images/shiro_matcha_dessert.jpg"));
            menuDAO.addMenu(new Menu(dbNagarjuna.getRestaurantId(), "Teriyaki Chicken", "Grilled chicken glazed with teriyaki sauce", 429.00, true, "images/shiro_teriyaki_chicken.jpg"));
            menuDAO.addMenu(new Menu(dbNagarjuna.getRestaurantId(), "Miso Soup", "Traditional Japanese soup with tofu and seaweed", 199.00, true, "images/shiro_miso_soup.jpg"));
            menuDAO.addMenu(new Menu(dbNagarjuna.getRestaurantId(), "Pad Thai", "Thai stir-fried noodles with shrimp and peanuts", 449.00, true, "images/shiro_pad_thai.jpg"));
            menuDAO.addMenu(new Menu(dbNagarjuna.getRestaurantId(), "Spring Rolls", "Crispy rolls filled with vegetables", 299.00, true, "images/shiro_spring_rolls.jpg"));
            menuDAO.addMenu(new Menu(dbNagarjuna.getRestaurantId(), "Green Tea", "Hot Japanese green tea", 149.00, true, "images/shiro_green_tea.jpg"));

            // Restaurant 4: Truffles (American Café) menus (under dbCornerHouse / ID 4)
            menuDAO.addMenu(new Menu(dbCornerHouse.getRestaurantId(), "Cheesy Fries", "Golden fries topped with melted cheese and herbs", 249.00, true, "images/truffles_cheesy_fries.jpg"));
            menuDAO.addMenu(new Menu(dbCornerHouse.getRestaurantId(), "Chicken Burger", "Grilled chicken patty with lettuce and mayo", 349.00, true, "images/truffles_chicken_burger.jpg"));
            menuDAO.addMenu(new Menu(dbCornerHouse.getRestaurantId(), "Veggie Burger", "Vegetable patty with cheese and spicy sauce", 299.00, true, "images/truffles_veggie_burger.jpg"));
            menuDAO.addMenu(new Menu(dbCornerHouse.getRestaurantId(), "Club Sandwich", "Triple-layer sandwich with chicken, egg, and veggies", 329.00, true, "images/truffles_club_sandwich.jpg"));
            menuDAO.addMenu(new Menu(dbCornerHouse.getRestaurantId(), "Pasta Alfredo", "Creamy pasta with chicken and cream sauce", 379.00, true, "images/truffles_pasta_alfredo.jpg"));
            menuDAO.addMenu(new Menu(dbCornerHouse.getRestaurantId(), "Pasta Arrabiata", "Spicy tomato sauce pasta with olives", 359.00, true, "images/truffles_pasta_arrabiata.jpg"));
            menuDAO.addMenu(new Menu(dbCornerHouse.getRestaurantId(), "Chocolate Shake", "Rich chocolate milkshake topped with cream", 199.00, true, "images/truffles_chocolate_shake.jpg"));
            menuDAO.addMenu(new Menu(dbCornerHouse.getRestaurantId(), "Brownie Sundae", "Warm brownie with ice cream and chocolate sauce", 229.00, true, "images/truffles_brownie_sundae.jpg"));
            menuDAO.addMenu(new Menu(dbCornerHouse.getRestaurantId(), "Grilled Chicken Steak", "Juicy chicken steak with pepper sauce", 449.00, true, "images/truffles_chicken_steak.jpg"));
            menuDAO.addMenu(new Menu(dbCornerHouse.getRestaurantId(), "Iced Coffee", "Cold coffee served with ice cream", 179.00, true, "images/truffles_iced_coffee.jpg"));

            // Restaurant 5: Nagarjuna (Andhra Cuisine) menus (under dbToit / ID 5)
            menuDAO.addMenu(new Menu(dbToit.getRestaurantId(), "Andhra Thali", "Traditional thali with rice, curries, and chutneys", 299.00, true, "images/nagarjuna_thali.jpg"));
            menuDAO.addMenu(new Menu(dbToit.getRestaurantId(), "Chicken Biryani", "Spicy Andhra-style chicken biryani", 349.00, true, "images/nagarjuna_chicken_biryani.jpg"));
            menuDAO.addMenu(new Menu(dbToit.getRestaurantId(), "Mutton Biryani", "Rich mutton biryani with spices", 399.00, true, "images/nagarjuna_mutton_biryani.jpg"));
            menuDAO.addMenu(new Menu(dbToit.getRestaurantId(), "Gongura Chicken", "Chicken cooked with tangy gongura leaves", 329.00, true, "images/nagarjuna_gongura_chicken.jpg"));
            menuDAO.addMenu(new Menu(dbToit.getRestaurantId(), "Gongura Mutton", "Mutton curry with gongura leaves", 379.00, true, "images/nagarjuna_gongura_mutton.jpg"));
            menuDAO.addMenu(new Menu(dbToit.getRestaurantId(), "Veg Curry", "Seasonal vegetables cooked Andhra style", 249.00, true, "images/nagarjuna_veg_curry.jpg"));
            menuDAO.addMenu(new Menu(dbToit.getRestaurantId(), "Rasam", "Spicy tamarind-based soup", 99.00, true, "images/nagarjuna_rasam.jpg"));
            menuDAO.addMenu(new Menu(dbToit.getRestaurantId(), "Curd Rice", "Rice mixed with curd and tempered spices", 129.00, true, "images/nagarjuna_curd_rice.jpg"));
            menuDAO.addMenu(new Menu(dbToit.getRestaurantId(), "Chicken Fry", "Spicy fried chicken pieces", 299.00, true, "images/nagarjuna_chicken_fry.jpg"));
            menuDAO.addMenu(new Menu(dbToit.getRestaurantId(), "Mutton Fry", "Dry-fried mutton with spices", 349.00, true, "images/nagarjuna_mutton_fry.jpg"));

            // Restaurant 6: Corner House Ice Creams menus (under dbKaravalli / ID 6)
            menuDAO.addMenu(new Menu(dbKaravalli.getRestaurantId(), "Death by Chocolate", "Signature dessert with cake, ice cream, and fudge", 249.00, true, "images/cornerhouse_dbc.jpg"));
            menuDAO.addMenu(new Menu(dbKaravalli.getRestaurantId(), "Hot Chocolate Fudge", "Vanilla ice cream topped with hot fudge sauce", 199.00, true, "images/cornerhouse_hcf.jpg"));
            menuDAO.addMenu(new Menu(dbKaravalli.getRestaurantId(), "Fruit Salad with Ice Cream", "Fresh fruits served with ice cream", 179.00, true, "images/cornerhouse_fruit_salad.jpg"));
            menuDAO.addMenu(new Menu(dbKaravalli.getRestaurantId(), "Banana Split", "Banana with scoops of ice cream and toppings", 229.00, true, "images/cornerhouse_banana_split.jpg"));
            menuDAO.addMenu(new Menu(dbKaravalli.getRestaurantId(), "Strawberry Sundae", "Strawberry ice cream with syrup and nuts", 199.00, true, "images/cornerhouse_strawberry_sundae.jpg"));
            menuDAO.addMenu(new Menu(dbKaravalli.getRestaurantId(), "Chocolate Sundae", "Chocolate ice cream with fudge and nuts", 199.00, true, "images/cornerhouse_choco_sundae.jpg"));
            menuDAO.addMenu(new Menu(dbKaravalli.getRestaurantId(), "Butterscotch Sundae", "Butterscotch ice cream with caramel sauce", 199.00, true, "images/cornerhouse_butterscotch_sundae.jpg"));
            menuDAO.addMenu(new Menu(dbKaravalli.getRestaurantId(), "Black Forest Sundae", "Layers of cake, ice cream, and cherries", 229.00, true, "images/cornerhouse_blackforest.jpg"));
            menuDAO.addMenu(new Menu(dbKaravalli.getRestaurantId(), "Fruit Bowl", "Seasonal fruits served chilled", 149.00, true, "images/cornerhouse_fruit_bowl.jpg"));
            menuDAO.addMenu(new Menu(dbKaravalli.getRestaurantId(), "Milkshake", "Thick milkshake with ice cream", 179.00, true, "images/cornerhouse_milkshake.jpg"));

            // Restaurant 7: Toit Brewpub menus (under dbByg / ID 7)
            menuDAO.addMenu(new Menu(dbByg.getRestaurantId(), "Craft Beer Pint", "House-brewed craft beer served chilled", 299.00, true, "images/toit_beer_pint.jpg"));
            menuDAO.addMenu(new Menu(dbByg.getRestaurantId(), "Beer Sampler", "Four craft beers served in tasting glasses", 499.00, true, "images/toit_beer_sampler.jpg"));
            menuDAO.addMenu(new Menu(dbByg.getRestaurantId(), "Chicken Wings", "Spicy wings served with dip", 349.00, true, "images/toit_chicken_wings.jpg"));
            menuDAO.addMenu(new Menu(dbByg.getRestaurantId(), "Veg Nachos", "Tortilla chips with cheese and salsa", 299.00, true, "images/toit_veg_nachos.jpg"));
            menuDAO.addMenu(new Menu(dbByg.getRestaurantId(), "Grilled Sausages", "Assorted sausages served with mustard", 399.00, true, "images/toit_sausages.jpg"));
            menuDAO.addMenu(new Menu(dbByg.getRestaurantId(), "Fish Fingers", "Crispy fish fingers with tartar sauce", 349.00, true, "images/toit_fish_fingers.jpg"));
            menuDAO.addMenu(new Menu(dbByg.getRestaurantId(), "Paneer Tikka", "Grilled paneer cubes with spices", 299.00, true, "images/toit_paneer_tikka.jpg"));
            menuDAO.addMenu(new Menu(dbByg.getRestaurantId(), "Chicken Burger", "Grilled chicken burger with fries", 379.00, true, "images/toit_chicken_burger.jpg"));
            menuDAO.addMenu(new Menu(dbByg.getRestaurantId(), "Veg Pizza", "Thin-crust pizza with vegetables", 399.00, true, "images/toit_veg_pizza.jpg"));
            menuDAO.addMenu(new Menu(dbByg.getRestaurantId(), "Brownie with Ice Cream", "Warm brownie topped with ice cream", 229.00, true, "images/toit_brownie.jpg"));

            // Restaurant 8: The Fatty Bao (Asian Fusion) menus (under dbCtr / ID 8)
            menuDAO.addMenu(new Menu(dbCtr.getRestaurantId(), "Pork Bao", "Soft bao bun filled with pork belly", 349.00, true, "images/fattybao_pork_bao.jpg"));
            menuDAO.addMenu(new Menu(dbCtr.getRestaurantId(), "Chicken Bao", "Bao bun filled with spicy chicken", 329.00, true, "images/fattybao_chicken_bao.jpg"));
            menuDAO.addMenu(new Menu(dbCtr.getRestaurantId(), "Veg Bao", "Bao bun filled with vegetables", 299.00, true, "images/fattybao_veg_bao.jpg"));
            menuDAO.addMenu(new Menu(dbCtr.getRestaurantId(), "Ramen Bowl", "Noodles in broth with egg and pork", 499.00, true, "images/fattybao_ramen.jpg"));
            menuDAO.addMenu(new Menu(dbCtr.getRestaurantId(), "Sushi Roll", "Assorted sushi rolls with wasabi", 449.00, true, "images/fattybao_sushi.jpg"));
            menuDAO.addMenu(new Menu(dbCtr.getRestaurantId(), "Dim Sum Basket", "Steamed dumplings served hot", 399.00, true, "images/fattybao_dim_sum.jpg"));
            menuDAO.addMenu(new Menu(dbCtr.getRestaurantId(), "Tempura Veggies", "Crispy battered vegetables", 299.00, true, "images/fattybao_tempura.jpg"));
            menuDAO.addMenu(new Menu(dbCtr.getRestaurantId(), "Teriyaki Chicken", "Chicken glazed with teriyaki sauce", 429.00, true, "images/fattybao_teriyaki_chicken.jpg"));
            menuDAO.addMenu(new Menu(dbCtr.getRestaurantId(), "Matcha Ice Cream", "Japanese green tea ice cream", 249.00, true, "images/fattybao_matcha_icecream.jpg"));
            menuDAO.addMenu(new Menu(dbCtr.getRestaurantId(), "Bubble Tea", "Sweet tea with tapioca pearls", 199.00, true, "images/fattybao_bubble_tea.jpg"));

            // Restaurant 9: Smoke House Deli (Continental) menus (under dbShiro / ID 9)
            menuDAO.addMenu(new Menu(dbShiro.getRestaurantId(), "Grilled Chicken Salad", "Grilled chicken with greens and vinaigrette", 379.00, true, "images/smokehouse_chicken_salad.jpg"));
            menuDAO.addMenu(new Menu(dbShiro.getRestaurantId(), "Veg Salad", "Fresh vegetables with olive oil dressing", 299.00, true, "images/smokehouse_veg_salad.jpg"));
            menuDAO.addMenu(new Menu(dbShiro.getRestaurantId(), "Pasta Alfredo", "Creamy white sauce pasta with garlic and mushrooms", 349.00, true, "images/smokehouse_pasta_alfredo.jpg"));
            menuDAO.addMenu(new Menu(dbShiro.getRestaurantId(), "Pasta Arrabiata", "Spicy tomato sauce pasta with garlic and basil", 329.00, true, "images/smokehouse_pasta_arrabiata.jpg"));
            menuDAO.addMenu(new Menu(dbShiro.getRestaurantId(), "Chicken Steak", "Grilled chicken breast served with pepper sauce and vegetables", 449.00, true, "images/smokehouse_chicken_steak.jpg"));
            menuDAO.addMenu(new Menu(dbShiro.getRestaurantId(), "Fish & Chips", "Crispy battered fish fillet served with fries and tartar sauce", 429.00, true, "images/smokehouse_fish_chips.jpg"));
            menuDAO.addMenu(new Menu(dbShiro.getRestaurantId(), "Margherita Pizza", "Classic pizza with tomato sauce, mozzarella, and fresh basil", 399.00, true, "images/smokehouse_margherita_pizza.jpg"));
            menuDAO.addMenu(new Menu(dbShiro.getRestaurantId(), "Club Sandwich", "Double-decker sandwich with chicken, egg, lettuce, and mayo", 349.00, true, "images/smokehouse_club_sandwich.jpg"));
            menuDAO.addMenu(new Menu(dbShiro.getRestaurantId(), "Tomato Basil Soup", "Rich tomato soup flavored with fresh basil", 199.00, true, "images/smokehouse_tomato_soup.jpg"));
            menuDAO.addMenu(new Menu(dbShiro.getRestaurantId(), "Tiramisu", "Classic Italian dessert with coffee-soaked layers", 249.00, true, "images/smokehouse_tiramisu.jpg"));

            // Restaurant 10: Hard Rock Cafe (American & Bar) menus (under dbHardRock / ID 10)
            menuDAO.addMenu(new Menu(dbHardRock.getRestaurantId(), "Classic Cheeseburger", "Juicy beef patty with melted cheddar, bacon, lettuce, tomato, and house sauce", 499.00, true, "images/hardrock_cheeseburger.jpg"));
            menuDAO.addMenu(new Menu(dbHardRock.getRestaurantId(), "Buffalo Wings", "Crispy chicken wings tossed in spicy buffalo sauce, served with ranch dip", 399.00, true, "images/hardrock_buffalo_wings.jpg"));
            menuDAO.addMenu(new Menu(dbHardRock.getRestaurantId(), "BBQ Pulled Pork Sandwich", "Slow-cooked pork with smoky BBQ sauce on a toasted bun", 459.00, true, "images/hardrock_pulled_pork.jpg"));
            menuDAO.addMenu(new Menu(dbHardRock.getRestaurantId(), "Loaded Nachos", "Tortilla chips topped with cheese, jalapeños, beans, salsa, and sour cream", 349.00, true, "images/hardrock_loaded_nachos.jpg"));
            menuDAO.addMenu(new Menu(dbHardRock.getRestaurantId(), "Grilled Chicken Caesar Salad", "Grilled chicken breast on romaine lettuce with Caesar dressing and croutons", 379.00, true, "images/hardrock_caesar_salad.jpg"));
            menuDAO.addMenu(new Menu(dbHardRock.getRestaurantId(), "Fish & Chips", "Beer-battered fish fillets served with fries and tartar sauce", 429.00, true, "images/hardrock_fish_chips.jpg"));
            menuDAO.addMenu(new Menu(dbHardRock.getRestaurantId(), "Veggie Burger", "Grilled vegetable patty with lettuce, tomato, and spicy mayo", 399.00, true, "images/hardrock_veggie_burger.jpg"));
            menuDAO.addMenu(new Menu(dbHardRock.getRestaurantId(), "BBQ Ribs", "Tender pork ribs glazed with smoky barbecue sauce", 599.00, true, "images/hardrock_bbq_ribs.jpg"));
            menuDAO.addMenu(new Menu(dbHardRock.getRestaurantId(), "Classic Margarita", "Tequila, triple sec, and lime juice served on the rocks", 299.00, true, "images/hardrock_margarita.jpg"));
            menuDAO.addMenu(new Menu(dbHardRock.getRestaurantId(), "Craft Beer Pint", "Locally brewed golden ale served chilled", 249.00, true, "images/hardrock_craft_beer.jpg"));

            // Fetch generated Menu Items
            List<Menu> dbMenus = menuDAO.getAllMenus();
            Menu mtrDosa = findMenuByName(dbMenus, "Masala Dosa");
            Menu mtrCoffee = findMenuByName(dbMenus, "Filter Coffee");
            Menu trufBurger = findMenuByName(dbMenus, "All American Cheese Burger");
            Menu trufFries = findMenuByName(dbMenus, "Peri Peri Fries");
            Menu chDBC = findMenuByName(dbMenus, "Death by Chocolate (DBC)");

            System.out.println("Menu Items inserted.");

            // 6. Insert Orders
            System.out.println("\nInserting sample Orders...");
            
            // Order 1: South Indian breakfast from MTR. Total = 170.00
            Order order1 = new Order(activeUser.getUserid(), dbMtr.getRestaurantId(), new Timestamp(System.currentTimeMillis()), 170.00, "DELIVERED", "COD");
            
            // Order 2: Burger combo from Truffles. Total = 350.00
            Order order2 = new Order(activeUser.getUserid(), dbTruffles.getRestaurantId(), new Timestamp(System.currentTimeMillis()), 350.00, "PREPARING", "UPI");
            
            // Order 3: Dessert from Corner House. Total = 280.00
            Order order3 = new Order(activeUser.getUserid(), dbCornerHouse.getRestaurantId(), new Timestamp(System.currentTimeMillis()), 280.00, "PENDING", "CARD");

            orderDAO.addOrder(order1);
            orderDAO.addOrder(order2);
            orderDAO.addOrder(order3);

            // Fetch generated Orders
            List<Order> dbOrders = orderDAO.getAllOrders();
            Order dbOrder1 = findOrderByUserAndTotal(dbOrders, activeUser.getUserid(), 170.00);
            Order dbOrder2 = findOrderByUserAndTotal(dbOrders, activeUser.getUserid(), 350.00);
            Order dbOrder3 = findOrderByUserAndTotal(dbOrders, activeUser.getUserid(), 280.00);

            System.out.println("Orders inserted.");

            // 7. Insert OrderItems
            System.out.println("\nInserting sample Order Items...");
            
            // Items for Order 1 (MTR)
            orderItemDAO.addOrderItem(new OrderItem(dbOrder1.getOrderId(), mtrDosa.getMenuId(), 1, 110.00));
            orderItemDAO.addOrderItem(new OrderItem(dbOrder1.getOrderId(), mtrCoffee.getMenuId(), 1, 60.00));

            // Items for Order 2 (Truffles)
            orderItemDAO.addOrderItem(new OrderItem(dbOrder2.getOrderId(), trufBurger.getMenuId(), 1, 220.00));
            orderItemDAO.addOrderItem(new OrderItem(dbOrder2.getOrderId(), trufFries.getMenuId(), 1, 130.00));

            // Items for Order 3 (Corner House)
            orderItemDAO.addOrderItem(new OrderItem(dbOrder3.getOrderId(), chDBC.getMenuId(), 1, 280.00));

            System.out.println("Order Items inserted.");

            System.out.println("\n==========================================");
            System.out.println("DATABASE SEEDING COMPLETED SUCCESSFULLY!");
            System.out.println("Preserved User table and seeded Bengaluru content.");
            System.out.println("==========================================");

        } catch (Exception e) {
            System.err.println("\nERROR: Database seeding failed!");
            e.printStackTrace();
        }
    }

    // Helper method to find Restaurant by name
    private static Restaurant findRestaurantByName(List<Restaurant> list, String name) {
        for (Restaurant r : list) {
            if (r.getName().equalsIgnoreCase(name)) {
                return r;
            }
        }
        throw new RuntimeException("Restaurant not found for name: " + name);
    }

    // Helper method to find Menu by name
    private static Menu findMenuByName(List<Menu> list, String name) {
        for (Menu m : list) {
            if (m.getItemName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        throw new RuntimeException("Menu not found for name: " + name);
    }

    // Helper method to find OrderTable by userId and totalAmount
    private static Order findOrderByUserAndTotal(List<Order> list, int userId, double totalAmount) {
        for (Order o : list) {
            if (o.getUserId() == userId && Math.abs(o.getTotalAmount() - totalAmount) < 0.01) {
                return o;
            }
        }
        throw new RuntimeException("Order not found for userId: " + userId + " with amount: " + totalAmount);
    }
}
