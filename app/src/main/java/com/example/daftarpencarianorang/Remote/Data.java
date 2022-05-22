package com.example.daftarpencarianorang.Remote;

import com.google.gson.annotations.SerializedName;

public class Data{
	@SerializedName("jwt")
	private String jwt;

	@SerializedName("id")
	private int id;

	@SerializedName("nama")
	private String nama;

	@SerializedName("ttl")
	private String ttl;

	@SerializedName("agama")
	private String agama;

	@SerializedName("pekerjaan")
	private String pekerjaan;

	@SerializedName("kewarganegaraan")
	private String kewarganegaraan;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("nohp")
	private String nohp;

	@SerializedName("email")
	private String email;

	@SerializedName("password")
	private String password;


	@SerializedName("updated_at")
	private String updatedAt;


	@SerializedName("created_at")
	private String createdAt;


	public String getJwt(){
		return jwt;
	}

	public int getId(){
		return id;
	}

	public String getNama(){
		return nama;
	}

	public String getTtl(){
		return ttl;
	}

	public String getAgama(){
		return agama;
	}

	public String getPekerjaan(){
		return pekerjaan;
	}

	public String getKewarganegaraan(){
		return kewarganegaraan;
	}

	public String getAlamat(){
		return alamat;
	}

	public String getNohp(){
		return nohp;
	}

	public String getEmail(){
		return email;
	}

	public String getPassword(){
		return password;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}