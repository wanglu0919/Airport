package com.intertid.airport.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.intertid.airport.app.R;
import com.intertid.airport.bean.SiteChannel;

public class SiteChannelAdapter extends BaseAdapter {

	private List<SiteChannel> siteChannels;
	private Context context;

	public SiteChannelAdapter(List<SiteChannel> siteChannels, Context context) {
		this.siteChannels = siteChannels;
		this.context = context;
	}

	@Override
	public int getCount() {

		return siteChannels.size();
	}

	@Override
	public Object getItem(int position) {

		return siteChannels.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHoler viewHoler = null;

		SiteChannel siteChannel = siteChannels.get(position);

		if (convertView == null) {
			convertView = View.inflate(context,
					R.layout.site_channel_item_layout, null);

			viewHoler = new ViewHoler();
			viewHoler.channelName = (TextView) convertView
					.findViewById(R.id.item_tv_name);
			viewHoler.channelIcon = (ImageView) convertView
					.findViewById(R.id.item_iv_icon);
			convertView.setTag(viewHoler);

		} else {

			viewHoler = (ViewHoler) convertView.getTag();

		}

		viewHoler.channelName.setText(siteChannel.getName());
		viewHoler.channelIcon.setBackgroundResource(siteChannel.getIconId());

		return convertView;
	}

	static class ViewHoler {

		TextView channelName;
		ImageView channelIcon;

	}

}
