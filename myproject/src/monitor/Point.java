package monitor;

import java.math.BigDecimal;

public class Point { //地图上的一个点
   private double lng;//经度
   private double lat;//纬度
   
      
   public Point(double lng,double lat){
	   this.lng = lng;
	   this.lat = lat;
   }
   
   public double getLng(){
	   return this.lng;
   }
   
   public double getLat(){
	   return this.lat;
   }
   
   //根据两地经纬度计算两地距离
   public static double geoDisatance(double lng1, double lat1, double lng2, double lat2)
   {
	    double earthRadius = 6371000; //meters
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = (double) (earthRadius * c);
	
	    return dist;
   }
   
   /**用来计算点c到线段ab的距离
    * 
    *1.首先从c向线段ab做垂线
    *2.判断垂足的位置是否在线段ab上(判断过c点的垂线的截距是否在分别过a、b点的垂线的截距之间)
    *3.垂足在线段上的话，求距离(两条直线方程就焦点，然后求距离)
    * 4，若垂足不在线段上的话，则取点c到a、b其中一点的距离最小值作为距离
    */
   public static double distance(Point a,Point b,Point c){
	   //已点a为坐标原点，建立直角坐标系   则a(0,0)
	   double bx = geoDisatance(a.lng,a.lat,b.lng,a.lat);//求b的横坐标
	   if(b.lng < a.lng) bx = (-bx) ;
	   double by = geoDisatance(a.lng,a.lat,a.lng,b.lat);//b的纵纵坐标
	   if(b.lat < a.lat) by = (-by);
	   double cx = geoDisatance(a.lng,a.lat,c.lng,a.lat);//求b的横坐标
	   if(c.lng < a.lng) cx = (-cx) ;
	   double cy = geoDisatance(a.lng,a.lat,a.lng,c.lat);//b的纵纵坐标
	   if(c.lat < a.lat) cy = (-cy);
	   
	   double kab = by/bx;//ab斜率
	   
	   double border = by - (bx/kab); //求过点b且与ab垂直的直线的截距
	   double x = cy - (cx/kab);//求过点c且与ab垂直的直线的截距
	   if((x >= 0 && x <= border) ||(x <= 0 && x >= border)){//求点到线段的距离
		   double footx = x/(kab - 1/kab);
		   double footy = x/(1-1/(kab*kab));
		   double dist = Math.sqrt((footx-cx)*(footx-cx)+(footy-cy)*(footy-cy));
		   return dist;
	   }
	   else{
		   double acdist = geoDisatance(a.lng,a.lat,c.lng,c.lat);
		   double bcdist = geoDisatance(b.lng,b.lat,c.lng,c.lat);
		   return (acdist < bcdist) ? acdist : bcdist; 
	   }
   }
   
   /*public static void main(String[] args){
	   System.out.print(geoDisatance(116.32227422857,39.997933574857,116.31148288448,40.045714277273));
   }*/
   
}
   
 