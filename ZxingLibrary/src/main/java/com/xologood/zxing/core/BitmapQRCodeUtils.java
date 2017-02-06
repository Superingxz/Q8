package com.xologood.zxing.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.util.Hashtable;
import java.util.Vector;

/**
 * 解析二维码图片工具类
 * @author Administrator
 */
public class BitmapQRCodeUtils {
	
	MultiFormatReader multiFormatReader;

	public BitmapQRCodeUtils(Context context) {

		multiFormatReader = new MultiFormatReader();

		// 解码的参数
		Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>(2);
		// 可以解析的编码类型
		Vector<BarcodeFormat> decodeFormats = new Vector<BarcodeFormat>();
		if (decodeFormats == null || decodeFormats.isEmpty()) {
			decodeFormats = new Vector<BarcodeFormat>();

			// 这里设置可扫描的类型，我这里选择了都支持
			decodeFormats.addAll(DecodeFormatManager.ONE_D_FORMATS);
			decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
			decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
		}
		hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);

		// 设置继续的字符编码格式为UTF8
		hints.put(DecodeHintType.CHARACTER_SET, "UTF8");

		// 设置解析配置参数
		multiFormatReader.setHints(hints);

	}
	
	/**
	 * 获取解码结果
	 * 
	 * @param bitmap
	 * @return
	 */
	public Result getRawResult(Bitmap bitmap) {
		if (bitmap == null) {
			return null;
		}

		try {
			return multiFormatReader.decodeWithState(new BinaryBitmap(
					new HybridBinarizer(new BitmapLuminanceSource(bitmap))));
		}
		catch (NotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	//获取图片Bitmap
	public static Bitmap getPictureBitmap(String path){
	    BitmapFactory.Options options = new BitmapFactory.Options();  
        options.inJustDecodeBounds = true; // 先获取原大小  
        BitmapFactory.decodeFile(path, options);  
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        options.inJustDecodeBounds = false; // 获取新的大小  
		return BitmapFactory.decodeFile(path, options);
	}
	
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}
}
