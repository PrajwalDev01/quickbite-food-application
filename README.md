# 🍔 QuickBite

![Project Stage](https://img.shields.io/badge/Project%20Stage-Completed-brightgreen?style=for-the-badge)
![Architecture](https://img.shields.io/badge/Architecture-MVC%20Pattern-blue?style=for-the-badge)
![Tech Stack](https://img.shields.io/badge/Tech%20Stack-Java%20EE%20%2F%20JSP%20%2F%20Servlets-orange?style=for-the-badge)
![Database](https://img.shields.io/badge/Database-MySQL-blue?style=for-the-badge)
![Web Server](https://img.shields.io/badge/Web%20Server-Apache%20Tomcat%2010.x-red?style=for-the-badge)

QuickBite is a dynamic, full-stack online food ordering and delivery web application built using Java Jakarta EE (Servlets & JSP) and MySQL database. It supports secure user registration, restaurant browsing, interactive cart operations, checkout processing, and real-time-simulated order history tracking.

---

## 🚀 Features

- **User Authentication:** 
  - Register with custom roles (Customer, Restaurant Owner, Delivery Partner, Admin).
  - Secure login with passwords encrypted using **BCrypt** hashing.
- **Dynamic Restaurant Directory:**
  - Browse available restaurants with delivery times, ratings, and operational status.
- **Interactive Menu Systems:**
  - View individual restaurant menu items, description, availability, and pricing.
- **Cart Management:**
  - Add items from a single restaurant to the cart.
  - Update quantities or remove items dynamically.
- **Checkout & Payment:**
  - Place orders with payment methods (COD, CARD, UPI).
- **Order History:**
  - View past orders and current order statuses.
  - Includes a simulated real-time transition logic (e.g., transition from *Pending* to *Delivered* after 2 minutes).

---

## 🛠️ Tech Stack

- **Backend:** Java (JDK 17/21), Jakarta Servlets, JDBC
- **Frontend:** JavaServer Pages (JSP), HTML5, CSS3, JavaScript
- **Security:** jBCrypt-0.4 (Blowfish password hashing)
- **Database:** MySQL 8.x / 9.x
- **Web Server:** Apache Tomcat 10.x (Jakarta namespace support)
- **IDE:** Eclipse IDE for Enterprise Java Web Developers

---

## 📂 Project Structure

```
QuickBite/
├── src/
│   └── main/
│       ├── java/
│       │   ├── com/
│       │   │   ├── Cart/Servlet/        # Cart Management Servlets
│       │   │   ├── Login/Servlet/       # Authentication Servlets
│       │   │   ├── Menu/Servlet/        # Menu Display Servlets
│       │   │   ├── OrderHistory/Servlet/ # History and Simulator Servlets
│       │   │   ├── Ordersuccess/Servlet/# Checkout & Success Servlets
│       │   │   ├── Register/Servlet/    # Registration Servlets
│       │   │   ├── Restaurant/Servlet/  # Homepage & Restaurant Servlets
│       │   │   └── tap/                 # Core DAO, DAOImpl, Models, Utility
│       └── webapp/
│           ├── css/                     # Styling stylesheets
│           ├── Images/                  # Food and Restaurant media assets
│           ├── WEB-INF/                 # Deployment descriptor (web.xml) and libs
│           └── *.jsp / *.html           # Dynamic & Static templates
├── lib/                                 # Project JAR dependencies
└── README.md
```

---

## 💾 Database Setup

The project connects to a MySQL database schema named `instant_food`.

### Table Schemas

1. **User Table:**
   ```sql
   CREATE TABLE user (
       userid INT PRIMARY KEY AUTO_INCREMENT,
       userName VARCHAR(100) UNIQUE NOT NULL,
       password VARCHAR(255) NOT NULL,
       email VARCHAR(100) UNIQUE NOT NULL,
       address TEXT,
       role VARCHAR(50) DEFAULT 'CUSTOMER',
       createddate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       lastlogindate TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
   );
   ```

2. **Restaurant Table:**
   ```sql
   CREATE TABLE restaurant (
       restaurantId INT PRIMARY KEY AUTO_INCREMENT,
       name VARCHAR(150) NOT NULL,
       cuisineType VARCHAR(100),
       deliveryTime INT,
       address TEXT,
       rating DOUBLE,
       isActive BOOLEAN DEFAULT TRUE,
       imagePath VARCHAR(255)
   );
   ```

3. **Menu Table:**
   ```sql
   CREATE TABLE menu (
       menuId INT PRIMARY KEY AUTO_INCREMENT,
       restaurantId INT,
       itemName VARCHAR(150) NOT NULL,
       description TEXT,
       price DOUBLE NOT NULL,
       isAvailable BOOLEAN DEFAULT TRUE,
       imagePath VARCHAR(255),
       FOREIGN KEY (restaurantId) REFERENCES restaurant(restaurantId) ON DELETE CASCADE
   );
   ```

4. **Order Table:**
   ```sql
   CREATE TABLE ordertable (
       orderId INT PRIMARY KEY AUTO_INCREMENT,
       userId INT,
       restaurantId INT,
       orderDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       totalAmount DOUBLE NOT NULL,
       status VARCHAR(50) DEFAULT 'pending',
       paymentMode VARCHAR(50),
       FOREIGN KEY (userId) REFERENCES user(userid) ON DELETE CASCADE,
       FOREIGN KEY (restaurantId) REFERENCES restaurant(restaurantId) ON DELETE CASCADE
   );
   ```

5. **Order Item Table:**
   ```sql
   CREATE TABLE orderitem (
       orderItemId INT PRIMARY KEY AUTO_INCREMENT,
       orderId INT,
       menuId INT,
       quantity INT NOT NULL,
       itemTotal DOUBLE NOT NULL,
       FOREIGN KEY (orderId) REFERENCES ordertable(orderId) ON DELETE CASCADE,
       FOREIGN KEY (menuId) REFERENCES menu(menuId) ON DELETE CASCADE
   );
   ```

---

## 🏃 Setup & Execution

### 1. Import to Eclipse
1. Open Eclipse IDE.
2. Go to **File -> Import -> Existing Projects into Workspace**.
3. Select the `QuickBite` directory as the root.
4. Ensure target runtime is set to **Apache Tomcat v10.x**.

### 2. Configure Libraries
Ensure the following JAR files are placed inside `src/main/webapp/WEB-INF/lib/` or in the build path:
- `mysql-connector-j-9.2.0.jar`
- `jbcrypt-0.4.jar`

### 3. Database Connection
Update your database configuration inside the connection utility class (`DBConnection.java` or utility config) with your local MySQL credentials:
- **URL:** `jdbc:mysql://localhost:3306/instant_food`
- **Username:** `root`
- **Password:** `<your_mysql_password>`

### 4. Seed the Database
Run the `com.tap.Main.java` class as a **Java Application** to clear old table entries and seed the database with real Bengaluru restaurants, menus, and sample orders.

### 5. Start the Server
1. Right-click on the project in Eclipse.
2. Select **Run As -> Run on Server**.
3. Choose **Apache Tomcat v10.x** server.
4. Open your browser and navigate to `http://localhost:8080/QuickBite/login.html` (or the URL assigned by your server).
