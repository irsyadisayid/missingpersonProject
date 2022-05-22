package com.example.daftarpencarianorang.Remote;

import java.util.List;

import com.example.daftarpencarianorang.Model.DataItemMissing;
import com.google.gson.annotations.SerializedName;

public class ResponseMissingPerson{

	@SerializedName("data")
	private List<DataItemMissing> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public List<DataItemMissing> getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}
}