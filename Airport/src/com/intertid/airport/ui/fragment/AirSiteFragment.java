package com.intertid.airport.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.intertid.airport.adapter.SiteChannelAdapter;
import com.intertid.airport.app.MainTabActivity;
import com.intertid.airport.app.R;
import com.intertid.airport.bean.SiteChannel;
import com.intertid.airport.utils.Constants;

/**
 * 机场官网
 * 
 * @author wanglu 泰得利通
 * 
 */
public class AirSiteFragment extends Fragment implements OnItemClickListener {

	private View view;
	private MainTabActivity activity;
	private GridView site_gv_menu;
	private String chaneNames[] = new String[] { "航班信息", "旅客服务", "手机值机",
			"机场交通", "商务信息", "机场新闻","玩在江南","舌尖美食","空港酒店" };

	private int channelIcon[] = new int[] { R.drawable.icon1,
			R.drawable.icon2,
			R.drawable.icon3, R.drawable.icon4,
			R.drawable.icon5, R.drawable.icon6, R.drawable.icon7,R.drawable.icon8,R.drawable.icon9};

	private String urls[] = new String[] { Constants.URLs.AIR_INFO,
			Constants.URLs.AIR_TRAVELLER_SERVICE,
			Constants.URLs.AIR_PHONE, Constants.URLs.AIR_TRAFFIC,
			Constants.URLs.BUSI_INFO, Constants.URLs.AIR_NEWS,
			Constants.URLs.PLAY_JANNAN,Constants.URLs.DE_FOOD,Constants.URLs.AIR_HOTEL

	};
	private List<SiteChannel> siteChannels;
	private SiteChannelAdapter siteChannelAdapter;
	private ImageView service_call_bg;
	private static final String CALL_NUMBER = "0510-96889788";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_site_fragment, null);
		activity = (MainTabActivity) getActivity();
		findViews();
		initData();
		addListners();
		return view;
	}

	private void addListners() {

		service_call_bg.setOnClickListener(new OnClickListener() {// 服务电话

					@Override
					public void onClick(View v) {

						showComfirmSeviceCallDialog();
					}
				});

		site_gv_menu.setOnItemClickListener(this);
	}

	private void initData() {

		siteChannels = new ArrayList<SiteChannel>();

		for (int i = 0; i < chaneNames.length; i++) {
			SiteChannel siteChannel = new SiteChannel();
			siteChannel.setIconId(channelIcon[i]);
			siteChannel.setName(chaneNames[i]);
			siteChannel.setUrl(urls[i]);
			siteChannels.add(siteChannel);

		}

		siteChannelAdapter = new SiteChannelAdapter(siteChannels, activity);

		site_gv_menu.setAdapter(siteChannelAdapter);

	}

	private void findViews() {

		site_gv_menu = (GridView) view.findViewById(R.id.site_gv_menu);
		service_call_bg = (ImageView) view
				.findViewById(R.id.site_iv_service_call);
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {

		SiteChannel siteChannel = siteChannels.get(position);
		String urls = siteChannel.getUrl();
		WebFragment wf = new WebFragment();
		Bundle bundle = new Bundle();
		bundle.putString(WebFragment.ITENT_URL_KEY, urls);
		wf.setArguments(bundle);

		activity.showFragment(wf);

	}

	private void showComfirmSeviceCallDialog() {

		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setIcon(R.drawable.logo_title_icon);
		builder.setTitle("提示");
		builder.setMessage("确认要拨打该服务电话吗?" + "\n服务电话:" + CALL_NUMBER);
		builder.setCancelable(false);

		builder.setPositiveButton("确认",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(Intent.ACTION_CALL, Uri
								.parse("tel:" + CALL_NUMBER));
						getActivity().startActivity(intent);// 内部类

					}
				});
		
		
		
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					

					}
				});
		
		

		builder.create().show();

	}

}
