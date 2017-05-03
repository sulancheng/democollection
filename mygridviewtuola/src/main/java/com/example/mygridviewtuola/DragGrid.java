package com.example.mygridviewtuola;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * ��ҳHomeFragment�е�GridView
 * @author Administrator
 *
 */
public class DragGrid extends GridView {
	/** �Ƿ����϶�*/
	public boolean isDrag = false;
	/** ���ʱ���Xλ�� */
	public int downX;
	/** ���ʱ���Yλ�� */
	public int downY;
	/** ���ʱ���Ӧ���������Xλ�� */
	public int windowX;
	/** ���ʱ���Ӧ���������Yλ�� */
	public int windowY;
	/** ��Ļ�ϵ�X */
	private int win_view_x;
	/** ��Ļ�ϵ�Y*/
	private int win_view_y;
	/** �϶�����x�ľ���  */
	int dragOffsetX;
	/** �϶�����Y�ľ���  */
	int dragOffsetY;
	/** ����ʱ���Ӧpostion */
	public int dragPosition;
	/** Up���Ӧ��ITEM��Position */
	private int dropPosition;
	/** ��ʼ�϶���ITEM��Position*/
	private int startPosition;
	/** item�� */
	private int itemHeight;
	/** item�� */
	private int itemWidth;
	/** �϶���ʱ���ӦITEM��VIEW */
	private View dragImageView = null;
	/** ������ʱ��ITEM��VIEW*/
	private ViewGroup dragItemView = null;
	/** WindowManager������ */
	private WindowManager windowManager = null;
	/** */
	private WindowManager.LayoutParams windowParams = null;
	/** item����*/
	private int itemTotalCount;
	/** һ�е�ITEM����*/
	private int nColumns = 3;
	/** ���� */
	private int nRows;
	/** ʣ�ಿ�� */
	private int Remainder;
	/** �Ƿ����ƶ� */
	private boolean isMoving = false;
	/** */
	private int holdPosition;
	/** �϶���ʱ��Ŵ�ı��� */
	private double dragScale = 1.2D;
	/** ����  */
	private Vibrator mVibrator;
	/** ÿ��ITEM֮���ˮƽ��� */
	private int mHorizontalSpacing = 15;
	/** ÿ��ITEM֮�����ֱ��� */
	private int mVerticalSpacing = 15;
	/* �ƶ�ʱ������������ID */
	private String LastAnimationID;
	/**
	 * ִ�ж����Ĳ���
	 */
	private RelativeLayout rootLayout;

	private Context mContext;
	public DragGrid(Context context) {
		super(context);
		init(context);
	}

	public DragGrid(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public DragGrid(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public void init(Context context){
		this.mContext = context;
		mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		//�������ļ������õļ��dipתΪpx
		mHorizontalSpacing = DataTools.dip2px(context, mHorizontalSpacing);
	}

	/**
	 * ��ȡ״̬���ĸ߶�
	 * @return
	 */
	private int getStatusBarHeight() {
		int result = 0;
		int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	/**
	 * �ж��Ƿ�����ָ��view��
	 * @param view
	 * @param ev
	 * @return
	 */
	private boolean inRangeOfView(View view, MotionEvent ev){
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		int downX = (int)ev.getX();
		int downY = (int)ev.getY();
		int x = location[0];
		int y = location[1] - getStatusBarHeight() - MainActivity.StruesHeight;
		int viewWidth = view.getWidth();
		int viewHeight = view.getHeight();

		if(downX < x || downX > (x + viewWidth) || downY < y || downY > (y + viewHeight)){
			return false;
		}
		return true;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			downX = (int) ev.getX();
			downY = (int) ev.getY();
			windowX = (int) ev.getX();
			windowY = (int) ev.getY();
			//��ǰ��ָ�������position
			int position = pointToPosition(downX, downY);
			if (position!=AdapterView.INVALID_POSITION) {
				//��ȡ��ǰ�����view
				View view = getChildAt(position - getFirstVisiblePosition());
				//�õ���item�е�ɾ����ť
				ImageView iView = (ImageView)view.findViewById(R.id.delet_iv);
				if (isDrag) {
					deleteInfo(position);
					if (!inRangeOfView(iView, ev)) { //�ж��ǲ��ǵ������ɾ������
						int x = (int) ev.getX();// �����¼���Xλ��
						int y = (int) ev.getY();// �����¼���yλ��
						isDrag = true;
						startPosition = pointToPosition(x, y);// ��һ�ε����postion
						dragPosition = pointToPosition(x, y);
						if (dragPosition != AdapterView.INVALID_POSITION) {

							//						if (startPosition == getCount() - 1) {
							//							return false;
							//						}
							ViewGroup dragViewGroup = (ViewGroup) getChildAt(dragPosition - getFirstVisiblePosition());
							TextView dragTextView = (TextView)dragViewGroup.findViewById(R.id.name_tv);
							dragTextView.setSelected(true);
							dragTextView.setEnabled(false);
							itemHeight = dragViewGroup.getHeight();
							itemWidth = dragViewGroup.getWidth();
							itemTotalCount = DragGrid.this.getCount();
							int row = itemTotalCount / nColumns;// �������
							Remainder = (itemTotalCount % nColumns);// ������һ�ж��������
							if (Remainder != 0) {
								nRows = row + 1;
							} else {
								nRows = row;
							}
							// ������������������϶����Ǹ�,���Ҳ�����-1
							if (dragPosition != AdapterView.INVALID_POSITION) {
								// �ͷŵ���Դʹ�õĻ�ͼ���档��������buildDrawingCache()�ֶ�û�е���setDrawingCacheEnabled(������),��Ӧ��������ʹ�����ַ�����
								win_view_x = windowX - dragViewGroup.getLeft();//VIEW����Լ���X�����
								win_view_y = windowY - dragViewGroup.getTop();//VIEW����Լ���y�����
								dragOffsetX = (int) (ev.getRawX() - x);//��ָ����Ļ����Xλ��-��ָ�ڿؼ��е�λ�þ��Ǿ�������ߵľ���
								dragOffsetY = (int) (ev.getRawY() - y);//��ָ����Ļ����yλ��-��ָ�ڿؼ��е�λ�þ��Ǿ������ϱߵľ���
								dragItemView = dragViewGroup;
								dragViewGroup.destroyDrawingCache();
								dragViewGroup.setDrawingCacheEnabled(true);
								Bitmap dragBitmap = Bitmap.createBitmap(dragViewGroup.getDrawingCache());
								//mVibrator.vibrate(50);//������ʱ��
								startDrag(dragBitmap, (int)ev.getRawX(),  (int)ev.getRawY());
								hideDropItem();
								dragViewGroup.setVisibility(View.INVISIBLE);
								isMoving = false;
								getParent().requestDisallowInterceptTouchEvent(true);
								return super.onInterceptTouchEvent(ev);
							}
						}
					} else {
						Log.i("DragGrid----->>>>>>>>>", "�ѵ��");//
					}
				}
				else {
					setOnClickListener(ev);
				}
			}
		}
		return super.onInterceptTouchEvent(ev);
	}


	/**
	 * ɾ������
	 * @param position
	 */
	public void deleteInfo(int position)
	{
		DeleteAnimation(position);
	}


	public void setRelativeLayout(RelativeLayout layout)
	{
		this.rootLayout = layout;
	}

	/**
	 * ɾ��View����
	 */
	public void DeleteAnimation(final int position)
	{

		final View view = getChildAt(position);
		view.setDrawingCacheEnabled(true);
		Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
		view.destroyDrawingCache();

		final ImageView imageView = new ImageView(mContext);
		imageView.setImageBitmap(bitmap);
		imageView.setLayoutParams(new LayoutParams((int) (Common.Width / 7),
				(int) (Common.Width / 7)));

		LayoutParams ivlp = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);

		final int aimPosit = getCount() - 1;//��ȡ���һ��view��λ�á�

		rootLayout.addView(imageView, ivlp);
		AnimatorSet animatorSet = createTranslationAnim(aimPosit, view, imageView);
		animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
		animatorSet.setDuration(500);
		animatorSet.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub
				DragAdapter adapter = (DragAdapter)getAdapter();
				adapter.setHidePosition(position);
				Common.isAnimaEnd = false;
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
				imageView.setVisibility(View.GONE);
				imageView.clearAnimation();
				rootLayout.removeView(imageView);
				DragAdapter adapter = (DragAdapter)getAdapter();
				adapter.deletInfo(position);
				final ViewTreeObserver vto = getViewTreeObserver();
				vto.addOnPreDrawListener(new OnPreDrawListener() {

					@Override
					public boolean onPreDraw() {
						// TODO Auto-generated method stub
						vto.removeOnPreDrawListener(this);
						animateReorder(position, aimPosit);
						return false;
					}
				});
			}
		});
		animatorSet.start();
	}

	/**
	 * ƽ�ƶ���
	 * @param view ��Ҫ�ƶ���view
	 * @param startX ��ʼ��X����
	 * @param endX ������X����
	 * @param startY ��ʼ��Y����
	 * @param endY ������Y����
	 * @return
	 */
	private AnimatorSet createAnimator(View view, float startX,
									   float endX, float startY, float endY) {
		ObjectAnimator animX = ObjectAnimator.ofFloat(view, "translationX",
				startX, endX);
		ObjectAnimator animY = ObjectAnimator.ofFloat(view, "translationY",
				startY, endY);
		AnimatorSet animSetXY = new AnimatorSet();
		animSetXY.playTogether(animX, animY);
		return animSetXY;
	}
	/**
	 * ���ཻ����item
	 */
	private void animateReorder(int deletePosition, int itemCount)
	{
		boolean isForward = itemCount > deletePosition;
		List<Animator> list = new ArrayList<Animator>();
		if (isForward) {

			for (int pos = deletePosition; pos < itemCount; pos++) {
				View view = getChildAt(pos - getFirstVisiblePosition());
				if ((pos + 1) % nColumns == 0) {
					list.add(createAnimator(view, -view.getWidth() * (nColumns - 1), 0, view.getHeight(), 0));
				}
				else {
					list.add(createAnimator(view, view.getWidth(), 0, 0, 0));
				}
			}

		}else {

			for (int pos = deletePosition; pos > itemCount; pos--) {
				View view = getChildAt(pos - getFirstVisiblePosition());
				if ((pos + nColumns) % nColumns == 0) {
					list.add(createAnimator(view,view.getWidth() * (nColumns - 1), 0, -view.getHeight(), 0));
				} else {
					list.add(createAnimator(view, -view.getWidth(), 0, 0, 0));
				}
			}
		}
		AnimatorSet set = new AnimatorSet();
		set.playTogether(list);
		set.setInterpolator(new AccelerateDecelerateInterpolator());
		set.setDuration(300);
		set.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
				Common.isAnimaEnd = true;
			}

		});
		set.start();
	}

	/**
	 *
	 * ����: createTranslationAnim
	 * <p>
	 * ����: TODO
	 * <p>
	 * ����: @param position ����: @param aimPosit ����: @param view ����: @param
	 * animView ����: @return
	 * <p>
	 * ����: AnimatorSet
	 * <p>
	 * �쳣
	 * <p>
	 * ����: wedcel wedcel@gmail.com
	 * <p>
	 * ʱ��: 2015��8��25�� ����4:49:23
	 */
	private AnimatorSet createTranslationAnim(int aimPosit,	View view, ImageView animView) {
		int startx = view.getLeft();
		int starty = view.getTop();
		View aimView = getChildAt(aimPosit);
		int endx = aimView.getLeft()+animView.getMeasuredWidth()/2;//����һ��view��λ��  Ҳ�ǽ�������������λ�á�
		int endy = aimView.getTop()+animView.getMeasuredHeight()/2;

		ObjectAnimator animX = ObjectAnimator.ofFloat(animView, "translationX",
				startx, endx);
		ObjectAnimator animY = ObjectAnimator.ofFloat(animView, "translationY",
				starty, endy);
		ObjectAnimator scaleX = ObjectAnimator.ofFloat(animView, "scaleX", 1f,
				0.5f);
		ObjectAnimator scaleY = ObjectAnimator.ofFloat(animView, "scaleY", 1f,
				0.5f);
		ObjectAnimator alpaAnim = ObjectAnimator.ofFloat(animView, "alpha", 1f,
				0.0f);

		AnimatorSet animSetXY = new AnimatorSet();
		animSetXY.playTogether(animX,animY,scaleX, scaleY, alpaAnim);
		return animSetXY;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (dragImageView != null && dragPosition != AdapterView.INVALID_POSITION) {
			// �ƶ�ʱ��Ķ�Ӧx,yλ��
			int x = (int) ev.getX();
			int y = (int) ev.getY();
			switch (ev.getAction()) {
				case MotionEvent.ACTION_DOWN:
					downX = (int) ev.getX();
					windowX = (int) ev.getX();
					downY = (int) ev.getY();
					windowY = (int) ev.getY();
					break;
				case MotionEvent.ACTION_MOVE:
					onDrag(x, y ,(int) ev.getRawX() , (int) ev.getRawY());
					if (!isMoving){
						OnMove(x, y);
					}
					if (pointToPosition(x, y) != AdapterView.INVALID_POSITION){
						break;
					}
					break;
				case MotionEvent.ACTION_UP:
					stopDrag();
					onDrop(x, y);
					getParent().requestDisallowInterceptTouchEvent(false);
					break;

				default:
					break;
			}
		}
		return super.onTouchEvent(ev);
	}

	public void refresh()
	{
		stopDrag();
		isDrag = false;
		DragAdapter mDragAdapter = (DragAdapter) getAdapter();
		mDragAdapter.setisDelete(false);
		mDragAdapter.notifyDataSetChanged();
	}

	/** ���϶������ */
	private void onDrag(int x, int y , int rawx , int rawy) {
		if (dragImageView != null) {
			windowParams.alpha = 0.6f;
			//			windowParams.x = rawx - itemWidth / 2;
			//			windowParams.y = rawy - itemHeight / 2;
			windowParams.x = rawx - win_view_x;
			windowParams.y = rawy - win_view_y;
			windowManager.updateViewLayout(dragImageView, windowParams);
		}
	}

	/** �������·ŵ���� */
	private void onDrop(int x, int y) {
		// �����϶�����x,y�����ȡ�϶�λ���·���ITEM��Ӧ��POSTION
		int tempPostion = pointToPosition(x, y);
		//		if (tempPostion != AdapterView.INVALID_POSITION) {
		dropPosition = tempPostion;
		DragAdapter mDragAdapter = (DragAdapter) getAdapter();
		//��ʾ���϶���ITEM
		mDragAdapter.setShowDropItem(true);
		//ˢ�����������ö�Ӧ��ITEM��ʾ
		mDragAdapter.notifyDataSetChanged();
		//		}
	}
	/**
	 * �����������
	 * @param ev
	 */
	public void setOnClickListener(final MotionEvent ev) {
		setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
										   int position, long id) {
				int x = (int) ev.getX();// �����¼���Xλ��
				int y = (int) ev.getY();// �����¼���yλ��
				isDrag = true;
				DragAdapter mDragAdapter = (DragAdapter) getAdapter();
				mDragAdapter.setisDelete(true);
				mDragAdapter.notifyDataSetChanged();
				startPosition = position;// ��һ�ε����postion
				dragPosition = position;
				//��������һ�����࣬���ܽ��г���
				//				if (startPosition == getCount() - 1) {
				//					return false;
				//				}
				ViewGroup dragViewGroup = (ViewGroup) getChildAt(dragPosition - getFirstVisiblePosition());
				TextView dragTextView = (TextView)dragViewGroup.findViewById(R.id.name_tv);


				dragTextView.setSelected(true);
				dragTextView.setEnabled(false);
				itemHeight = dragViewGroup.getHeight();
				itemWidth = dragViewGroup.getWidth();
				itemTotalCount = DragGrid.this.getCount();
				int row = itemTotalCount / nColumns;// �������
				Remainder = (itemTotalCount % nColumns);// ������һ�ж��������
				if (Remainder != 0) {
					nRows = row + 1;
				} else {
					nRows = row;
				}
				// ������������������϶����Ǹ�,���Ҳ�����-1
				if (dragPosition != AdapterView.INVALID_POSITION) {
					// �ͷŵ���Դʹ�õĻ�ͼ���档��������buildDrawingCache()�ֶ�û�е���setDrawingCacheEnabled(������),��Ӧ��������ʹ�����ַ�����
					win_view_x = windowX - dragViewGroup.getLeft();//VIEW����Լ���X�����
					win_view_y = windowY - dragViewGroup.getTop();//VIEW����Լ���y�����
					dragOffsetX = (int) (ev.getRawX() - x);//��ָ����Ļ����Xλ��-��ָ�ڿؼ��е�λ�þ��Ǿ�������ߵľ���
					dragOffsetY = (int) (ev.getRawY() - y);//��ָ����Ļ����yλ��-��ָ�ڿؼ��е�λ�þ��Ǿ������ϱߵľ���
					dragItemView = dragViewGroup;
					dragViewGroup.destroyDrawingCache();
					dragViewGroup.setDrawingCacheEnabled(true);
					Bitmap dragBitmap = Bitmap.createBitmap(dragViewGroup.getDrawingCache());
					//mVibrator.vibrate(50);//������ʱ��
					startDrag(dragBitmap, (int)ev.getRawX(),  (int)ev.getRawY());
					hideDropItem();
					dragViewGroup.setVisibility(View.INVISIBLE);
					isMoving = false;
					requestDisallowInterceptTouchEvent(true);
					return true;
				}
				return false;
			}
		});
	}

	public void startDrag(Bitmap dragBitmap, int x, int y) {
		stopDrag();
		windowParams = new WindowManager.LayoutParams();// ��ȡWINDOW�����
		//Gravity.TOP|Gravity.LEFT;��������
		windowParams.gravity = Gravity.TOP | Gravity.LEFT;
		//		windowParams.x = x - (int)((itemWidth / 2) * dragScale);
		//		windowParams.y = y - (int) ((itemHeight / 2) * dragScale);
		//�õ�preview���Ͻ��������Ļ������
		windowParams.x = x - win_view_x;
		windowParams.y = y  - win_view_y;
		//		this.windowParams.x = (x - this.win_view_x + this.viewX);//λ�õ�xֵ
		//		this.windowParams.y = (y - this.win_view_y + this.viewY);//λ�õ�yֵ
		//������קitem�Ŀ�͸�
		windowParams.width = (int) (dragScale * dragBitmap.getWidth());// �Ŵ�dragScale�������������϶���ı���
		windowParams.height = (int) (dragScale * dragBitmap.getHeight());// �Ŵ�dragScale�������������϶���ı���
		this.windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
		this.windowParams.format = PixelFormat.TRANSLUCENT;
		this.windowParams.windowAnimations = 0;
		ImageView iv = new ImageView(getContext());
		iv.setImageBitmap(dragBitmap);
		windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);// "window"
		windowManager.addView(iv, windowParams);
		dragImageView = iv;
	}

	/** ֹͣ�϶� ���ͷŲ���ʼ�� */
	private void stopDrag() {
		if (dragImageView != null) {
			windowManager.removeView(dragImageView);
			dragImageView = null;
		}
	}

	/** ��ScrollView�ڣ�����Ҫ���м���߶� */
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

	/** ���� ���� ��ITEM*/
	private void hideDropItem() {
		((DragAdapter) getAdapter()).setShowDropItem(false);
	}

	/** ��ȡ�ƶ����� */
	public Animation getMoveAnimation(float toXValue, float toYValue) {
		TranslateAnimation mTranslateAnimation = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0.0F,
				Animation.RELATIVE_TO_SELF,toXValue,
				Animation.RELATIVE_TO_SELF, 0.0F,
				Animation.RELATIVE_TO_SELF, toYValue);// ��ǰλ���ƶ���ָ��λ��
		mTranslateAnimation.setFillAfter(true);// ����һ������Ч��ִ����Ϻ�View����������ֹ��λ�á�
		mTranslateAnimation.setDuration(300L);
		return mTranslateAnimation;
	}

	/** �ƶ���ʱ�򴥷�*/
	public void OnMove(int x, int y) {
		// �϶���VIEW�·���POSTION
		int dPosition = pointToPosition(x, y);
		// �ж��·���POSTION�Ƿ����ʼ2�������϶���
		if (dPosition < getCount() - 1) {
			if ((dPosition == -1) || (dPosition == dragPosition)){
				return;
			}
			dropPosition = dPosition;
			if (dragPosition != startPosition){
				dragPosition = startPosition;
			}
			int movecount;
			//�϶���=��ʼ�ϵģ����� �϶��� �����ڷ��µ�
			if ((dragPosition == startPosition) || (dragPosition != dropPosition)){
				//����Ҫ�ƶ��Ķ�ITEM����
				movecount = dropPosition - dragPosition;
			}else{
				//����Ҫ�ƶ��Ķ�ITEM����Ϊ0
				movecount = 0;
			}
			if(movecount == 0){
				return;
			}

			int movecount_abs = Math.abs(movecount);

			if (dPosition != dragPosition) {
				//dragGroup����Ϊ���ɼ�
				ViewGroup dragGroup = (ViewGroup) getChildAt(dragPosition);
				dragGroup.setVisibility(View.INVISIBLE);
				float to_x = 1;// ��ǰ�·�positon
				float to_y;// ��ǰ�·��ұ�positon
				//x_vlaue�ƶ��ľ���ٷֱȣ�������Լ����ȵİٷֱȣ�
				float x_vlaue = ((float) mHorizontalSpacing / (float) itemWidth) + 1.0f;
				//y_vlaue�ƶ��ľ���ٷֱȣ�������Լ���ȵİٷֱȣ�
				float y_vlaue = ((float) mVerticalSpacing / (float) itemHeight) + 1.0f;
				Log.d("x_vlaue", "x_vlaue = " + x_vlaue);
				for (int i = 0; i < movecount_abs; i++) {
					to_x = x_vlaue;
					to_y = y_vlaue;
					//����
					if (movecount > 0) {
						// �ж��ǲ���ͬһ�е�
						holdPosition = dragPosition + i + 1;
						if (dragPosition / nColumns == holdPosition / nColumns) {
							to_x = - x_vlaue;
							to_y = 0;
						} else if (holdPosition % 3 == 0) {
							to_x = 2 * x_vlaue;
							to_y = - y_vlaue;
						} else {
							to_x = - x_vlaue;
							to_y = 0;
						}
					}else{
						//����,���Ƶ��ϣ����Ƶ���
						holdPosition = dragPosition - i - 1;
						if (dragPosition / nColumns == holdPosition / nColumns) {
							to_x = x_vlaue;
							to_y = 0;
						} else if((holdPosition + 1) % 3 == 0){
							to_x = -2 * x_vlaue;
							to_y = y_vlaue;
						}else{
							to_x = x_vlaue;
							to_y = 0;
						}
					}
					ViewGroup moveViewGroup = (ViewGroup) getChildAt(holdPosition);
					Animation moveAnimation = getMoveAnimation(to_x, to_y);
					moveViewGroup.startAnimation(moveAnimation);
					//��������һ���ƶ��ģ���ô����������������IDΪLastAnimationID
					if (holdPosition == dropPosition) {
						LastAnimationID = moveAnimation.toString();
					}
					moveAnimation.setAnimationListener(new AnimationListener() {

						@Override
						public void onAnimationStart(Animation animation) {
							// TODO Auto-generated method stub
							isMoving = true;
						}

						@Override
						public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onAnimationEnd(Animation animation) {
							// TODO Auto-generated method stub
							// ���Ϊ����������������ִ������ķ���
							if (animation.toString().equalsIgnoreCase(LastAnimationID)) {
								DragAdapter mDragAdapter = (DragAdapter) getAdapter();
								mDragAdapter.exchange(startPosition,dropPosition);
								startPosition = dropPosition;
								dragPosition = dropPosition;
								isMoving = false;
							}
						}
					});
				}
			}
		}
	}
}