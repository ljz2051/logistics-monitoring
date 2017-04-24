<%@page import="java.net.URLDecoder"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ page import="java.sql.*" %>
<%@ page import="DB.DBAcess" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>移动端信息管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	
	
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<script src="js/jquery-3.1.1.js"></script>
    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.validate.js"></script>
   <!--  <script src="js/messages_zh.js"></script> -->
    <script src="js/validate.expand.js"></script>

    
  </head>
  
  <body>
    <%
      request.setCharacterEncoding("utf-8"); 
      String id = request.getParameter("id");
      String courierId = request.getParameter("courierId");
      String phone = request.getParameter("phone");
      if(phone==null) phone="";
    %>
    <div class="container">
      <div class="col-md-7">
	      <div class="page-header">
	        <h2>修改移动端信息</h2>
	      </div>
	      <form action="modifyTerminal" id="modifyForm" method="post" accept-charset="utf-8">
	        <table class="table table-striped">
	          <tbody>
	            <tr>
	              <td class="col-md-3 text-center">移动端ID</td>
	              <td class="col-md-3"><input class="form-control" readonly="readonly" type="text" name="id" value="<%=id%>"></td>
	            </tr>
	            <tr>
	              <td class="text-center">对应快递员ID</td>
	              <td><input class="form-control" type="text" name="courierId" value="<%=courierId %>"></td>
	            </tr>
	            <tr>
	              <td class="text-center">联系电话</td>
	              <td><input class="form-control" type="text" name="phone" value="<%=phone %>"></td>
	            </tr>  
	          </tbody>
	        </table>
	        <hr/>
	        <center>
	          <input class="btn btn-default" type="submit" value="提交">&nbsp;&nbsp;
	          <input class="btn btn-default" type="reset" value="重置">
	        </center>
	      </form>
      </div>
    </div>
  </body>
  <script type="text/javascript">
     $().ready(function(){
       $("#modifyForm").validate({
       rules:{
         /* id:{
           required:true,
           numAndchar:true,
           minlength:4
         }, */
         courierId:{
           required:true,
           numAndchar:true,
           minlength:4
         }, 
         phone:"digits"
       },
       messages:{
         /* id: {
           required:"请输入移动端的ID",
           numAndchar:"移动端D只能是数字和字母",
           minlength:"移动端ID的长度不能小于4"
         }, */
         courierId:"请输入正确的快递员ID",
         phone:"请输入正确格式的电话号码"
       },

       wrapper:"li"
       
       });
    });
    
  </script>
</html>
