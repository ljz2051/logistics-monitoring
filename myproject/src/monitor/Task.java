package monitor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import DB.DBAcess;

public class Task {
	private String ID;
	private int state;//任务状态： 0表示新创建未下发，1表示已经下发给移动端未揽收，2表示已揽收，正在运输，3表示已签收任务结束
	private String courierId;
	private String terminalId;
	private Set<String> goodList;
	private ArrayList<Point> routes;
	private String startLocation;
	private String endLocation;
	
	public Task(String ID,int state,String courierId,String terminalId,Set<String> goodList,ArrayList<Point> routes,String start,String end){
		this.ID = ID;
		this.state = state;
		this.courierId = courierId;
		this.terminalId = terminalId;
		this.goodList = goodList;
		this.routes = routes;
		this.startLocation = start;
		this.endLocation = end;
	}
	
	//通过ID访问数据库，获取Task的信息，构造Task
	public Task(String ID){
		Statement sql = null;
        ResultSet rs = null;
        String tableName = "task";
       
		DBAcess db = new DBAcess();
		sql =  db.DBConnect();
		try{
			  rs = sql.executeQuery("select * from "+tableName+" where ID = '"+ID+"'");
			  if(rs.next()){
				  String goodIdList = rs.getString(5);
				  String[] s = goodIdList.split("/");
				  Set<String> goodlist = new HashSet<String>();
				  for(int i = 0; i < s.length; i++){
					  goodlist.add(s[i]);
				  }
				  String routeString = rs.getString(6); 
				  String[] tem = routeString.split(" ");
				  ArrayList<Point> routes = new ArrayList<Point>();
				  for(int i = 0; i < tem.length/2; i++){
					  Point p = new Point(Double.parseDouble(tem[2*i]),Double.parseDouble(tem[2*i+1])); 
					  routes.add(p);
				  }
				  
				  this.ID = ID;
				  this.state = rs.getInt(2);
				  this.courierId = rs.getString(3);
				  this.terminalId = rs.getString(4);
				  this.goodList = goodlist;
				  this.routes = routes;
				  this.startLocation = rs.getString(7);
				  this.endLocation = rs.getString(8);
			  }
			  
		} 
		catch(SQLException e){
		  System.out.println(e);
		} 
	}
	
	public ArrayList<Point> getRoutes(){
		return this.routes;
	}
	
	//改变任务状态，同时修改数据库中任务的状态
	public void changeTaskState(){
		this.state += 1;
		Statement sql = null;
        ResultSet rs = null;
        String tableName = "task";
       
		DBAcess db = new DBAcess();
		sql =  db.DBConnect();
		try{
			 sql.executeUpdate("update "+tableName+" set state = state + 1 where ID = '"+this.ID+"'");
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//根据移动端ID，返回一个未分配的任务（任务state为0）
	public static Task distributeTask(String terminalId){
		Statement sql = null;
        ResultSet rs = null;
        String tableName = "task";
       
		DBAcess db = new DBAcess();
		sql =  db.DBConnect();
		try{
			 rs = sql.executeQuery("select * from "+tableName+" where terminalId='"+terminalId+"' and state = 0 order by ID");
			 if(rs.next()){
				 String goodIdList = rs.getString(5);
				  String[] s = goodIdList.split("/");
				  Set<String> goodlist = new HashSet<String>();
				  for(int i = 0; i < s.length; i++){
					  goodlist.add(s[i]);
				  }
				  String routeString = rs.getString(6); 
				  String[] tem = routeString.split(" ");
				  ArrayList<Point> routes = new ArrayList<Point>();
				  for(int i = 0; i < tem.length/2; i++){
					  Point p = new Point(Double.parseDouble(tem[2*i]),Double.parseDouble(tem[2*i+1])); 
					  routes.add(p);
				  } 
				  
				  Task t = new Task(rs.getString(1),0,rs.getString(3),rs.getString(4),goodlist,routes,rs.getString(7),rs.getString(8));
				  return t;
			 }
			 else
				 return null;
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public String getId(){
		return this.ID;
	}
	
	
	//比较两个任务是否相同
	public boolean isEqual(Task t){
		/*if(!t.ID.equals(this.ID)) System.out.println("ID:"+t.ID);
		if(t.state != this.state) System.out.println("state:"+t.state);
		if(!t.courierId.equals(this.courierId)) System.out.println("courierId"+t.courierId);
		if(!t.terminalId.equals(this.terminalId)) System.out.println("terminalId:"+t.terminalId);
		if(!t.goodList.equals(this.goodList)) System.out.println("goodlist:"+t.goodList);
		if(!t.startLocation.equals(this.startLocation)) System.out.println("startl:"+t.startLocation);
		if(!t.endLocation.equals(this.endLocation)) System.out.println("endl:"+this.endLocation);*/
		
		if(t.ID.equals(this.ID) && t.state == this.state && t.courierId.equals(this.courierId) && 
				t.terminalId.equals(this.terminalId) && t.goodList.equals(this.goodList) &&
				t.startLocation.equals(this.startLocation) && t.endLocation.equals(this.endLocation)){
			if(t.routes.size() != this.routes.size())
				return false;
			else{
				for(int i = 0; i < t.routes.size(); i++){	
			    	if(t.routes.get(i).getLng() != this.routes.get(i).getLng() || t.routes.get(i).getLat() != this.routes.get(i).getLat())
			    		return false;
			    }
				return true;
			}   
		}
		else
			return false;
		
	}

}
