package com.example.su.mygzzx.recycleview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.su.mygzzx.R;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {
	
	private List<String> mDatas;
	private Context mContext;
	private LayoutInflater inflater;
	private OnItemClickListener mOnItemClickListener;
	
	public interface OnItemClickListener{
		void onClick(int position);
		void onLongClick(int position);
	}
	
	public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
		this.mOnItemClickListener=onItemClickListener;
	}
	
	public MyRecyclerAdapter(Context context, List<String> datas){
		this.mContext=context;
		this.mDatas=datas;
		inflater=LayoutInflater.from(mContext);
	}
	
	@Override
	public int getItemCount() {
		
		return mDatas.size();
	}

	//填充onCreateViewHolder方法返回的holder中的控件
	@Override
	public void onBindViewHolder(MyViewHolder holder, final int position) {
		
		holder.tv.setText(mDatas.get(position));
		
		if(mOnItemClickListener!=null){
			holder.itemView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mOnItemClickListener.onClick(position);
				}
			});
			
			holder.itemView.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					mOnItemClickListener.onLongClick(position);
					return false;
				}
			});
		}
	}

	//重写onCreateViewHolder方法，返回一个自定义的ViewHolder
	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		
		View view = inflater.inflate(R.layout.item_home,parent,false);
		MyViewHolder holder=new MyViewHolder(view);
		return holder;
	}
	
	class MyViewHolder extends RecyclerView.ViewHolder {
		
		TextView tv;

		public MyViewHolder(View view) {
			super(view);
			tv=(TextView) view.findViewById(R.id.tv_item);
		}
		
	}

	public void addData(int position) {
		 mDatas.add(position, "Insert item");
	     notifyItemInserted(position);
	     notifyItemRangeChanged(position,mDatas.size());
	}

	public void removeData(int position) {
		mDatas.remove(position);
		notifyDataSetChanged();
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,mDatas.size());
	}
	
}
