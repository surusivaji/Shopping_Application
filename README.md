<h1 align="center"> 🛍️ Shopping Application</h1>

## 📌 Overview
The **Shopping Application** provides users with a seamless online shopping experience. Users can browse various products, add items to their cart, and make secure purchases. The application supports order and account management, allowing users to track orders, view purchase history, and update their personal details.

### 🛠️ Tech Stack
- **Backend:** 🖥️ Spring MVC, Spring Data JPA  
- **Frontend:** 🎨 HTML, CSS, Bootstrap  
- **Database:** 🗄️ MySQL  
- **Build Tool:** 🛠️ Spring Tool Suite  

## 🚀 Features

### 👤 User Features
- 🔑 **User Registration & Login:** Secure authentication with password reset via email & mobile.
- 🛒 **Product Browsing & Search:** Explore products by category and latest arrivals.
- 🛍️ **Shopping Cart & Orders:** Add products to cart, checkout, and track orders.
- 👤 **Profile Management:** View and update user details.

### 🛠️ Admin Features
- 📊 **Admin Dashboard:** A centralized panel to manage the application.
- 📦 **Product Management:** Add, edit, delete, and view products.
- 📂 **Category Management:** Manage product categories efficiently.
- 👥 **User Management:** View, update, and remove users.
- 🛡️ **Admin Management:** Add, view, edit, and remove admin users.
- 📦 **Order Management:** Track and update order statuses.

## 📐 Application Structure
- 📂 **Model Layer:** Defines entity classes representing database tables.
- 📂 **Repository Layer:** Handles database operations using Spring Data JPA.
- 📂 **Service Layer:** Implements business logic.
- 📂 **Controller Layer:** Manages HTTP requests and responses.
- 🎨 **Frontend:** Built using HTML, CSS, Bootstrap.

## 🛠️ How the Project Works
1. **Users** register and log in securely.
2. They can **browse products**, search by category, and view product details.
3. Users **add products to their cart**, update quantities, and proceed to checkout.
4. Orders are **stored in the database**, and users can track order history.
5. The **Admin Panel** allows administrators to **manage products, categories, users, and orders** efficiently.

## 🛠️ Setup & Installation

### 📋 Prerequisites
- ☕ **Java Development Kit (JDK) 8+**
- ⚙️ **Spring Boot Framework**
- 🗄️ **MySQL Database**
- 🛠️ **Maven**
- 💻 **Spring Tool Suite (STS)**

### ⚙️ Steps to Run the Application

#### 1️⃣ Clone the Repository
```sh
git clone https://github.com/your-username/shopping-application.git
cd shopping-application
```

#### 2️⃣ Configure Database (MySQL)
- Create a database in MySQL:
  ```sql
  CREATE DATABASE shopping_app;
  ```
- Update `application.properties` with MySQL credentials:
  ```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/shopping_app
  spring.datasource.username=root
  spring.datasource.password=yourpassword
  spring.jpa.hibernate.ddl-auto=update
  ```

#### 3️⃣ Build the Project Using Maven
```sh
mvn clean install
```

#### 4️⃣ Run the Spring Boot Application
```sh
mvn spring-boot:run
```

#### 5️⃣ Access the Application
- **User Panel:** `http://localhost:8080/`
- **Admin Panel:** `http://localhost:8080/admin`

## 🖼️ Screenshots
<p>Below are sample screenshots showcasing the Shopping Application:</p>
<div>
  <h4>1. Index Page</h4>
  <img src="index.png" alt="Index Page" width="80%">
  <h4>2. Registration Page</h4>
  <img src="register.png" alt="User Registration Page" width="80%">
   <h4>3. Login Page</h4>
  <img src="login.png" alt="User Login Page" width="80%">
   <h4>4. Forgot Password Page</h4>
  <img src="forgotpassword.png" alt="Forgot Password Page" Page" width="80%">
   <h4>5. Reset Password Page</h4>
  <img src="changepassword.png" alt="Reset Password Page" width="80%">
   <h4>6. Home Page</h4>
  <img src="userhome.png" alt="Home Page" width="80%">
  <h4>7. Products Page</h4>
  <img src="products.png" alt="products Page" width="80%">
   <h4>8. View Product Page</h4>
   <img src="viewproduct.png" alt="view product Page" width="80%">
   <h4>9. View Cart Page</h4>
   <img src="viewcart.png" alt="view cart Page" width="80%">
   <h4>10. Address Page</h4>
   <img src="address.png" alt="Address Page" width="80%">
   <h4>11. Order Success Page</h4>
   <img src="ordersuccess.png" alt="order success" width="80%">
   <h4>12. My Orders Page</h4>
   <img src="myorders.png" alt="Orders Page" width="80%">
   <h4>13. Update Profile Page</h4>
   <img src="updateprofile.png" alt="Update Profile Page" width="80%">
   <h4>14. User Logout Page</h4>
   <img src="userlogout.png" alt="User Logout Page" width="80%">
    <h4>15. Admin Login Page</h4>
    <img src="adminlogin.png" alt="Admin Login Page" width="80%">
    <h4>16. Admin Dashboard Page</h4>
    <img src="admindashboard.png" alt="Admin Login Page" width="80%">
    <h4>17. Add Product Page</h4>
    <img src="addproduct.png" alt="Add Product Page" width="80%">
    <h4>18. Categories Page</h4>
    <img src="categories.png" alt="Categories Page" width="80%">
    <h4>19. Update Category Page</h4>
    <img src="editcategory.png" alt="Edit Category Page" width="80%">
    <h4>20. Delete Category Page</h4>
    <img src="deletecategory.png" alt="Delete Category Page" width="80%">
    <h4>21. Display Products Page</h4>
    <img src="displayproducts.png" alt="Display Products Page" width="80%">
    <h4>22. Edit Product Page</h4>
    <img src="editproduct.png" alt="Edit Product Page" width="80%">
    <h4>23. Delete Product Page</h4>
    <img src="deleteproduct.png" alt="Delete Product Page" width="80%">
    <h4>24. Orders Page</h4>
    <img src="orders.png" alt="Orders Page" width="80%">
    <h4>25. Update Order Status Page</h4>
    <img src="orderstatus.png" alt="Order Status Page" width="80%">
    <h4>26. Delete Order Page</h4>
    <img src="deleteorder.png" alt="Delte Order Page" width="80%">
    <h4>27. User Details Page</h4>
    <img src="orderdetails.png" alt="User details Page" width="80%">
    <h4>28. Edit User Page</h4>
    <img src="edituser.png" alt="Edit User Page" width="80%">
    <h4>29. Delete User Page</h4>
    <img src="deleteuser.png" alt="Delete User Page" width="80%">
    <h4>30. Add Admin Page</h4>
    <img src="addadmin.png" alt="Add Admin Page" width="80%">
    <h4>31. Admin Details Page</h4>
    <img src="admindetails.png" alt="Admin Details Page" width="80%">
    <h4>32. Update Admin Page</h4>
    <img src="updateadmin.png" alt="Update Admin Info Page" width="80%">
    <h4>33. Delete Admin Page</h4>
    <img src="deleteadmin.png" alt="Delte Admin Page" width="80%">
    <h4>34. Admin Profile Page</h4>
    <img src="adminprofile.png" alt="Admin Profile Page" width="80%">
    <h4>35. Admin Logout Page</h4>
    <img src="adminlogout.png" alt="Admin Logout Page" width="80%">
<hr>
<h3 align="center">🎉 Happy Shopping! 🚀</h3> 
<p align="center">Thank you for checking out this project! If you found it helpful, <b>give it a star ⭐ on GitHub! </b> 😊</p>


