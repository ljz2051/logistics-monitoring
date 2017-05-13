<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@page import="java.sql.*" %>
<%@page import="DB.DBAcess" %>
<%@page import="java.io.PrintWriter" %>


  <%
    String terminalId= request.getParameter("tid");
    Statement sql = null;
    ResultSet rs = null;
    String tableName = "terminal";
     
    DBAcess db = new DBAcess(); 
    sql =  db.DBConnect();
	try{
	  rs = sql.executeQuery("select courierId from "+tableName+" where ID='"+terminalId+"'");
	  if(rs.next())
	  {
	    PrintWriter pout = response.getWriter();
	    pout.write(rs.getString(1));
	  }
	}
	catch(SQLException e){
	  out.println(e);
	}  
  %>
 
