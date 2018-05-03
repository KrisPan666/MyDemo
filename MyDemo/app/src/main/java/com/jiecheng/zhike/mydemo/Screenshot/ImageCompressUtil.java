package com.jiecheng.zhike.mydemo.Screenshot;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class ImageCompressUtil {
	/**
	 * 压缩相关配置参数
	 */
	private static final int BITMAP_MAX_SIZE = 1024 * 225;
	private static final int COMPRESS_WIDTH = 768;//默认缩放宽度
	private static final int COMPRESS_HEIGHT = 1024;//默认缩放高度
	private static final int COMPRESS_QUALITY = 100;//默认压缩质量
	
	/**
	 * 压缩文件存放目录
	 */
	private final static String COMPRESS_PICTURE_DIRECTROY = "iflytek/compress/";
	private static final String TAG = "ImageCompressUtil";
	
	/**
	 * 压缩, TODO 每次压缩后保存始终都是相同文件
	 * @param origPath
	 * @return
	 */
	public static String compressSave(String origPath) {
		long t1 = System.currentTimeMillis();
		// 计算图片
		File origFile = new File(origPath);
		Log.i(TAG, "压缩 前文件大小：" + origFile.length() + ", 名称: " + origFile.getName());
		
		Bitmap compressBmp = null;
		if (origFile.length() <= BITMAP_MAX_SIZE) {
			// 仅仅裁剪尺寸
			//compressBmp = compressBySize(BitmapUtils.zoomImage(BitmapFactory.decodeFile(origPath)));
			return origPath;
		}
		else {
			// 压缩图片
			compressBmp = getSmallBitmap(origPath);
		}
		
		// 保存压缩后的图片
		String savePath = getSavePath();;

		BitmapUtils.saveFile(compressBmp, savePath);

		Log.i(TAG, "压缩 后文件大小：" + new File(savePath).length() + ",压缩用时:" + (System.currentTimeMillis() - t1) +  ", 保存: " + savePath);
		return savePath;
	}
	
	/**
	 * 得到图片保存的路径
	 * @return
	 */
	private static String getSavePath() {
		String saveDir = Environment.getExternalStorageDirectory().getPath();
		String compress_pic_direc = COMPRESS_PICTURE_DIRECTROY;
		String tempName = "compress_" + System.currentTimeMillis() + ".jpg";//临时文件名称
		
		String savePath = "";
		if (saveDir.endsWith(File.separator)) {
			savePath = saveDir + compress_pic_direc;
		}
		else {
			savePath = saveDir + File.separator + compress_pic_direc;
		}
		checkDirectory(savePath);
		
		savePath += tempName; 
		return savePath;
	}

	public static void checkDirectory(String savePath) {
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdirs();//TODO 创建多层目录,mkdir只能创建一层目录
		}
	}
	
	/**
	 * 根据路径获得突破并压缩返回bitmap用于显示
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath) {
		// 1. 进行缩放压缩, 以480 * 800 读取图片,防止内存溢出
		Bitmap compressBmp = compressBySize(decodeBitmap(filePath));
		
		// 2. 进行质量压缩
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		compressBmp.compress(Bitmap.CompressFormat.JPEG, COMPRESS_QUALITY, bos);
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		
		//L.d("debug", "压缩后的流: " + bos.toByteArray().length);
		return BitmapFactory.decodeStream(bis);
	}

	/**
	 * 读取图片
	 * @param filePath
	 * @return
	 */
	public static Bitmap decodeBitmap(String filePath) {
		// 1. 采样率压缩
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, COMPRESS_WIDTH, COMPRESS_HEIGHT);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
        
        return BitmapFactory.decodeFile(filePath, options);
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
			final float heightRatio = (float) height / (float) reqHeight;
			final float widthRatio = (float) width / (float) reqWidth;

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = Math.round( (widthRatio + heightRatio) / 2 ); 
			//Math.round( (widthRatio + heightRatio) / 2 );
			
		}
		Log.d(TAG, "inSampleSize " + inSampleSize);
		return inSampleSize;
	}
	
	/**
	 * 按尺图片压缩图片
	 * @param origBmp
	 * @param scale
	 * @return
	 */
	public static Bitmap compressBySize(Bitmap origBmp) {
		float origW = origBmp.getWidth();
		float origH = origBmp.getHeight();
	
		float widthScale = 1, heightScale = 1;
		if (origW > origH) {
			widthScale = origW / (float)COMPRESS_HEIGHT;
			heightScale = origH / (float)COMPRESS_WIDTH;
		}
		else {
			widthScale = origW / COMPRESS_WIDTH;
			heightScale = origH / COMPRESS_HEIGHT;
		}
		Log.i(TAG, "widthScale,heightScale, mid " 
				+ widthScale + ", " 
				+ heightScale );
		float sca = Math.max(heightScale, widthScale);
		Log.i(TAG, "缩放比例: " + sca);
		float scaleW = origW, scaleH = origH;
		if (sca > 1) {
			scaleW = (origW / sca);
			scaleH = (origH / sca);
		}
		return BitmapUtils.zoomImage(origBmp, (int)scaleW, (int)scaleH);
	}
}