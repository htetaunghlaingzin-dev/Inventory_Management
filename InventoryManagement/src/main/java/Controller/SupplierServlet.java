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
import java.util.ArrayList;

import Model.SupplierAccess;
import Model.SuppllierBean;
import Model.UserAccess;
import Model.Userbean;
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024, // 1MB
	    maxFileSize = 1024 * 1024 * 10,  // 10MB
	    maxRequestSize = 1024 * 1024 * 50 // 50MB
	)
/**
 * Servlet implementation class SupplierServlet
 */
@WebServlet("/SupplierServlet")
public class SupplierServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupplierServlet() {
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
		out.print(request.getParameter("cmd"));
		String cmd=request.getParameter("cmd");
		SupplierAccess sa=new SupplierAccess();
		if ("edit".equals(cmd)) {
		    SuppllierBean ub = sa.searchById(request.getParameter("id"));
		    request.setAttribute("edit", ub);
		    RequestDispatcher rd = request.getRequestDispatcher("jsp/editsupplier.jsp");
		    rd.forward(request, response);
		}
	 	if (cmd.equals("delete")) {
		String sid=request.getParameter("id");
		int id =Integer.parseInt(sid);
		boolean ub=sa.deleteUser(id);
		request.setAttribute("delete", ub);
		 RequestDispatcher rd=request.getRequestDispatcher("jsp/supplierlist.jsp");
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
		SupplierAccess sa=new SupplierAccess();
		String cmd=request.getParameter("cmd");
		if (cmd.equals("Insert")) {
			String name = request.getParameter("name");
			String mail = request.getParameter("mail");
			String ph = request.getParameter("ph");
			String country = request.getParameter("country");
			String city = request.getParameter("city");
			String address = request.getParameter("address");
			String description = request.getParameter("description");
			String uploadPath =
					"C:\\Users\\User\\Documents\\UCSPyay\\ee prj\\InventoryManagement\\src\\main\\webapp\\profiles";

					 // Create the directory if it does not exist
					 File uploadDir = new File(uploadPath);
					 if (!uploadDir.exists()) {
					 uploadDir.mkdirs();
					 }
					 // Get the uploaded file
					 Part filePart = request.getPart("avatar");
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
					 SuppllierBean insert=sa.setUser(name,mail,ph,country,city,address,description,img);
					 RequestDispatcher rd=request.getRequestDispatcher("jsp/supplierlist.jsp");
						rd.forward(request, response);
		}
		else if (cmd.equals("update")) {
		    // Get parameters from form
		    String sid = request.getParameter("sid");
		    String suppliername = request.getParameter("name");
		    String email = request.getParameter("email");
		    String phone = request.getParameter("phone");
		    String country = request.getParameter("country");
		    String city = request.getParameter("city");
		    String address = request.getParameter("address");
		    String description = request.getParameter("description");

		    // Call DAO to update supplier
		    SuppllierBean sb = sa.updateUser(sid, suppliername, email, phone, country, city, address, description
		    );

		    // Forward back to supplier list page
		    RequestDispatcher rd = request.getRequestDispatcher("jsp/supplierlist.jsp");
		    rd.forward(request, response);
		}
		 ArrayList<SuppllierBean> suppliers = sa.viewUser();
	        request.setAttribute("UserSer", suppliers);
		
	}

	private String getValue(HttpServletRequest request, String string) {
		// TODO Auto-generated method stub
		return null;
	}

}
