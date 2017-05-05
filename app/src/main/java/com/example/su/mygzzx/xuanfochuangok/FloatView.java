
package com.example.su.mygzzx.xuanfochuangok;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

import com.example.su.mygzzx.R;

/**
 *  <a href="http://blog.csdn.net/manymore13">http://blog.csdn.net/manymore13</a>
 */
public class FloatView extends ImageView{
	
	private float mTouchX;
	private float mTouchY;
	private float x;
	private float y;
	int startX;
	int startY;
	private Context c;
	private int imgId = R.mipmap.ic_launcher;
	private int controlledSpace = 20; 
	private int screenWidth;
	boolean isShow = false;
	private OnClickListener mClickListener;
	
	private WindowManager windowManager ;
	
	private LayoutParams windowManagerParams = new LayoutParams();

	public FloatView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public FloatView(Context c)
	{
		super(c);
		initView(c);
	}
	// ��ʼ������
	public void initView(Context c)
	{
		windowManager = (WindowManager) c.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		screenWidth = windowManager.getDefaultDisplay().getWidth();
		this.setImageResource(imgId);
		windowManagerParams.type = LayoutParams.TYPE_PHONE;
		windowManagerParams.format = PixelFormat.RGBA_8888;	// ����͸��
		windowManagerParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
				| LayoutParams.FLAG_NOT_FOCUSABLE;
		windowManagerParams.gravity = Gravity.LEFT | Gravity.TOP;
		windowManagerParams.x = 1100;
		windowManagerParams.y = 1940;
		// �����������ڳ�������
		windowManagerParams.width = LayoutParams.WRAP_CONTENT;
		windowManagerParams.height = LayoutParams.WRAP_CONTENT;
		
	}
	
	public void setImgResource(int id)
	{
		imgId = id;
	}
		
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		x = event.getRawX();
		y = event.getRawY();
				
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
			{
				mTouchX = event.getX();
				mTouchY = event.getY();
				startX = (int) event.getRawX();
				startY = (int) event.getRawY();
				break;
				
			}
			case MotionEvent.ACTION_MOVE:
			{
				updateViewPosition();
				break;
			}
			case MotionEvent.ACTION_UP:
			{
			
				if(Math.abs(x - startX) < controlledSpace && Math.abs(y - startY) < controlledSpace)
				{
					if(mClickListener != null)
					{
						mClickListener.onClick(this);
					}
				}
				Log.i("tag", "x="+x+" startX+"+startX+" y="+y+" startY="+startY);
				if(x <= screenWidth/2)
				{
					x = 0;
				}else{
					x = screenWidth;
				}
				
				updateViewPosition();
							
				break;
			}
		}
			
		return super.onTouchEvent(event);
	}
	
	// ���ظô���
	public void hide()
	{
		if(isShow)
		{
			windowManager.removeView(this);
			isShow = false;
		}
				
	}
	
	// ��ʾ�ô���
	public void show()
	{
		if(isShow == false)
		{
			windowManager.addView(this, windowManagerParams);
			isShow = true;
		}
		
	}
	
	@Override
    public void setOnClickListener(OnClickListener l) {
         this.mClickListener = l;
    }
	
    private void updateViewPosition() {
         // ���¸�������λ�ò���
	     windowManagerParams.x = (int) (x - mTouchX);
	     windowManagerParams.y = (int) (y - mTouchY);
	     windowManager.updateViewLayout(this, windowManagerParams); // ˢ����ʾ
    }
}
