package com.jiecheng.zhike.mydemo.Screenshot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class BitmapUtils {

	// 图片标准宽度
	public static int IMG_DEFAULT_STANDARD_W_SIZE = 1024;
	// 图片标准高度
	public static int IMG_DEFAULT_STANDARD_H_SIZE = 768;
	//压缩阈值 add by zhangaiming
	public static int COMPRESS_THREAD = 200;

	public static Bitmap getBitmap(String path) {
		if (TextUtils.isEmpty(path))
			return null;

		byte[] buffer = FileUtils.getBuffer(path);

		return getBitmap(buffer);
	}

	public static Bitmap getBitmap(InputStream is) {
		try {
			if (is == null)
				return null;

			int size = is.available();
			byte[] bs = new byte[size];
			is.read(bs, 0, bs.length);

			return getBitmap(bs);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static Bitmap getBitmap(byte[] coverByte) {
		try {
			if (coverByte == null)
				return null;

			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeByteArray(coverByte, 0, coverByte.length, opts);
			opts.inSampleSize = computeSampleSize(coverByte);
			opts.inJustDecodeBounds = false;
			return BitmapFactory.decodeByteArray(coverByte, 0,
					coverByte.length, opts);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

    private static final int COLORDRAWABLE_DIMENSION = 2;
    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;

    public static Bitmap getBitmapFromDrawable(Drawable drawable) {
//	        if (drawable == null) {
//	            return null;
//	        }
//
//	        if (drawable instanceof BitmapDrawable) {
//	            return ((BitmapDrawable) drawable).getBitmap();
//	        }
//
//	        try {
//	            Bitmap bitmap;
//
//	            if (drawable instanceof ColorDrawable) {
//	                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
//	            } else {
//	                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
//	            }
//
//	            Canvas canvas = new Canvas(bitmap);
//	            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//	            drawable.draw(canvas);
//	            return bitmap;
//	        } catch (OutOfMemoryError e) {
//	            return null;
//	        }
    	return null;
	    }

	public static int computeSampleSize(byte[] coverByte) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inScaled = false;
		options.inDither = false;
		options.inJustDecodeBounds = true;

		try {
			BitmapFactory.decodeByteArray(coverByte, 0, coverByte.length,
					options);
		} catch (OutOfMemoryError e) {
			return 1;
		}

		int width = options.outWidth;
		int height = options.outHeight;

		if (width == 0 || height == 0)
			return 1;

		return Math.max(width / IMG_DEFAULT_STANDARD_W_SIZE, height
				/ IMG_DEFAULT_STANDARD_H_SIZE);
	}

	public static int computeSampleSize(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inScaled = false;
		options.inDither = false;
		options.inJustDecodeBounds = true;

		try {
			BitmapFactory.decodeFile(path, options);
		} catch (OutOfMemoryError e) {
			return 1;
		}

		int width = options.outWidth;
		int height = options.outHeight;

		if (width == 0 || height == 0)
			return 1;

		return Math.max(width / IMG_DEFAULT_STANDARD_W_SIZE, height
				/ IMG_DEFAULT_STANDARD_H_SIZE);
	}

	public static int computeSampleSize(String path, int samplewidth, int sampleheight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inScaled = false;
		options.inDither = false;
		options.inJustDecodeBounds = true;

		try {
			BitmapFactory.decodeFile(path, options);
		} catch (OutOfMemoryError e) {
			return 1;
		}

		int width = options.outWidth;
		int height = options.outHeight;

		if (width == 0 || height == 0)
			return 1;

		return Math.max(width / samplewidth, height
				/ sampleheight);
	}

//	public static void setViewHolder(ViewHolder holder, String path, int width) {
//		if (!holder.tag.equals(path)) {
//			holder.image.destroyDrawingCache();
//			Bitmap bm = getBitmapPath(path, width);
//			if (bm == null)
//				return;
//
//			holder.image.setImageBitmap(bm);
//		}
//		holder.tag = path;
//	}
//
//	public static class ViewHolder {
//		public ImageView image;
//		public String tag = "";
//	}

	private static int getSize(Point pt) {
		return pt.x > pt.y ? pt.x : pt.y;
	}

	public static Point getBitmapSize(String path) {
		Point pt = new Point();
		BufferedInputStream in = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		try {
			in = new BufferedInputStream(new FileInputStream(path));
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(in, null, options);
			pt.set(options.outWidth, options.outHeight);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			FileUtils.closeCloseable(in);
		}

		return pt;
	}

	public static Bitmap scaledBitmap(String path, int width, int height) {
		Bitmap bitmap = null;
		try{
			bitmap = getBitmap(path);
			Matrix m = new Matrix();
	        m.setRectToRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), new RectF(0, 0, width, height), Matrix.ScaleToFit.CENTER);
	        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
		}catch(OutOfMemoryError ex) {
			return null;
		}finally {
			if(null != bitmap) {
				bitmap.recycle();
				bitmap = null;
				System.gc();
			}
		}
	}

	public static Bitmap revitionImageSize(String path) throws IOException {
		return revitionBitmap(path);
	}

	public static Bitmap revitionBitmap(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

		newOpts.inJustDecodeBounds = false;
		newOpts.inPreferredConfig = Config.RGB_565;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 1024f;// 这里设置高度为800f
		float ww = 768f;// 这里设置宽度为480f

		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		float be = 1f;// be=1表示不缩放
		// be = (int) ((w / ww + h / hh) / 2);

		if (w >= h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = newOpts.outWidth / ww * 1.0f;
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = newOpts.outHeight / hh * 1.0f;
		}

		newOpts.inSampleSize = calculateInSampleSize(be);// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		newOpts.inJustDecodeBounds = false;
		// 优化内存溢出问题 modify by xfchen 2015/06/26
		try {
			bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		} catch (OutOfMemoryError ex) {
			Log.e("---OutOfMemory----", "revitionBitmap1");
			// 节约内存
			newOpts.inSampleSize *= 2;
			newOpts.inDither = false;
			newOpts.inPreferredConfig = Config.RGB_565;
			newOpts.inPurgeable = true;
			newOpts.inInputShareable = true;
			bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		} catch (Exception ex) {
			Log.e("-------OutOfMemory-----", "revitionBitmap2");
			bitmap = null;
		}

		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	private static int calculateInSampleSize(float resSampleSize) {
		int inSampleSize = 1;

		// 只能是2的次幂
		if(resSampleSize < 2)
			inSampleSize = 1;
		else if (resSampleSize < 4)
			inSampleSize = 2;
		else if (resSampleSize < 6.5)
			inSampleSize = 4;
		else if (resSampleSize < 8)
			inSampleSize = 8;
		else
			inSampleSize = (int) resSampleSize;
		return inSampleSize;
	}

	/**
	 * 压缩图片
	 *
	 * @param image
	 * @return
	 */
	public static Bitmap compressImage(Bitmap image) {

		if (image == null)
			return null;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 90, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 90;
		while (baos.toByteArray().length / 1024 > 200) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			options -= 10;// 每次都减少10
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
		}
		Log.e("------------------", options + "");
		image.recycle();
		image = null;
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		// 优化内存溢出 modify by xfchen 2015/06/17
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		} catch (OutOfMemoryError e) {
			Log.e("----OutOfMemory--", "compressImage");
			Options op = new Options();
			// 节约内存
			op.inSampleSize *= 2;
			op.inJustDecodeBounds = false;
			op.inDither = false;
			op.inPreferredConfig = Config.RGB_565;
			op.inPurgeable = true;
			op.inInputShareable = true;

			bitmap = BitmapFactory.decodeStream(isBm, null, op);
		} catch (Exception e) {
			bitmap = null;
		}

		return bitmap;

		// Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//
		// 把ByteArrayInputStream数据生成图片
		// return bitmap;
	}

	/**
	 * 获取旋转正常的图片
	 *
	 * @param pathName
	 * @param bitmap
	 * @return
	 */
	public static Bitmap getRotateImg(String pathName, Bitmap src) {
		if (pathName == null || pathName.equals("") || src == null)
			return null;
		if (!new File(pathName).exists()) {
			return null;
		}

		ExifInterface exif = null;
		try {
			exif = new ExifInterface(pathName);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if (exif == null)
			return src;

		int rate = 0;
		int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
				ExifInterface.ORIENTATION_NORMAL);

		switch (orientation) {
		case ExifInterface.ORIENTATION_ROTATE_90:
			rate = 90;
			break;
		case ExifInterface.ORIENTATION_ROTATE_180:
			rate = 180;
			break;
		case ExifInterface.ORIENTATION_ROTATE_270:
			rate = 270;
			break;
		}

		if(0 == rate) return src;
		Bitmap dest = getRotateBitmap(src, rate);
		if (!dest.equals(src))
			src.recycle();
		return dest;
	}

	public static Bitmap getRotateBitmap(Bitmap src, int rate) {
		Matrix matrix = new Matrix();
		matrix.setRotate(rate);
		Bitmap dest = Bitmap.createBitmap(src, 0, 0, src.getWidth(),
				src.getHeight(), matrix, false);

		return dest;
	}

	public static String getTempPicPath(String bmPath, String savePath) {
		String result = bmPath;
		try {
			Bitmap bit = revitionImageSize(bmPath);

			String temp = System.currentTimeMillis() + "";
			savePath = savePath + temp + ".JPEG";

			File picfile = new File(bmPath);
			boolean writeResult = false;
			if (picfile.exists() && picfile.length() > 100 * 1024) {
				writeResult = saveFile(bit, savePath);
				bit.recycle();
				bit = null;
			} else {
				writeResult = FileUtils.copyFile(bmPath, savePath);
			}

			if (writeResult) {
				result = savePath;
				return result;
			}

		} catch (IOException e) {

			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 默认以jpg格式保存图片到指定路径
	 *
	 * @param bm
	 * @param path
	 * @return
	 */
	public static boolean saveFile(Bitmap bm, String path) {
		return saveFile(bm, path, Bitmap.CompressFormat.JPEG, 90);
	}

	/**
	 * 以指定格式保存图片到指定路径
	 *
	 * @param bm
	 * @param path
	 * @param format
	 * @return
	 */
	public static boolean saveFile(Bitmap bm, String path,
                                   Bitmap.CompressFormat format, int rate) {
		if (bm == null || TextUtils.isEmpty(path))
			return false;
		File myCaptureFile = new File(path);
		if (myCaptureFile.exists()) {
			myCaptureFile.delete();
		} else {
			myCaptureFile.getParentFile().mkdirs();
		}
		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(myCaptureFile));
			bm.compress(format, rate, bos);
			bos.flush();
			bos.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	// Bitmap转byte[]
	public static byte[] Bitmap2Bytes(Bitmap bm, Bitmap.CompressFormat format) {
		return Bitmap2Bytes(bm, format, 100);
	}

	public static byte[] Bitmap2Bytes(Bitmap bm, Bitmap.CompressFormat format,
                                      int rate) {
		if (bm == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(format, rate, baos);
		return baos.toByteArray();
	}

	// byte[]转Bitmap
	public static Bitmap Bytes2Bimap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	public static Bitmap toRoundBitmap(Bitmap bitmap, int round) {
		if (bitmap == null)
			return null;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			if (round != 0)
				roundPx = round;
			else
				roundPx = width / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			if (round != 0)
				roundPx = round;
			else
				roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();

		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return output;
	}

	public static Bitmap zoomImage(Bitmap bgimage) {
		float scale_x = (float)bgimage.getWidth() / BitmapUtils.IMG_DEFAULT_STANDARD_W_SIZE;
		float scale_y = (float)bgimage.getHeight() / BitmapUtils.IMG_DEFAULT_STANDARD_H_SIZE;
		float scale = Math.max(scale_x, scale_y);
		if (scale <= 1) {
			return bgimage;
		}

		Bitmap bm = Bitmap.createBitmap((int)(bgimage.getWidth() / scale),
				                        (int)(bgimage.getHeight() / scale),
				                        Config.RGB_565);
		Canvas canvas = new Canvas(bm);
		canvas.drawBitmap(bgimage,
				new Rect(0, 0, bgimage.getWidth(), bgimage.getHeight()),
				new Rect(0, 0, bm.getWidth(), bm.getHeight()),
				new Paint());
		bgimage.recycle();
		return bm;
	}

	public static Bitmap zoomImage(Bitmap bgimage, int newWidth, int newHeight) {
		// 获取这个图片的宽和高
		int width = bgimage.getWidth();
		int height = bgimage.getHeight();

		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();

		// 计算缩放率，新尺寸除原始尺寸
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, width, height,
				matrix, true);
		return bitmap;
	}

	public static Bitmap zoomImage(Bitmap bgimage, int newWidth) {
		int newHeight = newWidth * bgimage.getHeight() / bgimage.getWidth();
		return zoomImage(bgimage, newWidth, newHeight);
	}

	public static Bitmap loadBitmap(String path) {
		Point pt = getBitmapSize(path);
		int size = getSize(pt);
		if (size >= 1024) {
			return loadBitmap(path, Config.RGB_565);
		} else {
			return loadBitmap(path, Config.ARGB_8888);
		}
	}

	public static Bitmap loadBitmap(String path, Bitmap.Config config) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = config;
		return BitmapFactory.decodeFile(path, options);
	}

	public static Bitmap getBitmapPath(String path, int newWidth) {
		Bitmap src = loadBitmap(path);
		if (src == null || newWidth == 0)
			return src;

		Bitmap dest = zoomImage(src, newWidth);
		if (!dest.equals(src))
			src.recycle();

		return dest;
	}

	public static Bitmap getBitmapPath(String path, int newWidth, int newHeight) {
		Bitmap src = loadBitmap(path);
		if (src == null || newWidth == 0)
			return src;

		Bitmap dest = zoomImage(src, newWidth, newHeight);
		if (!dest.equals(src))
			src.recycle();

		return dest;
	}

	public static Bitmap drawBitmap(Bitmap src, int width, int height, int color) {
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		paint.setStyle(Style.FILL);
		paint.setColor(color);
		canvas.drawRect(new Rect(0, 0, width, height), paint);
		canvas.drawBitmap(src, (width - src.getWidth()) / 2,
				(height - src.getHeight()) / 2, null);

		return bitmap;
	}

	   /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }



    /**
	 * caculate the bitmap sampleSize
	 * @param path
	 * @return
	 */
	public final static int caculateInSampleSize(BitmapFactory.Options options, int rqsW, int rqsH) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (rqsW == 0 || rqsH == 0)
			return 1;
		if (height > rqsH || width > rqsW) {
			final int heightRatio = Math.round((float) height / (float) rqsH);
			final int widthRatio = Math.round((float) width / (float) rqsW);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

    /**
	 * 压缩指定路径的图片，并得到图片对象
	 * @author DaCui
	 * @param context
	 * @param path
	 *            bitmap source path
	 * @return Bitmap {@link android.graphics.Bitmap}
	 */
	public final static Bitmap compressBitmap(String path, int rqsW, int rqsH) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		options.inSampleSize = caculateInSampleSize(options, rqsW, rqsH);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	public static  void compressBitmaps(ArrayList<String> pathStrings) {
		for (int i = 0; i < pathStrings.size(); i++) {
			boolean updateSizeFlag = false;
			String path = pathStrings.get(i);
			Point ptSize = getBitmapSize(path);
			Bitmap bmp = null;
			int hopeWidth = 1024;
			int hopeHeight = 768;

			//长宽压缩
			if (ptSize.x>hopeWidth && ptSize.y>hopeHeight) {
				int inSampleSize = 1;
				final int heightRatio = Math.round((float) ptSize.x / (float) hopeWidth);
				final int widthRatio = Math.round((float) ptSize.y / (float) hopeHeight);
				inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
				final BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(path, options);
				options.inSampleSize = inSampleSize;
				options.inJustDecodeBounds = false;
				bmp =  BitmapFactory.decodeFile(path, options);
				updateSizeFlag = true;

			}
			else {
				bmp = getBitmap(path);
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.JPEG, 90, baos);
			boolean compressFlag = false;

			long fileLength = (new File(path)).length();
			if (fileLength > 102400) {
				int options = 90;
				while (baos.toByteArray().length / 1024 > COMPRESS_THREAD) {
					baos.reset();
					options -= 10;
					bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
					compressFlag = true;
				}
			}
			if (compressFlag == true ) {//有压缩文件大小的行为
				if (bmp != null) {
					bmp.recycle();
					bmp = null;
				}
				ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
				Bitmap bitmap = null;
				Options op = new Options();
				op.inPreferredConfig = Bitmap.Config.RGB_565;
				op.inScaled = false;
				op.inDither = false;
				bitmap = BitmapFactory.decodeStream(isBm, null, op);
				if (bitmap != null) {
					saveFile(bitmap, path, Bitmap.CompressFormat.JPEG, 80);
					bitmap.recycle();
					bitmap = null;
				}
			}
			else if (updateSizeFlag == true) {//没有压缩文件大小，但是有压缩长宽的行为
				if (bmp != null) {
					saveFile(bmp, path, Bitmap.CompressFormat.JPEG, 80);
					bmp.recycle();
				}
				
			}
			
		}
	
	}
	
	/**
	 * 将Base64字符串转为图片
	 */
	public static Bitmap getBitmapForBase64(String base64Str) {
		Bitmap bitmap = null;
		try {
			byte[] bitmapArray;
			bitmapArray = Base64.decode(base64Str, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
					bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;
	}

}
