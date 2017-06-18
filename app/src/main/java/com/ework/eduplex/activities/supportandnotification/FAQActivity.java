package com.ework.eduplex.activities.supportandnotification;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ework.eduplex.R;
import com.ework.eduplex.activities.BaseActivity;
import com.ework.eduplex.utils.Constant;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FAQActivity extends BaseActivity {

    String strSearch;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
//    @Bind(R.id.searchBox)
//    SearchBox searchBox;
    @Bind(R.id.wvFAQ)
    WebView wvFAQ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        initComponents();
    }

    /**
     * This method is used to initiate basic layout function such as var initialization, set toolbar, etc.
     */
    private void initComponents() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_faq));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WebSettings webSettings = wvFAQ.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUserAgentString("Mozilla/5.0 (Linux; U; Android 2.0; en-us; Droid Build/ESD20) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17");

        wvFAQ.setWebViewClient(new MyWebViewClient());
        log("WEBVIEW","URL:"+ Constant.API_URL + "/4eduplex/client/index.html");
        wvFAQ.loadUrl(Constant.API_URL + "/4eduplex/client/index.html");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_icon, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wvFAQ.canGoBack()) {
            wvFAQ.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
//            case R.id.action_search_icon:
//                if (!searchBox.getSearchOpen()) {
//                    openSearch();
//                }
//                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //class to overwrite
    private class MyWebViewClient extends WebViewClient {
        final String js = "";

//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            Log.d(TAG, "uri parse : " + Uri.parse(url).getHost());
//            if (Uri.parse(url).getHost().equals(EtterGlobal.G_URL)) {
//                // This is my web site, so do not override; let my WebView load the page
//                return false;
//            }
//            else{
//                showToast("Sorry you can't open this page");
//            }
//
//            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
//            return true;
//        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            if (Build.VERSION.SDK_INT >= 19) {
                view.evaluateJavascript(js, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {

                    }
                });
            } else {
                view.loadUrl(js);
            }
        }


    }

//    /**
//     * This method is used to the open search bar.
//     */
//    public void openSearch() {
//        searchBox.revealFromMenuItem(R.id.action_search_icon, this);
//        searchBox.setHint(getStringFromRes(R.string.search));
//
//        searchBox.setSearchListener(new SearchBox.SearchListener() {
//
//            @Override
//            public void onSearchOpened() {
//                // Use this to tint the screen
//            }
//
//            @Override
//            public void onSearchClosed() {
//                // Use this to un-tint the screen
//                closeSearch();
//            }
//
//            @Override
//            public void onSearchTermChanged(String term) {
//                // React to the search term changing
//                // Called after it has updated results
//            }
//
//            @Override
//            public void onSearch(final String searchTerm) {
//
//                wvFAQ.setWebViewClient(new WebViewClient() {
//
//                    @Override
//                    public void onPageFinished(WebView view, String url) {
//
//                        view.findAllAsync(searchTerm);
//                        view.findFocus();
//                    }
//                });
//                wvFAQ.loadUrl(Constant.FAQ_URL);
//            }
//
//            @Override
//            public void onResultClick(SearchResult result) {
//                //React to result being clicked
//            }
//
//            @Override
//            public void onSearchCleared() {
//
//            }
//
//        });
//
//    }

//    /**
//     * This method is used to close the search bar.
//     */
//    protected void closeSearch() {
//        searchBox.hideCircularly(this);
//    }
}
