package Model;

import java.awt.desktop.SystemSleepEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SupplierAccess {
	public SuppllierBean setUser(String name,String mail,String ph,String country,String city,String address,String description,String img) {
		SuppllierBean user=new SuppllierBean();
		Connection con;
		Statement stmt;
		int rs=0;
		System.out.print(name+mail+country+city+address+description+ph+mail+img);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				con=DriverManager.getConnection("jdbc:mysql://localhost/inventory_management","root","");
				stmt=con.createStatement();
				String sql="insert into suppliers(suppliername,email,phone,country,city,address,description,img) values('"+name+"','"+mail+"','"+ph+"','"+country+"','"+city+"','"+address+"','"+description+"','"+img+"')";
				rs=stmt.executeUpdate(sql);
				 System.out.print("sql"+sql+rs);
				if(rs>0) {
					System.out.println("Query ok!!");
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.print(e);
			}
		} catch (Exception e) {
			System.out.print(e);
			// TODO: handle exception
		}
		return user;
	}
	public ArrayList<SuppllierBean> searchUser(String name) {
		ArrayList <SuppllierBean> user=new ArrayList<SuppllierBean>();
		Connection con;
		Statement stmt;
		ResultSet rs;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				con=DriverManager.getConnection("jdbc:mysql://localhost/inventory_management","root","");
				stmt=con.createStatement();
				String sql="Select *, CONCAT(firstname, ' ', lastname) AS name from user where name Like '%"+name+"%'";
				rs=stmt.executeQuery(sql);
				String fname="",lname="",role = "",username="",mail="",img="",ph="";
				while(rs.next()) {
					fname=rs.getString("fname");
					lname=rs.getString("lname");
					role=rs.getString("role");
					username=rs.getString("username");
					mail=rs.getString("mail");
					img=rs.getString("img");
					ph=rs.getString("phone");
					SuppllierBean user1=new  SuppllierBean();
//					user1.setFname(fname);
//					user1.setLname(lname);
//					user1.setRole(role);
//					user1.setUsername(username);
//					user1.setPh(ph);
					user1.setMail(mail);
					user1.setImg(img);
					user.add(user1);
				}
				
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return user;
	}
	public SuppllierBean searchById(String id) {
		SuppllierBean supplier=new SuppllierBean();
		Connection con;
		Statement stmt;
		ResultSet rs;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				con=DriverManager.getConnection("jdbc:mysql://localhost/inventory_management","root","");
				stmt=con.createStatement();
				String sql="Select * from suppliers where supplier_id='"+id+"'";
				rs=stmt.executeQuery(sql);
				System.out.print(sql);
				while(rs.next()) {
					supplier.setSid(rs.getInt("supplier_id"));
	                supplier.setName(rs.getString("suppliername"));
	                supplier.setPhone(rs.getString("phone"));
	                supplier.setMail(rs.getString("email"));
	                supplier.setCountry(rs.getString("country"));
	                supplier.setCity(rs.getString("city")); 
	                supplier.setAddress(rs.getString("address"));
	                supplier.setDescription(rs.getString("description"));
				}
				
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.print(e);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print(e);
		}
		return supplier;
	}
	public ArrayList <SuppllierBean>  viewUser() {
		ArrayList <SuppllierBean> supplier=new ArrayList<SuppllierBean>();
		Connection con;
		Statement stmt;
		ResultSet rs;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				con=DriverManager.getConnection("jdbc:mysql://localhost/inventory_management","root","");
				stmt=con.createStatement();
				String sql="Select * from suppliers";
				rs=stmt.executeQuery(sql);
				System.out.print(sql);
				while(rs.next()) {
					 int supplier_id = rs.getInt("supplier_id");
		                String suppliername = rs.getString("suppliername");
		                String email = rs.getString("email");
		                String phone = rs.getString("phone");
		                String country = rs.getString("country");
		                String city = rs.getString("city");
		                String address = rs.getString("address");
		                String description = rs.getString("description");
		                String img = rs.getString("img");

		                SuppllierBean supplier1 = new SuppllierBean();
		                supplier1.setSid(supplier_id);
		                supplier1.setName(suppliername);
		                supplier1.setMail(email);
		                supplier1.setPhone(phone);
		                supplier1.setCountry(country);
		                supplier1.setCity(city);
		                supplier1.setAddress(address);
		                supplier1.setDescription(description);
		                supplier1.setImg(img);

		                supplier.add(supplier1);
				System.out.print(supplier1.getName());
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.print(e);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print(e);
		}
		return supplier;
	}

//Update method
public SuppllierBean updateUser(String id, String suppliername, String email, String phone, String country, String city, String address, String description) {
   SuppllierBean supplier=new SuppllierBean();
    Connection con;
    Statement stmt;
    int rs = 0;
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost/inventory_management", "root", "");
        stmt = con.createStatement();
        String sql = "UPDATE supplier SET " +
                "suppliername='" + suppliername + "', " +
                "email='" + email + "', " +
                "phone='" + phone + "', " +
                "country='" + country + "', " +
                "city='" + city + "', " +
                "address='" + address + "', " +
                "description='" + description + "', " +
                "WHERE supplier_id='" + id + "';";
        rs = stmt.executeUpdate(sql);
        System.out.print(sql);
        if (rs > 0) {
        	System.out.print("sql ok");
        	
        }else {
        	System.out.print("not ok!");
        }
        con.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return supplier;
}

// Delete method
public boolean deleteUser(int id) {
    Connection con;
    Statement stmt;
    int rs = 0;
    boolean isDeleted = false;
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost/inventory_management", "root", "");
        stmt = con.createStatement();
        String sql = "DELETE FROM suppliers WHERE supplier_id='" + id+ "'";
        rs = stmt.executeUpdate(sql);
        if (rs > 0) {
            isDeleted = true;
        }
        con.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return isDeleted;
}
}
