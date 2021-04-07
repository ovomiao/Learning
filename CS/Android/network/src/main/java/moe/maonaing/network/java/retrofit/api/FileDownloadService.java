package moe.maonaing.network.java.retrofit.api;

import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Retrofit 的接口文件习惯使用：功能/模块+Service 命名
 */
public interface FileDownloadService {

    @Streaming // 下载文件
    @GET
    void downloadFile(@Url String url);

}
