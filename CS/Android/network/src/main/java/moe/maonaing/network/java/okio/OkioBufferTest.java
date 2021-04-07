package moe.maonaing.network.java.okio;


import android.util.Log;

import java.io.EOFException;

import okio.Buffer;
import okio.Sink;
import okio.Source;

/**
 * 源 <- IO 流  -> 内存
 *      IO 流模型在数据源和内存中抽象出一个虚拟通道，这条虚拟通道给你提供
 *      输入到内存、写入到数据源和处理数据的能力。
 *      IO 流模型的好处是不需要关注数据源到达是什么，只要能建立 IO 通道就可以
 *      完成数据的读写。不关注你是本地文件还是网络上的文件或是网络数据库
 *
 * OkIO Source（对应 Java 输入流） 接口
 *      interface Source extends Closeable
 *
 *      long read(Buffer sink, long byteCount)
 *          * 读入内存
 *
 *      Timeout timeout();
 *          * 超时时间，Java 文件 IO 无此功能
 *          * 无论是本地文件流还是网络流，都不可能无限等待
 *              * 本地 IO 也可能超时
 *
 *      void close()
 *          * 关闭
 *
 *
 *  Sink（OutputStream）（数据的接收方） OKIO 中的输出流
 *      * interface Sink extends Closeable, Flushable
 *      * 方法
 *          * void flush()
 *              * 将缓存数据写入目的地，例如磁盘
 *          * Timeout timeout();
 *          * void write(Buffer source, long byteCount)
 *              * 写入，从内存输出到 Sink
 *
 *
 * * Buffer 类是 BufferedSource、和 BufferedSink 的实现类
 *    *  * 自带一个缓冲区，用于提高读写效率
 *      * BufferedSource  接口
 *      * BufferedSink    接口
 *
 * * 注意 Buffer 是在内存中的
 *
 */
public class OkioBufferTest {
    private static final String TAG = "OkioBufferTest";

    public void testBuffer() throws EOFException {
        Buffer buffer = new Buffer();
        Log.i(TAG, "testBuffer: " + buffer);
        buffer.writeUtf8("写入到 Buffer");
        Log.i(TAG, "testBuffer: >>> " + buffer);

        // 读入到 Buffer

        while (!buffer.exhausted()) {
            String s = buffer.readUtf8(1);
            Log.i(TAG, "testBuffer: " + s);
        }
    }

} 