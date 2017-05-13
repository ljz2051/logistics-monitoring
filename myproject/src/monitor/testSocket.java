package monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class testSocket {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		
		try{
			Socket socket = new Socket("127.0.0.1",11112);
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in,"UTF-8"));
			PrintWriter write = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			
			
			String readline;
			readline = br.readLine();
			while(true){
				//System.out.println(readline);
				write.println(readline);
				write.flush();
				System.out.println("response:"+in.readLine());
				readline = br.readLine();
			}
			/*System.out.println("bbb");*/
			
		}
        catch(Exception e){
        	e.printStackTrace();
        }
	}

}

//{"type":"requesttask","terminalId":"0001"}
//{"result":"true","task":{"ID":"201704232","state":1,"courierId":"2013","terminalId":"0001","goodList":["323213232","32321","32dads","dsa3232","90932","323hhj"],"routes":[{"lng":116.36752699745,"lat":39.968311931246},{"lng":116.36759670596,"lat":39.9684031027},{"lng":116.36786089761,"lat":39.963866361998},{"lng":116.36122583345,"lat":39.963828688194},{"lng":116.36166025399,"lat":39.96159352275},{"lng":116.36170885232,"lat":39.960293271547},{"lng":116.36218531357,"lat":39.941268277425},{"lng":116.36322456321,"lat":39.908648274682},{"lng":116.36998170709,"lat":39.905275130574},{"lng":116.38082308679,"lat":39.905778940442},{"lng":116.39072834233,"lat":39.906257840015},{"lng":116.40258274116,"lat":39.906372686854},{"lng":116.40611011741,"lat":39.90662908518},{"lng":116.40581421557,"lat":39.91405653726},{"lng":116.40395400452,"lat":39.913994070434}],"startLocation":"北京市海淀区北京邮电大学","endLocation":"北京市东城区天安门"}}
//{"type":"location","taskId":"201704232","gps":{"lng":126.36759670596,"lat":49.9684031027}}