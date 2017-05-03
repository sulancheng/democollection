package com.example.su.mygzzx.recycleview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.su.mygzzx.R;
import com.example.su.mygzzx.recycleview.adapter.MyRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

//博客地址：http://blog.csdn.net/dmk877/article/details/50816933
public class RecycleActivity extends ActionBarActivity {

	private RecyclerView recyclerView;
	private List<String> mDatas;
	private MyRecyclerAdapter recycleAdapter;
	
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recycle);

		recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		
		initData();
		recycleAdapter=new MyRecyclerAdapter(RecycleActivity.this, mDatas);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		//设置布局管理器
		recyclerView.setLayoutManager(layoutManager);
		//设置为垂直布局，这也是默认的
//		layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
		//设置Adapter
		recyclerView.setAdapter(recycleAdapter);
		recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
		//设置增加或删除条目的动画
		recyclerView.setItemAnimator(new DefaultItemAnimator());

		recycleAdapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
			
			@Override
			public void onLongClick(int position) {
				Toast.makeText(RecycleActivity.this,"onLongClick事件       您点击了第："+position+"个Item",Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onClick(int position) {
				Toast.makeText(RecycleActivity.this,"onClick事件       您点击了第："+position+"个Item",Toast.LENGTH_SHORT).show();
			}
		});
		
	}

	private void initData() {
		mDatas = new ArrayList<String>();
		for (int i=0; i < 80; i++) {
			mDatas.add("item"+i);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.id_action_add:
			recycleAdapter.addData(1);
			break;
			
		case R.id.id_action_delete:
			recycleAdapter.removeData(1);
			break;
			
		case R.id.id_action_gridview:
			//recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
			doSomething2();
			break;
			
		case R.id.id_action_listview:
			recyclerView.setLayoutManager(new LinearLayoutManager(this));
			break;
			
		case R.id.id_action_horizontalGridView:
//			recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,
//					StaggeredGridLayoutManager.HORIZONTAL));
			doSomething();
			break;
			
		}
		return true;
	}
	public void doSomething(){
		CustomSGLayoutManager skyLayoutManager = new CustomSGLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);//实例化自定义类
		skyLayoutManager.setSpeedRatio(0.2);//设置其速度因子
		recyclerView.setLayoutManager(skyLayoutManager);
	}
	public void doSomething2(){
		MyGridLayoutManager skyLayoutManager = new MyGridLayoutManager(this,4);//实例化自定义类
		skyLayoutManager.setSpeedRatio(0.1);//设置其速度因子
		recyclerView.setLayoutManager(skyLayoutManager);
	}
	public class CustomSGLayoutManager extends StaggeredGridLayoutManager {
		private double speedRatio;
//		public CustomSGLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//			super(context, attrs, defStyleAttr, defStyleRes);
//		}

		public CustomSGLayoutManager(int spanCount, int orientation) {
			super(spanCount, orientation);
		}

		@Override
		public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
			int a = super.scrollHorizontallyBy((int)(speedRatio*dx), recycler, state);//屏蔽之后无滑动效果，证明滑动的效果就是由这个函数实现
			if(a == (int)(speedRatio*dx)){
				return dx;
			}
			return a;
		}

		public void setSpeedRatio(double speedRatio){
			this.speedRatio = speedRatio;
		}
	}
	public class MyGridLayoutManager extends GridLayoutManager{
		private double speedRatio;
		public MyGridLayoutManager(Context context, int spanCount) {
			super(context, spanCount);
		}

		public MyGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
			super(context, spanCount, orientation, reverseLayout);
		}

		@Override
		public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
			int a = super.scrollVerticallyBy((int)(speedRatio*dy), recycler, state);//屏蔽之后无滑动效果，证明滑动的效果就是由这个函数实现
			if(a == (int)(speedRatio*dy)){
				return dy;
			}
			return a;
		}
		public void setSpeedRatio(double speedRatio){
			this.speedRatio = speedRatio;
		}
	}
	
}
