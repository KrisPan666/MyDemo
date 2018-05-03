package com.jiecheng.zhike.mydemo.Screenshot;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;


import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class FileUtils {
    public static final String UTF_8 = "UTF-8";

    public static final String TAG = FileUtils.class.getSimpleName();

    public static StringBuffer readText(String filePath, String decoder) {
        try {
            File file = new File(filePath);
            if (!file.exists() || !file.canRead())
                return null;

            return readText(filePath, decoder, 0, (int) file.length());

        } catch (Exception e) {
            Log.e(TAG,
                    String.format("readText Error! message:%s", e.getMessage()));
            return null;
        }
    }

    public static String getFileName(String filePath, boolean useSuffix) {
        int len = filePath.length();
        filePath = filePath.replaceAll("\\\\", "/");
        File tempFile = new File(filePath);
        String fileName = tempFile.getName();
        if (useSuffix)
            return fileName;
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    public static File createFile(String path) {
        final String FILE = "file:";
        final String FILE_BEG = "///";
        if (path.startsWith(FILE)) {
            path = path.replace(FILE, "");
            if (path.startsWith(FILE_BEG))
                path = path.replace(FILE_BEG, "/");
        }

        return new File(path);
    }

    /**
     * @author 刘泾铭 后期迁移到FileUtils中 拷贝assest的文件
     */
    public static boolean copyFileFromAssest(Context context,
                                             String assestFile, String destFile) {
        try {
            File dFile = new File(destFile);
            if (dFile.exists()) {
                dFile.delete();
            } else {
                createLocalDiskPath(dFile.getParent());
            }
            dFile.createNewFile();

        } catch (Exception e) {
            return false;
        }
        AssetManager assetManager = context.getAssets();
        try {
            InputStream in = assetManager.open(assestFile);
            byte[] buffer = new byte[2048];
            OutputStream outFileStream = new FileOutputStream(destFile);
            int read = 0;
            while ((read = in.read(buffer)) != -1) {
                outFileStream.write(buffer, 0, read);
            }
            in.close();
            outFileStream.flush();
            outFileStream.close();
            in = null;
            outFileStream = null;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * create the folder with selected path.
     *
     * @param path
     */
    public static void createLocalDiskPath(String path) {
        File folder = new File(path);
        try {
            if (!folder.exists()) {

                boolean rtn = folder.mkdirs();
            }
        } catch (Exception e) {
        }
    }

    /**
     * create the file with selected path.
     *
     * @param path
     */
    public static void createLocalFile(String path) {
        File f = new File(path);
        try {
            if (!f.exists())
                f.createNewFile();
        } catch (Exception e) {

        }
    }

    /**
     * 根据编码读取文本
     *
     * @param filePath 文件路径
     * @param decoder  字符集名称 例：GBK UTF-8
     * @param offset   偏移量
     * @param length   长度
     * @return 读取的文本
     */
    public static StringBuffer readText(String filePath, String decoder,
                                        int offset, int length) {
        FileInputStream fileInputStream = null;
        BufferedInputStream buffReader = null;

        try {
            fileInputStream = new FileInputStream(filePath);
            buffReader = new BufferedInputStream(fileInputStream);

            StringBuffer buffer = new StringBuffer();

            byte[] bytesBuf = new byte[length];
            buffReader.skip(offset);
            buffReader.read(bytesBuf, 0, length);

            return buffer.append(new String(bytesBuf, decoder));
        } catch (Exception e) {
            Log.e(TAG,
                    String.format("readText Error!\te.getMessage:%s",
                            e.getMessage()));
        } finally {
            closeCloseable(fileInputStream);
            closeCloseable(buffReader);
        }

        return null;
    }

    /**
     * @param filePath 文件路径
     * @param decoder  解码格式 GBK | UTF-8
     * @return
     * @description 读取文本
     */
    public static StringBuilder readTextStr(String filePath, String decoder) {
        try {
            File file = new File(filePath);
            if (!file.exists() || !file.canRead())
                return null;

            return readTextStr(filePath, decoder, 0, (int) file.length());

        } catch (Exception e) {
            Log.e(TAG, String.format("readText Error! message:%s", e.getMessage()));
            return null;
        }
    }

    /**
     * @param filePath 文件路径
     * @param decoder  解码格式 GBK | UTF-8
     * @param offset   起始偏移量
     * @param length   长度
     * @return 字符串
     * @description 读取文本
     */
    public static StringBuilder readTextStr(String filePath, String decoder, int offset, int length) {
        FileInputStream fileInputStream = null;
        BufferedInputStream buffReader = null;
        try {
            fileInputStream = new FileInputStream(filePath);
            buffReader = new BufferedInputStream(fileInputStream);

            StringBuilder sBuilder = new StringBuilder();

            byte[] bytesBuf = new byte[length];
            buffReader.skip(offset);
            buffReader.read(bytesBuf, 0, length);
            return sBuilder.append(new String(bytesBuf, decoder));
        } catch (Exception e) {
            Log.e(TAG, String.format("readText Error!\te.getMessage:%s", e.getMessage()));
        } finally {
            closeCloseable(fileInputStream);
            closeCloseable(buffReader);
        }

        return null;
    }

    public static byte[] getBuffer(String path, int off, int length) {
        byte[] cover = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            cover = getBuffer(fis, off, length);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileUtils.closeCloseable(fis);
        }

        return cover;
    }

    public static byte[] getBuffer(String path) {
        FileInputStream fis = null;
        byte[] cover = null;
        try {
            fis = new FileInputStream(path);
            cover = getBuffer(fis, 0, fis.available());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileUtils.closeCloseable(fis);
        }
        return cover;
    }

    public static long getBigbuffer(String path) {
        long fileSize = 0;
        try {
            File copyf = new File(path);       //copyPath 为目标文件的全路径  例如要将a/b/文件夹下的c.xml复制到其他处 a/b/c.xml
            FileInputStream fis = null;
            fis = new FileInputStream(copyf);
            fileSize = (long) fis.available();     //文件大小

        } catch (Exception e) {
            return 0;
        }
        return fileSize;
    }

    public static byte[] getBuffer(AssetManager assets, String fileName) {
        byte[] cover = null;
        InputStream stream = null;
        try {
            stream = assets.open(fileName);
            cover = getBuffer(stream, 0, stream.available());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭文件
            closeCloseable(stream);
        }
        return cover;
    }

    private static byte[] getBuffer(InputStream stream, int offset, int length) {
        byte[] cover = null;
        try {
            if (offset + length > stream.available())
                length = stream.available() - offset;

            cover = new byte[length];
            stream.skip(offset);
            stream.read(cover, 0, length);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileUtils.closeCloseable(stream);
        }
        return cover;
    }

    /**
     * 关闭stream or reader
     *
     * @param closeObj
     */
    public static void closeCloseable(Closeable closeObj) {
        try {
            if (null != closeObj)
                closeObj.close();
        } catch (IOException e) {
            Log.e("ReadFileUtils Error",
                    "Method:readFile, Action:closeReader\t" + e.getMessage());
        }
    }

    public static String getContent(AssetManager assets, String fileName) {
        String ret = "";
        InputStream stream = null;
        try {
            stream = assets.open(fileName);
            ret = getContent(stream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭文件
            closeCloseable(stream);
        }
        return ret;
    }

    // 从流中, 获取数据
    private static String getContent(InputStream stream) {
        String ret = "";
        try {
            int len = stream.available();
            byte[] buffer = new byte[len];
            stream.read(buffer);

            ret = new String(buffer, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 以 <b> UTF-8 </b>格式从文件开始处写入字符串,如果文件存在，则会被重写
     *
     * @param path    文件路径
     * @param content 待写入的字符串
     * @return 成功时返回true，失败返回false
     */
    public static boolean writeString(String path, String content) {
        String encoding = "UTF-8";
        File file = new File(path);
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        return writeString(path, content, encoding);
    }

    /**
     * 从文件开始处写入字符串,如果文件存在，则会被重写
     *
     * @param path     文件路径
     * @param content  待写入的字符串
     * @param encoding String转换为byte[]编码
     * @return 成功时返回true，失败返回false
     */
    public static boolean writeString(String path, String content,
                                      String encoding) {
        FileOutputStream fos = null;
        boolean result = false;
        try {
            fos = new FileOutputStream(path);
            byte[] cover = content.getBytes(encoding);
            fos.write(cover, 0, cover.length);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileUtils.closeCloseable(fos);
        }
        return result;
    }

    /**
     * 从文件开始处写入字符串
     *
     * @param path    文件路径
     * @param content 待写入的字符串
     * @return 成功时返回true，失败返回false
     */
    public static boolean writeUrl(String path, String name, String content) {
        FileOutputStream fos = null;
        boolean result = false;
        try {
            File filePath = new File(path);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            File file = new File(path, name);
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            byte[] cover = content.getBytes("UTF-8");
            fos.write(cover, 0, cover.length);
            result = true;
        } catch (Exception e) {
            result = false;
        } finally {
            FileUtils.closeCloseable(fos);
        }
        return result;
    }

    /**
     * 创建目标文件写入字节数组
     *
     * @param fileData 文件字节数组
     * @return 写入成功，返回true，否则返回false
     */
    public static boolean writeBytes(String targetFilePath, byte[] fileData) {
        boolean result = false;
        File targetFile = new File(targetFilePath);
        File parentFile = targetFile.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            targetFile.getParentFile().mkdirs();
        }
        if (targetFile.exists()) {
            targetFile.delete();
        }
        ByteArrayInputStream fosfrom = null;
        FileOutputStream fosto = null;
        try {
            fosfrom = new ByteArrayInputStream(fileData);
            fosto = new FileOutputStream(targetFile);
            byte[] buffer = new byte[1024 * 4];
            int length;
            while ((length = fosfrom.read(buffer)) != -1) {
                fosto.write(buffer, 0, length);
            }
            fosto.flush();
            result = true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeCloseable(fosto);
            closeCloseable(fosfrom);
        }
        return result;
    }

    public static boolean saveCameraBitmap(Bitmap bm, String path) {
        boolean result = false;
        Log.e("", "保存图片");
        try {
            File f = new File(path);
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 75, out);
            out.flush();
            out.close();
            result = true;
            Log.e("", "已经保存");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static boolean saveBitmap(Bitmap bm, String path) {
        boolean result = false;
        Log.e("", "保存图片");
        try {
            File f = new File(path);
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            result = true;
            Log.e("", "已经保存");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * add by xfchen 2015/12/28
     *
     * @param bm
     * @param format
     * @param path
     * @return
     */
    public static boolean saveBitmap(Bitmap bm, Bitmap.CompressFormat format,
                                     String path) {
        boolean result = false;
        Log.e("", "保存图片");
        try {
            File f = new File(path);
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(format, 100, out);
            out.flush();
            out.close();
            result = true;
            Log.e("", "已经保存");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 复制文件
     *
     * @param oldPath String 原文件路径
     * @param newPath String 复制后路径
     * @return
     */
    public static boolean copyFile(String oldPath, String newPath) {
        boolean result = false;
        File oldFile = new File(oldPath);
        File newFile = new File(newPath);
        if (!oldFile.exists() || !oldFile.isFile() || !oldFile.canRead()) {
            return result;
        }

        if (oldFile.getPath().equals(newFile.getPath())) {
            Log.d(TAG, "拷贝文件：源文件和目标文件路径相同,不需要重新复制");
            return true;
        }
        File parentFile = newFile.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        if (newFile.exists()) {
            newFile.delete();
        }
        FileInputStream fosfrom = null;
        FileOutputStream fosto = null;

        try {
            fosfrom = new FileInputStream(oldFile);
            fosto = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024 * 4];
            int length;
            while ((length = fosfrom.read(buffer)) != -1) {
                fosto.write(buffer, 0, length);
            }
            fosto.flush();
            result = true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeCloseable(fosto);
            closeCloseable(fosfrom);
        }
        return result;
    }

    /**
     * 进行文件拷贝
     *
     * @param oldPath 旧的文件路径
     * @param newPath 新的文件路径
     * @param name    新的文件名字
     * @return 是否成功
     */
    public static boolean copyFile(String oldPath, String newPath, String name) {
        InputStream input = null;
        OutputStream output = null;
        try {
            File oldfile = new File(oldPath);
            File newfile = new File(newPath);
            if (!newfile.exists()) {
                newfile.mkdirs();
            }
            if (oldfile.exists()) {
                input = new FileInputStream(oldfile); // 读入原文件
                output = new FileOutputStream(new File(newfile, name));
                byte[] buffer = new byte[1024];
                int length;
                while ((length = input.read(buffer)) != -1) {
                    output.write(buffer, 0, length);
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeCloseable(output);
            closeCloseable(input);
        }

    }

    /**
     * 递归删除文件和文件夹
     *
     * @param file 要删除的根目录
     */
    public static void deleteAllFiles(File file, boolean deleteRootFolder) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteAllFiles(files[i], true);
                }
            }
            if (deleteRootFolder)
                file.delete();
        } else {
            System.out.println("所删除的文件不存在！" + '\n');
        }
    }

    /**
     * 文件删除
     *
     * @param filePath
     * @return
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile())
            file.delete();
    }

    /**
     * add by xfchen 2015/12/2 根据路径删除对应文件
     *
     * @param paths 所有的文件路径
     */
    public static void deleteFiles(List<String> paths) {
        try {
            if (null == paths || 0 == paths.size())
                return;

            int size = paths.size();
            File file = null;
            for (int i = 0; i < size; i++) {
                file = new File(paths.get(i));
                if (file.exists() && file.isFile())
                    file.delete();
            }
        } catch (Exception ex) {
        }
    }

    public static boolean renameFile(String path, String newName) {
        File file = new File(path);
        if (file.exists()) {
            file.renameTo(new File(newName));
            return true;
        }
        return false;
    }

    public static boolean isExistPath(String path) {
        return new File(path).exists();
    }

    public static void saveToSDCard(String fileNamePath, String content) {
        File file = new File(fileNamePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            file.delete();
        }
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            out.write(content.getBytes());
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static boolean isFileExist(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        File file = new File(path);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    public static boolean isFileDirExist(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            return true;
        }
        return false;
    }

    public static String[] listImageFiles(String dirPath) {
        if (!isFileDirExist(dirPath)) { // 文件夹不在或文件夹为空
            return null;
        }
        File dirFile = new File(dirPath);
        FilenameFilter filter = new FilenameFilter() {

            @Override
            public boolean accept(File dir, String filename) {
                File file = new File(dir + File.separator + filename);
                if (file.isFile()) {
                    String suffix = filename.substring(
                            filename.lastIndexOf(".")).toLowerCase();
                    if (suffix.equals(".jpeg") || suffix.equals(".jpg")
                            || suffix.equals(".png")) {
                        return true;
                    }
                }
                return false;
            }
        };
        return dirFile.list(filter);
    }

    /**
     * 删除文件或文件夹下所有文件
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
                file.delete();
            }
        } else {
            Log.e("", "文件不存在！" + "\n");
        }
    }

    /**
     * 删除文件或文件夹下所有文件,并返回结果
     *
     * @param file 文件
     */
    public static boolean deleteFileForResult(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
                file.delete();
            }
            return true;
        } else {
            return false;
        }
    }
}
