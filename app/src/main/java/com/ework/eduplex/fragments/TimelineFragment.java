package com.ework.eduplex.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ework.eduplex.R;
import com.ework.eduplex.activities.BaseActivity;
import com.ework.eduplex.adapters.TimelineAdapter;
import com.ework.eduplex.service.model.Timeline;
import com.ework.eduplex.service.model.response.GetTimelinesResponse;
import com.ework.eduplex.service.model.response.LoginResponse;
import com.ework.eduplex.utils.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class TimelineFragment extends BaseFragment {

    TimelineAdapter mAdapter;
    List<Timeline> listTimeline;
    int indexTimeline;
    @Bind(R.id.viewLine)
    View viewLine;
    @Bind(R.id.tvRefreshing)
    TextView tvRefreshing;
    private boolean mLoadingItems = true;
    int mOnScreenItems, mTotalItemsInList, mFirstVisibleItem, mPreviousTotal, mVisibleThreshold;

    private LinearLayoutManager mLayoutManager;

    private int MAX_TIMELINE = 20;
    private int PER_LOAD = 5;

    Gson gson;

    @Bind(R.id.rvTimeline)
    RecyclerView rvTimeline;
    @Bind(R.id.srlTimeline)
    SwipeRefreshLayout srlTimeline;

    public TimelineFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_timeline, container, false);
        ButterKnife.bind(this, rootView);

        init();
        setRecyclerView();
        setPullToRefreshComponents();

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    /**
     * This method is used to set Timeline's Recycler View.
     */
    private void setRecyclerView() {
        mAdapter = new TimelineAdapter(listTimeline);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rvTimeline.setLayoutManager(mLayoutManager);
        rvTimeline.setItemAnimator(new DefaultItemAnimator());
//        rvTimeline.setAdapter(mAdapter);
        rvTimeline.swapAdapter(mAdapter, true);

        rvTimeline.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mOnScreenItems = rvTimeline.getChildCount();
                mTotalItemsInList = mLayoutManager.getItemCount();
                mFirstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (mLoadingItems) {
                    if (mTotalItemsInList > mPreviousTotal) {
                        mLoadingItems = false;
                        mPreviousTotal = mTotalItemsInList;
                    }
                }

                if (!mLoadingItems && (mTotalItemsInList - mOnScreenItems) <= (mFirstVisibleItem + mVisibleThreshold)) {

                    //CHECK if sudah max
                    if (indexTimeline * PER_LOAD < MAX_TIMELINE) {
                        requestTimelines(indexTimeline, PER_LOAD);
                        indexTimeline++;
                        mLoadingItems = true;
                    }

                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

        });
    }

    /**
     * This method is used to add and set swipe refresh layout for timeline.
     */
    private void setPullToRefreshComponents() {

        srlTimeline.setColorSchemeResources(R.color.eduplex_yellow_main, R.color.eduplex_red_main, R.color.text_blue);
        srlTimeline.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
    }

    /**
     * This method is used to refresh timeline's content.
     */
    private void refreshContent() {

        //IF Guest dont set the pull to refresh function
        if (isGuest()) {
            srlTimeline.setRefreshing(false);
        } else {
            listTimeline.removeAll(listTimeline);
            mAdapter.notifyDataSetChanged();

            tvRefreshing.setVisibility(View.VISIBLE);
            viewLine.setVisibility(View.INVISIBLE);

            //Reset components
            mOnScreenItems = rvTimeline.getChildCount();
            mTotalItemsInList = mLayoutManager.getItemCount();
            mFirstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
            mPreviousTotal = 0;
            indexTimeline = 1;
            mLoadingItems = false;

            requestTimelines(0, PER_LOAD);
        }
    }

    /**
     * This method is used to request some timeline(s) based on its parameter onpage and onsize.
     *
     * @param onPage starting index of timelines which are going to be taken.
     * @param onSize quantity of timeline(s) that will be taken.
     */
    private void requestTimelines(int onPage, int onSize) {

        Call<GetTimelinesResponse> call = getService().get_timelines(getAuthorization(), onPage + "", onSize + "");
        call.enqueue(new Callback<GetTimelinesResponse>() {
            @Override
            public void onResponse(Response<GetTimelinesResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    log("gettimelines", "success");

                    dismissProgress();
                    GetTimelinesResponse getTimelinesResponse = response.body();

                    if (getTimelinesResponse.getMeta().getCode().equals(Constant.ResponseCode.CODE_200)) {

                        listTimeline.addAll(getTimelinesResponse.getData().getTimelines());
                        log("timelinesize", " : " + listTimeline.size());

                        mAdapter.refreshDataSet(listTimeline);

                        srlTimeline.setRefreshing(false);
                    } else {
                        srlTimeline.setRefreshing(false);
                    }

                } else {
                    log("gettimelines", "is not success");
                    srlTimeline.setRefreshing(false);

                    //CHECK FOR BLOCKED USER
                    ((BaseActivity) getActivity()).doCheckBlockedUser(response, retrofit);

                    if (response.code() == 403) {
                        GetTimelinesResponse myError = null;
                        try {
                            myError = (GetTimelinesResponse) retrofit.responseConverter(
                                    GetTimelinesResponse.class, GetTimelinesResponse.class.getAnnotations())
                                    .convert(response.errorBody());

                            //IF TOKEN EXPIRED ERROR< DO AUTOLOGIN
                            if (myError.getMeta().getError().equals("4031")) {
                                ((BaseActivity) getActivity()).autoLoginIfTokenExpired();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                tvRefreshing.setVisibility(View.GONE);
                viewLine.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {
                log("gettimelines", "failure");

                tvRefreshing.setVisibility(View.GONE);
                viewLine.setVisibility(View.VISIBLE);

                showAlert(R.string.alert_connection_fail);
                srlTimeline.setRefreshing(false);
            }

        });
    }

    private void init() {
        listTimeline = new ArrayList<>();
        gson = new GsonBuilder().serializeNulls().create();
        String json = getMySharedPreferences().getString(Constant.SharedPrefs.KEY_LOGIN_RESPONSE, "");
        if (!json.equals("")) {
            LoginResponse loginResponse = gson.fromJson(json, LoginResponse.class);
            listTimeline = loginResponse.getData().getTimelines();
        }

        indexTimeline = 1;
    }
}
