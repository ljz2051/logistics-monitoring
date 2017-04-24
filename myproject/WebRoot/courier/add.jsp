<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>快递员信息管理</title>
    
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
    <div class="container">
      <div class="col-md-7">
	      <div class="page-header">
	        <h2>添加快递员信息</h2>
	      </div>
	      <form id="addForm"  action="addCourier" method="post" accept-charset="utf-8">
	        <table class="table table-striped">
	          <tbody>
	            <tr>
	              <td class="col-md-3 text-center">ID<small>(数字和字母，长度最少为4)</small></td>
	              <td class="col-md-3"><input class="form-control" type="text" id="id" name="id" value=""></td>
	            </tr>
	            <tr>
	              <td class="text-center">姓名</td>
	              <td><input class="form-control" type="text" id="name" name="name" value=""></td>
	            </tr>
	            <tr>
	              <td class="text-center">性别</td>
	              <td>
	                <select class="form-control" name="sex">
	                  <option value="1">男</option>
	                  <option value="0">女</option>
	                </select>
	              </td>
	            </tr>
	            <tr>
	              <td class="text-center">年龄</td>
	              <td><input class="form-control" type="text" id="age" name="age" value=""></td>
	            </tr>
	            <tr>
	              <td class="text-center">电话</td>
	              <td><input class="form-control" type="text" id="phone" name="phone" value=""></td>
	            </tr>
	            <tr>
	              <td class="text-center">备注</td>
	              <td><input class="form-control" type="text" id="ramark" name="remark" value=""></td>
	            </tr>
	          </tbody>
	        </table>
	        <hr/>
	        <center>
	          <input class="btn btn-default" type="submit" value="提交">&nbsp;&nbsp;
	          <input class="btn btn-default" type="reset" value="重置">
	        </center>
	      </form>
      
      <!-- <ul id="errorul"></ul> -->
      </div>
    </div>
  </body>
  <script>
    $().ready(function(){
       $("#addForm").validate({
       rules:{
         id:{
           required:true,
           numAndchar:true,
           minlength:4
         },
         name:"required",
         age:{
           number:true,
           max:100,
           min:1
         },
         phone:"digits"
       },
       messages:{
         id: {
           required:"请输入快递员的ID",
           numAndchar:"快递员ID只能是数字和字母",
           minlength:"快递员ID的长度不能小于4"
         },
         name:"请输入快递员的姓名",
         age:"请输入正确的年龄",
         phone:"请输入正确格式的电话号码"
       },
      
    /*    errorContainer: "#errorul",
       errorLableContainer:"#errorul", */
       wrapper:"li"
       
       });
    });
   
  </script>
</html>
