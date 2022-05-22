package com.example.daftarpencarianorang.Remote;

import com.google.gson.annotations.SerializedName;

public class DataItemMissing{
	@SerializedName("id")
	private int id;

	@SerializedName("nama")
	private String nama;

	@SerializedName("ttl")
	private String ttl;

	@SerializedName("jekel")
	private String jekel;

	@SerializedName("tb")
	private String tb;

	@SerializedName("rambut")
	private String rambut;

	@SerializedName("kulit")
	private String kulit;

	@SerializedName("mata")
	private String mata;

	@SerializedName("cirik")
	private String cirik;

	@SerializedName("tglhilang")
	private String tglhilang;

	@SerializedName("infot")
	private String infot;

	@SerializedName("photo")
	private String photo;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("updated_at")
	private String updatedAt;


	public int getId(){
		return id;
	}

	public String getNama(){
		return nama;
	}

	public String getTtl(){
		return ttl;
	}

	public String getJekel(){
		return jekel;
	}

	public String getTb(){
		return tb;
	}

	public String getRambut(){
		return rambut;
	}

	public String getKulit(){
		return kulit;
	}

	public String getMata() {
		return mata;
	}

	public String getCirik() {
		return cirik;
	}

	public String getTglhilang(){
		return tglhilang;
	}

	public String getInfot() {
		return infot;
	}

	public String getPhoto(){
		return photo;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}