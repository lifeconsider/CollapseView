package com.example.admin.testcollapseview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by admin on 2017/1/4.
 */

public class CollapseView extends LinearLayout {

    private long duration = 350;
    private final Context mContext;
    private TextView mNumberTextView;
    private TextView mTitleTextView;
    private RelativeLayout mTitleRelativeLayout;
    private RelativeLayout mContentRelativeLayout;
    private ImageView mArrowImageView;
    private int parentWidthMeasureSpec;
    private int parentHeightMeasureSpec;

    public CollapseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.collapse_view,this);
        initView();
    }

    private void initView() {
        mNumberTextView = (TextView) findViewById(R.id.numberTextView);
        mTitleTextView = (TextView) findViewById(R.id.titleTextView);
        mTitleRelativeLayout = (RelativeLayout) findViewById(R.id.title_relativelayout);
        mContentRelativeLayout = (RelativeLayout) findViewById(R.id.contentRelativeLayout);
        mArrowImageView = (ImageView) findViewById(R.id.arrowImageView);

        mTitleRelativeLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateArrow();
            }
        });

        //折叠
        collapse(mContentRelativeLayout);
    }

    public void setNum(String number){
        if (!TextUtils.isEmpty(number)){
            mNumberTextView.setText(number);
        }
    }

    public void setTitle(String title){
        if (!TextUtils.isEmpty(title)){
            mTitleTextView.setText(title);
        }
    }

    public void setContent(int resID){
        View view = LayoutInflater.from(mContext).inflate(resID, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.
                LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        view.setLayoutParams(layoutParams);

        mContentRelativeLayout.addView(view);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        parentWidthMeasureSpec = widthMeasureSpec;
        parentHeightMeasureSpec = heightMeasureSpec;
    }

    /**
     * 折叠
     * @param view
     */
    private void collapse(final View view) {
        final int measureHeight = view.getMeasuredHeight();

        Animation animation = new Animation() {
            @Override
            public boolean willChangeBounds() {
                return super.willChangeBounds();
            }

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().height = (int) (measureHeight - interpolatedTime * measureHeight);
                    view.requestLayout();
                }
            }
        };

        animation.setDuration(duration);
        view.startAnimation(animation);

    }

    /**
     * 箭头的上下指示变化的动画
     */
    private void rotateArrow() {
        int degree = 0;

        if (mArrowImageView.getTag() ==null || mArrowImageView.getTag().equals(true)){
            degree = 180;
            mArrowImageView.setTag(false);
            //展开
            expend(mContentRelativeLayout);

        }else{
            degree = 0;
            mArrowImageView.setTag(true);
            //折叠
            collapse(mContentRelativeLayout);
        }

        //执行动画，使箭头旋转
        mArrowImageView.animate().setDuration(duration).rotation(degree);
    }

    /**
     * 展开
     * @param view
     */
    private void expend(final View view) {

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        view.measure(parentWidthMeasureSpec,parentHeightMeasureSpec);

        int widthMeasureSpec = view.getMeasuredWidth();
        final int heightMeasureSpec = view.getMeasuredHeight();

        view.setVisibility(View.VISIBLE);
        Animation animation = new Animation(){
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime ==1){
                    view.getLayoutParams().height = heightMeasureSpec;
                }else{
                    view.getLayoutParams().height =  (int)(heightMeasureSpec*interpolatedTime);
                }

                view.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return super.willChangeBounds();
            }
        };

        animation.setDuration(duration);
        view.startAnimation(animation);
    }


}
