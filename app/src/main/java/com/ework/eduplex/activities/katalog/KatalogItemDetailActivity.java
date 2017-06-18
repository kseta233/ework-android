package com.ework.eduplex.activities.katalog;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ework.eduplex.R;
import com.ework.eduplex.activities.BaseActivity;
import com.ework.eduplex.activities.TarikCabangActivity;
import com.ework.eduplex.activities.TarikKPTunaiPinActivity;
import com.ework.eduplex.activities.loginandregister.LoginActivity;
import com.ework.eduplex.service.model.BaseEduResponse;
import com.ework.eduplex.service.model.Product;
import com.ework.eduplex.service.model.ProductDetailData;
import com.ework.eduplex.service.model.ProductInstallment;
import com.ework.eduplex.service.model.edu.response.SecretResponse;
import com.ework.eduplex.service.model.response.BaseResponse;
import com.ework.eduplex.service.model.response.ProductDetailResponse;
import com.ework.eduplex.utils.Constant;
import com.ework.eduplex.utils.Utils;
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

public class KatalogItemDetailActivity extends BaseActivity {

    String productId;
    ProductDetailData productDetail;
    ItemDetailPagerAdapter adapter;
    int ITEMS = 3;
    List<String> listDetailPhoto;
    List<LinearLayout> listLinearLayout;
    List<TextView> listTextView;
    List<TextView> listTextView2;
    int selectedIdx = 0;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tvItemDetailName)
    TextView tvItemDetailName;
    @Bind(R.id.tvItemDetailHarga)
    TextView tvItemDetailHarga;
    @Bind(R.id.vpItemDetail)
    ViewPager vpItemDetail;
    @Bind(R.id.cpiItemDetail)
    CirclePageIndicator cpiItemDetail;

    @Bind(R.id.rlItemDetailPagerContainer)
    RelativeLayout rlItemDetailPagerContainer;
    @Bind(R.id.tvItemDetailDescription)
    TextView tvItemDetailDescription;

    @Bind(R.id.tvPayCheckOut)
    TextView tvPayCheckOut;

    String isPromo, branchId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_katalog_item_detail);
        ButterKnife.bind(this);

        initComponents();
//        requestGetProductDetail();
        requestGetProductDetailNew();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * This method is used to set size of card KPTunai based on device screen dimension.
     */
    private void setSizeImageGallery() {
        int screenWidth = getScreenWidth(this);
        int cardHeight = screenWidth;

        rlItemDetailPagerContainer.getLayoutParams().height = cardHeight;
        rlItemDetailPagerContainer.requestLayout();
    }

    /**
     * This method is used to initiate basic layout function such as var initialization, set toolbar, etc.
     */
    private void initComponents() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_katalog_item_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setSizeImageGallery();

        //CLEAR CACHE FIELDS FROM OTHERS TRANSACTION
        clearSavedFieldsData();

        isPromo = "0";
        try {
            isPromo = getIntent().getStringExtra(Constant.IntentExtraKeys.KEY_IS_PROMO_PRODUCT);
            if (isPromo.equals(""))
                isPromo = "0";
        } catch (Exception e) {
            isPromo = "0";
        }
    }

    /**
     * This method is used to set Katalog Item viewpager.
     */
    private void setViewPager() {
        //Set adapter
        adapter = new ItemDetailPagerAdapter(this);
        vpItemDetail.setAdapter(adapter);
        //Custom indicator
        cpiItemDetail.setCentered(true);
        cpiItemDetail.setViewPager(vpItemDetail);
        cpiItemDetail.setPageColor(0x77000000);
        cpiItemDetail.setStrokeColor(0x00000000);
        cpiItemDetail.setFillColor(0xAA000000);
        final float density = getResources().getDisplayMetrics().density;
        cpiItemDetail.setRadius(4 * density);
    }

    /**
     * This inner class is used as Pager Adapter for Katalog Item viewpager.
     */
    class ItemDetailPagerAdapter extends PagerAdapter {
        Context mContext;
        LayoutInflater mLayoutInflater;

        public ItemDetailPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return listDetailPhoto.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.item_viewpager_item_detail, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.ivItemDetailViewPager);

            try {
                ((BitmapDrawable) imageView.getDrawable()).getBitmap().recycle();
            } catch (Exception e) {
            }

            Glide.with(KatalogItemDetailActivity.this).load(listDetailPhoto.get(position)).into(imageView);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    @OnClick(R.id.tvPayCheckOut)
    public void orderViaVoucher(){

        Call<BaseEduResponse> call = getService().order(getAuthorization(),productDetail.getProduct_price(), productDetail.getProduct_id());
        call.enqueue(
                new Callback<BaseEduResponse>() {
                    @Override
                    public void onResponse(Response<BaseEduResponse> response, Retrofit retrofit) {

                        if (response.isSuccess()) {

                            try{
                                log("getTable", "success");
                                BaseEduResponse baseResponse = response.body();
                                showAlert(baseResponse.getMeta().getMessage());

                                //
//                                if ( baseResponse.getMeta().getCode() == "200") {
//                                    showAlert("Pesanan Sukses");
//                                }
//                                else{
//                                    showAlert(baseResponse.getMeta().getCode());
//                                }


                            }catch (Exception ex) {
                                showAlert(response.errorBody().toString());
                            }

                        } else {
                            showAlert(response.errorBody().toString());

                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        showAlert(t.toString());
                    }
                }
        );

    }

    /**
     * This method is used to set prduct detail.
     */
    private void setProductDetail() {
        try {
            tvItemDetailName.setText(productDetail.getProduct_name());
            tvItemDetailHarga.setText(Utils.getShownNominal(productDetail.getProduct_price()));
            tvItemDetailDescription.setText(productDetail.getProduct_description());
//            tvItemDetailSpec.setText(productDetail.getProduct_short_terms());

            listDetailPhoto = new ArrayList<>();
            for (String s : productDetail.getProduct_images()) {
                String prefix = Constant.API_URL;
                String xUrl = prefix + s;


                log("PHOTOURL",xUrl);
                listDetailPhoto.add(xUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log("catchsetproductdetail", "katalog barang");
        }
    }


    private void requestGetProductDetailNew(){
        Intent intent = getIntent();
        Product product = (Product) intent.getExtras().getSerializable(Constant.IntentExtraKeys.KEY_PRODUCT_OBJECT);

        productDetail = new ProductDetailData(product);
        branchId = "2";
        //Set the detail
        setProductDetail();
        setViewPager();

    }


}
