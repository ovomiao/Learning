package moe.maonaing.network.java.retrofit;

/**
 * 下载进度回调
 *      只负责回调下载进度，不管下载的状态
 */
public interface ProgressListener {

    /**
     * @param url           当前的正在下载的 URL
     * @param bytesRead     已经下载的字节数
     * @param contentLength 文件的总字节数
     * @param isDone        释放下载完毕
     */
    void onProgress(String url, long bytesRead, long contentLength, boolean isDone);

}
