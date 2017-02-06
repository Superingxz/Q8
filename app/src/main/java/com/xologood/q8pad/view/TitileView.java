package com.xologood.q8pad.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xologood.q8pad.R;

/**
 * Created by Administrator on 2016/10/26.
 */
public class TitileView extends RelativeLayout {
    TextView title, right_bt;
    TextView back;
    Context context;
    ImageView iv;

    public TitileView(Context context) {
        super(context);
        inttView(context);
    }

    public TitileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inttView(context);
    }


    void inttView(Context context) {
        inflate(context, R.layout.title_view, this);
        right_bt = (TextView) findViewById(R.id.right_bt);
        title = (TextView) findViewById(R.id.title_text);
        back = (TextView) findViewById(R.id.back_text);
        iv = (ImageView) findViewById(R.id.back_iv);
        this.context = context;
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lefebtIface!=null){
                    lefebtIface.onClick();
                }else{
                    back();
                }

            }
        });
        iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    public void setTitle(String titletext) {
        title.setText(titletext);
    }

    public void back() {
        ((Activity) context).finish();
    }

    public void setRightText(String text, final RightBtIface rightBtIface) {
        right_bt.setText(text);
        right_bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rightBtIface.onClick();
            }
        });
        right_bt.setVisibility(View.VISIBLE);

    }


    RightBtIface rightBtIface;

    public interface RightBtIface {
        void onClick();
    }

    public interface LeftBtIface {
        void onClick();
    }

    public void setLeftBackGone() {
        back.setVisibility(View.GONE);
    }

    public void setLeftIvGone() {
        iv.setVisibility(View.GONE);
    }

    public void setRgbtnVisible(int vis) {
        right_bt.setVisibility(vis);
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
