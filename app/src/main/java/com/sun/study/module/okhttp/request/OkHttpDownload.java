package com.sun.study.module.okhttp.request;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.sun.study.module.okhttp.callback.OkHttpCallBack;
import com.sun.study.module.okhttp.core.OkHttpClientManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by sunfusheng on 15/12/8.
 */
public class OkHttpDownload extends OkHttpGet {

    private String destFileDir;
    private String destFileName;

    public OkHttpDownload(String url, Map<String, String> params, String destFileName, String destFileDir) {
        super(url, params);
        this.destFileName = destFileName;
        this.destFileDir = destFileDir;
    }

    public OkHttpDownload(String url, Map<String, String> params, Map<String, String> headers, String tag,
                          String destFileName, String destFileDir) {
        super(url, params, headers, tag);
        this.destFileName = destFileName;
        this.destFileDir = destFileDir;
    }

    @Override
    public void invokeAsync(final OkHttpCallBack callback) {
        prepareInvoked(callback);

        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                mOkHttpClientManager.sendFailResultCallback(request, e, callback);
            }

            @Override
            public void onResponse(Response response) {
                try {
                    String filePath = saveFile(response, callback);
                    OkHttpClientManager.getInstance().sendSuccessResultCallback(filePath, callback);
                } catch (IOException e) {
                    e.printStackTrace();
                    OkHttpClientManager.getInstance().sendFailResultCallback(response.request(), e, callback);
                }
            }
        });

    }

    private String getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

    @Override
    public <T> T invoke(Class<T> clazz) throws IOException {
        final Call call = mOkHttpClient.newCall(request);
        Response response = call.execute();
        return (T) saveFile(response, null);
    }

    public String saveFile(Response response, final OkHttpCallBack callback) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            final long total = response.body().contentLength();
            long sum = 0;

            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);

                if (callback != null) {
                    final long finalSum = sum;
                    mOkHttpClientManager.getHandler().post(new Runnable() {
                        @Override
                        public void run() {

                            callback.inProgress(finalSum * 1.0f / total);
                        }
                    });
                }
            }
            fos.flush();

            return file.getAbsolutePath();

        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
            }

        }
    }

}
