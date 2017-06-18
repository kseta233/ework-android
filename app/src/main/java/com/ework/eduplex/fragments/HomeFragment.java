package com.ework.eduplex.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ework.eduplex.R;
import com.ework.eduplex.activities.TableCheckoutActivity;
import com.ework.eduplex.activities.TarikKPTunaiPinActivity;
import com.ework.eduplex.activities.eduplex.TableActivity;
import com.ework.eduplex.activities.katalog.KatalogBarangTabsActivity;
import com.ework.eduplex.activities.supportandnotification.FAQActivity;
import com.ework.eduplex.adapters.BannerPagerAdapter;
import com.ework.eduplex.service.model.Category;
import com.ework.eduplex.service.model.GetHomeData;
import com.ework.eduplex.service.model.KPTunai;
import com.ework.eduplex.service.model.Product;
import com.ework.eduplex.service.model.Promo;
import com.ework.eduplex.service.model.edu.response.CheckTransResponse;
import com.ework.eduplex.service.model.response.LoginResponse;
import com.ework.eduplex.utils.Constant;
import com.ework.eduplex.utils.Utils;
import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class HomeFragment extends BaseFragment {

    public static GetHomeData getHomeData;
    List<Product> hotProducts;
    BannerPagerAdapter adapter;

    //addition for saldo
    LoginResponse loginResponse;
    @Bind(R.id.tvSisaSaldo)
    TextView tvSisaSaldo;
    @Bind(R.id.tvKodeReferensi)
    TextView tvKodeReferensi;
    Gson gson;
    String saldo;


    @Bind(R.id.vpBanner)
    ViewPager vpBanner;
    @Bind(R.id.cpiBanner)
    CirclePageIndicator cpiBanner;


    // item for menu
    //list : menu, table, top up, call cashier
    @Bind(R.id.ivMenu)
    ImageView ivMenu;
    @Bind(R.id.tvMenu)
    TextView tvMenu;
    @Bind(R.id.llMenu)
    LinearLayout llMenu;

    //table -> see table
    @Bind(R.id.ivTable)
    ImageView ivTable;
    @Bind(R.id.tvTable)
    TextView tvTable;
    @Bind(R.id.llTable)
    LinearLayout llTable;

    //top up -> go to top up
    @Bind(R.id.ivTopUp)
    ImageView ivTopUp;
    @Bind(R.id.tvTopUp)
    TextView tvTopUp;
    @Bind(R.id.llTopUp)
    LinearLayout llTopUp;

    //call cashier
    @Bind(R.id.ivCashier)
    ImageView ivCashier;
    @Bind(R.id.tvCashier)
    TextView tvCashier;
    @Bind(R.id.llCashier)
    LinearLayout llCashier;

//    @Bind(R.id.ivModalKerja)
//    ImageView ivModalKerja;
//    @Bind(R.id.tvModalKerja)
//    TextView tvModalKerja;
//    @Bind(R.id.llModalKerja)
//    LinearLayout llModalKerja;

//    SharedPreferences myPrefs;

//    @Bind(R.id.textView)
//    TextView textView;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.cvBanner)
    CardView cvBanner;
//    @Bind(R.id.rlBlankSlateBarangTerlaris)
//    RelativeLayout rlBlankSlateBarangTerlaris;



    int position = 0;
    private Handler handler = new Handler();
    private Runnable runnable;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);

        log("currentbranchid", getBranchId());

        initComponents();

        //GetHome
        setSizeOfBanner();

        //for API ON:
//        this.requestGetHome(getBranchId());
        this.requestGetHomeNew();
        this.setPullToRefreshComponents();

        return rootView;
    }

    /**
     * This method is used to init basic components.
     */
    private void initComponents() {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics());
        int squareSize = getScreenWidth(getActivity())/3 - (int) pixels;

        ViewGroup.LayoutParams params = llMenu.getLayoutParams();
        params.width = squareSize;
        params.height = squareSize;

        llMenu.setLayoutParams(params);
        llCashier.setLayoutParams(params);
        llTable.setLayoutParams(params);
        llTopUp.setLayoutParams(params);

//        myPrefs = getMySharedPreferences();
    }

    /**
     * This method is used to add and set swipe refresh layout for home page.
     */
    private void setPullToRefreshComponents() {
        swipeRefreshLayout.setColorSchemeResources(R.color.eduplex_yellow_main, R.color.eduplex_red_main, R.color.text_blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                log("currentbranchid", getBranchId());

                if (handler != null) {
                    handler.removeCallbacks(runnable);
                }

                runnable = null;
                position = 0;
                requestGetHomeNew();
//                requestGetHome(getBranchId());
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        position = 0;
        handler.postDelayed(runnable, 3000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * This method is used to set size of banner based on device screen dimension.
     */
    private void setSizeOfBanner() {
        int screenWidth = getScreenWidth(getActivity());
        int cardHeight = screenWidth / 2;

        cvBanner.getLayoutParams().height = cardHeight;
        cvBanner.requestLayout();
    }

    /**
     * This method is used to set Banner viewpager.
     */
    private void setBannerViewPager(List<Promo> listPromo) {
        //Set adapter

        //CHECK MAX PROMO 15
        if (listPromo.size() > 15) {
            List<Promo> currPromoList = new ArrayList<>();
            for (int i = 0; i < 15; i++) {
                currPromoList.add(listPromo.get(i));
            }
            adapter = new BannerPagerAdapter(getActivity(), currPromoList);
        } else {
            adapter = new BannerPagerAdapter(getActivity(), listPromo);
        }


        vpBanner.setAdapter(adapter);
        //Custom indicator
        cpiBanner.setCentered(true);
        cpiBanner.setViewPager(vpBanner);
        cpiBanner.setPageColor(0xFFFFFFFF);
        cpiBanner.setStrokeColor(0x00FFFFFF);
        cpiBanner.setFillColor(0xFFFDD103);
        final float density = getResources().getDisplayMetrics().density;
        cpiBanner.setRadius(4 * density);
    }

    /**
     * This method is used to set onClick action for all buttons.
     *
     * @param view Passing the button's view.
     */
    @OnClick({R.id.llMenu, R.id.llTable, R.id.llTopUp, R.id.llCashier})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llMenu:
                Intent intentKatalogBarang = new Intent(getActivity(), KatalogBarangTabsActivity.class);
                startActivity(intentKatalogBarang);
                break;
            case R.id.llTable:
                if(isCheckedIn()){
                    Intent intentTableCheckOut = new Intent(getActivity(), TableCheckoutActivity.class);
                    startActivity(intentTableCheckOut);
                }
                else {
                    Intent intentTableActivity = new Intent(getActivity(), TableActivity.class);
                    startActivity(intentTableActivity);
                }
                break;
            case R.id.llTopUp:
                Intent intentTarikKPTunaiPinActivity = new Intent(getActivity(), TarikKPTunaiPinActivity.class);
                startActivity(intentTarikKPTunaiPinActivity);
                break;
            case R.id.llCashier:
                Intent chatActivity = new Intent(getActivity(), FAQActivity.class);
                startActivity(chatActivity);
                break;
//            case R.id.llModalKerja:
//                requestModalUsahaByCategory();
//                break;
//            case R.id.llKPR:
//                Intent intentKPR = new Intent(getActivity(), KatalogItemDetailActivity.class);
//                intentKPR.putExtra(Constant.IntentExtraKeys.KEY_PRODUCT_ID, productIdKPR);
//                startActivity(intentKPR);
//                break;
        }
    }


    //
    private void requestGetHomeNew(){
        if (!swipeRefreshLayout.isRefreshing()) {
            showProgress(getStringFromRes(R.string.loading));
        }
        saldo = "0";

        getHomeData = new GetHomeData();

        List<Category> listCtg = new ArrayList<>();
        Category ctg1 = new Category("1","beverages coffe");
        Category ctg2 = new Category("2","beverages tea");
        Category ctg3 = new Category("3","beverages juice");
        Category ctg4 = new Category("4","ala carte");
        Category ctg5 = new Category("5","buffet");
        Category ctg6 = new Category("6","main dish");
        Category ctg7 = new Category("7","other");
        listCtg.add(ctg1);
        listCtg.add(ctg2);
        listCtg.add(ctg3);
        listCtg.add(ctg4);
        listCtg.add(ctg5);
        listCtg.add(ctg6);
        listCtg.add(ctg7);
        getHomeData.setCategories(listCtg);

        List<Promo> dummyListPromo = new ArrayList<>();
        //dummy1
        Promo dummyPromo1 = new Promo();
        dummyPromo1.setImage_url("http://eduplex.id/wp-content/uploads/2016/02/12749946_568896443274997_1215317797_n-1.jpg");
        dummyPromo1.setPromo_url("http://eduplex.id");
        dummyListPromo.add(dummyPromo1);

        Promo dummyPromo2 = new Promo();
        dummyPromo1.setImage_url("http://qraved-live.s3.amazonaws.com/images/image/cache/data/Indonesia/Bandung/Ir__Haji_Juanda__Dago_Bawah_/Eduplex_Cafe/img_3053.14782453022967.1243-870x370.jpg");
        dummyPromo1.setPromo_url("http://eduplex.id");
        dummyListPromo.add(dummyPromo2);

        //add semua ke dummyListPromo
        //setting

        this.setBannerViewPager(dummyListPromo);
        swipeRefreshLayout.setRefreshing(false);
        dismissProgress();
        requestKPTunai();
    }




    private void requestKPTunai() {
//        showProgress(getStringFromRes(R.string.loading));

        Call<CheckTransResponse> call = getService().getBalance(getAuthorization());
        call.enqueue(
                new Callback<CheckTransResponse>() {
                    @Override
                    public void onResponse(Response<CheckTransResponse> response, Retrofit retrofit) {

                        if (response.isSuccess()) {

                            try{
                                log("getTable", "success");
                                CheckTransResponse checkTransResponse = response.body();

                                if (checkTransResponse.getMeta().getCode().equals(Constant.ResponseCode.CODE_200)) {
                                    saldo = checkTransResponse.getData().getMessage();
                                }
                                else{
                                    saldo = "0";
                                }

                            }catch (Exception ex) {
//                                showAlert(response.errorBody().toString());
                            }

                        } else {
//                            showAlert(response.errorBody().toString());

                        }
                        KPTunai kpTunai = new KPTunai(saldo,"");

                        tvSisaSaldo.setText(Utils.getShownNominal(kpTunai.getAmount()));
                        tvKodeReferensi.setText(getStringFromRes(R.string.kode_referal_header) + "");
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        showAlert(t.toString());
                    }
                }
        );
    }

    /**
     * This method is used to get home such as hot products, promo, etc.
     */
//    private void requestGetHome(String branchId) {
//        if (!swipeRefreshLayout.isRefreshing())
//            showProgress(getStringFromRes(R.string.loading));
//
//        Call<GetHomeResponse> call = getService().get_home(branchId);
//        call.enqueue(new Callback<GetHomeResponse>() {
//            @Override
//            public void onResponse(Response<GetHomeResponse> response, Retrofit retrofit) {
//                if (response.isSuccess()) {
//                    log("gethomerequest", "success");
//
//                    dismissProgress();
//                    final GetHomeResponse getHomeResponse = response.body();
//
//                    if (getHomeResponse.getMeta().getCode().equals(Constant.ResponseCode.CODE_200)) {
//
//                        //Set getHomeData
//                        getHomeData = getHomeResponse.getData();
//                        setBannerViewPager(getHomeResponse.getData().getPromos());
//                        if (runnable == null)
//                            setAutoSlideViewPager(getHomeResponse.getData().getPromos().size());
//
////                        //Set Hot Products
////                        hotProducts = new ArrayList<Product>();
////                        hotProducts = getHomeResponse.getData().getHot_products();
////                        setHotProducts();
//
////                        try {
////                            //Set Product ID for KPR
////                            productIdKPR = getHomeResponse.getData().getKpr().get(0).getProduct_id();
////                        } catch (Exception e) {
////                            log("kprhome", "null");
////                        }
//
//
//
//                        //CHECK GO TO INBOX FLAG
//                        if (getActivity().getSharedPreferences(Constant.SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE)
//                                .getBoolean("gotoinbox", false) && !isGuest()) {
//                            Intent intent = new Intent(getActivity(), InboxActivity.class);
//                            startActivity(intent);
//
//                            getActivity().getSharedPreferences(Constant.SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE)
//                                    .edit().putBoolean("gotoinbox", false).commit();
//                        }
//
//                    } else {
//                        log("gethomerequest", "is not 200");
//                    }
//
//                } else {
//                    log("gethomerequest", "is not success");
//
//                    if (!swipeRefreshLayout.isRefreshing())
//                        dismissProgress();
//                }
//
//                try {
//                    swipeRefreshLayout.setRefreshing(false);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                try {
//                    swipeRefreshLayout.setRefreshing(false);
//                    dismissProgress();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                log("gethomerequest", "failure");
//                showAlert(R.string.alert_connection_fail);
//            }
//        });
//    }

    /**
     * This method is used to set Home hot products.
     */
//    private void setHotProducts() {
//
//        llMenuTerlarisContainer.removeAllViews();
//
//        //If list is empty
//        if (hotProducts.size() == 0) {
//            rlBlankSlateBarangTerlaris.setVisibility(View.VISIBLE);
//        } else {
//            rlBlankSlateBarangTerlaris.setVisibility(View.GONE);
//            for (final Product p : hotProducts) {
//
//                View v = getActivity().getLayoutInflater().inflate(R.layout.item_barang_terlaris, null);
//                ImageView ivItemBarangTerlaris = (ImageView) v.findViewById(R.id.ivItemBarangTerlaris);
//                TextView tvItemTerlarisName = (TextView) v.findViewById(R.id.tvItemTerlarisName);
//                TextView tvItemTerlarisHargaTotal = (TextView) v.findViewById(R.id.tvItemTerlarisHargaTotal);
//                CardView cvItemBarangTerlaris = (CardView) v.findViewById(R.id.cvItemBarangTerlaris);
//
//                try {
//                    Glide.with(getActivity()).load(p.getProduct_images().get(0)).into(ivItemBarangTerlaris);
//                } catch (Exception e) {
//                    Glide.with(getActivity()).load(R.mipmap.ic_launcher_app).into(ivItemBarangTerlaris);
//                }
//
//                tvItemTerlarisName.setText(p.getProduct_name());
//                tvItemTerlarisHargaTotal.setText(Utils.getShownNominal(p.getProduct_price()));
//
//                cvItemBarangTerlaris.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(getActivity(), KatalogItemDetailActivity.class);
//                        intent.putExtra(Constant.IntentExtraKeys.KEY_PRODUCT_ID, p.getProduct_id());
//                        intent.putExtra(Constant.IntentExtraKeys.KEY_IS_PROMO_PRODUCT, "1");
//                        startActivity(intent);
//                    }
//                });
//
//                llMenuTerlarisContainer.addView(v);
//            }
//        }
//    }

    /**
     * This method is used to auto slide viewpager in Home's banner.
     *
     * @param listSize Pass the size of sliding items
     */
    private void setAutoSlideViewPager(final int listSize) {
        vpBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int pos) {
                position = pos;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //START SLIDESHOW HOME PROMO
        try {
            runnable = new Runnable() {
                public void run() {
                    if (position >= listSize) {
                        position = 0;
                    } else {
                        position = position + 1;
                    }
                    vpBanner.setCurrentItem(position, true);
                    handler.postDelayed(runnable, 3000);
                }
            };

            handler.postDelayed(runnable, 3000);
        } catch (Exception e) {
        }
    }

//    /**
//     * This method is used to get product detail of catalog Motor.
//     */
//    private void requestMotorCatalogDetail() {
//        showProgress(getStringFromRes(R.string.loading));
//
//
//        String branchId = "0";
//        String categoryId = "0";
//        try {
//            Category mCategory = getMotorDetailByCategory();
//            branchId = getBranchId();
//            categoryId = mCategory.getCategory_id();
//        } catch (Exception e) {
//            e.printStackTrace();
//            showToast(R.string.server_undermaintenance);
//            dismissProgress();
//            return;
//        }
//
//        Call<GetProductResponse> call = getService().get_products_by_category(categoryId, "0", "5", branchId);
//        final String finalCategoryId = categoryId;
//        call.enqueue(new Callback<GetProductResponse>() {
//            @Override
//            public void onResponse(Response<GetProductResponse> response, Retrofit retrofit) {
//                if (response.isSuccess()) {
//                    log("getproductsbycategory", "success");
//
//                    dismissProgress();
//                    GetProductResponse getProductResponse = response.body();
//
//                    if (getProductResponse.getMeta().getCode().equals(Constant.ResponseCode.CODE_200)) {
//
//                        try {
//                            Intent intent = new Intent(getActivity(), KatalogItemDetailActivity.class);
//                            intent.putExtra(Constant.IntentExtraKeys.KEY_PRODUCT_ID, getProductResponse.getData().getProducts().get(0).getProduct_id());
//                            startActivity(intent);
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            log("datakendaraankosong", finalCategoryId);
//                            showToast(getProductResponse.getMeta().getMessage());
//                        }
//
//                    } else {
//                        log("getproductsbycategory", "is not 200");
//                        showToast(getProductResponse.getMeta().getMessage());
//                    }
//
//                } else {
//                    log("getproductsbycategory", "is not success");
//                    showToast("Request code : " + response.code());
//                    dismissProgress();
//                }
//
//                dismissProgress();
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                log("getproductsbycategory", "failure");
//                dismissProgress();
//                showAlert(R.string.alert_connection_fail);
//            }
//        });
//    }

//    /**
//     * This method is used to get Mobil categories if there is any.
//     */
//    private Category getMotorDetailByCategory() {
//        try {
//            for (Category c : HomeFragment.getHomeData.getCategories()) {
//                if (c.getCategory_id().equals(Constant.CategoryID.KENDARAAN_CATEGORY_ID)) {
//                    return c;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            showToast("Error load data from branch");
//        }
//
//        return null;
//    }

    /**
     * This method is used to get Modal Usaha categories if there is any.
     */
//    private Category getModalUsahaDetailByCategory() {
//        try {
//            for (Category c : HomeFragment.getHomeData.getCategories()) {
//                if (c.getCategory_id().equals(Constant.CategoryID.MODAL_KERJA_CATEGORY_ID)) {
//                    return c;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            showToast("Error load data from branch");
//        }
//
//        return null;
//    }

    /**
     * This method is used to get product detail of catalog Modal Usaha.
     */
//    private void requestModalUsahaByCategory() {
//        showProgress(getStringFromRes(R.string.loading));
//
//        String branchId = "0";
//        String categoryId = "0";
//        try {
//            Category mCategory = getModalUsahaDetailByCategory();
//            branchId = getBranchId();
//            categoryId = mCategory.getCategory_id();
//        } catch (Exception e) {
//            e.printStackTrace();
//            showToast(R.string.server_undermaintenance);
//            dismissProgress();
//            return;
//        }
//
//        Call<GetProductResponse> call = getService().get_products_by_category(categoryId, "0", "5", branchId);
//        final String finalCategoryId = categoryId;
//        call.enqueue(new Callback<GetProductResponse>() {
//            @Override
//            public void onResponse(Response<GetProductResponse> response, Retrofit retrofit) {
//                if (response.isSuccess()) {
//                    log("getproductsbycategory", "success");
//
//                    dismissProgress();
//                    GetProductResponse getProductResponse = response.body();
//
//                    if (getProductResponse.getMeta().getCode().equals(Constant.ResponseCode.CODE_200)) {
//
//                        try {
//                            Intent intent = new Intent(getActivity(), KatalogItemDetailActivity.class);
//                            intent.putExtra(Constant.IntentExtraKeys.KEY_PRODUCT_ID, getProductResponse.getData().getProducts().get(0).getProduct_id());
//                            startActivity(intent);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            log("datamodalkerjakosong", finalCategoryId);
//                            showToast(getProductResponse.getMeta().getMessage());
//                        }
//
//                    } else {
//                        log("getproductsbycategory", "is not 200");
//                        showToast(getProductResponse.getMeta().getMessage());
//                    }
//
//                } else {
//                    log("getproductsbycategory", "is not success");
//                    dismissProgress();
//                    showToast("Request code : " + response.code());
//                    dismissProgress();
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                log("getproductsbycategory", "failure");
//                dismissProgress();
//                showAlert(R.string.alert_connection_fail);
//            }
//        });
//    }
}
