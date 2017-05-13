package task;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import DB.DBAcess;

public class modifyTask extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public modifyTask() {
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
	 *
	 
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	
	}

	/**
	 * 根据表单返回信息，修改数据库中的任务信息
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String ID = request.getParameter("id");
		String terminalId = request.getParameter("terminalId");
		String goodlist = request.getParameter("goodlist");
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String courierId = null;
		String routes = "";//存储规划路径 格式 ：第一个点经度+" "+第一个点纬度+" "+第二个点经度+" "+.......
		/*System.out.println(start);*/
		
		/*response.setCharacterEncoding("utf-8");
		  PrintWriter out = response.getWriter();
		  response.setContentType("text/html;charset=utf-8");
		  out.println("<p>"+start+"</p><br><p>"+end+"</p>");*/
		  
		String s = HttpRequest.sendGet("http://api.map.baidu.com/direction/v1?mode=driving&origin="+start+"&destination="+end+"&origin_region=北京&destination_region=北京&output=json&ak=BSfLmgG7cMYzjRukm8Xjc8v6hYOZT0lh");
		//out.print("<p>"+s+"</p>");
		JsonObject jsonobj = new JsonParser().parse(s).getAsJsonObject();
		
		JsonArray steparray = jsonobj.get("result").getAsJsonObject().get("routes").getAsJsonArray().get(0).getAsJsonObject().get("steps").getAsJsonArray();
		for(int i = 0; i < steparray.size(); i++){
			JsonObject step = steparray.get(i).getAsJsonObject();
			JsonObject origin = step.get("stepOriginLocation").getAsJsonObject();
			routes += (origin.get("lng").getAsString()+" "+origin.get("lat").getAsString()+" ");	
		}
		JsonObject destiny = steparray.get(steparray.size()-1).getAsJsonObject().get("stepDestinationLocation").getAsJsonObject();
		routes += (destiny.get("lng").getAsString()+" "+destiny.get("lat").getAsString());	
		
		
		/*response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print("<p>"+routes+"</p>");
		System.out.println(ID+" "+terminalId+" "+goodlist+" "+start+" "+end);*/
		
		Statement sql = null;
        ResultSet rs = null;
        String tableName1 = "terminal";
        String tableName2 = "task";
       
		DBAcess db = new DBAcess();
		sql =  db.DBConnect();
		try{
			  rs = sql.executeQuery("select courierId from "+tableName1+" where ID = '"+terminalId+"'");
			  if(rs.next()){
				courierId = rs.getString(1);  
			  }
			  sql.executeUpdate("update "+tableName2+" set courierId='"+courierId+"',terminalId='"+terminalId+"',goodIdList='"+goodlist+"',routes='"+routes+"',startLocation='"+start+"',endLocation='"+end+"' where ID='"+ID+"'");
			  response.sendRedirect("task/task.jsp");
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
