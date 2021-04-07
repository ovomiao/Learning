package moe.maonaing.network.java.retrofit;

import androidx.annotation.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 1. 自定义响应体，从中获取每次读取的字节数
 */
public class ProgressResponseBody extends ResponseBody {
    private ResponseBody mResponseBody;
    private ProgressListener mListener;
    private BufferedSource mSource;
    private String mUrl;

    public ProgressResponseBody(
            ResponseBody responseBody,
            ProgressListener listener,
            String url
    ) {
        mResponseBody = responseBody;
        mListener = listener;
        mUrl = url;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (mSource == null) {
            mSource = Okio.buffer(source(mResponseBody.source()));
        }
        return mSource;
    }

    private Source source(final Source source) {
        return new ForwardingSource(source) {
            long totalBytes = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                 long bytesRead = super.read(sink, byteCount);
                 // 本次从流中读取的字节数，可能为 -1（和原生 Java 流一样）
                 bytesRead = bytesRead == -1 ? 0 : bytesRead;
                 // 以下载的字节数
                totalBytes += bytesRead;
                 // 回调进度
                if (mListener != null) {
                    mListener.onProgress(
                            mUrl,
                            bytesRead,
                            mResponseBody.contentLength(),bytesRead == -1
                    );
                }
                // 注意这里需要返回的是本次读取的字节数
                return bytesRead;
            }

        };
    }

}