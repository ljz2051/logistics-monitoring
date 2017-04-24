package task;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class HttpRequest {
	
	/**
    * 向指定URL发送GET方法的请求
    * 
    * @param url
    *            发送请求的URL
    * @param param
    *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
    * @return URL 所代表远程资源的响应结果
    */
   public static String sendGet(String urlNameString){
	   String result = "";
	   BufferedReader in =null;
	   try{
		   /*String urlNameString = url + "?" + param;*/
		   URL realUrl = new URL(urlNameString);
		   URLConnection connection = realUrl.openConnection();
		   /*connection.setRequestProperty("accept", "**");*/
           connection.setRequestProperty("connection", "Keep-Alive");
           connection.setRequestProperty("Charset","UTF-8");
           connection.connect();
           
           in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		   String line;
		   while((line = in.readLine()) != null){
			   result += line;
		   }
	   }
	   catch(Exception e){
		   System.out.println("发送GET请求出现异常："+e);
		   e.printStackTrace();
	   }
	   finally{
		   try{
			   if(in != null)
				   in.close();
		   }
		   catch(Exception e2){
			   e2.printStackTrace();
		   }
	   }
	   return result;
   }
}
