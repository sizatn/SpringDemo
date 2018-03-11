package com.sizatn.springdemo.common.utils.latlng;

/**
 * 
 * @desc 根据给定经纬度(lat,lng)求出其左上角(left_top),右上角(right_top),左下角(left_bottom)，右下角(right_bottom)的四个位置。所有在这个区域的范围都在该点附近。
 * @author sizatn
 * @date Mar 5, 2018
 */
public class LatitudeLongtitudeUtil {

	// http://blog.charlee.li/location-search/

	/** 地球半径 地球是一个近乎标准的椭球体，它的赤道半径为6378.140千米，极半径为6356.755千米，平均半径6371.004千米。如果我们假设地球是一个完美的球体，那么它的半径就是地球的平均半径 */
	private static final double EARTH_RADIUS = 6371000;

	/** 范围距离 */
	private double distance;

	/** 左上角 */
	private LatLng left_top = null;

	/** 右上角 */
	private LatLng right_top = null;

	/** 左下角 */
	private LatLng left_bottom = null;

	/** 右下角 */
	private LatLng right_bottom = null;

	private LatitudeLongtitudeUtil(double distance) {  
        this.distance = distance;  
    }

	private void getRectangle4Point(double lat, double lng) {

		// float dlng = 2 * asin(sin(distance / (2 * EARTH_RADIUS)) / cos(lat));
		// float dlng = degrees(dlng) // 弧度转换成角度
		double dlng = 2 * Math.asin(Math.sin(distance / (2 * EARTH_RADIUS)) / Math.cos(lat));
		dlng = Math.toDegrees(dlng);

		// dlat = distance / EARTH_RADIUS
		// dlng = degrees(dlat) # 弧度转换成角度
		double dlat = distance / EARTH_RADIUS;
		dlat = Math.toDegrees(dlat); // # 弧度转换成角度

		// left-top : (lat + dlat, lng - dlng)
		// right-top : (lat + dlat, lng + dlng)
		// left-bottom : (lat - dlat, lng - dlng)
		// right-bottom: (lat - dlat, lng + dlng)
		left_top = new LatLng(lat + dlat, lng - dlng);
		right_top = new LatLng(lat + dlat, lng + dlng);
		left_bottom = new LatLng(lat - dlat, lng - dlng);
		right_bottom = new LatLng(lat - dlat, lng + dlng);

	}

	public static double hav(double theta) {
		double s = Math.sin(theta / 2);
		return s * s;
	}

	public static double getDistance(double lat0, double lng0, double lat1, double lng1) {
		// from math import sin, asin, cos, radians, fabs, sqrt

		// def hav(theta):
		// s = sin(theta / 2)
		// return s * s

		// def get_distance_hav(lat0, lng0, lat1, lng1):
		// "用haversine公式计算球面两点间的距离。"
		// # 经纬度转换成弧度
		// lat0 = radians(lat0)
		// lat1 = radians(lat1)
		// lng0 = radians(lng0)
		// lng1 = radians(lng1)

		// dlng = fabs(lng0 - lng1)
		// dlat = fabs(lat0 - lat1)
		// h = hav(dlat) + cos(lat0) * cos(lat1) * hav(dlng)
		// distance = 2 * EARTH_RADIUS * asin(sqrt(h))

		// return distance
		lat0 = Math.toRadians(lat0);
		lat1 = Math.toRadians(lat1);
		lng0 = Math.toRadians(lng0);
		lng1 = Math.toRadians(lng1);

		double dlng = Math.abs(lng0 - lng1);
		double dlat = Math.abs(lat0 - lat1);
		double h = hav(dlat) + Math.cos(lat0) * Math.cos(lat1) * hav(dlng);
		double distance = 2 * EARTH_RADIUS * Math.asin(Math.sqrt(h));
		return distance;
	}

	public static LatLng[] getRectangle4Point(double lat, double lng, double distance) {
		LatitudeLongtitudeUtil llu = new LatitudeLongtitudeUtil(distance);
		llu.getRectangle4Point(lat, lng);
		LatLng[] latLngs = new LatLng[4];
		latLngs[0] = llu.left_top;
		latLngs[1] = llu.right_top;
		latLngs[2] = llu.left_bottom;
		latLngs[3] = llu.right_bottom;
		return latLngs;
	}

	public static void main(String[] args) {
		double lat = 30.500;
		double lng = 120.500;
		double distance = 500d;
		LatLng[] latLngs = LatitudeLongtitudeUtil.getRectangle4Point(lat, lng, distance);
		String sql = "SELECT * FROM place WHERE lat > " + latLngs[2].getLatitude() + " AND lat < "
				+ latLngs[0].getLatitude() + " AND lng > " + latLngs[0].getLongitude() + " AND lng < "
				+ latLngs[1].getLongitude();
		System.out.println(sql);

		double lat1 = 30.495503391970406;
		double lng1 = 120.49261708577215;
		double d = LatitudeLongtitudeUtil.getDistance(lat, lng, lat1, lng1);
		System.out.println(d);
	}
}
