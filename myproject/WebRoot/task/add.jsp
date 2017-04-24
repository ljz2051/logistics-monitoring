<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page import="javax.servlet.*,java.text.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>任务信息管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	
	
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<script src="js/jquery-3.1.1.js"></script>
    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.validate.js"></script>
    <script src="js/validate.expand.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=BSfLmgG7cMYzjRukm8Xjc8v6hYOZT0lh"></script>
  
  <style type="text/css">
	#l-map {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
	</style>
  
  </head>
  <body>
    <%!
    //任务编号有系统自动生成，前8位为新建任务时的日期，后3位为从0自增的序号，且每过一天，后三位清零
       synchronized String getTaskId(){
         Date now = new Date();
		 SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
         ServletContext application = getServletContext();
         String taskId = (String)application.getAttribute("taskId");
         if(taskId == null){
           String id = ft.format(now)+"000";
           application.setAttribute("taskId", id);
           return id;
         }
         else{
            String day = taskId.substring(0, 8);
            if(ft.format(now).equals(day)){
              String id = day+(Integer.parseInt(taskId.substring(8))+1);
              application.setAttribute("taskId", id);
              return id;
            }
            else{
              String id = ft.format(now).substring(0,8)+"000";
              application.setAttribute("taskId", id);
              return id;
            }
         }
       }
    %>
    <div class="container">
      <div class="row">
	      <div class="col-md-7">
		      <div class="page-header">
		        <h2>新建任务 </h2>
		      </div>
		      <form id="addForm" action="addTask" method="post" accept-charset="utf-8">
		        <table class="table table-striped">
		          <tbody>
		            <tr>
		              <td class="col-md-3 text-center">任务编号</td>
		              <td class="col-md-3"><input class="form-control" readonly="readonly" type="text" id="id" name="id" value="<%=getTaskId() %>"></td>
		            </tr>
		            <!--      快递员ID会根据移动端ID自动绑定 -->
		            <!-- <tr>
		              <td class="text-center">快递员ID</td>
		              <td><input class="form-control" type="text" id="courierId" name="courierId" value=""></td>
		            </tr> -->
		           
		            <tr>
		              <td class="text-center">移动端ID</td>
		              <td><input class="form-control" type="text" id="terminalId" name="terminalId" value=""></td>
		            </tr>
		            <tr>
		              <td class="text-center">货物二维码ID</td>
		              <td >
		                <input class="form-control"  type="text" class="col-md-2" id="goodId" name="goodId">
		                <!-- <button id="addGood" class="col-md-5">tianjia</button> -->
		                <input type="button" value="添加" id="addGood">
		              </td>
		            </tr>
		            <tr>
		              <td class="text-center">货物二维码ID列表</td>
		              <td>
		                <!-- <input class="form-control" type="text" id="goodlist" name="goodlist" readonly="readonly"  value=""> -->
		                <textarea class="form-control" id="goodlist" name="goodlist" readonly="readonly"></textarea>
		              </td>
		            </tr>
		            <tr>
		              <td class="text-center">起始地点</td>
		              <td><input class="form-control" type="text" id="start" name="start" value=""></td>
		            </tr>
		            <tr>
		              <td class="text-center">终止地点</td>
		              <td>
		                <input class="form-control" type="text" id="end" name="end" value="">
		                <input type="button" id="route" value="查看规划路径">
		              </td>
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
	      <div class="col-md-5">
	        <div id="l-map"></div>
	        <div id="searchResultPanel"></div>
	      </div>
      </div>
    </div>
  </body>
  <script> 
	// 百度地图API功能
	function G(id) {
		return document.getElementById(id);
	}

	var map = new BMap.Map("l-map");
	map.centerAndZoom("北京",12);                   // 初始化地图,设置城市和地图级别。

	var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
		{"input" : "start",
		 "location" : map
	});
	
	var bc = new BMap.Autocomplete(    //建立一个自动完成的对象
		{"input" : "end",
		 "location" : map
	});

	function down(e) {  //鼠标放在下拉列表上的事件
	var str = "";
		var _value = e.fromitem.value;
		var value = "";
		if (e.fromitem.index > -1) {
			value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
		}    
		str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;
		
		value = "";
		if (e.toitem.index > -1) {
			_value = e.toitem.value;
			value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
		}    
		str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
		G("searchResultPanel").innerHTML = str;
	}
	
	ac.addEventListener("onhighlight", down);
	bc.addEventListener("onhighlight", down);

	var myValue;
	ac.addEventListener("onconfirm", cclick);
	function cclick(e) {    //鼠标点击下拉列表后的事件
	var _value = e.item.value;
		myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
		G("searchResultPanel").innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;
		
		setPlace();
	}
	
	bc.addEventListener("onconfirm", cclick);

	function setPlace(){
		map.clearOverlays();    //清除地图上所有覆盖物
		function myFun(){
			var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
			map.centerAndZoom(pp, 18);
			map.addOverlay(new BMap.Marker(pp));    //添加标注
		}
		var local = new BMap.LocalSearch(map, { //智能搜索
		  onSearchComplete: myFun
		});
		local.search(myValue);
	}
	
	
	$("#route").click(function(){
	  var start1 = $("#start").val()+"";
	  var end1 = $("#end").val()+"";
	 /*  var start1="北京大学";
	  var end1="清华大学"; */
	  map.clearOverlays(); 
	  var driving = new BMap.DrivingRoute(map,{renderOptions:{map: map, autoViewport: true},policy:BMAP_DRIVING_POLICY_LEAST_TIME});
	  driving.search(start1,end1);
	});
	
	
	$("#addGood").click(function(){
	  var goodid = $("#goodId")[0].value;
	  var goodlist = $("#goodlist")[0].value;
	  var newlist = goodlist+goodid+"/";
	  $("#goodlist").val(newlist);
	  $("#goodId").val("");
	
	});
   
  </script>
</html>
