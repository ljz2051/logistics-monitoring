package DB;

import java.sql.*;


public class DBAcess {
	private Statement sql = null;
    //private ResultSet rs = null;
    private String userName = "root";;
    private String userPass = "123456";
    private String dbName = "myproject";
	
    
    public Statement DBConnect(){
			try{
			  Class.forName("com.mysql.jdbc.Driver").newInstance();
			}
			catch(Exception e){
			   System.out.println(e);
			}
			
			try{
			   String url = "jdbc:mysql://127.0.0.1/"+dbName+"?user="+userName+"&password="+userPass;
			   Connection con =  DriverManager.getConnection(url);
			   sql = con.createStatement();
			   return sql;
		    }
			catch(Exception e){
			  System.out.println(e);
			}
			return null;
		}
}
