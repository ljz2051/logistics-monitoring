package courier;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.DBAcess;
import java.sql.*;

@SuppressWarnings({ "serial" })
public class modifyCourier extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public modifyCourier() {
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
	 * 根据表单提供的数据修改数据库中的快递员信息
	 *
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String ID = request.getParameter("id");
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		/*int sex = Integer.parseInt(s1);*/
		String age = request.getParameter("age");
		if(age.equals("")) age=null;
		String phone = request.getParameter("phone");
		String remark = request.getParameter("remark");
		
		/*System.out.println(ID+" "+name+" "+sex+" "+age+" "+phone+" "+remark);*/
		
		Statement sql = null;
        /*ResultSet rs = null;*/
        String tableName = "courier";
       
		DBAcess db = new DBAcess();
		sql =  db.DBConnect();
		
		try{
		  sql.executeUpdate("update "+tableName+" set Name='"+name+"',Sex="+sex+",Age="+age+",Phone='"+phone+"',Remark='"+remark+"' where ID='"+ID+"'");
		  response.sendRedirect("courier/courier.jsp");
		  
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