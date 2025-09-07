# Northwind-JDBC-Explorer

This project is a **Java JDBC application** built using the **Northwind database schema**.  
It demonstrates how to interact with a MySQL database using **JDBC** with real-world queries such as customer management, product listings, and order tracking.

---

## 🛠️ Tech Stack
- **Java**
- **JDBC**
- **MySQL**
- **Northwind Database Schema**

---

## 📦 Database Setup
1. Install **MySQL** and create a database named **Northwind**.  
2. Run the provided **DDL scripts** (to create tables like Customers, Employees, Orders, Products, etc.).  
3. Optionally insert **sample data** for testing.  

---

## ⚙️ Configure Database Credentials
Open **`CustomerOrder.java`** and update your database credentials:

```java
static final String DB_URL = "jdbc:mysql://localhost:3306/Northwind";
static final String uname = "your-username";
static final String pass = "your-password";
```
---
## 🔗 Add MySQL Connector

Download the MySQL Connector/J jar (e.g., `mysql-connector-j-8.0.xx.jar`) and add it to your project classpath.

---
## 📂Project Structure
```
CustomerOrder-JDBC/
│── src/
│   └── jdbc/
│       └── CustomerOrder.java
│── lib/
│   └── mysql-connector-j-8.0.xx.jar
│── README.md
│── .gitignore
```




