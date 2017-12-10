package com.karthik.splash.Models.PhotoDetail;


import com.google.gson.annotations.SerializedName;


public class Exif{

	@SerializedName("exposure_time")
	private String exposureTime;

	@SerializedName("aperture")
	private String aperture;

	@SerializedName("focal_length")
	private String focalLength;

	@SerializedName("iso")
	private int iso;

	@SerializedName("model")
	private String model;

	@SerializedName("make")
	private String make;
}