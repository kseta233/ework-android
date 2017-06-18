package com.ework.eduplex.activities.loginandregister;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ework.eduplex.R;
import com.ework.eduplex.activities.BaseActivity;
import com.ework.eduplex.utils.Constant;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TermAndConditionActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.wvFAQ)
    WebView wvFAQ;
    @Bind(R.id.searchBox)
    SearchBox searchBox;

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
        getSupportActionBar().setTitle(getResources().getString(R.string.title_terms_and_condition));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchBox.setVisibility(View.GONE);
        wvFAQ.loadUrl(Constant.TERMS_URL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_icon, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_search_icon:
                if (!searchBox.getSearchOpen()) {
                    openSearch();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is used to the open search bar.
     */
    public void openSearch() {
        searchBox.revealFromMenuItem(R.id.action_search_icon, this);
        searchBox.setHint(getStringFromRes(R.string.search));

        searchBox.setSearchListener(new SearchBox.SearchListener() {

            @Override
            public void onSearchOpened() {
                // Use this to tint the screen
            }

            @Override
            public void onSearchClosed() {
                // Use this to un-tint the screen
                closeSearch();
            }

            @Override
            public void onSearchTermChanged(String term) {
                // React to the search term changing
                // Called after it has updated results
            }

            @Override
            public void onSearch(final String searchTerm) {

                wvFAQ.setWebViewClient(new WebViewClient() {

                    @Override
                    public void onPageFinished(WebView view, String url) {

                        view.findAllAsync(searchTerm);
                        view.findFocus();
                    }
                });
                wvFAQ.loadUrl(Constant.TERMS_URL);
            }

            @Override
            public void onResultClick(SearchResult result) {
                //React to result being clicked
            }

            @Override
            public void onSearchCleared() {

            }

        });

    }

    /**
     * This method is used to close the search bar.
     */
    protected void closeSearch() {
        searchBox.hideCircularly(this);
    }
}
