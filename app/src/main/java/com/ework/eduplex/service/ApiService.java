package com.ework.eduplex.service;

import com.ework.eduplex.service.model.BaseEduResponse;
import com.ework.eduplex.service.model.edu.response.CheckTransResponse;
import com.ework.eduplex.service.model.edu.response.CheckTransResponseData;
import com.ework.eduplex.service.model.edu.response.LoginResponseData;
import com.ework.eduplex.service.model.edu.response.SecretResponse;
import com.ework.eduplex.service.model.edu.response.TableResponse;
import com.ework.eduplex.service.model.response.BaseResponse;
import com.ework.eduplex.service.model.response.CancelOrderResponse;
import com.ework.eduplex.service.model.response.CreditSubmissionResponse;
import com.ework.eduplex.service.model.response.DeleteMailResponse;
import com.ework.eduplex.service.model.response.EditProfileResponse;
import com.ework.eduplex.service.model.response.GetDescriptionTypeResponse;
//import com.ework.eduplex.service.model.response.GetEditProfileResponse;
import com.ework.eduplex.service.model.response.GetHomeResponse;
import com.ework.eduplex.service.model.response.GetProductResponse;
import com.ework.eduplex.service.model.response.GetTenorAngsuranResponse;
import com.ework.eduplex.service.model.response.GetTimelinesResponse;
import com.ework.eduplex.service.model.response.InputMyCreditDataResponse;
import com.ework.eduplex.service.model.response.InstallmentPaymentResponse;
import com.ework.eduplex.service.model.response.LogoutResponse;
import com.ework.eduplex.service.model.response.ProductDetailResponse;
import com.ework.eduplex.service.model.response.RedeemViaRefCodeResponse;
import com.ework.eduplex.service.model.response.RedeemViaVoucherResponse;
import com.ework.eduplex.service.model.response.RegisterVerifyResponse;
import com.ework.eduplex.service.model.response.ResetPasswordResponse;
import com.ework.eduplex.service.model.response.ResetPinResponse;
import com.ework.eduplex.service.model.response.LoginResponse;
import com.ework.eduplex.service.model.response.ReviewMyCreditSubmissionResponse;
import com.ework.eduplex.service.model.response.SignupResponse;
import com.ework.eduplex.service.model.response.SubmitFeedbackResponse;
import com.ework.eduplex.service.model.response.UbahPINResponse;
import com.ework.eduplex.service.model.response.UbahPasswordResponse;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by eWork on 3/11/2016.
 */
public interface ApiService {

    //API LOGIN - DONE
    @FormUrlEncoded
    @POST("/4eduplex/api/v1/custom/login")
    public Call<LoginResponse> login(@Field("email") String email,
                                     @Field("password") String password);

    //API LOGOUT- DONE
    @FormUrlEncoded
    @POST("/4eduplex/api/v1/customer/logout")
    public Call<LogoutResponse> logout(@Header("authorization") String authorization,
                                       @Field("token") String token);

    //API REFRESH TOKEN - DONE
    @POST("/4eduplex/api/v1/customer/relogin")
    @FormUrlEncoded
    public Call<LoginResponse> refresh_token(@Header("authorization") String authorization,
                                             @Field("user_id") String user_id,
                                             @Field("device_id") String device_id,
                                             @Field("token") String token);

    //API SIGNUP - DONE
    @FormUrlEncoded
    @POST("/4eduplex/api/v1/custom/register")
    public Call<SignupResponse> register(@Field("email") String email,
                                         @Field("password") String password,
                                         @Field("pin") String pin,
                                         @Field("name") String name,
                                         @Field("hp") String hp
    );


    //API GET TABLE - DONE
    @GET("/4eduplex/api/v1/customer/table")
    public Call<TableResponse> get_table(@Header("authorization") String authorization);


    //API CHECKIN - DONE
    @FormUrlEncoded
    @POST("/4eduplex/api/v1/customer/checkin")
    public Call<BaseResponse> checkin(@Header("authorization") String authorization,
                                      @Field("edu_table_id") String tableId
    );

    //API CHECKOUT - DONE
    @FormUrlEncoded
    @POST("/4eduplex/api/v1/customer/checkout")
    public Call<BaseResponse> checkout(@Header("authorization") String authorization,
                                      @Field("edu_table_id") String tableId
    );

    //API Get Secret - DONE
    @POST("/4eduplex/api/v1/customer/secret")
    public Call<SecretResponse> secret(@Header("authorization") String authorization
    );


    //API PAY - UNIMPLEMENTED
    @FormUrlEncoded
    @POST("/4eduplex/api/v1/customer/pay")
    public Call<BaseResponse> pay(@Header("authorization") String authorization,
                                       @Field("ammount") String ammount
    );

    //API ORDER - DONE
    @FormUrlEncoded
    @POST("/4eduplex/api/v1/customer/order")
    public Call<BaseEduResponse> order(@Header("authorization") String authorization,
                                       @Field("ammount") String ammount,
                                       @Field("menu_id") String menuId
    );

    //API Check Transaksi - DONE
    @FormUrlEncoded
    @POST("/4eduplex/api/v1/customer/checktrans")
    public Call<CheckTransResponse> checktrans(@Header("authorization") String authorization,
                                               @Field("secret") String secret,
                                               @Field("pair_id") String pairId
    );

    //API GET TIMELINES
    @FormUrlEncoded
    @POST("/4eduplex/api/v1/customer/history")
    public Call<GetTimelinesResponse> get_timelines(@Header("authorization") String authorization,
                                                    @Field("onpage") String onpage,
                                                    @Field("onsize") String onsize);


    //API GET PRODUCTS BY CATEGORY
    @FormUrlEncoded
    @POST("/4eduplex/api/v1/menu/search")
    public Call<GetProductResponse> get_products_by_category(@Field("category_id") String category_id,
                                                             @Field("onpage") String onpage,
                                                             @Field("onsize") String onsize,
                                                             @Field("branch_id") String branch_id);

    //API GET PRODUCTS BY SEARCH
    @FormUrlEncoded
    @POST("/4eduplex/api/v1/menu/search")
    public Call<GetProductResponse> get_products_by_search(@Field("name") String name,
                                                           @Field("onpage") String onpage,
                                                           @Field("onsize") String onsize,
                                                           @Field("branch_id") String branch_id);


    //API GET CUSTOMER BALANCE
    @POST("/4eduplex/api/v1/customer/getbalance")
    public Call<CheckTransResponse> getBalance(@Header("authorization") String authorization);


    //API EDIT PROFILE
    @FormUrlEncoded
    @POST("/api/v1/mobile/user/profile/edit")
    public Call<EditProfileResponse> edit_profile(@Header("authorization") String authorization,
                                                  @Field("email") String email,
                                                  @Field("name") String name,
                                                  @Field("ktp") String ktp,
                                                  @Field("address") String address,
                                                  @Field("provinsi") String provinsi,
                                                  @Field("kelurahan") String kelurahan,
                                                  @Field("kecamatan") String kecamatan,
                                                  @Field("kota") String kota,
                                                  @Field("kode_pos") String kode_pos,
                                                  @Field("handphone") String handphone,
                                                  @Field("home_phone") String home_phone,
                                                  @Field("birth_date") String birth_date);

    //API UBAH PASSWORD
    @FormUrlEncoded
    @POST("/api/v1/mobile/user/credential/change")
    public Call<UbahPasswordResponse> ubah_password(@Header("authorization") String authorization,
                                                    @Field("old_password") String old_password,
                                                    @Field("new_password") String new_password,
                                                    @Field("new_password_confirm") String new_password_confirm);

    //API UBAH PIN
    @FormUrlEncoded
    @POST("/api/v1/mobile/user/kptunai/pin/change")
    public Call<UbahPINResponse> ubah_pin(@Header("authorization") String authorization,
                                          @Field("old_pin") String old_pin,
                                          @Field("new_pin") String new_pin,
                                          @Field("new_pin_confirm") String new_pin_confirm);

    //API RESET PIN
    @FormUrlEncoded
    @POST("/api/v1/mobile/user/kptunai/pin/reset")
    public Call<ResetPinResponse> reset_pin(@Header("authorization") String authorization,
                                            @Field("secret_answer") String secret_answer);

    //API RESET PASSWORD
    @FormUrlEncoded
    @POST("/api/v1/mobile/user/credential/reset")
    public Call<ResetPasswordResponse> reset_password(@Field("email") String email);


    //API DELETE MAIL
    @FormUrlEncoded
    @POST("/api/v1/mobile/user/inbox/delete")
    public Call<DeleteMailResponse> delete_mail(@Header("authorization") String authorization,
                                                @Field("message_id") String message_id);

    //API SUBMIT FEEDBACK
    @FormUrlEncoded
    @POST("/api/v1/mobile/customer_care/add")
    public Call<SubmitFeedbackResponse> submit_feedback(@Header("authorization") String authorization,
                                                        @Field("message_type") String message_type,
                                                        @Field("subject") String subject,
                                                        @Field("body") String body);


    //API INPUT MY CREDIT DATA
    @FormUrlEncoded
    @POST("/api/v1/mobile/user/kreditku/add")
    public Call<InputMyCreditDataResponse> input_my_credit_data(@Header("authorization") String authorization,
                                                                @Field("job") String job,
                                                                @Field("company_name") String company_name,
                                                                @Field("company_address") String company_address,
                                                                @Field("company_city") String company_city,
                                                                @Field("company_phone") String company_phone,
                                                                @Field("mother_name") String mother_name,
                                                                @Field("emergency_contact_name") String emergency_contact_name,
                                                                @Field("emergency_contact_relation") String emergency_contact_relation,
                                                                @Field("emergency_contact_address") String emergency_contact_address,
                                                                @Field("emergency_contact_phone") String emergency_contact_phone,
                                                                @Field("ktp_photo") String ktp_photo_url);

    //API INPUT MY CREDIT DATA
    @FormUrlEncoded
    @POST("/api/v1/mobile/user/kreditku/add")
    public Call<InputMyCreditDataResponse> edit_my_credit_data(@Header("authorization") String authorization,
                                                               @Field("job") String job,
                                                               @Field("company_name") String company_name,
                                                               @Field("company_address") String company_address,
                                                               @Field("company_city") String company_city,
                                                               @Field("company_phone") String company_phone,
                                                               @Field("mother_name") String mother_name,
                                                               @Field("emergency_contact_name") String emergency_contact_name,
                                                               @Field("emergency_contact_relation") String emergency_contact_relation,
                                                               @Field("emergency_contact_address") String emergency_contact_address,
                                                               @Field("emergency_contact_phone") String emergency_contact_phone,
                                                               @Field("ktp_photo") String ktp_photo_url,
                                                               @Field("edit") String edit);



    //API REDEEM VIA REF CODE
    @FormUrlEncoded
    @POST("/api/v1/mobile/user/referal/add")
    public Call<RedeemViaRefCodeResponse> redeem_via_ref_code(@Header("authorization") String authorization,
                                                              @Field("referal_id") String referal_id);

    //API REDEEM VIA VOUCHER
    @FormUrlEncoded
    @POST("/api/v1/mobile/user/kptunai/debit/redeem")
    public Call<RedeemViaVoucherResponse> redeem_via_voucher(@Header("authorization") String authorization,
                                                             @Field("voucher_code") String voucher_code);

    //API CREDIT SUBMISSION
    @FormUrlEncoded
    @POST("/api/v1/mobile/user/credit/add")
    public Call<CreditSubmissionResponse> credit_submission(@Header("authorization") String authorization,
                                                            @Field("product_id") String product_id,
                                                            @Field("product_name") String product_name,
                                                            @Field("tenor") String tenor,
                                                            @Field("quantity") String quantity,
                                                            @Field("asset_brand") String asset_brand,
                                                            @Field("asset_type") String asset_type,
                                                            @Field("asset_otr") String asset_otr,
                                                            @Field("asset_year") String asset_year,
                                                            @Field("asset_owner") String asset_owner,
                                                            @Field("asset_needs_amount") String asset_needs_amount,
                                                            @Field("asset_foto_1") String asset_foto_1,
                                                            @Field("asset_foto_2") String asset_foto_2,
                                                            @Field("asset_foto_3") String asset_foto_3,
                                                            @Field("asset_document_1") String asset_document_1,
                                                            @Field("asset_document_2") String asset_document_2,
                                                            @Field("asset_document_3") String asset_document_3,
                                                            @Field("job") String job,
                                                            @Field("company_name") String company_name,
                                                            @Field("company_address") String company_address,
                                                            @Field("company_city") String company_city,
                                                            @Field("company_phone") String company_phone,
                                                            @Field("mother_name") String mother_name,
                                                            @Field("emergency_contact_name") String emergency_contact_name,
                                                            @Field("emergency_contact_relation") String emergency_contact_relation,
                                                            @Field("emergency_contact_address") String emergency_contact_address,
                                                            @Field("emergency_contact_phone") String emergency_contact_phone,
                                                            @Field("ktp_photo") String ktp_photo,
                                                            @Field("downpayment") String downpayment,
                                                            @Field("installment") String installment,
                                                            @Field("emergency_contact_village_code") String emergency_contact_village_code,
                                                            @Field("company_village_code") String company_village_code,
                                                            @Field("is_promo") String is_promo,
                                                            @Field("branch_id") String branch_id,
                                                            @Field("product_branch_id") String product_branch_id);


    //API CREDIT SUBMISSION
    @FormUrlEncoded
    @POST("/api/v1/mobile/user/credit/add")
    public Call<CreditSubmissionResponse> credit_submission(@Header("authorization") String authorization,
                                                            @Field("asset_needs_amount") String asset_needs_amount,
                                                            @Field("product_id") String product_id,
                                                            @Field("product_name") String product_name,
                                                            @Field("tenor") String tenor,
                                                            @Field("quantity") String quantity,
                                                            @Field("job") String job,
                                                            @Field("company_name") String company_name,
                                                            @Field("company_address") String company_address,
                                                            @Field("company_city") String company_city,
                                                            @Field("company_phone") String company_phone,
                                                            @Field("mother_name") String mother_name,
                                                            @Field("emergency_contact_name") String emergency_contact_name,
                                                            @Field("emergency_contact_relation") String emergency_contact_relation,
                                                            @Field("emergency_contact_address") String emergency_contact_address,
                                                            @Field("emergency_contact_phone") String emergency_contact_phone,
                                                            @Field("ktp_photo") String ktp_photo,
                                                            @Field("downpayment") String downpayment,
                                                            @Field("installment") String installment,
                                                            @Field("emergency_contact_village_code") String emergency_contact_village_code,
                                                            @Field("company_village_code") String company_village_code,
                                                            @Field("is_promo") String is_promo,
                                                            @Field("branch_id") String branch_id,
                                                            @Field("product_branch_id") String product_branch_id);



    //API INSTALLMENT PAYMENT
    @FormUrlEncoded
    @POST("/api/v1/mobile/user/kptunai/credit/installment/payment")
    public Call<InstallmentPaymentResponse> installment_payment(@Header("authorization") String authorization,
                                                                @Field("agreement_id") String agreement_id,
                                                                @Field("sequence_id") String sequence_id,
                                                                @Field("amount_paid") String amount_paid,
                                                                @Field("pin") String pin);




    //API GET PRODUCT DETAIL
    @GET("/api/v1/mobile/product")
    public Call<ProductDetailResponse> get_product_detail(@Query("product_id") String product_id,
                                                          @Query("branch_id") String branch_id,
                                                          @Query("is_promo") String is_promo);


    //API REVIEW MY CREDIT SUBMISSION
    @POST("/api/v1/mobile/user/credit/review")
    @FormUrlEncoded
    public Call<ReviewMyCreditSubmissionResponse> review_my_credit_submission(@Header("authorization") String authorization,
                                                                              @Field("submission_id") String submission_id,
                                                                              @Field("rate") String rate,
                                                                              @Field("comment") String comment);

    //API GET HOME
    @GET("/api/v1/mobile/home")
    public Call<GetHomeResponse> get_home(@Query("branch_id") String branch_id);


    //API GET USER TYPE IN DESCRIPTION
    @POST("api/v1/mobile/user/asset/gettype")
    @FormUrlEncoded
    public Call<GetDescriptionTypeResponse> get_user_type(@Header("authorization") String authorization,
                                                          @Field("asset") String asset,
                                                          @Field("brand") String brand);


    //API GET TENOR ANGSURAN MOTOR
    @POST("/api/v1/mobile/user/product/formula/param/motor/calculate")
    @FormUrlEncoded
    public Call<GetTenorAngsuranResponse> get_tenor_angsuran_motor(@Header("authorization") String authorization,
                                                                   @Field("product_id") String product_id,
                                                                   @Field("work_id") String work_id,
                                                                   @Field("asset_owner_id") String asset_owner_id,
                                                                   @Field("year_id") String year_id,
                                                                   @Field("brand_id") String brand_id,
                                                                   @Field("price") String price,
                                                                   @Field("motor_price") String motor_price,
                                                                   @Field("motor_downpayment") String motor_downpayment);

    //API GET TENOR ANGSURAN MOBIL
    @POST("/api/v1/mobile/user/product/formula/param/mobil/calculate")
    @FormUrlEncoded
    public Call<GetTenorAngsuranResponse> get_tenor_angsuran_mobil(@Header("authorization") String authorization,
                                                                   @Field("product_id") String product_id,
                                                                   @Field("car_type_id") String car_type_id,
                                                                   @Field("manufacturer_id") String manufacturer_id,
                                                                   @Field("year_id") String year_id);



    //API GET TENOR ANGSURAN KMOB
    @POST("/api/v1/mobile/user/product/formula/param/dana/tunai/calculate")
    @FormUrlEncoded
    public Call<GetTenorAngsuranResponse> get_tenor_angsuran_dana_tunai(@Header("authorization") String authorization,
                                                                        @Field("car_type") String car_type,
                                                                        @Field("manufacturer_id") String manufacturer_id,
                                                                        @Field("year_id") String year_id,
                                                                        @Field("nilai_pinjaman") String nilai_pinjaman,
                                                                        @Field("otr") String otr);

    //API GET TENOR ANGSURAN KMOB
    @POST("/api/v1/mobile/user/profile/verify")
    @FormUrlEncoded
    public Call<RegisterVerifyResponse> register_verify(@Field("user_id") String user_id,
                                                        @Field("code") String code,
                                                        @Field("email") String email,
                                                        @Field("password") String password);


}











