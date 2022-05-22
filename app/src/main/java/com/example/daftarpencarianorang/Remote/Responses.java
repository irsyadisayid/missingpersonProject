package com.example.daftarpencarianorang.Remote;

import com.google.gson.annotations.SerializedName;

public class Responses{

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}
}