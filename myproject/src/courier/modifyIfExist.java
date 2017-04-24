package courier;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
import DB.DBAcess;

@SuppressWarnings("serial")
public class modifyIfExist extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public modifyIfExist() {
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
	 * 根据ID判断快递员信息是否存在
	 * 
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id"); 
	    Statement sql = null;
		ResultSet rs = null;
		String tableName = "courier";
		     
		DBAcess db = new DBAcess();
		sql =  db.DBConnect();
		
		try{
		  rs = sql.executeQuery("select * from "+tableName+" where ID = '"+id+"'");
		  if(rs.next()){
			String name = rs.getString(2);
			name = java.net.URLEncoder.encode(name,"utf-8");
			name = java.net.URLEncoder.encode(name,"utf-8");
			String sex = rs.getString(3);
			String age = rs.getString(4);
			if(age==null) age="";
			String phone = rs.getString(5);
			String remark = rs.getString(6);
			remark = java.net.URLEncoder.encode(remark,"utf-8");
			remark = java.net.URLEncoder.encode(remark,"utf-8");
			String url = "courier/modify.jsp?id="+id+"&name="+name+"&sex="+sex+"&age="+age+"&phone="+phone+"&remark="+remark;
			/*try{
				
			    Matcher matcher = Pattern.compile("[\\u4e00-\\u9fa5]").matcher(url);  
				int count = 0;  
				while (matcher.find()) {  
			        //System.out.println(matcher.group());  
			        String tmp=matcher.group(); 
			        if(tmp!="")
			          url=url.replaceAll(tmp,java.net.URLEncoder.encode(tmp,"utf-8"));  
			     }  
			     //System.out.println(count);   
			     //url = java.net.URLEncoder.encode(url,"gbk");  
			} catch (UnsupportedEncodingException e) {  
			    // TODO Auto-generated catch block  
			    e.printStackTrace();  
			}  */
			
		    response.sendRedirect(url);
		  }
		  else {
			/*response.sendRedirect("courier/courier.jsp");
			response.getWriter().write("<script>alert(\"此ID不存在！\");</script>");*/
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			
			response.setContentType("text/html;charset=utf-8");
			out.print("<script language='JavaScript' type='text/javascript' charset='utf-8'>location.href='courier/courier.jsp'; alert('此ID不存在!');</script>");
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
