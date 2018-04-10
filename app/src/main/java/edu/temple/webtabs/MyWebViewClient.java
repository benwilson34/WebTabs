package edu.temple.webtabs;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import drawable.TabFragment;

public class MyWebViewClient extends WebViewClient {

    TabFragment fragment;

    public MyWebViewClient(TabFragment fragment){
        this.fragment = fragment;
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        Toast.makeText(fragment.getActivity(), description, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageFinished(WebView view, String url){
        super.onPageFinished(view, url);
        fragment.setUrl(url);
    }
}
