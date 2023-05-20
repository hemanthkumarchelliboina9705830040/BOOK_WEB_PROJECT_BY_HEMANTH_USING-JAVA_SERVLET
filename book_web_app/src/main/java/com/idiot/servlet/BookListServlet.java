package com.idiot.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@WebServlet("/bookList")
public class BookListServlet extends HttpServlet
{
	
	private static final String query = "SELECT ID,BOOKNAME,BOOKEDITION,BOOKPRICE FROM BOOKDATA";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //get PrintWriter
        PrintWriter pw = res.getWriter();
        //set content type
        res.setContentType("text/html");
        //GET THE book info
        //LOAD jdbc driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
        //generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///book", "root", "9705830040@Chk");
        	PreparedStatement ps = con.prepareStatement(query);) 
        {
        	ResultSet rs=ps.executeQuery();
        	pw.println("<table border='1' align='center'>");
        	pw.println("<tr>");
        	pw.println("<th> BOOK ID</th>");
        	pw.println("<th> BOOK NAME</th>");
        	pw.println("<th> BOOK EDITION</th>");
        	pw.println("<th> BOOK PRICE</th>");
        	pw.println("<th> EDIT </th>");
        	pw.println("<th> DELETE </th>");
        	pw.println("</tr>");
        	while(rs.next())
        	{
            	pw.println("<tr>");
            	pw.println("<td>"+rs.getInt(1)+"</td>");
            	pw.println("<td>"+rs.getString(2)+"</td>");
            	pw.println("<td>"+rs.getString(3)+"</td>");
            	pw.println("<td>"+rs.getFloat(4)+"</td>");
            	pw.println("<td><a href='editScreen?id="+rs.getInt(1)+"'>EDIT</a></td>");
            	pw.println("<td><a href='deleteurl?id="+rs.getInt(1)+"'>DELETE</a></td>");
            	pw.println("</tr>");
        	}
        	pw.println("</table>");
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>" + se.getMessage() + "</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h2>");
        }
        pw.println("<a href='book_web_app1.html'>Home</a>");
        pw.println("<br>");
        //pw.println("<a href='bookList'>Book List</a>");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
