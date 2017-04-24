<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
   String path = request.getContextPath(); 
   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>homePage</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-3.1.1.js"></script>
    <script src="js/bootstrap.min.js"></script>

  </head>
  
  <body>
    <nav class="navbar navbar-fixed-top navbar-inverse" role="navigation">
      <div class="container-fluid">
	      <div class=""></div>
	      <div class="navbar-header">
	        <a class="navbar-brand" href="#">物流监控系统服务器平台</a>
	      </div>
	      <div class="collapse navbar-collapse">
		      <ul class="nav navbar-nav">
		         <li class="active">
		            <a href=""><span class="glyphicon glyphicon-home"></span>&nbsp;主页</a>
		         </li>
		         <li>
		           <a href="courier/courier.jsp" >快递员</a>
		         </li>
		         <li>
		           <a href="terminal/terminal.jsp" >移动端</a>
		         </li>
		         <li>
		           <a href="task/task.jsp">任务</a>
		         </li>
		      </ul>  
		      
	        <div class="col-md-3 col-md-offset-2">
	           <ul class="nav navbar-nav">
	             <li class="active"><a>当前登录用户为：</a></li>
	             <li class="dropdown active">
	               <a class="dropdown-toggle" href="#" data-toggle="dropdown"><b id="loginName">xxx<span class="caret"></span></b></a>
	                 <ul class="dropdown-menu">
	                   <li>用户名：<lable id="name"></lable></li>
	                   <li class="divider"></li>
	                   <li><a href="#">退出 </a></li>
	                 </ul>
	             </li>
	           </ul>
	        </div>
 
	    </div>  
      </div>
    </nav>
    
   
      
      
      

  </body>
</html>
