package com.xologood.zxing.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.xologood.zxing.R;
import com.xologood.zxing.core.DecodeThread;



public class CaptureShowActivity extends Activity implements OnClickListener{
	private Context mContext;
	private TextView text;
	private ImageView image;
	
	private TextView title_text;
	private Button title_left_btn;
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_capture_show);
		mContext = CaptureShowActivity.this;
		initUI();
		initData();
	}
	
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		title_text.setText("扫描结果");
		
		text = (TextView) findViewById(R.id.capture_show_text);
		image = (ImageView) findViewById(R.id.capture_show_image);
	}

	private void initData() {
		Bundle extras = getIntent().getBundleExtra("pict_bundle");
		String num = (String) getIntent().getSerializableExtra("ewm_num");
		String type = (String) getIntent().getSerializableExtra("ewm_type");

		initView(extras, num);
	}
	
	private void initView(Bundle extras, String num) {
		if(extras != null){
			int width = (int) getIntent().getSerializableExtra("pict_width"); 
			int height = (int) getIntent().getSerializableExtra("pict_height"); 
			
			LayoutParams lps = new LayoutParams(width, height);
			lps.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
			lps.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
			lps.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
			
			image.setLayoutParams(lps);
			

			Bitmap barcode = null;
			byte[] compressedBitmap = extras.getByteArray(DecodeThread.BARCODE_BITMAP);
			if (compressedBitmap != null) {
				barcode = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length, null);
				barcode = barcode.copy(Bitmap.Config.RGB_565, true);
			}

			image.setImageBitmap(barcode);
		}
		if(num != null){
			text.setText(num);
		}
	}
	@Override
	public void onClick(View v) {
		int i = v.getId();
		if (i == R.id.layout_main_left_btn) {
			finish();

		} else {
		}
	}
}
