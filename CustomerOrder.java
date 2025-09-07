package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CustomerOrder {
	
	static final String DB_URL="jdbc:mysql://localhost:3306/Northwind";
	static final String uname="root";
	static final String pass="subha";
	
	static Connection con=null;

	public static void main(String[] args) {
		Scanner obj=new Scanner(System.in);
		int choose;
		do {
			System.out.println("==========CustomerOrderDetails===========");
			
			System.out.println("1.Add a new record to the Customer table");
			System.out.println("2.Display a list of all customer and their total number of orders");
			System.out.println("3.Display top 5 expensive product");
			System.out.println("4.Display customer who didnot place any orders ");
			System.out.println("5.Display all products with category contains ");
			System.out.println("6.Display each employee full name and number of orders they handled");
			System.out.println("7.Exit");
			System.out.println();
			System.out.println("**************************************");
			System.out.println("Enter the choice");
			choose=obj.nextInt();
			System.out.println("**************************************");
			
			switch(choose) {
			
			case 1:
				try {
					con=DriverManager.getConnection(DB_URL,uname,pass);
					
					obj.nextLine();
					System.out.println("Enter the CustomerName : ");
					String CustomerName=obj.nextLine();

					System.out.println("Enter the ContactName : ");
					String ContactName=obj.nextLine();
					System.out.println("Enter the Address : ");
					String Address=obj.nextLine();
					System.out.println("Enter the City : ");
					String City=obj.nextLine();
					System.out.println("Enter the PostalCode : ");
					String PostalCode=obj.nextLine();
					System.out.println("Enter the Country : ");
					String Country=obj.nextLine();
					
					String sql="insert into Customers(CustomerName,ContactName,Address,City,PostalCode,Country) values(?,?,?,?,?,?)";
					PreparedStatement ps=con.prepareStatement(sql);
					ps.setString(1, CustomerName);
					ps.setString(2, ContactName);
					ps.setString(3, Address);
					ps.setString(4, City);
					ps.setString(5, PostalCode);
					ps.setString(6, Country);
					ps.executeUpdate();
					System.out.println("Added Successfully.......");	
					}
					catch(SQLException e) {
						System.out.println(e);
					}
				break;
				
			case 2:
				try {
					con=DriverManager.getConnection(DB_URL,uname,pass);
					Statement st=con.createStatement();
					String sql1="select c.CustomerID,c.CustomerName,count(o.OrderId) as TotalOrders from Customers c left join Orders o on c.CustomerID = o.CustomerID group by c.CustomerID, c.CustomerName ORDER BY Totalorders DESC";
					ResultSet rs=st.executeQuery(sql1);
					
					 int idWidth = 12;
					 int nameWidth = 37;
					 int ordersWidth = 12;
					 
					 String border = "+" + "-".repeat(idWidth + 2) +
			                    "+" + "-".repeat(nameWidth + 2) +
			                    "+" + "-".repeat(ordersWidth + 2) + "+";
					 
					 System.out.println(border);
					 System.out.printf("| %-"+idWidth+"s | %-"+nameWidth+"s | %-"+ordersWidth+"s |%n", 
		                      "CustomerID", "CustomerName", "TotalOrders");
		             System.out.println(border);

					
					
					while(rs.next()) {
						int CustomerID=rs.getInt("CustomerID");
						String CustomerName=rs.getString("CustomerName");
						int TotalOrders=rs.getInt("TotalOrders");
						
						System.out.printf("| %-"+idWidth+"d | %-"+nameWidth+"s | %-"+ordersWidth+"d |%n", 
		                          CustomerID, CustomerName, TotalOrders);
					}
					 System.out.println(border);
					
				}
				catch(SQLException e) {
					System.out.println(e);
				}
				break;
				
			case 3:
				try {
					con=DriverManager.getConnection(DB_URL,uname,pass);
					Statement stm=con.createStatement();
					String sql2="select ProductID,ProductName,Price from Products order by Price desc limit 5 ";
					ResultSet rs1=stm.executeQuery(sql2);
					
					
					 int idWidth = 12;
					 int nameWidth = 25;
					 int priceWidth = 12;
					
					String border="+" + "-".repeat(idWidth+2) + 
							      "+" + "-".repeat(nameWidth+2) +
							      "+" + "-".repeat(priceWidth+2) + "+";
					
					System.out.println(border);
					System.out.printf("| %-"+idWidth+"s | %-"+nameWidth+"s | %-"+priceWidth+"s |%n", 
		                      "ProductID", "ProductName", "price");
		             System.out.println(border);
		             
		            while(rs1.next()) {
		            	int ProductID=rs1.getInt("ProductID");
		            	String ProductName=rs1.getString("ProductName");
		            	int price=rs1.getInt("Price") ;
		            	
		            	
		            	System.out.printf("| %-"+idWidth+"d | %-"+nameWidth+"s | %-"+priceWidth+"d|%n", 
		            			ProductID, ProductName, price);
		            	
		            }
		            System.out.println(border);
		            
				}catch(SQLException e) {
					System.out.println(e);
				}
				break;
				
			case 4:

				try {
					con=DriverManager.getConnection(DB_URL,uname,pass);
					Statement str=con.createStatement();
					String sql3="select c.CustomerID,c.CustomerName from Customers c  left join Orders o on c.CustomerID = o.CustomerID where o.orderId is null";
					ResultSet rs=str.executeQuery(sql3);
					
					int idWidth = 12;
					int nameWidth = 37;
					String border="+" + "-".repeat(idWidth+2) + 
						      "+" + "-".repeat(nameWidth+2) +
						      "+";
				
				  System.out.println(border);
				  System.out.printf("| %-"+idWidth+"s | %-"+nameWidth+"s |%n", 
	                      "CustomerID", "CustomerName");
	              System.out.println(border);
	              
	              while(rs.next()) {
	            	  int CustomerID=rs.getInt("CustomerID");
	            	  String CustomerName=rs.getString("CustomerName");
	            	  
	            	  System.out.printf("| %-"+idWidth+"d | %-"+nameWidth+"s |%n", 
	            			  CustomerID, CustomerName);  
	            	  
	              }
	              System.out.println(border);
					
				}
				catch(SQLException e) {
					System.out.println(e);
				}
				break;
				
			case 5:
				try {
					con=DriverManager.getConnection(DB_URL,uname,pass);
					System.out.println("List of Categories.....");
					Statement st=con.createStatement();
					String sql4="select CategoryName from Categories";
					ResultSet rs=st.executeQuery(sql4);
					int i=1;
					  while(rs.next()) {
						  
						  String s=rs.getString("CategoryName");
						  System.out.println(i +" "+ s);
						  i++; 
					  }
					  
					  
					System.out.println("Enter the  choice.....");
					int choice=obj.nextInt();
					
					switch(choice) {
					
					case 1:
						System.out.println(".............Beverages................");
						Statement stm=con.createStatement();
						String sql5="select p.ProductID,p.ProductName from Products p join Categories c on p.CategoryID=c.CategoryID where c.CategoryName='Beverages'";
						ResultSet rs1=stm.executeQuery(sql5);
						
						int idWidth = 12;
						int nameWidth = 37;
						String border="+" + "-".repeat(idWidth+2) + 
							      "+" + "-".repeat(nameWidth+2) +
							      "+";
					
					  System.out.println(border);
					  System.out.printf("| %-"+idWidth+"s | %-"+nameWidth+"s |%n", 
		                      "ProductID", "ProductName");
		              System.out.println(border);
		              
		              while(rs1.next()) {
		            	  int ProductID=rs1.getInt("ProductID");
		            	  String ProductName=rs1.getString("ProductName");
		            	  
		            	  System.out.printf("| %-"+idWidth+"d | %-"+nameWidth+"s |%n", 
		            			  ProductID, ProductName); 
		            	  
		              }
		              System.out.println(border);
		              
		              break;
		              
					case 2:
						System.out.println("...........Condiments..........");
						Statement stm1=con.createStatement();
						String sql6="select p.ProductID,p.ProductName from Products p join Categories c on p.CategoryID=c.CategoryID where c.CategoryName='Condiments'";
						ResultSet rs2=stm1.executeQuery(sql6);
						
						int id = 12;
						int name = 37;
						String bor="+" + "-".repeat(id+2) + 
							      "+" + "-".repeat(name+2) +
							      "+";
					
					  System.out.println(bor);
					  System.out.printf("| %-"+id+"s | %-"+name+"s |%n", 
		                      "ProductID", "ProductName");
		              System.out.println(bor);
		              while(rs2.next()) {
		            	  int ProductID=rs2.getInt("ProductID");
		            	  String ProductName=rs2.getString("ProductName");
		            	  
		            	  System.out.printf("| %-"+id+"d | %-"+name+"s |%n", 
		            			  ProductID, ProductName); 
		            	  
		              }
		              System.out.println(bor);
		              break;
					case 3:
						System.out.println("...........Confections..........");
						Statement stm2=con.createStatement();
						String sql7="select p.ProductID,p.ProductName from Products p join Categories c on p.CategoryID=c.CategoryID where c.CategoryName='Confections'";
						ResultSet rs3=stm2.executeQuery(sql7);
						int id1 = 12;
						int name1 = 37;
						String bor1="+" + "-".repeat(id1+2) + 
							      "+" + "-".repeat(name1+2) +
							      "+";
					
					    System.out.println(bor1);
					    System.out.printf("| %-"+id1+"s | %-"+name1+"s |%n", 
		                      "ProductID", "ProductName");
		                System.out.println(bor1);
		                while(rs3.next()) {
		            	  int ProductID=rs3.getInt("ProductID");
		            	  String ProductName=rs3.getString("ProductName");
		            	  
		            	  System.out.printf("| %-"+id1+"d | %-"+name1+"s |%n", 
		            			  ProductID, ProductName); 
		            	  
		              }
		              System.out.println(bor1);
		              
		              break;
					case 4:
						System.out.println("...........Dairy Products..........");
						Statement stm3=con.createStatement();
						String sql8="select p.ProductID,p.ProductName from Products p join Categories c on p.CategoryID=c.CategoryID where c.CategoryName='Dairy Products'";
						ResultSet rs4=stm3.executeQuery(sql8);
						int id2 = 12;
						int name2 = 37;
						String bor2="+" + "-".repeat(id2+2) + 
							      "+" + "-".repeat(name2+2) +
							      "+";
					
					    System.out.println(bor2);
					    System.out.printf("| %-"+id2+"s | %-"+name2+"s |%n", 
		                      "ProductID", "ProductName");
		                System.out.println(bor2);
		                while(rs4.next()) {
		            	  int ProductID=rs4.getInt("ProductID");
		            	  String ProductName=rs4.getString("ProductName");
		            	  
		            	  System.out.printf("| %-"+id2+"d | %-"+name2+"s |%n", 
		            			  ProductID, ProductName); 
		            	  
		              }
		              System.out.println(bor2);
		              break;
		              
					case 5:
						System.out.println("...........Grains/Cereals..........");
						Statement stm4=con.createStatement();
						String sql9="select p.ProductID,p.ProductName from Products p join Categories c on p.CategoryID=c.CategoryID where c.CategoryName='Grains/Cereals'";
						ResultSet rs5=stm4.executeQuery(sql9);
						int id3 = 12;
						int name3 = 37;
						String bor3="+" + "-".repeat(id3+2) + 
							      "+" + "-".repeat(name3+2) +
							      "+";
					
					    System.out.println(bor3);
					    System.out.printf("| %-"+id3+"s | %-"+name3+"s |%n", 
		                      "ProductID", "ProductName");
		                System.out.println(bor3);
		                while(rs5.next()) {
		            	  int ProductID=rs5.getInt("ProductID");
		            	  String ProductName=rs5.getString("ProductName");
		            	  
		            	  System.out.printf("| %-"+id3+"d | %-"+name3+"s |%n", 
		            			  ProductID, ProductName); 
		            	  
		              }
		              System.out.println(bor3);
		              break;
					case 6:
						System.out.println("...........Meat/Poultry..........");
						Statement stm5=con.createStatement();
						String sql10="select p.ProductID,p.ProductName from Products p join Categories c on p.CategoryID=c.CategoryID where c.CategoryName='Meat/Poultry'";
						ResultSet rs6=stm5.executeQuery(sql10);
						int id4 = 12;
						int name4 = 37;
						String bor4="+" + "-".repeat(id4+2) + 
							      "+" + "-".repeat(name4+2) +
							      "+";
					
					    System.out.println(bor4);
					    System.out.printf("| %-"+id4+"s | %-"+name4+"s |%n", 
		                      "ProductID", "ProductName");
		                System.out.println(bor4);
		                while(rs6.next()) {
		            	  int ProductID=rs6.getInt("ProductID");
		            	  String ProductName=rs6.getString("ProductName");
		            	  
		            	  System.out.printf("| %-"+id4+"d | %-"+name4+"s |%n", 
		            			  ProductID, ProductName); 
		            	  
		              }
		              System.out.println(bor4);
		              break;
					case 7:
						System.out.println("...........Produce..........");
						Statement stm6=con.createStatement();
						String sql11="select p.ProductID,p.ProductName from Products p join Categories c on p.CategoryID=c.CategoryID where c.CategoryName='Produce'";
						ResultSet rs7=stm6.executeQuery(sql11);
						int id5 = 12;
						int name5 = 37;
						String bor5="+" + "-".repeat(id5+2) + 
							      "+" + "-".repeat(name5+2) +
							      "+";
					
					    System.out.println(bor5);
					    System.out.printf("| %-"+id5+"s | %-"+name5+"s |%n", 
		                      "ProductID", "ProductName");
		                System.out.println(bor5);
		                while(rs7.next()) {
		            	  int ProductID=rs7.getInt("ProductID");
		            	  String ProductName=rs7.getString("ProductName");
		            	  
		            	  System.out.printf("| %-"+id5+"d | %-"+name5+"s |%n", 
		            			  ProductID, ProductName); 
		            	  
		              }
		              System.out.println(bor5);
		              break;
					case 8:
						System.out.println("...........Seafood..........");
						Statement stm7=con.createStatement();
						String sql12="select p.ProductID,p.ProductName from Products p join Categories c on p.CategoryID=c.CategoryID where c.CategoryName='Seafood'";
						ResultSet rs8=stm7.executeQuery(sql12);
						int id6 = 12;
						int name6 = 37;
						String bor6="+" + "-".repeat(id6+2) + 
							      "+" + "-".repeat(name6+2) +
							      "+";
					
					    System.out.println(bor6);
					    System.out.printf("| %-"+id6+"s | %-"+name6+"s |%n", 
		                      "ProductID", "ProductName");
		                System.out.println(bor6);
		                while(rs8.next()) {
		            	  int ProductID=rs8.getInt("ProductID");
		            	  String ProductName=rs8.getString("ProductName");
		            	  
		            	  System.out.printf("| %-"+id6+"d | %-"+name6+"s |%n", 
		            			  ProductID, ProductName); 
		            	  
		              }
		              System.out.println(bor6);
		              break;
		              default :
		            	  System.out.println("Invalid choice");  
						
					}
					  
				}
				catch(SQLException e) {
					System.out.println(e);
				}
				break;
				
			case 6:
				try {
					con=DriverManager.getConnection(DB_URL,uname,pass);
					Statement st=con.createStatement();
					String sql13="select e.EmployeeID,concat(e.FirstName,' ',e.LastName)   as EmployeeName ,count(o.orderId) AS orderhandled from Employees e left join Orders o on e.EmployeeID=o.EmployeeID group by e.EmployeeID";
					ResultSet rs=st.executeQuery(sql13);
					
					int idWidth = 12;
					int nameWidth = 25;
					int orderWidth = 12;
					
					String border="+" + "-".repeat(idWidth+2) + 
							      "+" + "-".repeat(nameWidth+2) +
							      "+" + "-".repeat(orderWidth+2) + "+";
					
					System.out.println(border);
					System.out.printf("| %-"+idWidth+"s | %-"+nameWidth+"s | %-"+orderWidth+"s |%n", 
		                      "EmployeeID", "EmployeeName", "orderhandled");
		             System.out.println(border);
		             
		            while(rs.next()) {
		            	int EmployeeID=rs.getInt("EmployeeID");
		            	String EmployeeName=rs.getString("EmployeeName");
		            	int orderhandled=rs.getInt("orderhandled") ;
		            	
		            	
		            	System.out.printf("| %-"+idWidth+"d | %-"+nameWidth+"s | %-"+orderWidth+"d|%n", 
		            			EmployeeID, EmployeeName, orderhandled);
		            	
		            }
		            System.out.println(border);
					
					
					
				}
				catch(SQLException e) {
					System.out.println(e);
				}
				break;
			case 7:
				System.out.println("Thank you Visiting.....");
				break;
				default :
					System.out.println("Invalid choice");		
				
			}
			
		}
		while(choose!=7); 
		
	}

}
