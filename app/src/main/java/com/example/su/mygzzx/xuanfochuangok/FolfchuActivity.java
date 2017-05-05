package com.example.su.mygzzx.xuanfochuangok;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.su.mygzzx.R;

public class FolfchuActivity extends Activity implements OnClickListener{
	
	 private WindowManager windowManager = null;
     private WindowManager.LayoutParams windowManagerParams = null;
     private FloatView floatView = null;
     private Button show,hide;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);//ȡ��������
	        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
	                      WindowManager.LayoutParams. FLAG_FULLSCREEN);//ȫ��
	        setContentView(R.layout.activity_xuanfuchuna);

		ImageView left = (ImageView) findViewById(R.id.left);
		ImageView right = (ImageView) findViewById(R.id.right);
		if (Build.VERSION.SDK_INT >= 23) {
			if(!Settings.canDrawOverlays(this)) {
				Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
				startActivity(intent);
				return;
			} else {
				//绘ui代码, 这里说明6.0系统已经有权限了
				buidView();
				floatView = new FloatView(this);
				floatView.setOnClickListener(this);
			}
		} else {
			//绘ui代码,这里android6.0以下的系统直接绘出即可
			buidView();
			floatView = new FloatView(this);
			floatView.setOnClickListener(this);
		}

		TranslateAnimation leftanm = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0f, TranslateAnimation.RELATIVE_TO_SELF, 0f,
				TranslateAnimation.RELATIVE_TO_SELF, 1.2f, TranslateAnimation.RELATIVE_TO_SELF, 0f);
		leftanm.setDuration(800);
		leftanm.setFillAfter(true);
		//设置动画插入器
		leftanm.setInterpolator(new OvershootInterpolator());//出屏再回。
		left.setAnimation(leftanm);
		right.setAnimation(leftanm);


	}
	
	private void buidView()
	{
		show = (Button)findViewById(R.id.show);
		hide = (Button)findViewById(R.id.hide);
		show.setOnClickListener(new ButtonClick());
		hide.setOnClickListener(new ButtonClick());
	}
	
	class ButtonClick implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.show){
				floatView.show();
			}else if(v.getId() == R.id.hide){
				floatView.hide();
			}
			
		}
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		startActivity(new Intent(FolfchuActivity.this,donghuaActivity.class));
		floatView.hide();
		Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
	}

}
