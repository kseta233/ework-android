package com.ework.eduplex.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ework.eduplex.R;
import com.ework.eduplex.activities.katalog.KatalogItemDetailActivity;
import com.ework.eduplex.adapters.ProductAdapter;
import com.ework.eduplex.customcomponents.RecyclerItemClickListener;
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

public class SearchActivity extends BaseActivity {


    GridLayoutManager gridLayoutManager;
    List<Product> listProduct;
    List<String> autoCompleteList;
    int indexRefresh;
    String branchId;
    ProductAdapter rcAdapter;
    @Bind(R.id.tvEmptyState)
    TextView tvEmptyState;
    private boolean mLoadingItems = true;
    int mOnScreenItems, mTotalItemsInList, mFirstVisibleItem, mPreviousTotal, mVisibleThreshold;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.imageView10)
    ImageView imageView10;
    @Bind(R.id.actvSearchBar)
    AutoCompleteTextView actvSearchBar;
    @Bind(R.id.tvDeleteSearch)
    ImageView tvDeleteSearch;
    @Bind(R.id.rvSearchResult)
    RecyclerView rvSearchResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        initComponents();
        setAutoCompleteSearchBar();
        setGridItem();
        setSearchBar();

        //Set first search product
        String searchKey = getIntent().getStringExtra(Constant.IntentExtraKeys.SEARCH_KEY);
        actvSearchBar.setText(searchKey);
        hideSoftKeyboard();
        performSearch(searchKey);
    }

    @Override
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
     * This method is used to initiate basic layout function such as var initialization, set toolbar, etc.
     */
    private void initComponents() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_search));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvDeleteSearch.setVisibility(View.INVISIBLE);

        listProduct = new ArrayList<>();
        indexRefresh = 1;
        branchId = getBranchId();

        //TODO AUTOCOMPLETE TEXT
        autoCompleteList = new ArrayList<>();
//        autoCompleteList.add("Test 1");
//        autoCompleteList.add("A Test 2");
//        autoCompleteList.add("BTest 6");
//        autoCompleteList.add("C Test 2");
//        autoCompleteList.add("zTest 3");
//        autoCompleteList.add("C Test 6");
//        autoCompleteList.add("Test ");
    }

    /**
     * This method is used to set the search bar, such as set the type of the soft keyboard that will be shown, set action when user typing, and etc.
     */
    private void setSearchBar() {

        actvSearchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    //Reset components
                    listProduct.removeAll(listProduct);
                    mOnScreenItems = rvSearchResult.getChildCount();
                    mTotalItemsInList = gridLayoutManager.getItemCount();
                    mFirstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();
                    mPreviousTotal = 0;
                    indexRefresh = 1;
                    mLoadingItems = false;

                    performSearch(actvSearchBar.getText().toString());
                    return true;
                }
                return false;
            }
        });

        actvSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (actvSearchBar.getText().toString().equals(""))
                    tvDeleteSearch.setVisibility(View.INVISIBLE);
                else
                    tvDeleteSearch.setVisibility(View.VISIBLE);
            }
        });

        tvDeleteSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listProduct.removeAll(listProduct);
                performSearch(actvSearchBar.getText().toString());
                actvSearchBar.setText("");
            }
        });
    }

    /**
     * This method is used to search items based on search key in edittext on search bar.
     */
    private void performSearch(String searchKey) {
        requestProductsBySearch(searchKey, "0", "5", getBranchId());
    }

    /**
     * This method is used for set grid layout and call the item adapter.
     */
    private void setGridItem() {
        gridLayoutManager = new GridLayoutManager(this, 2);

        rvSearchResult.setHasFixedSize(true);
        rvSearchResult.setLayoutManager(gridLayoutManager);

        rcAdapter = new ProductAdapter(this, listProduct);
        rvSearchResult.setAdapter(rcAdapter);

        rvSearchResult.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(SearchActivity.this, KatalogItemDetailActivity.class);
                        intent.putExtra(Constant.IntentExtraKeys.KEY_PRODUCT_OBJECT, listProduct.get(position));
                        intent.putExtra(Constant.IntentExtraKeys.KEY_PRODUCT_ID, listProduct.get(position).getProduct_id());
                        startActivity(intent);
                    }
                })
        );

        rvSearchResult.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mOnScreenItems = rvSearchResult.getChildCount();
                mTotalItemsInList = gridLayoutManager.getItemCount();
                mFirstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();

                if (mLoadingItems) {
                    if (mTotalItemsInList > mPreviousTotal) {
                        mLoadingItems = false;
                        mPreviousTotal = mTotalItemsInList;
                    }
                }

                if (!mLoadingItems && (mTotalItemsInList - mOnScreenItems) <= (mFirstVisibleItem + mVisibleThreshold)) {

                    requestProductsBySearch(actvSearchBar.getText().toString(), indexRefresh + "", "5", branchId);
                    indexRefresh++;
                    mLoadingItems = true;

                    log("searchbottomscroll", "in");
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

        });
    }

    /**
     * This method is used to set list of autocomplete text in search bar.
     */
    private void setAutoCompleteSearchBar() {

        //Creating the instance of ArrayAdapter containing list of language names
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, autoCompleteList);
        //Getting the instance of AutoCompleteTextView
        actvSearchBar.setThreshold(1);//will start working from first character
        actvSearchBar.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
    }

    /**
     * This method is used to request products by search.
     */
    private void requestProductsBySearch(String search, String onpage, String onsize, String branch_id) {
//        showProgress(getStringFromRes(R.string.loading));

        Call<GetProductResponse> call = getService().get_products_by_search(search, onpage, onsize, branch_id);
        call.enqueue(new Callback<GetProductResponse>() {
            @Override
            public void onResponse(Response<GetProductResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    log("getproductsbysearch", "success");

//                    dismissProgress();
                    GetProductResponse getProductResponse = response.body();

                    if (getProductResponse.getMeta().getCode().equals(Constant.ResponseCode.CODE_200)) {

                        try {
                            listProduct.addAll(getProductResponse.getData().getProducts());
                            log("searchproductsize", " : " + listProduct.size());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        rcAdapter.notifyDataSetChanged();

                        //Cek delivered product list
                        if (listProduct.size() == 0) {
                            tvEmptyState.setVisibility(View.VISIBLE);
                            rvSearchResult.setVisibility(View.GONE);
                        } else {
                            tvEmptyState.setVisibility(View.GONE);
                            rvSearchResult.setVisibility(View.VISIBLE);
                        }
                    } else {

                    }

                    hideSoftKeyboard();

                } else {
                    log("getproductsbysearch", "is not success");
//                    dismissProgress();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                log("getproductsbysearch", "failure");
//                dismissProgress();
                showAlert(R.string.alert_connection_fail);
            }
        });
    }
}














