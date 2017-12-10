package com.karthik.splash.Models.PhotoDetail;


import com.google.gson.annotations.SerializedName;


public class Location{

	@SerializedName("country")
	public String country;

	@SerializedName("city")
	public String city;

	@SerializedName("position")
	public Position position;
}