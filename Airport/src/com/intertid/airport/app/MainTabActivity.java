package com.intertid.airport.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.intertid.airport.ui.fragment.AirSiteFragment;
import com.intertid.airport.ui.fragment.AirWeiBoFragment;
import com.intertid.airport.ui.fragment.AirWeiXinFragment;
import com.intertid.airport.ui.fragment.WebFragment;
import com.intertid.airport.utils.Constants;

@SuppressLint("NewApi")
public class MainTabActivity extends FragmentActivity implements
		OnClickListener {

	private Fragment showFragment;

	private AirSiteFragment airSiteFragment;
	private AirWeiBoFragment airWeiBoFragment;
	private AirWeiXinFragment airWeiXinFragment;
	private Button tab_rb_site, tab_rb_weibo, tab_rb_weixin;
	private WebFragment webFragment;
	private long lastExitTime;
	private Drawable siteNomal;
	private Drawable sitePress;
	private Drawable siteWeiBoNomal;
	private Drawable siteWeiBoPress;
	private Drawable siteWeixinNomal;
	private Drawable siteWeixinPress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_tab);

		findViews();
		initTabIcon();
		addLinsteners();

		initFragment();

	}

	private void initTabIcon() {
	/*机场网站图标初始化*/
		siteNomal = this.getResources().getDrawable(R.drawable.tab_site_nomal_icon);
		siteNomal.setBounds(0, 0, siteNomal.getMinimumWidth(),
				siteNomal.getMinimumHeight());
		sitePress = this.getResources().getDrawable(R.drawable.tab_site_press_icon);
		sitePress.setBounds(0, 0, sitePress.getMinimumWidth(),
				sitePress.getMinimumHeight());
		
		
		
		/*机场微博图标初始化*/
		siteWeiBoNomal = this.getResources().getDrawable(R.drawable.tab_weibo_nomal_icon);
		siteWeiBoNomal.setBounds(0, 0, siteWeiBoNomal.getMinimumWidth(),
				siteWeiBoNomal.getMinimumHeight());
		siteWeiBoPress = this.getResources().getDrawable(R.drawable.tab_weibo_press_icon);
		siteWeiBoPress.setBounds(0, 0, siteWeiBoPress.getMinimumWidth(),
				siteWeiBoPress.getMinimumHeight());
		/**
		 * 机场微信
		 */
		
		
		siteWeixinNomal = this.getResources().getDrawable(R.drawable.tab_weixin_nomal_icon);
		siteWeixinNomal.setBounds(0, 0, siteWeixinNomal.getMinimumWidth(),
				siteWeixinNomal.getMinimumHeight());
		siteWeixinPress = this.getResources().getDrawable(R.drawable.tab_weixin_press_icon);
		siteWeixinPress.setBounds(0, 0, siteWeixinPress.getMinimumWidth(),
				siteWeixinPress.getMinimumHeight());
		
		
		
		
	}

	private void showFragment() {

		FragmentManager fm = this.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.tab_fl_content, showFragment);
		ft.commit();

	}

	public void showFragment(WebFragment fragment) {
		this.webFragment = fragment;
		FragmentManager fm = this.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.tab_fl_content, fragment);
		ft.addToBackStack(null);
		ft.commit();
	}

	public void webFragmentGoBack() {
		this.webFragment = null;

	}

	@Override
	public void onBackPressed() {

		if (webFragment != null) {
			webFragment.goBack();
		} else {
			exitApp();
			// super.onBackPressed();
		}

	}

	private void exitApp() {

		if ((System.currentTimeMillis() - lastExitTime) > 2000) {
			Toast.makeText(getApplicationContext(), "再按一次退出程序",
					Toast.LENGTH_SHORT).show();
			lastExitTime = System.currentTimeMillis();
		} else {

			finish();
			System.exit(0);
		}

	}

	private void initFragment() {

		airSiteFragment = new AirSiteFragment();
		airWeiBoFragment = new AirWeiBoFragment();
		airWeiXinFragment = new AirWeiXinFragment();

		showFragment = airSiteFragment;
		showFragment();
	}

	private void addLinsteners() {

		tab_rb_site.setOnClickListener(this);
		tab_rb_weibo.setOnClickListener(this);
		tab_rb_weixin.setOnClickListener(this);

	}

	private void findViews() {
		tab_rb_site = (Button) findViewById(R.id.tab_rb_site);
		tab_rb_weibo = (Button) findViewById(R.id.tab_rb_weibo);
		tab_rb_weixin = (Button) findViewById(R.id.tab_rb_weixin);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.tab_rb_site:

			tab_rb_site.setTextColor(Color.WHITE);
			tab_rb_weibo.setTextColor(Color.parseColor("#73A2CD"));
			tab_rb_weixin.setTextColor(Color.parseColor("#73A2CD"));
			openBrowser(com.intertid.airport.utils.Constants.URLs.AIR_SITE);// 打开浏览器
			tab_rb_site.setCompoundDrawables(sitePress, null, null, null);
			tab_rb_weibo.setCompoundDrawables(siteWeiBoNomal, null, null, null);
			tab_rb_weixin.setCompoundDrawables(siteWeixinNomal, null, null, null);
			break;
		case R.id.tab_rb_weibo:
			showFragment = airWeiBoFragment;
			tab_rb_weibo.setTextColor(Color.WHITE);
			tab_rb_site.setTextColor(Color.parseColor("#73A2CD"));
			tab_rb_weixin.setTextColor(Color.parseColor("#73A2CD"));
			
			openBrowser(com.intertid.airport.utils.Constants.URLs.AIR_WEIBO);// 打开浏览器
			tab_rb_site.setCompoundDrawables(siteNomal, null, null, null);
			tab_rb_weibo.setCompoundDrawables(siteWeiBoPress, null, null, null);
			tab_rb_weixin.setCompoundDrawables(siteWeixinNomal, null, null, null);
			break;
		case R.id.tab_rb_weixin:
			showFragment = airWeiXinFragment;
			tab_rb_weixin.setTextColor(Color.WHITE);
			tab_rb_site.setTextColor(Color.parseColor("#73A2CD"));
			tab_rb_weibo.setTextColor(Color.parseColor("#73A2CD"));
			tab_rb_site.setCompoundDrawables(siteNomal, null, null, null);
			tab_rb_weibo.setCompoundDrawables(siteWeiBoNomal, null, null, null);
			tab_rb_weixin.setCompoundDrawables(siteWeixinPress, null, null, null);
			weixinFragment();
		
			break;

		}

	

	}

	private void weixinFragment() {
		WebFragment wf=null;
		
		if(this.webFragment!=null){
			webFragment.flushWebViewData(Constants.URLs.AIR_WEIXIN);
		}else{
			wf = new WebFragment();
			Bundle bundle = new Bundle();
			bundle.putString(WebFragment.ITENT_URL_KEY,
					Constants.URLs.AIR_WEIXIN);
			wf.setArguments(bundle);
			this.webFragment=null;

			showFragment(wf);
		}
		
		
		

	}

	/**
	 * 打开浏览器 wanglu 泰得利通
	 * 
	 * @param url
	 */
	private void openBrowser(String url) {

		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		Uri content_url = Uri.parse(url);
		intent.setData(content_url);
		startActivity(intent);

	}

}
