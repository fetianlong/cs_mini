package com.dearho.cs.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class GpsTransUtil {
	/**这里先调用gps转谷歌坐标的方式后，在调用转百度坐标，因为暂时没有直接由GPS转百度坐标的方法**/

	private static final double pi = 3.14159265358979324;
	private static final Log log = LogFactory.getLog(GpsTransUtil.class);
	
	public static void main(String[] args) {
		Point result = GpsTransUtil.transform(39.802574, 116.49522);
		String latstr = ""+ result.getLat();
		String lngstr = ""+ result.getLng();
		latstr = latstr.substring(0, 8);
		lngstr = lngstr.substring(0, 8);
		System.out.println(latstr+";"+lngstr);
		result = GpsTransUtil.bd_encrypt(Double.parseDouble(latstr), Double.parseDouble(lngstr));
		log.info(result.getLat()+","+result.getLng());
//		Point p = GpsTransUtil.Map_tx2bd(39.80246, 116.4956);
		System.out.println(result.getLat()+":"+result.getLng());
		
		Point p = transPoint(39.8033066, 116.4983883);
		System.out.println(p.getLat()+";"+p.getLng());
		
		double d = getDistance(39.7999433, 116.5590416, 39.8118816, 116.5466466);
		System.out.println(d);
	}

	public static Point transPoint(double lat,double lng){
		Point result = transform(lat, lng);
		result = bd_encrypt(result.getLat(), result.getLng());
		return result;
	}
	public static List<Point> transMore(List<Point> point){
		List<Point> results = new ArrayList<Point>();
		for (Point result : point) {
			results.add(transPoint(result.getLat(), result.getLng()));
		}
		return results;
	}
    //
    // Krasovsky 1940
    //
    // a = 6378245.0, 1/f = 298.3
    // b = a * (1 - f)
    // ee = (a^2 - b^2) / a^2;
    private static final double a = 6378245.0;
    private static final double ee = 0.00669342162296594323;
    private static final double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

    /// <summary>
    /// GPS转google坐标进行转换
    /// </summary>
    /// <param name="wgLat"></param>
    /// <param name="wgLon"></param>
    /// <param name="mgLat"></param>
    /// <param name="mgLon"></param>
    public static Point transform(double wgLat, double wgLon)
    {
    	Point result = new Point();
        if (outOfChina(wgLat, wgLon))
        {
        	result.setLat(wgLat);
        	result.setLng(wgLon);
            return result;
        }
        double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
        double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
        double radLat = wgLat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        result.setLat(wgLat + dLat);
        result.setLng(wgLon + dLon);
        return result;
    }
    /// <summary>
    /// 
    /// </summary>
    /// <param name="lat"></param>
    /// <param name="lon"></param>
    /// <returns></returns>
    static boolean outOfChina(double lat, double lon)
    {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }
    /// <summary>
    /// gps坐标转谷歌坐标
    /// </summary>
    /// <param name="x"></param>
    /// <param name="y"></param>
    /// <returns></returns>
    static double transformLat(double x, double y)
    {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }
    /// <summary>
    /// gps坐标转谷歌坐标
    /// </summary>
    /// <param name="x"></param>
    /// <param name="y"></param>
    /// <returns></returns>
    private static double transformLon(double x, double y)
    {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }

    

    /// <summary>
    /// 谷歌坐标转百度坐标
    /// </summary>
    /// <param name="gg_lat"></param>
    /// <param name="gg_lon"></param>
    /// <param name="bd_lat"></param>
    /// <param name="bd_lon"></param>
    public static Point bd_encrypt(double gg_lat, double gg_lon)
    {
    	Point result = new Point();
        double x = gg_lon, y = gg_lat;

        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);

        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);

        result.setLng(z * Math.cos(theta) + 0.0065);

        result.setLat(z * Math.sin(theta) + 0.006);
        return result;
    }

    /// <summary>
    /// 百度坐标转谷歌坐标
    /// </summary>
    /// <param name="bd_lat"></param>
    /// <param name="bd_lon"></param>
    /// <param name="gg_lat"></param>
    /// <param name="gg_lon"></param>
    public static Point bd_decrypt(double bd_lat, double bd_lon)
    {
    	Point result = new Point();
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;

        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);

        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);

        result.setLat(z * Math.cos(theta));

        result.setLng(z * Math.sin(theta));

        return result;
    }
    
    /**
	 * 坐标转换，腾讯地图转换成百度地图坐标
	 * @param lat 腾讯纬度
	 * @param lon 腾讯经度
	 * @return 返回结果：经度,纬度
	 */
	public static Point Map_tx2bd(double lat, double lon){
		Point result = new Point();
	    double x = lon, y = lat;
	    double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * pi);
	    double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * pi);
	    result.setLng(z * Math.cos(theta) + 0.0065);
	    result.setLat(z * Math.sin(theta) + 0.006);
	    return result;
	}

	 
	/**
	 * 坐标转换，百度地图坐标转换成腾讯地图坐标
	 * @param lat  百度坐标纬度
	 * @param lon  百度坐标经度
	 * @return 返回结果：纬度,经度
	 */
	public static Point Map_bd2tx(double lat, double lon){
		Point result = new Point();
		double x = lon - 0.0065, y = lat - 0.006;
	    double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * pi);
	    double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * pi);
	    result.setLng(z * Math.cos(theta));
	    result.setLat(z * Math.sin(theta));
	    return result;
	}
	
	private static double EARTH_RADIUS = 6378.137;
	private static double rad(double d)
	{
	    return d * Math.PI / 180.0;
	}
	public static double getDistance(double lat1, double lng1, double lat2, double lng2)
	{
	    double radLat1 = rad(lat1);
	    double radLat2 = rad(lat2);
	    double a = radLat1 - radLat2;
	    double b = rad(lng1) - rad(lng2);
	    double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
	     Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
	    s = s * EARTH_RADIUS;
	    s = Math.round(s * 10000);
	    return s;
	}
	
	
}

