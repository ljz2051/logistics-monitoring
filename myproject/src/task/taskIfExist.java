package task;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
import DB.DBAcess;

@SuppressWarnings("serial")
public class taskIfExist extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public taskIfExist() {
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
	 * 根据ID判断任务信息是否存在
	 * 
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id"); 
	    Statement sql = null;
		ResultSet rs = null;
		String tableName = "task";
		     
		DBAcess db = new DBAcess();
		sql =  db.DBConnect();
		
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		
		try{
		  rs = sql.executeQuery("select * from "+tableName+" where ID = '"+id+"'");
		  if(rs.next()){
			int state = rs.getInt(2);
			if(state == 0){
				String url = "task/modify.jsp?id="+id;
			    response.sendRedirect(url);
			}
			else
				out.print("<script language='JavaScript' type='text/javascript' charset='utf-8'>location.href='task/task.jsp'; alert('此任务已下发，无法修改！');</script>");
		  }
		  else {
			out.print("<script language='JavaScript' type='text/javascript' charset='utf-8'>location.href='task/task.jsp'; alert('此任务编号不存在!');</script>");
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
