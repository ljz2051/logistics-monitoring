package terminal;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.DBAcess;
import java.sql.*;

@SuppressWarnings({ "serial" })
public class modifyTerminal extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public modifyTerminal() {
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
	 * The doGet method of the servlet. <br>
	
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


	}

	/**
	 * 根据表单提供的数据修改数据库中的移动端信息
	 *
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String ID = request.getParameter("id");
		String courierId = request.getParameter("courierId");
		String phone = request.getParameter("phone");
		
		Statement sql = null;
        ResultSet rs = null;
        String tableName1 = "courier";
        String tableName2 = "terminal";
       
		DBAcess db = new DBAcess();
		sql =  db.DBConnect();
		
		try{
			rs = sql.executeQuery("select * from "+tableName1+" where ID = '"+courierId+"'");
			if(rs.next()){
				sql.executeUpdate("update "+tableName2+" set courierId='"+courierId+"',phone='"+phone+"' where ID='"+ID+"'");
			    response.sendRedirect("terminal/terminal.jsp");
			}
			else{
				 response.setCharacterEncoding("utf-8");
				 PrintWriter out = response.getWriter();
				 response.setContentType("text/html;charset=utf-8");
				 out.print("<script language='JavaScript' type='text/javascript' charset='utf-8'>location.href='terminal/modify.jsp?id="+ID+"&courierId="+courierId+"&phone="+phone+"';alert('对应的快递员ID不存在，修改失败！');</script>");
			}
		} 
		catch(SQLException e){
		  System.out.println(e);
		} 
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