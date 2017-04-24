package courier;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.DBAcess;

@SuppressWarnings("serial")
public class deleteCourier extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public deleteCourier() {
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
	 * 根据ID查询要删除的快递员信息是否存在，存在的话删除，不存在的话返回提示信息
	 * 
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id"); 
	    Statement sql = null;
		/*ResultSet rs = null;*/
		String tableName = "courier";
		     
		DBAcess db = new DBAcess();
		sql =  db.DBConnect();
		int result = 0;
		
		try{
		  
		  result = sql.executeUpdate("delete from "+tableName+" where ID = '"+id+"'");
		  
		  response.setCharacterEncoding("utf-8");
		  PrintWriter out = response.getWriter();
		  response.setContentType("text/html;charset=utf-8");
		  if(result == 0)
		      out.print("<script language='JavaScript' type='text/javascript' charset='utf-8'>location.href='courier/courier.jsp'; alert('此ID不存在，删除失败！');</script>");
		  else
			  out.print("<script language='JavaScript' type='text/javascript' charset='utf-8'>location.href='courier/courier.jsp'; alert('删除成功！');</script>");
		  
		}
		catch(SQLException e){
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			response.setContentType("text/html;charset=utf-8");
			out.print("<script language='JavaScript' type='text/javascript' charset='utf-8'>location.href='courier/courier.jsp'; alert('删除失败！');</script>");
			
		} 
		
		
	}

	/**
	 
	 
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
