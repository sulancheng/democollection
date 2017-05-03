package com.example.mygridviewtuola;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mygridviewtuola.two.views.MyInmager;

import java.util.List;

public class DragAdapter extends BaseAdapter {
	/** TAG*/
	private final static String TAG = "DragAdapter";
	/** 是否显示底部的ITEM */
	private boolean isItemShow = false;
	private Context context;
	/** 控制的postion */
	private int holdPosition;
	/** 是否改变 */
	private boolean isChanged = false;
	/** 是否可见 */
	boolean isVisible = true;
	/** 可以拖动的列表（即用户选择的频道列表） */
	public List<DragView> channelList;
	/** TextView 频道内容 */
	class HolderView
	{
		private TextView item_text;
		private ImageView iv_icon;
		private MyInmager iv_delete;
	}
	private boolean isDelete = false;
	/** 要删除的position */
	public int remove_position = -1;

	private Handler mHandler = new Handler();

	private DragGrid grid;

	/**
	 * 指定隐藏的position
	 */
	private int hideposition = -1;

	public DragAdapter(Context context, List<DragView> channelList,DragGrid grid) {
		this.context = context;
		this.channelList = channelList;
		this.grid = grid;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return channelList == null ? 0 : channelList.size();
	}

	@Override
	public DragView getItem(int position) {
		// TODO Auto-generated method stub
		if (channelList != null && channelList.size() != 0) {
			return channelList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		HolderView holderView = null;
		View view = null;
		if (view == null) {
			holderView = new HolderView();
			view = LayoutInflater.from(context).inflate(R.layout.gridview_itemview, parent,false);
			holderView.iv_icon = (ImageView) view.findViewById(R.id.icon_iv);
			holderView.item_text = (TextView) view.findViewById(R.id.name_tv);
			holderView.iv_delete = (MyInmager) view.findViewById(R.id.delet_iv);

			LayoutParams mLayoutParams = holderView.iv_icon.getLayoutParams();
			mLayoutParams.width = (int) (Common.Width / 7);
			mLayoutParams.height = (int) (Common.Width / 7);
			holderView.iv_icon.setLayoutParams(mLayoutParams);

			view.setTag(holderView);
		}
		holderView = (HolderView)view.getTag();
		final DragView iconInfo = getItem(position);
		holderView.iv_icon.setImageResource(iconInfo.getResid());
		holderView.item_text.setText(iconInfo.getName());
		holderView.iv_delete.setEnabled(true);
		holderView.iv_delete.setClickable(true);
		holderView.iv_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Log.i("onAnimationEnds",position+"holderView.iv_delete");
						if (!Common.isAnimaEnd) {
							return;
						}
						grid.deleteInfo(position);
					}
				});
			}
		});

		if (position == getCount()-1){
			if (convertView == null) {
				convertView = view;
			}
			convertView.setEnabled(false);
			convertView.setFocusable(false);
			convertView.setClickable(false);
		}
		//		DragView channel = getItem(position);
		//
		//		item_text.setText(channel.getName());
		if (isChanged && (position == holdPosition) && !isItemShow) {
			holderView.item_text.setText("");
			holderView.item_text.setSelected(true);
			holderView.item_text.setEnabled(true);
			isChanged = false;
		}
		if (!isVisible && (position == -1 + channelList.size())) {

			holderView.item_text.setText("");
			holderView.item_text.setSelected(true);
			holderView.item_text.setEnabled(true);
		}
		if(remove_position == position){
			deletInfo(position);
		}
		if (!isDelete) {
			holderView.iv_delete.setVisibility(View.GONE);
		} else {
			if (!iconInfo.getName().equals("更多")) {
				holderView.iv_delete.setVisibility(View.VISIBLE);
			}
		}if (hideposition == position) {
			view.setVisibility(View.INVISIBLE);
		}else {
			view.setVisibility(View.VISIBLE);
		}
		return view;
	}

	public void setisDelete(boolean isDelete)
	{
		this.isDelete = isDelete;
	}

	/**
	 * 删除某个position
	 * @param position
	 */
	public void deletInfo(int position)
	{
		channelList.remove(position);
		hideposition = -1;
		notifyDataSetChanged();
	}


	/** 添加频道列表 */
	public void addItem(DragView channel) {
		channelList.add(channel);
		notifyDataSetChanged();
	}

	/** 拖动变更频道排序 */
	public void exchange(int dragPostion, int dropPostion) {
		holdPosition = dropPostion;
		DragView dragItem = getItem(dragPostion);
		Log.d(TAG, "startPostion=" + dragPostion + ";endPosition=" + dropPostion);
		if (dragPostion < dropPostion) {
			channelList.add(dropPostion + 1, dragItem);
			channelList.remove(dragPostion);
		} else {
			channelList.add(dropPostion, dragItem);
			channelList.remove(dragPostion + 1);
		}
		isChanged = true;
		notifyDataSetChanged();
	}

	/** 设置删除的position */
	public void setRemove(int position) {
		remove_position = position;
		notifyDataSetChanged();
	}

	/** 获取是否可见 */
	public boolean isVisible() {
		return isVisible;
	}

	/** 设置是否可见 */
	public void setVisible(boolean visible) {
		isVisible = visible;
	}
	/** 显示放下的ITEM */
	public void setShowDropItem(boolean show) {
		isItemShow = show;
	}

	public void setHidePosition(int position) {
		// TODO Auto-generated method stub
		this.hideposition = position;
		notifyDataSetChanged();
	}
}