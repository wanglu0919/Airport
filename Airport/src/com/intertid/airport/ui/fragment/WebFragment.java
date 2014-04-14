package com.intertid.airport.ui.fragment;

import com.intertid.airport.app.MainTabActivity;
import com.intertid.airport.app.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

/**
 * 机场微博
 * 
 * @author wanglu 泰得利通
 * 
 */
public class WebFragment extends Fragment {

	private View view;
	private WebView web_wv_content;
	public static final String ITENT_URL_KEY = "intent_url_key";
	private Button head_btn_back;
	private Button head_btn_index;//首页
	private ProgressBar wb_pb;
	private MainTabActivity mainTabActivity;
	private String rootUrl;
	private int url_dept=0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.web_fragment_layout, null);
		findViews();
		showBtnState();
		mainTabActivity=(MainTabActivity) getActivity();
		addListners();
		
		rootUrl=getArguments().getString(ITENT_URL_KEY);
		initWebViewData();

		return view;
	}

	/**
	 * 事件监听
	 *wanglu 泰得利通
	 */
	private void addListners() {
		head_btn_back.setOnClickListener(new OnClickListener() {//返回
			
			@Override
			public void onClick(View v) {
			
				goBack();
				
			}
		});
		
		
		head_btn_index.setOnClickListener(new OnClickListener() {//首页
			
			@Override
			public void onClick(View v) {
				
				goHome();
			}
		});
		
	}
	
	
	/**
	 * 首页
	 *wanglu 泰得利通
	 */
	private void goHome(){
		
		url_dept=0;
		mainTabActivity.webFragmentGoBack();//通知主activity返回
		getFragmentManager().popBackStack();
		
	}
	
	
	
	
	/**
	 * 返回
	 *wanglu 泰得利通
	 */
	public void goBack(){
		
		if(url_dept==0){
			mainTabActivity.webFragmentGoBack();//通知主activity返回
			getFragmentManager().popBackStack();
			
		}else{
			web_wv_content.goBack();
			url_dept--;
			showBtnState();
		}
		
		
	}

	
	public void flushWebViewData(String  url){
	
		url_dept=0;
		showBtnState();
		rootUrl=url;
		initWebViewData();
		
	}
	
	private void initWebViewData() {

		
		if (rootUrl!=null) {

			web_wv_content.loadUrl(rootUrl);

			web_wv_content.setWebChromeClient(new WebChromeClient() {//进度条处理
				@Override
				public void onProgressChanged(WebView view, int newProgress) {

					super.onProgressChanged(view, newProgress);

					if (newProgress != 100) {

						wb_pb.setVisibility(View.VISIBLE);
					} else {
						wb_pb.setVisibility(View.GONE);
					}

				}
			});
			
			
			
			web_wv_content.setWebViewClient(new WebViewClient(){

				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					
					view.loadUrl(url);
					url_dept++;//标示又深了一层URL
					showBtnState();
					Log.i("Web", url_dept+":"+url);
					return true;
				}
				
			});

		}
		
		

	}

	@SuppressLint("SetJavaScriptEnabled")
	private void findViews() {

		web_wv_content = (WebView) view.findViewById(R.id.web_wv_content);
		WebSettings webSettings = web_wv_content.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(true);
		
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		head_btn_back=(Button) view.findViewById(R.id.head_btn_back);
		head_btn_index=(Button) view.findViewById(R.id.head_btn_index);
		wb_pb = (ProgressBar) view.findViewById(R.id.wb_pb);
		
		
		head_btn_back.setVisibility(View.VISIBLE);
	}
	
	
	private void showBtnState(){
		
		if(url_dept>=0){
			
			head_btn_back.setVisibility(View.VISIBLE);
			
		}
		
		if(url_dept>=1){
			head_btn_index.setVisibility(View.VISIBLE);
		}else{
			head_btn_index.setVisibility(View.GONE);
		}
		
	}

}
