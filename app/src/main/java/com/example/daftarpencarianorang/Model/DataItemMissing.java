package com.example.daftarpencarianorang.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class DataItemMissing implements Parcelable {
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

    protected DataItemMissing(Parcel in) {
        id = in.readInt();
        nama = in.readString();
        ttl = in.readString();
        jekel = in.readString();
        tb = in.readString();
        rambut = in.readString();
        kulit = in.readString();
        mata = in.readString();
        cirik = in.readString();
        tglhilang = in.readString();
        infot = in.readString();
        photo = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
    }

    public static final Creator<DataItemMissing> CREATOR = new Creator<DataItemMissing>() {
        @Override
        public DataItemMissing createFromParcel(Parcel in) {
            return new DataItemMissing(in);
        }

        @Override
        public DataItemMissing[] newArray(int size) {
            return new DataItemMissing[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getJekel() {
        return jekel;
    }

    public void setJekel(String jekel) {
        this.jekel = jekel;
    }

    public String getTb() {
        return tb;
    }

    public void setTb(String tb) {
        this.tb = tb;
    }

    public String getRambut() {
        return rambut;
    }

    public void setRambut(String rambut) {
        this.rambut = rambut;
    }

    public String getKulit() {
        return kulit;
    }

    public void setKulit(String kulit) {
        this.kulit = kulit;
    }

    public String getMata() {
        return mata;
    }

    public void setMata(String mata) {
        this.mata = mata;
    }

    public String getCirik() {
        return cirik;
    }

    public void setCirik(String cirik) {
        this.cirik = cirik;
    }

    public String getTglhilang() {
        return tglhilang;
    }

    public void setTglhilang(String tglhilang) {
        this.tglhilang = tglhilang;
    }

    public String getInfot() {
        return infot;
    }

    public void setInfot(String infot) {
        this.infot = infot;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nama);
        parcel.writeString(ttl);
        parcel.writeString(jekel);
        parcel.writeString(tb);
        parcel.writeString(rambut);
        parcel.writeString(kulit);
        parcel.writeString(mata);
        parcel.writeString(cirik);
        parcel.writeString(tglhilang);
        parcel.writeString(infot);
        parcel.writeString(photo);
        parcel.writeString(createdAt);
        parcel.writeString(updatedAt);
    }
}