<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ page import="java.sql.*"%>
<%@ page import="DB.DBAcess" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>移动端信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<!-- <link rel="stylesheet" type="text/css" href="logistic.css"> -->
	
	
	<link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-3.1.1.js"></script>
    <script src="js/bootstrap.min.js"></script>

  </head>
  
  <body>
    <%request.setCharacterEncoding("utf-8"); %>
    
    <div class="container">
      <div class="row">
	      <div class="col-md-9">
		    <div class="page-header">
		    </div>
		    <h1>移动端信息</h1>
		    <table class="table  table-bordered table-condensed">
		       <thead>
		         <tr>
		           <th>移动端ID</th>
		           <th>快递员ID</th>
		           <th>联系电话</th>
		         </tr>
		       </thead>
		     <% 
		        Statement sql = null;
		        ResultSet rs = null;
		        String tableName = "terminal";
		       
				DBAcess db = new DBAcess();
				sql =  db.DBConnect();
				
				try{
				  rs = sql.executeQuery("select * from "+tableName);
				  while(rs.next())
				  {
				    out.print("<tr>");
				    out.print("<td>"+rs.getString(1)+"</td>");
				    out.print("<td>"+rs.getString(2)+"</td>");

				    if(rs.getString(3) == null)
				      out.print("<td></td>");
				    else
				      out.print("<td>"+rs.getString(3)+"</td>");
				    
				    out.print("</tr>");
				    
				  }
				  /* out.println("ok"); */
				}
				catch(SQLException e){
				  out.println(e);
				} 
		      %>
		    </table>
		  </div>
		  <div class="col-md-3">
		    <div>
		    <br/><br/><br/><br/>
		    </div>
		    <div class="well">
		    <h3>管理</h3>
		    <a href="terminal/add.jsp" class="btn btn-default btn-block" >添加</a>
		    <input id="modify" class="btn btn-default btn-block" type="button" value="修改">
		    <input id="delete" class="btn btn-default btn-block" type="button" value="删除">
		    </div>
		  </div>
		</div>    
	</div>
  </body>
  <script>
      $(document).ready(function(){
       $("#modify").click(function(){
         var t = prompt("请输入移动端的ID:","");
         var tem = /^[A-Za-z0-9]+$/;
         if(t != null && t != "" && tem.test(t)){
            window.location.href = "../terminalIfExist?id="+t;
         }
         else 
           alert("输入的ID无效！");
       });
       
       $("#delete").click(function(){
         var d = prompt("请输入要删除的移动端的ID:","");
         var tem = /^[A-Za-z0-9]+$/;
         if(d != null && d != "" && tem.test(d)){
            window.location.href = "../deleteTerminal?id="+d;
         }
         else
           alert("输入的ID无效！");
       });
     });
  </script>
</html>
