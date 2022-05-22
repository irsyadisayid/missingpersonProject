package com.example.daftarpencarianorang.Remote;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    //    Login
    @POST("/login")
    @FormUrlEncoded
    Call<ResponseLogin> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @POST("/register")
    @FormUrlEncoded
    Call<ResponseRegister> register(
            @Field("nama") String nama,
            @Field("ttl") String ttl,
            @Field("agama") String agama,
            @Field("pekerjaan") String pekerjaan,
            @Field("kewarganegaraan") String kewarganegaraan,
            @Field("alamat") String alamat,
            @Field("nohp") String nohp,
            @Field("email") String email,
            @Field("password") String password
    );

    @Multipart
    @POST("/daftarpo")
    Call<Responses> insertMissing(
            @Header("Authorization") String jwt,
            @Part("nama") RequestBody nama,
            @Part("ttl") RequestBody ttl,
            @Part("jekel") RequestBody jekel,
            @Part("tb") RequestBody tb,
            @Part("rambut") RequestBody rambut,
            @Part("kulit") RequestBody kulit,
            @Part("mata") RequestBody mata,
            @Part("cirik") RequestBody cirik,
            @Part("tglhilang") RequestBody tglHilang,
            @Part("infot") RequestBody infot,
            @Part MultipartBody.Part image
    );

    @Multipart
    @POST("/daftarpo/{id}")
    Call<Responses> editMissing(
            @Header("Authorization") String jwt,
            @Path("id") int id,
            @Part("id_user") RequestBody idUser,
            @Part("nama") RequestBody nama,
            @Part("ttl") RequestBody ttl,
            @Part("jekel") RequestBody jekel,
            @Part("tb") RequestBody tb,
            @Part("rambut") RequestBody rambut,
            @Part("kulit") RequestBody kulit,
            @Part("mata") RequestBody mata,
            @Part("cirik") RequestBody cirik,
            @Part("tglhilang") RequestBody tglHilang,
            @Part("infot") RequestBody infot,
            @Part MultipartBody.Part image
    );


    @GET("/daftarpoall")
    Call<ResponseMissingPerson> getAllMissing(
            @Header("Authorization") String jwt
    );

    @GET("/daftarpoid")
    Call<ResponseMissingPerson> getAllMissingUser(
            @Header("Authorization") String jwt
    );

    @DELETE("/daftarpo/{id}")
    Call<ResponseMissingPerson> delete(
            @Header("Authorization") String jwt,
            @Path("id") int id
    );

}
