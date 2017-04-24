package monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class testSocket {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		
		try{
			Socket socket = new Socket("127.0.0.1",11112);
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter write = new PrintWriter(socket.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			
			String readline;
			readline = br.readLine();
			while(!readline.equals(null)){
				write.println(readline);
				write.flush();
				System.out.println("Server:"+in.readLine());
				readline = br.readLine();
			}
		}
        catch(Exception e){
        	e.printStackTrace();
        }
	}

}
