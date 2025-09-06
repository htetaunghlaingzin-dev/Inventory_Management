package Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.UserAccess;
import Model.Userbean;
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024, // 1MB
	    maxFileSize = 1024 * 1024 * 10,  // 10MB
	    maxRequestSize = 1024 * 1024 * 50 // 50MB
	)
/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private String getValue(HttpServletRequest request, String fieldName) throws IOException, ServletException {
	    Part part = request.getPart(fieldName);
	    if (part != null) {
	        // read the input stream and convert to String
	        return new String(part.getInputStream().readAllBytes(), "UTF-8");
	    }
	    return null;
	}
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		String cmd=request.getParameter("cmd");
		UserAccess ua=new UserAccess();
		if ("edit".equals(cmd)) {
		    Userbean ub = ua.searchById(request.getParameter("id"));
		    request.setAttribute("edit", ub);
		    RequestDispatcher rd = request.getRequestDispatcher("jsp/newuseredit.jsp");
		    rd.forward(request, response);
		}
	 	if (cmd.equals("delete")) {
		String uid=request.getParameter("id");
		int id =Integer.parseInt(uid);
		boolean ub=ua.deleteUser(id);
		request.setAttribute("delete", ub);
		 RequestDispatcher rd=request.getRequestDispatcher("jsp/userlists.jsp");
			rd.forward(request, response);
	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		
				 String cmd=request.getParameter("cmd");
				 UserAccess ua=new UserAccess();
				 if(cmd.equals("Insert")) {
					 String fname = getValue(request, "fname");
						String lname = getValue(request, "lname");
						String username = getValue(request, "username");
						String pwd = getValue(request, "pwd");
						String mail = getValue(request, "mail");
						String role = getValue(request, "role");
						String ph = getValue(request, "ph"); // will be string, can parse safely
						String uploadPath =
								"C:\\Users\\User\\Documents\\UCSPyay\\ee prj\\InventoryManagement\\src\\main\\webapp\\profiles";

								 // Create the directory if it does not exist
								 File uploadDir = new File(uploadPath);
								 if (!uploadDir.exists()) {
								 uploadDir.mkdirs();
								 }
								 // Get the uploaded file
								 Part filePart = request.getPart("img");
								 String fileName = filePart.getSubmittedFileName();
								 if (fileName == null || fileName.isEmpty()) {
								 out.println("No file selected!");
								 return;
								 }
								 // Save the file to the upload directory
								 File file = new File(uploadPath + File.separator + fileName);
								 try {
								 filePart.write(file.getAbsolutePath());
								 } catch (IOException e) {
								 
								 out.print(e.getMessage());
								 }
								 // Store the file path in the database
								 String img = "profiles/" + fileName; // Relative path to save in DB
					 
					 Userbean ub=ua.setUser(fname,lname,username,pwd,ph,mail,role,img);
					 RequestDispatcher rd=request.getRequestDispatcher("jsp/userlists.jsp");
						rd.forward(request, response);
												
				 }else if(cmd.equals("update")) {
					 String ufname=request.getParameter("fname");
					 String ulname=request.getParameter("lname");
					 String umail=request.getParameter("mail");
					 String urole=request.getParameter("role");
					 String uph=request.getParameter("ph");
					 String uusername=request.getParameter("username");
					 String uid=request.getParameter("id");
					 Userbean ub=ua.updateUser(uid,ufname,ulname,uph,umail,urole,uusername);
					 RequestDispatcher rd=request.getRequestDispatcher("jsp/userlists.jsp");
						rd.forward(request, response);
				 }
				 
				 System.out.print(cmd);
				 out.print(cmd);
//				 ArrayList<Userbean> view=ua.viewUser();
//					request.setAttribute("UserSer", view);
					
				 

	}

}
