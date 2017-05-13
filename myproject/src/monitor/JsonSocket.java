package monitor;

import java.net.Socket;
import java.util.ArrayList;
import java.io.*;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
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
		   //从已知的Socket获取输入流
		   InputStream in = socket_json.getInputStream();
		   InputStreamReader inReader = new InputStreamReader(in,"UTF-8");
	       BufferedReader br = new BufferedReader(inReader);
	       
          
	       
	       JsonParser parser = new JsonParser();  
	       JsonElement element;
	       JsonObject jsonObj;
	       String json = "";
	      /* String info;*/
	       while(true){
	    	 /*while((info = br.readLine())!=null){
	    		 json += info;
	    		 System.out.println("json"+json);
	    	 }*/
	    	 json = br.readLine();
	         System.out.println("json:"+json);
	         element = parser.parse(json);
	         //System.out.println("element:"+element);
	         if(element.isJsonObject()){
		    	  jsonObj = element.getAsJsonObject();
		    	  System.out.println("jsonobj:"+jsonObj);
		    	  handleQeuest(jsonObj);
		    	  json = "";
		    	/*  info = br.readLine();*/
		       }
	         else{
	        	 json = "";
	         }
	       }
	   }
	   catch(IOException e){
	     e.printStackTrace();
	   }
   }

	       
   public void handleQeuest(JsonObject jsonObj){
	   
	   try{
	      //从已知的socket获取输出流
	      /* OutputStream out = System.out;*/
	       OutputStreamWriter out = new OutputStreamWriter(socket_json.getOutputStream(),"UTF-8");
	       PrintWriter pout = new PrintWriter(out);
	       //将流传给JSon对象
	      /* JsonObject jsonObj = (JsonObject)parser.parse(br);*/
	       String type = jsonObj.get("type").getAsString();
	       if(type.equals("requesttask")){
	    	   //返回任务
	    	   String terminalId = jsonObj.get("terminalId").getAsString();
	    	   Task t = Task.distributeTask(terminalId);
	    	   String task;
	    	   if(t != null){
	    		   //System.out.println(t.toString());
	    		   t.changeTaskState();
	    		   Gson gson = new Gson();
	    		   task = gson.toJson(t);
	    		   /*System.out.println(result);*/
	    		   pout.println("{\"result\":\"true\",\"task\":"+task+"}");//请求成功返回任务
	    		   pout.flush();
	    	   }
	    	   else {
	    		   pout.println("{\"result\":\"false\"}");//请求失败
	    		   pout.flush();
	    	   }
	    	   
	       }
	       else if(type.equals("receive")){
	    	   /*System.out.println("aaa");*/
	    	   //揽收校验结果  ,移动端传回任务信息的字段=Task的属性
	    	   JsonObject t = jsonObj.get("task").getAsJsonObject();
	
	    	    //System.out.println("t2:"+t.toString());
	    	   Gson gson = new Gson();
	    	   Task checkTask = gson.fromJson(t, Task.class);//移动端返回的校验任务信息
	    	   //System.out.println("bbb");
	    	   String terminalId = checkTask.getId();
	    	   Task realTask = new Task(terminalId);//数据库中读取的任务信息
	    	   
	    	   if(realTask.isEqual(checkTask)){
	    		   realTask.changeTaskState();
	    		   pout.println("{\"result\":\"true\"}");//校验通过
	    		   pout.flush();
	    	   }
	    	   else{
	    		   //System.out.println(realTask.isEqual(checkTask));
	    		   String task = gson.toJson(realTask);
	    		   pout.println("{\"result\":\"false\",\"task\":"+task+"}");//校验失败，同时返回正确的任务信息
	    		   pout.flush();
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
	    		   pout.flush();
	    	   }
	    	   
	       }
	       else if(type.equals("sign")){
	    	   //签收校验结果
	    	   JsonObject t = jsonObj.get("task").getAsJsonObject();
	    	   Gson gson = new Gson();
	    	   Task checkTask = gson.fromJson(t, Task.class);//移动端返回的校验任务信息
	    	   
	    	   String terminalId = checkTask.getId();
	    	   Task realTask = new Task(terminalId);//数据库中读取的任务信息
	    	   
	    	   if(realTask.isEqual(checkTask)){
	    		   realTask.changeTaskState();//修改任务状态，结束任务
	    		   pout.println("{\"result\":\"true\"}");//校验通过
	    		   pout.flush();
	    		   //关闭socket
	    		   this.socket_json.shutdownInput();
	    		   this.socket_json.close();
	    		   
	    	   }
	    	   else{
	    		   String task = gson.toJson(realTask);
	    		   pout.println("{\"result\":\"false\",\"task\":"+task+"}");//校验失败，同时返回正确的任务信息
	    		   pout.flush();
	    	   }
	       }
	   }
	   catch(IOException e){
		   e.printStackTrace();
	   }
	       
   } 
	       
	   
}
