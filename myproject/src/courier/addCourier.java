package courier;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.DBAcess;
import java.sql.*;

@SuppressWarnings({ "serial" })
public class addCourier extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public addCourier() {
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
	 * 向数据库中添加快递员信息
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
        ResultSet rs = null;
        String tableName = "courier";
       
		DBAcess db = new DBAcess();
		sql =  db.DBConnect();
		
		try{
		  rs = sql.executeQuery("select * from "+tableName+" where ID = '"+ID+"';");
		  if(rs.next()){
			  response.setCharacterEncoding("utf-8");
			  PrintWriter out = response.getWriter();
			  response.setContentType("text/html;charset=utf-8");
			  out.print("<script language='JavaScript' type='text/javascript' charset='utf-8'>location.href='courier/add.jsp'; alert('此ID已经存在，添加失败！');</script>");
		  }
		  else{
			  sql.executeUpdate("insert into "+tableName+" values('"+ID+"','"+name+"',"+sex+","+age+",'"+phone+"','"+remark+"');");
			  response.sendRedirect("courier/courier.jsp");
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
