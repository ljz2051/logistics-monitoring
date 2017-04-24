package terminal;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
import DB.DBAcess;

@SuppressWarnings("serial")
public class terminalIfExist extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public terminalIfExist() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * 根据ID判断移动端信息是否存在
	 * 
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id"); 
	    Statement sql = null;
		ResultSet rs = null;
		String tableName = "terminal";
		     
		DBAcess db = new DBAcess();
		sql =  db.DBConnect();
		
		try{
		  rs = sql.executeQuery("select * from "+tableName+" where ID = '"+id+"'");
		  if(rs.next()){
			String courierId = rs.getString(2);
			String phone = rs.getString(3);
			String url = "terminal/modify.jsp?id="+id+"&courierId="+courierId+"&phone="+phone;
		    response.sendRedirect(url);
		  }
		  else {
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			
			response.setContentType("text/html;charset=utf-8");
			out.print("<script language='JavaScript' type='text/javascript' charset='utf-8'>location.href='terminal/terminal.jsp'; alert('此ID不存在!');</script>");
		  }
		}
		catch(SQLException e){
		  System.out.println(e);
		} 
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
