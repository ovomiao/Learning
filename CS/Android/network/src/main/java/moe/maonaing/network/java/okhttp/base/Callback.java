package moe.maonaing.network.java.okhttp.base;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public interface Callback {

    void onFailure(Call call, IOException e);

    void onResponse(Call call, String string);

}
