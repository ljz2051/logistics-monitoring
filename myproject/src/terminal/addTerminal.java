package terminal;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.DBAcess;
import java.sql.*;

@SuppressWarnings("serial")
public class addTerminal extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public addTerminal() {
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
	 * 向数据库添加移动端信息，需要验证其对应的快递员ID是否存在
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String ID = request.getParameter("id");
		String courierid = request.getParameter("courierid");
		String phone = request.getParameter("phone");
		
		//System.out.println(ID+" "+name+" "+sex+" "+age+" "+phone+" "+remark);
		
		Statement sql = null;
        ResultSet rs = null;
        String tableName1 = "courier";
        String tableName2 = "terminal";
       
		DBAcess db = new DBAcess();
		sql =  db.DBConnect();
		
		try{
		   rs = sql.executeQuery("select * from "+tableName2+" where ID = '"+ID+"'" );
		   if(rs.next()){
			   response.setCharacterEncoding("utf-8");
			   PrintWriter out = response.getWriter();
			   response.setContentType("text/html;charset=utf-8");
		       out.print("<script language='JavaScript' type='text/javascript' charset='utf-8'>location.href='terminal/add.jsp'; alert('移动端ID已经存在，添加失败！');</script>");
		   }
		   else{
			   rs = sql.executeQuery("select * from "+tableName1+" where ID = '"+courierid+"'");
		       if(rs.next()){
				   sql.executeUpdate("insert into "+tableName2+" values('"+ID+"','"+courierid+"','"+phone+"')");
				   response.sendRedirect("terminal/terminal.jsp");
		       }
		       else{
		    	   response.setCharacterEncoding("utf-8");
				   PrintWriter out = response.getWriter();
				   response.setContentType("text/html;charset=utf-8");
				   out.print("<script language='JavaScript' type='text/javascript' charset='utf-8'>location.href='terminal/add.jsp'; alert('对应的快递员ID不存在，添加失败！');</script>");
		       }
	       
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
