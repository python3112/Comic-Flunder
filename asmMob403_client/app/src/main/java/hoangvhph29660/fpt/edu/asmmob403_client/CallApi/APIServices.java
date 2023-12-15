package hoangvhph29660.fpt.edu.asmmob403_client.CallApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import hoangvhph29660.fpt.edu.asmmob403_client.model.UserModel;
import hoangvhph29660.fpt.edu.asmmob403_client.model.cateModel;
import hoangvhph29660.fpt.edu.asmmob403_client.model.cmtModel;
import hoangvhph29660.fpt.edu.asmmob403_client.model.comicModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIServices {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
    APIServices apiServices = new Retrofit.Builder()
            .baseUrl("http://192.168.1.89:3000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIServices.class);

    @GET("apiUser/users/login/{username}/{password}")
    Call<UserModel> loginUser(@Path("username") String username , @Path("password") String password );

    @GET("apiUser/users")
    Call<ArrayList<UserModel>> getAllUser();

    @GET("apiUser/users/{id}")
    Call<UserModel> getOneUser(@Path("id") String id);

    @POST("apiUser/users/register")
    Call<UserModel> PostUser(@Body UserModel userModel);
    @Multipart
    @PATCH("apiUser/users/update/{id}")
    Call<UserModel> patchUser(@Path("id") String id,
                          @Part("username") RequestBody username,
                          @Part("password") RequestBody password,
                          @Part("email") RequestBody email,
                          @Part("fullname") RequestBody fullname,
                          @Part("role") RequestBody role,
                          @Part MultipartBody.Part avata);

    @Multipart
    @PATCH("apiUser/users/update/{id}")
    Call<UserModel> patchUserNoImg(@Path("id") String id,
                              @Part("username") RequestBody username,
                              @Part("password") RequestBody password,
                              @Part("email") RequestBody email,
                              @Part("fullname") RequestBody fullname,
                              @Part("role") RequestBody role);



    @DELETE("apiUser/users/delete/{id}")
    Call<UserModel> deletUser(@Path("id") String id);


    ///////////// comic ////////////

    @GET("apiComic/getAll")
    Call<ArrayList<comicModel>> getAllComic();

    @GET("apiComic/getComicRamdom")
    Call<ArrayList<comicModel>> getRamdomComic();
    @GET("apiComic/getComicSlide")
    Call<ArrayList<comicModel>> getSlideComic();
    @GET("apiComic/{id}/readComic")
    Call<ArrayList<String>> getReadComic(@Path("id") String id);
    @GET("apiComic/{id}")
    Call<comicModel> getOneComic(@Path("id") String id);
    @GET("apiComic/getComicByidCate/{id}")
    Call<ArrayList<comicModel>> getAllComicByCate(@Path("id") String id);
        /////////// cate/////////////
    @GET("apiCate/getCate")
    Call<ArrayList<cateModel>> getAllCate();
    @GET("apiCate/getOneCate/{id}")
    Call<cateModel> getOneCate(@Path("id")String id);

    @GET("apiCate/getCateLimit")
    Call<ArrayList<cateModel>> getCatelimit();

    @PATCH("apiCate/updateCate/{id}")
    Call<cateModel> patchCate(@Path("id")String id , @Body cateModel cate);

    @POST("apiCate/addCate")
    Call<cateModel> postCate(@Body cateModel model);



    /////////////////comment ///////////


    @GET("apiCmt/getCmt/{id}")
    Call<ArrayList<cmtModel>> getCmtByid(@Path("id")String id);

    @POST("apiCmt/addCmt")
    Call<cmtModel> postComent( @Body cmtModel cmt);


    @PATCH("apiCmt/patchCmt/{id}")
    Call<cmtModel> patchComent(@Path("id")String id , @Body cmtModel cmt);

   @DELETE("apiCmt/deleteCmt/{id}")
    Call<cmtModel> deleteCmt(@Path("id") String id);




}
