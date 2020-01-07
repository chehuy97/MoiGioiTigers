package com.example.moigioitiges.remote;

import com.example.moigioitiges.model.Broker;
import com.example.moigioitiges.model.Catalog;
import com.example.moigioitiges.model.CatalogDetail;
import com.example.moigioitiges.model.Currency;
import com.example.moigioitiges.model.Owner;
import com.example.moigioitiges.model.CityIndex;
import com.example.moigioitiges.model.Customer;
import com.example.moigioitiges.model.DistrictIndex;
import com.example.moigioitiges.model.LoginResponse;
import com.example.moigioitiges.model.ProductResponse;
import com.example.moigioitiges.model.RequestChangePass;
import com.example.moigioitiges.model.UserLogin;
import com.example.moigioitiges.model.WardIndex;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("rest/login")
    Call<LoginResponse> login(@Body UserLogin userLogin);
    @GET("a/broker/detail")
    Call<Broker> getBrokerDetail(@Header("Authorization") String authorization);

    @GET("a/broker/listPostApproved")
    Call<List<ProductResponse>>getListApprovedPostOfBroker(@Header("Authorization") String authorization);

    @GET("a/broker/listPostPending")
    Call<List<ProductResponse>>getListPostPendingOfBroker(@Header("Authorization") String authorization);

    @GET("a/broker/post/{index}")
    Call<ProductResponse>getPostDetailOfBroker(@Header("Authorization") String authorization,
                                               @Path("index") int index);
    @Multipart
    @PUT("a/broker/editProfileBroker")
    Call<String>editBroker(@Header("Authorization") String authorization,
                           @Part("brokerObject") RequestBody jsonBroker,
                           @Part MultipartBody.Part file);

    @POST("a/broker/change-password-profile")
    Call<String>updatePassword(@Header("Authorization") String authorization,
                               @Body RequestChangePass requestChangePass);

    @Multipart
    @POST("a/broker/addNewPostAndUploadFile")
    Call<String>addNewPost(@Header("Authorization") String authorization,
                           @Part("newPost") RequestBody jsonPost,
                           @Part MultipartBody.Part file);

    @GET("a/broker/listCustomers")
    Call<List<Customer>>getListCustomer(@Header("Authorization") String authorization);

    @GET("a/broker/customers/{index}")
    Call<Customer>getCustomerById(@Header("Authorization") String authorization,
                                  @Path("index") int index);
    @GET("a/broker/listOwnersWithBroker")
    Call<List<Owner>> getListOwner(@Header("Authorization") String authorization);
    @GET("a/broker/getAllPostWithBroker")
    Call<List<ProductResponse>> getListProductPayed(@Header("Authorization") String authorization);

    @Multipart
    @PUT("a/broker/editPostBrokerWithImage")
    Call<String> editPost(@Header("Authorization") String authorization,
                          @Part("postObject") RequestBody jsonEditPost,
                          @Part MultipartBody.Part file);
    @DELETE("a/broker/post/{index}")
    Call<String> deletePost(@Header("Authorization") String authorization,
                            @Path("index") int index);

    @GET("a/broker/listCurrencys")
    Call<List<Currency>> getListCurrency(@Header("Authorization") String authorization);
//    @GET("detailProductPublic/{index}")
//    Call<ProductResponse> getDetailProduct(@Path("index") int index);

    @GET("listAllPostPublic")
    Call<List<ProductResponse>> getListProduct();

    @GET("detailProductPublic/{index}")
    Call<ProductResponse> getProductDetail(@Path("index") int index);

    @GET("position/listCity")
    Call<List<CityIndex>> getListCity();
    @GET("position/city/{index}")
    Call<List<DistrictIndex>> getListDistrict(@Path("index") int index);
    @GET("position/city/district/{index}")
    Call<List<WardIndex>> getListWard(@Path("index") int index);
    @GET("listCatalogPublic")
    Call<List<Catalog>> getListCatalog();
    @GET("catalogPublic/{index}")
    Call<List<CatalogDetail>> getListTypeCatalog(@Path("index") int index);

    @GET("searchPublic/{type}")
    Call<List<ProductResponse>> getProductResult(
            @Path("type") int type,
            @Query("zipcode") String zip);
}


