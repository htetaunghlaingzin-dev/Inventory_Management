package Model;

import java.io.PrintWriter;
import java.net.http.HttpResponse.ResponseInfo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import jakarta.servlet.http.HttpServletResponse;

public class UserAccess {
	public Userbean setUser(String fname,String lname,String username,String pwd,String ph,String mail,String role,String img) {
		Userbean user=new Userbean();
		Connection con;
		Statement stmt;
		int rs=0;
		System.out.print(fname+lname+username+pwd+ph+mail+role+img);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				con=DriverManager.getConnection("jdbc:mysql://localhost/inventory_management","root","");
				stmt=con.createStatement();
				String sql="insert into user(fname,lname,username,password,phone,mail,role,img) values('"+fname+"','"+lname+"','"+username+"','"+pwd+"','"+ph+"','"+mail+"','"+role+"','"+img+"')";
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
	public ArrayList<Userbean> searchUser(String name) {
		ArrayList <Userbean> user=new ArrayList<Userbean>();
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
					Userbean user1=new Userbean();
					user1.setFname(fname);
					user1.setLname(lname);
					user1.setRole(role);
					user1.setUsername(username);
					user1.setPh(ph);
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
	public ArrayList <Userbean>  viewUser() {
		ArrayList <Userbean> user=new ArrayList<Userbean>();
		Connection con;
		Statement stmt;
		ResultSet rs;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				con=DriverManager.getConnection("jdbc:mysql://localhost/inventory_management","root","");
				stmt=con.createStatement();
				String sql="Select * from user";
				rs=stmt.executeQuery(sql);
				String fname="",lname="",role = "",username="",mail="",img="",ph="";
				int uid=0;
				while(rs.next()) {
					uid=rs.getInt("uid");
					fname=rs.getString("fname");
					lname=rs.getString("lname");
					role=rs.getString("role");
					username=rs.getString("username");
					mail=rs.getString("mail");
					img=rs.getString("img");
					ph=rs.getString("phone");
					Userbean user1=new Userbean();
					user1.setUid(uid);
					user1.setFname(fname);
					user1.setLname(lname);
					user1.setRole(role);
					user1.setUsername(username);
					user1.setPh(ph);
					user1.setMail(mail);
					user1.setImg(img);
					user.add(user1);
					System.out.print(user1.getFname());
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return user;
	}

//Update method
public Userbean updateUser(String fname,String lname,String username,String pwd,int ph,String mail,String role,String img) {
    Userbean user = new Userbean();
    Connection con;
    Statement stmt;
    int rs = 0;
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost/javatest", "root", "");
        stmt = con.createStatement();
        String sql =  "UPDATE user SET " +
                "fname='" + fname + "', " +
                "lname='" + lname + "', " +
                "username='" + username + "', " +
                "mail='" + mail + "', " +
                "role='" + role + "', " +
                "img='" + img + "' " +
                "WHERE username='" + username + "'";
        rs = stmt.executeUpdate(sql);
        if (rs > 0) {
        	System.out.print("sql ok");
        	
        }
        con.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return user;
}

// Delete method
public boolean deleteUser(String name) {
    Connection con;
    Statement stmt;
    int rs = 0;
    boolean isDeleted = false;
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost/javatest", "root", "");
        stmt = con.createStatement();
        String sql = "DELETE FROM user WHERE name='" + name + "'";
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
