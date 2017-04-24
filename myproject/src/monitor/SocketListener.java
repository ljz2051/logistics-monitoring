package monitor;

import java.net.ServerSocket;
import java.net.Socket;

public class SocketListener extends Thread {
    private ServerSocket myServerSocket = null;
    private Socket mySocket;
    private JsonSocket myJsonSocket;
    private int port = 11112;
    
    
    public void run(){
    	try{
    		myServerSocket = new ServerSocket(port);
    		while(true){
    			mySocket = new Socket();
    			mySocket = myServerSocket.accept();
    			// 将socket对象传给JsonSocket中的的Socket对象，用来读取输入流和处理Json数据 
    			myJsonSocket = new JsonSocket(mySocket);
    			myJsonSocket.start();
    			
    		}
    	}
    	catch(Exception e){
    		System.out.println("服务器端异常："+e);
    	}
    }
}
