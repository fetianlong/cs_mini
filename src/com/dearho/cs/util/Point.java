package com.dearho.cs.util;

public class Point{
	private double lat;
	private double lng;
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLat() {
		return lat;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public double getLng() {
		return lng;
	}
	
	public Point() {
		
	}
	public Point(double lat,double lng) {
		this.lat = lat;
		this.lng = lng;
	}
}
