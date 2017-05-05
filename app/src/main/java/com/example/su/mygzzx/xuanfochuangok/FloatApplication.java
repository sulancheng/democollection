/**
 * @author manymore13  Blog:<a href="http://blog.csdn.net/manymore13">http://blog.csdn.net/manymore13</a>
 * @version 1.0
 */
package com.example.su.mygzzx.xuanfochuangok;

import android.app.Application;
import android.view.WindowManager;

public class FloatApplication extends Application{
	
	private WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
	
	public WindowManager.LayoutParams getWindowParams()
	{
		return windowParams;
	}

}
