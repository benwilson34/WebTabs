package drawable;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.temple.webtabs.MainActivity;
import edu.temple.webtabs.MyWebViewClient;
import edu.temple.webtabs.R;

public class TabFragment extends Fragment {
    private String url = "";
    private OnFragmentInteractionListener mListener;
    private WebView webView;

    private static final String ARG_URL = "ARG_URL";

    public TabFragment() {
        // Required empty public constructor
    }

    public static TabFragment newInstance(String url) {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();

        args.putString(ARG_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            url = getArguments().getString(ARG_PARAM1);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab, container, false);
        this.webView = v.findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new MyWebViewClient(this));

        return v;
    }

    @Override
    public void onStart(){
        super.onStart();

//        Log.d("webtabd-Start", "url1>>>"+url);

        if(url.isEmpty())
            url = getArguments().getString(ARG_URL);
//        Log.d("webtabd-Start", "url2>>>"+url);

        if(url.isEmpty())
            url = "http://google.com";
//        Log.d("webtabd-Start", "url3>>>"+url);

        reloadPage();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String newUrl);
    }

    public void setUrl(String url){
        Log.d("webtabd", "setting url to "+url);
        this.url = url;

        if(mListener != null)
            mListener.onFragmentInteraction(url);
    }

    public String getUrl(){ return url; }

    public void loadURL(String url){
        this.url = url;
        Log.d("webtabd", "load url: "+url);
        webView.loadUrl(url);
    }

    public void reloadPage(){
        Log.d("webtabd", "reload url: "+url);
        if(!url.isEmpty())
            webView.loadUrl(url);
    }
}
