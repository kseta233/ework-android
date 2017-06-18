package com.ework.eduplex.fragments;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ework.eduplex.R;
import com.ework.eduplex.adapters.BarangAdapter;
import com.ework.eduplex.service.model.Product;
import com.ework.eduplex.service.model.response.GetProductResponse;
import com.ework.eduplex.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by eWork on 3/23/2016.
 */
public class KatalogItemFragment extends BaseFragment {

    List<Product> rowListItem;
    BarangAdapter rcAdapter;
    Bundle b;
    int indexProduct;
    @Bind(R.id.tvEmptyState)
    TextView tvEmptyState;
    private boolean mLoadingItems = true;
    int mOnScreenItems, mTotalItemsInList, mFirstVisibleItem, mPreviousTotal, mVisibleThreshold;
    private String categoryId;
    private String branchId;
    private GridLayoutManager gridLayoutManager;
    @Bind(R.id.tvKatalogGridHeader)
    TextView tvKatalogGridHeader;
    @Bind(R.id.rvKatalogGrid)
    RecyclerView rvKatalogGrid;

    public KatalogItemFragment() {
        // Required empty public constructor
    }

    public static KatalogItemFragment newInstance() {
        KatalogItemFragment fragment = new KatalogItemFragment();
        return fragment;
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
        View rootView = inflater.inflate(R.layout.item_grid_list, container, false);
        ButterKnife.bind(this, rootView);


        initComponents();
        setRecyclerView();
        requestProductsByCategory(categoryId, 0, 5, branchId);

        return rootView;
    }

    /**
     * This method is used to init components.
     */
    private void initComponents() {
        indexProduct = 1;
        //SET BRANCH ID
        branchId = getBranchId();

        //Get Category Key
        rowListItem = new ArrayList<>();

        b = getArguments();
        categoryId = b.getString(Constant.CategoryID.BUNDLE_KEY);
        log("categoryIdBarang", categoryId);
//        tvKatalogGridHeader.setText(getStringFromRes(R.string.catalog) + " " + b.getString(Constant.CategoryID.CATEGORY_NAME_KEY).toString());

        tvEmptyState.setText(R.string.belum_ada_product);
    }

    /**
     * This method is used to set Barang list.
     */
    private void setRecyclerView() {
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        rvKatalogGrid.setHasFixedSize(true);
        rvKatalogGrid.setLayoutManager(gridLayoutManager);

        rcAdapter = new BarangAdapter(getActivity(), rowListItem);
        rvKatalogGrid.setAdapter(rcAdapter);

        rvKatalogGrid.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mOnScreenItems = rvKatalogGrid.getChildCount();
                mTotalItemsInList = gridLayoutManager.getItemCount();
                mFirstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();

                if (mLoadingItems) {
                    if (mTotalItemsInList > mPreviousTotal) {
                        mLoadingItems = false;
                        mPreviousTotal = mTotalItemsInList;
                    }
                }

                if (!mLoadingItems && (mTotalItemsInList - mOnScreenItems) <= (mFirstVisibleItem + mVisibleThreshold)) {
                    requestProductsByCategory(categoryId, indexProduct, 5, branchId);
                    indexProduct++;
                    mLoadingItems = true;
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * This method is used to request products by category.
     */
    private void requestProductsByCategory(final String category_id, int onpage, int onsize, String branch_id) {
//        showProgress(getStringFromRes(R.string.loading));

        Call<GetProductResponse> call = getService().get_products_by_category(category_id, onpage + "", onsize + "", branch_id);
        call.enqueue(new Callback<GetProductResponse>() {
            @Override
            public void onResponse(Response<GetProductResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    log("getproductsbycategory", "success");

//                    dismissProgress();
                    GetProductResponse getProductResponse = response.body();

                    if (getProductResponse.getMeta().getCode().equals(Constant.ResponseCode.CODE_200)) {

                        try {
                            rowListItem.addAll(getProductResponse.getData().getProducts());
                        } catch (Exception e) {
                            e.printStackTrace();
                            log("databarangkosong", category_id);

                            //SHOW EMPTY STATE
                            if (rowListItem.size() == 0) {
                                try {
                                    tvEmptyState.setVisibility(View.VISIBLE);
                                    rvKatalogGrid.setVisibility(View.GONE);
                                } catch (Exception ex) {}
                            }
                        }

                        rcAdapter.notifyDataSetChanged();

                        try {
                            //TO FIX FIRST LOAD PAGE, UNKNOWN BUG
                            if (b.getString(Constant.CategoryID.CATEGORY_POSITION).equals("0")) {
                                if (rcAdapter.getItemCount() > 0) {
                                    tvEmptyState.setVisibility(View.GONE);
                                    rvKatalogGrid.setVisibility(View.VISIBLE);
                                } else {
                                    tvEmptyState.setVisibility(View.VISIBLE);
                                    rvKatalogGrid.setVisibility(View.GONE);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        log("getproductsbycategory", "is not 200");
                    }

                } else {
                    log("getproductsbycategory", "is not success");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                log("getproductsbycategory", "failure");
//                dismissProgress();
                showAlert(R.string.alert_connection_fail);
            }
        });
    }

}
