package moe.maonaing.network.java.okio;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import moe.maonaing.network.R;
import okio.ByteString;

/**
 *
 * ByteString() 字节数组,使用字节数组表示字符串
 *      * static ByteString encodeString(String s, Charset charset)
 *      * static ByteString of(byte... data)
 *      * static ByteString encodeUtf8(String s)
 *      * static ByteString decodeHex(String hex)
 *      * ByteString read(InputStream in, int byteCount)
 *          * 注意：ByteString 还可以从流中获取字符串
 *      * ByteString decodeBase64(String base64)
 *
 *
 * Base64 用于二进制数据和文本数据互相转换
 *    * 图片、音视频、字符串本身等 -> 可编码为 Base64 字符串
 *    * Base64 字符串 -> 解码为图片、音视频、原始字符串本身
 *
 * * Base64 主要用于记录不可显的字符串，例如可以将图片转换为 Base64 编码放入 JSON 文件中
 *
 * * MD5 是一种不可逆算法，输入可以是字符串或者一共文件，输出是一个 128bit 的校验和
 * * MD5 的作用：
 *      * MD5 类似于文件（字符串）的 “指纹” 不同文件或者字符串的 MD5 一定是不同的，相同的 MD5 值
 *        一定是同一个文件或字符串做 MD5 运算得来的
 *      * MD5 的应用场景
 *          * 确定文件未被第三方修改，或者下载完整。因为文件一定被修改或者下载不完整，那么它的 MD5
 *            值一定和原始文件的 MD5 值不一致。通过比对目标文件和原始文件的 MD5 值，可以判断文件是否
 *            为完整的原始文件
 *          * 密码存储：
 *              * 例如：用户登录时，将用户的密码做 MD5 运算
 *      * 和 MD5 同类的校验算法：它们只是算法不同，但作用是一致的
 *          * SH1、SH256、SH516
 *
 */
public class OkioTest {

    private static final String TAG = "OkioTest";

    public static void testByteString() {
        ByteString byteString = ByteString.encodeUtf8("ABC");

        // 编码为 Base64
        String base64 = byteString.base64();
        Log.i(TAG, "testByteString: base64 = " + base64);

        // 将 Base64 解码为原始字符串
        ByteString orlString = ByteString.decodeBase64(base64);
        Log.i(TAG, "testByteString: 原始字符串 = " + orlString);

        // 获取字符串的 MD5
        ByteString md5 = byteString.md5();
        Log.i(TAG, "testByteString: MD5 = " + md5);

        // 获取字符串的 sh1
        ByteString sh1 = byteString.sha1();
        Log.i(TAG, "testByteString: sh1 = " + sh1);

        // 获取十六进制值
        String hex = byteString.hex();
        Log.i(TAG, "testByteString: hex = " + hex);
    }

    public void readImageToByteString(@NotNull Context context) throws IOException {
        InputStream inputStream = context.getResources().openRawResource(R.raw.image_emoticon95);
        ByteString read = ByteString.read(inputStream, inputStream.available());
        Log.i(TAG, "readImageToByteString: image byte string >>>" + read);

        File file;
        FileOutputStream fos = new FileOutputStream("out.txt");

        // 使用 ByteString 将 ByteString 写入文件
        read.write(fos);

        inputStream.close();

    }

} 