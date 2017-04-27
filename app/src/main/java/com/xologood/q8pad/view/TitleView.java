package com.xologood.q8pad.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xologood.q8pad.R;

/**
 * Created by Administrator on 2016/10/26.
 */
public class TitleView extends RelativeLayout {
    TextView tv_title, tv_right, tv_back;
    ImageView iv_back;
    Context context;
    String title,titleRight;

    public TitleView(Context context) {
        super(context);
        inttView(context);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleView);
        if(typedArray.hasValue(R.styleable.TitleView_mtitle)){
            title = typedArray.getString(R.styleable.TitleView_mtitle);
        }
        if(typedArray.hasValue(R.styleable.TitleView_titleRight)){
            titleRight = typedArray.getString(R.styleable.TitleView_titleRight);
        }
        typedArray.recycle();
        inttView(context);
    }


    void inttView(Context context) {
        this.context = context;
        inflate(context, R.layout.title_view, this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_back = (TextView) findViewById(R.id.tv_back);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_title.setText(title);

        tv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lefebtIface!=null){
                    lefebtIface.onClick();
                }else{
                    back();
                }

            }
        });
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    public void setTitle(String titletext) {
        tv_title.setText(titletext);
    }

    public void back() {
        ((Activity) context).finish();
    }

    public void setRightText(String text, final RightBtIface rightBtIface) {
        tv_right.setText(text);
        tv_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rightBtIface.onClick();
            }
        });
        tv_right.setVisibility(View.VISIBLE);

    }


    RightBtIface rightBtIface;

    public interface RightBtIface {
        void onClick();
    }

    public interface LeftBtIface {
        void onClick();
    }

    public void setLeftBackGone() {
        tv_back.setVisibility(View.GONE);
    }

    public void setLeftIvGone() {
        iv_back.setVisibility(View.GONE);
    }

    public void setRgbtnVisible(int vis) {
        tv_right.setVisibility(vis);
    }

    public RightBtIface getRightBtIface() {
        return rightBtIface;
    }

    public LeftBtIface lefebtIface;

    public LeftBtIface getLefebtIface() {
        return lefebtIface;
    }

    public void setLefebtIface(LeftBtIface lefebtIface) {
        this.lefebtIface = lefebtIface;
    }

    public void setRightBtIface(RightBtIface rightBtIface) {
        this.rightBtIface = rightBtIface;
    }
}
