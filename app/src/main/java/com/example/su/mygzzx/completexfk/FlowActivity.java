package com.example.su.mygzzx.completexfk;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.su.mygzzx.R;

public class FlowActivity extends Activity implements OnClickListener{
	
	 private WindowManager windowManager = null;
     private WindowManager.LayoutParams windowManagerParams = null;
     private FloatView floatView = null;
     private Button show,hide;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
	                      WindowManager.LayoutParams. FLAG_FULLSCREEN);//ȫ��
	        setContentView(R.layout.activity_main_haha);
	        buidView();
	        floatView = new FloatView(this);
	        floatView.setOnClickListener(this);
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
	

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}*/

	@Override
	public void onClick(View v) {
		Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
	}

}
