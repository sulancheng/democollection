package com.example.su.mygzzx.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/10.
 */
public class SkyWheel extends FrameLayout {

    private Paint mCirclePaint;
    private Paint mCircleCenterPointPaint;
    private Paint mLinePaint;
    private GestureDetector mOnGestureListener;//手势识别器

    public SkyWheel(Context context) {
        this(context, null);
    }

    public SkyWheel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkyWheel(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //圆环的画笔,参数是抗锯齿。
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.BLACK);
        //设置镂空 无填充
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(2);//线宽
        //圆点画笔
        mCircleCenterPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCircleCenterPointPaint.setColor(Color.RED);
        //线的画笔灰色
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.GRAY);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(1);
        //手势识别器
        mOnGestureListener = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            //点击事件
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                float x = e.getX();
                float y = e.getY();
                View tapChildView = getTapChildView(x, y);
                if (tapChildView != null) {
                    //CommenUtils.showSafeToast(context, (String) tapChildView.getTag());
                }
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                //滑动的起点的坐标
                float startX = e1.getX();
                float startY = e1.getY();

                float startAngle = pointReleativeToXAnxisAngle(startX, startY);


                //滑动的结束点坐标
                float endX = e2.getX();
                float endY = e2.getY();

                float endAngle = pointReleativeToXAnxisAngle(endX, endY);

                float changeAngle = endAngle - startAngle;

                //调用方法重新摆放位置
                changeAngle(changeAngle);
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            /*
                          当手指滑动离开的时候调用，并且探测手指滑动离开的速度
                          velocityX， velocityY 手指离开时候速度，像素每秒
                          pixels per second

                          float velocityX, float velocityY值越大转动的圈数越多


                       */
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                //手指离开假看做开始坐标
                float startX = e2.getX();
                float startY = e2.getY();
                float startAngle = pointReleativeToXAnxisAngle(startX, startY);
//模拟滑动结束点：结束点有滑动离开的速度来决定float velocityX, float velocityY
                float endX = e2.getX() + velocityX / 1000;
                float endY = e2.getY() + velocityY / 1000;
                float endAngle = pointReleativeToXAnxisAngle(endX, endY);
                //转动的弧度。每秒
                float changleAngleWithSecond = (endAngle - startAngle)*1000;
                //这样转动看不来效果，用动画来解决
                //changeAngle(changleAngleWithSecond);
                changeAngleByAnimaton(changleAngleWithSecond);

                return false;
            }
        });
    }

    private void changeAngleByAnimaton(float changleAngleWithSecond) {
        //changleAngleWithSecond摩天轮要转动的弧度，总距离
        // 假定一个初速度为PI

        //执行时间，动画执行以毫秒为单位
       float duration = (float) (2*Math.abs(changleAngleWithSecond)/Math.PI)*1000;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(mChangeAngle, mChangeAngle+changleAngleWithSecond);

        //动画的更新的监听器，在动画执行的时候这个方法会不断的调用
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //不断的获得值，变化的，在区间之内的。
                float animatedValue = (float) animation.getAnimatedValue();
                changeAngle(animatedValue);
            }
        });
        valueAnimator.setDuration((long) duration);
        //减速效果
        valueAnimator.setInterpolator(new DecelerateInterpolator(2));
        valueAnimator.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //将事件给手势识别器
        mOnGestureListener.onTouchEvent(event);
        return true;
    }

    //整个摩天轮控件的中心点坐标,实现定义好
    private PointF mCenterPoint = new PointF();
    private float mCellAngle;
    //整个摩天轮控件半径
    private float RADUIS = 180;
    //每个子控件的位置的集合
    private List<RectF> mChildViewCenterRectFs = new ArrayList<>();
    //子空间中心点集合
    private List<PointF> mChildViewCenterPoints = new ArrayList<>();

    //输入一个坐标，看那个儿子包含它
    public View getTapChildView(float x, float y) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            RectF rectF = mChildViewCenterRectFs.get(i);
            //判断是否包含
            if (rectF.contains(x, y)) {
                return getChildAt(i);
            }
        }
        return null;
    }

    //计算按下或者离开时候的点与中心点连线 与x轴的夹角
    private float pointReleativeToXAnxisAngle(float x, float y) {
        float deltalY = y - mCenterPoint.y;
        float deltalX = x - mCenterPoint.x;
        float angleToX = (float) Math.atan2(deltalY, deltalX);
        return angleToX;
    }

    /*
      控件测量完毕调用这个方法 onSizeChanged，可以用onMeasure
   */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int childCount = getChildCount();
        //获取整个控件的宽高
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        mCenterPoint.set(measuredWidth * 0.5f, measuredHeight * 0.5f);
        //计算每个子控件分配的角度：平分
        mCellAngle = (float) (Math.PI * 2 / childCount);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //清空集合，保证集合里面每次都是最新位置子控件的坐标
        mChildViewCenterPoints.clear();
        mChildViewCenterRectFs.clear();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);

            int childViewWidth = childView.getMeasuredWidth();
            int childViewHeight = childView.getMeasuredHeight();
            //儿子的中心点
            PointF childViewCenterPoint = new PointF();
            childViewCenterPoint.x = (float) (mCenterPoint.x + RADUIS * Math.cos(mCellAngle * i + mChangeAngle));
            childViewCenterPoint.y = (float) (mCenterPoint.y + RADUIS * Math.sin(mCellAngle * i + mChangeAngle));

            mChildViewCenterPoints.add(childViewCenterPoint);
            //把子的位置存放到这个Rect对象
            //每个子控件的位置left, top, right, bottom进行封装
            RectF childViewRect = new RectF(childViewCenterPoint.x - 0.5f * childViewWidth, childViewCenterPoint.y - childViewHeight * 0.5f,
                    childViewCenterPoint.x + 0.5f * childViewWidth, childViewCenterPoint.y + childViewHeight * 0.5f);
            mChildViewCenterRectFs.add(childViewRect);
            //子控件的位置由这个方法的left, top, right, bottom决定
            childView.layout((int) childViewRect.left, (int) childViewRect.top, (int) childViewRect.right, (int) childViewRect.bottom);
        }

    }
    //一般onDraw是继承view时用的，viewgroup中一般不调用 用这个方法。


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        //绘制圆环

        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, RADUIS, mCirclePaint);
        canvas.drawCircle(mCenterPoint.x,mCenterPoint.y-RADUIS,8,mCircleCenterPointPaint);
        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, 4, mCircleCenterPointPaint);
        //中心点到每个孩子的连线,遍历出所有的中心点
        int size = mChildViewCenterPoints.size();
        for (int i = 0; i < size; i++) {
            PointF pointF = mChildViewCenterPoints.get(i);
            canvas.drawLine(mCenterPoint.x, mCenterPoint.y, pointF.x, pointF.y, mLinePaint);
        }
    }

    //对外界提供的改变角度的方法
    private float mChangeAngle;

    public void changeAngle(float changeAngle) {
        mChangeAngle = changeAngle;
        //刷新重绘
        requestLayout();
    }
}
