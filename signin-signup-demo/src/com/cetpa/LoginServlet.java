package com.cetpa;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.omg.CORBA.portable.OutputStream;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/authenticateuser")
public class LoginServlet extends HttpServlet 
{
	public void service(HttpServletRequest request ,HttpServletResponse response)throws ServletException,IOException
	{
		String email=request.getParameter("loginEmail");
		String password=request.getParameter("loginPassword");
		PrintWriter out= response.getWriter();
		out.println("<div style='text-align:center ;margin-top:20px'> ");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection cn=DriverManager.getConnection("jdbc:mysql://localhost/servlet10","root","A2019cse@6991");
		    PreparedStatement ps = cn.prepareStatement("select * from userinfo where email=?");
		    ps.setString(1,email);
		    ResultSet rst=ps.executeQuery();
		    if(rst.next()) {
		    	String dpass =rst.getString(3);
		    	if(password.equals(dpass)) {
		    		response.sendRedirect("Home.html");
		    	}
		    	else {
		    		RequestDispatcher dp= request.getRequestDispatcher("login.html");
					dp.include(request,response);
					out.println("<h2 style='color:red'>Entered password is worng</h2>");
		    	}
		    }
		    else {
		    	RequestDispatcher dp= request.getRequestDispatcher("login.html");
				dp.include(request,response);
				out.println("<h2 style='color:red'>Entered email id does not exist</h2>");
				 }
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}
}
