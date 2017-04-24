package monitor;

import java.net.Socket;
import java.util.ArrayList;
import java.io.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class JsonSocket extends Thread {
   private Socket socket_json;
   
   private static final double THRESHOLD = 100;//阈值，当定位偏离规划路径的距离超过这个阈值后会发出警告信息
   
   public JsonSocket(Socket s){
	   this.socket_json = s;
   }
   
   @Override
   public void run(){
	   super.run();
	   getJson();
   }
   
   public void getJson(){
	   try{
		   JsonParser parser = new JsonParser(); 
		   
		   
		   //从已知的Socket获取输入流
		   InputStream in = socket_json.getInputStream();
		   InputStreamReader inReader = new InputStreamReader(in,"UTF-8");
	       BufferedReader br = new BufferedReader(inReader);
	       
	       //从已知的socket获取输出流
	       OutputStream out = socket_json.getOutputStream();
	       OutputStreamWriter outWriter = new OutputStreamWriter(out,"UTF-8");
	       BufferedWriter bw = new BufferedWriter(outWriter);
	       PrintWriter pout = new PrintWriter(bw);
	       
	       //将流传给JSon对象
	       JsonObject jsonObj = (JsonObject)parser.parse(br);
	       System.out.println(jsonObj.toString());
	       String type = jsonObj.get("type").getAsString();
	       if(type.equals("requesttask")){
	    	   //返回任务
	    	   String terminalId = jsonObj.get("terminalId").getAsString();
	    	   Task t = Task.distributeTask(terminalId);
	    	  
	    	   String task;
	    	   if(t != null){
	    		   t.changeTaskState();
	    		   Gson gson = new Gson();
	    		   task = gson.toJson(t);
	    		   /*System.out.println(result);*/
	    		   pout.println("{\"result\":\"true\",\"task\":"+task+"}");//请求成功返回任务
	    	   }
	    	   else {
	    		  pout.println("{\"result\":\"false\"}");//请求失败
	    	   }
	    	   
	       }
	       else if(type.equals("receive")){
	    	   //揽收校验结果  ,移动端传回任务信息的字段=Task的属性
	    	   String s = jsonObj.get("task").getAsString();
	    	   Gson gson = new Gson();
	    	   Task checkTask = gson.fromJson(s, Task.class);//移动端返回的校验任务信息
	    	   
	    	   String terminalId = jsonObj.get("taskId").getAsString();
	    	   Task realTask = new Task(terminalId);//数据库中读取的任务信息
	    	   
	    	   if(realTask.isEqual(checkTask)){
	    		   realTask.changeTaskState();
	    		   pout.println("{\"result\":\"true\"}");//校验通过
	    	   }
	    	   else{
	    		   String task = gson.toJson(realTask);
	    		   pout.println("{\"result\":\"true\",\"task\":"+task+"}");//校验失败，同时返回正确的任务信息
	    	   }
	    	   
	       }
	       else if(type.equals("location")){
	    	   //检查是否偏离路径
	    	   JsonObject gps = jsonObj.get("gps").getAsJsonObject();//定位信息
	    	   double lng = gps.get("lng").getAsDouble();
	    	   double lat = gps.get("lat").getAsDouble();
	    	   Point p = new Point(lng,lat);
	    	   String taskId = jsonObj.get("taskId").getAsString();
	    	   Task task = new Task(taskId);
	    	   ArrayList<Point> routes = task.getRoutes();
	    	   double minDist = 10000;
	    	   double tem;
	    	   for(int i = 0; i < routes.size()-1;i++ ){//遍历规划路径，找到最小距离
	    		   tem = Point.distance(routes.get(i),routes.get(i+1),p);
	    		   if(tem < minDist)
	    			   minDist = tem;
	    	   }
	    	   if(minDist > THRESHOLD){
	    		   pout.println("{\"type\":\"warning\",\"content\":\"departure route!\"}");//发出偏离路线的警告
	    	   }
	    	   
	       }
	       else if(type.equals("sign")){
	    	   //签收校验结果
	    	   String s = jsonObj.get("task").getAsString();
	    	   Gson gson = new Gson();
	    	   Task checkTask = gson.fromJson(s, Task.class);//移动端返回的校验任务信息
	    	   
	    	   String terminalId = jsonObj.get("taskId").getAsString();
	    	   Task realTask = new Task(terminalId);//数据库中读取的任务信息
	    	   
	    	   if(realTask.isEqual(checkTask)){
	    		   realTask.changeTaskState();//修改任务状态，结束任务
	    		   pout.println("{\"result\":\"true\"}");//校验通过
	    	   }
	    	   else{
	    		   String task = gson.toJson(realTask);
	    		   pout.println("{\"result\":\"true\",\"task\":"+task+"}");//校验失败，同时返回正确的任务信息
	    	   }
	       }
	       
	       
	       
	   
	   }
	   catch(IOException e){
	     e.printStackTrace();
	   }
   }
}
