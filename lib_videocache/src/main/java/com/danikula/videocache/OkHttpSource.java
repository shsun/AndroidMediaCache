package com.danikula.videocache;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;

import static com.danikula.videocache.ProxyCacheUtils.DEFAULT_BUFFER_SIZE;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_PARTIAL;

/**
 * Created by shsun on 17/2/16.
 */

public class OkHttpSource extends UrlSource {

    private static final String LOG_TAG = "OkHttpSource";

    private static final Logger LOG = LoggerFactory.getLogger("OkHttpSource");

    private static final int MAX_REDIRECTS = 5;

    //
    private OkHttpClient httpClient = new OkHttpClient();
    private InputStream inputStream;

    public OkHttpSource(IMimeCache mimeCache, String url) {
        super(url);
        this.mimeCache = mimeCache;
    }

    public OkHttpSource(UrlSource urlSource) {
        super(urlSource);
    }

    @Override
    public int length() throws ProxyCacheException {
        if (length == Integer.MIN_VALUE) {
            tryLoadMimeCache();
        }
        if (length == Integer.MIN_VALUE) {
            fetchContentInfo();
        }
        return length;
    }

    @Override
    public String getMime() throws ProxyCacheException {
        if (TextUtils.isEmpty(mime)) {
            tryLoadMimeCache();
        }
        if (TextUtils.isEmpty(mime)) {
            fetchContentInfo();
        }
        return mime;
    }

    @Override
    public void open(int offset) throws ProxyCacheException {
        try {
            Response response = openConnection(offset, -1);
            mime = response.body().contentType().toString();
            length = readSourceAvailableBytes(response, offset);
            inputStream = new BufferedInputStream(response.body().byteStream(), DEFAULT_BUFFER_SIZE);
        } catch (IOException e) {
            throw new ProxyCacheException("Error opening connection for " + url + " with offset " + offset, e);
        }
    }

    private int readSourceAvailableBytes(Response response, int offset) throws IOException {
        int responseCode = response.code();
        int contentLength = (int) response.body().contentLength();
        return responseCode == HTTP_OK ? contentLength
                : responseCode == HTTP_PARTIAL ? contentLength + offset : length;
    }

    @Override
    public int read(byte[] buffer) throws ProxyCacheException {
        if (inputStream == null) {
            throw new ProxyCacheException("Error reading data from " + url + ": connection is absent!");
        }
        try {
            return inputStream.read(buffer, 0, buffer.length);
        } catch (InterruptedIOException e) {
            throw new InterruptedProxyCacheException("Reading source " + url + " is interrupted", e);
        } catch (IOException e) {
            throw new ProxyCacheException("Error reading data from " + url, e);
        }
    }

    @Override
    public void close() throws ProxyCacheException {
        ProxyCacheUtils.close(inputStream);
    }

    private void fetchContentInfo() throws ProxyCacheException {
        Log.d(LOG_TAG, "Read content info from " + url);
        Response response = null;
        try {
            response = openConnectionForHeader(10000);
            if (response == null || !response.isSuccessful()) {
                throw new ProxyCacheException("Fail to fetchContentInfo: " + url);
            }
            length = (int) response.body().contentLength();
            mime = response.body().contentType().toString();
            tryPutMimeCache();
            Log.i(LOG_TAG, "Content info for `" + url + "`: mime: " + mime + ", content-length: " + length);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error fetching info from " + url, e);
        } finally {
            Log.d(LOG_TAG, "Closed connection from :" + url);
        }
    }

    private Response openConnectionForHeader(int timeout) throws IOException, ProxyCacheException {
        if (timeout > 0) {
            httpClient.setConnectTimeout(timeout, TimeUnit.MILLISECONDS);
            httpClient.setReadTimeout(timeout, TimeUnit.MILLISECONDS);
            httpClient.setWriteTimeout(timeout, TimeUnit.MILLISECONDS);
        }
        Response response;
        boolean isRedirect = false;
        String newUrl = this.url;
        int redirectCount = 0;
        do {
            Request request = new Request.Builder()
                    .head()
                    .url(newUrl)
                    .build();
            response = httpClient.newCall(request).execute();
            if (response.isRedirect()) {
                newUrl = response.header("Location");
                isRedirect = response.isRedirect();
                redirectCount++;
            }
            if (redirectCount > MAX_REDIRECTS) {
                throw new ProxyCacheException("Too many redirects: " + redirectCount);
            }
        } while (isRedirect);

        return response;
    }

    private Response openConnection(int offset, int timeout) throws IOException, ProxyCacheException {
        if (timeout > 0) {
            httpClient.setConnectTimeout(timeout, TimeUnit.MILLISECONDS);
            httpClient.setReadTimeout(timeout, TimeUnit.MILLISECONDS);
            httpClient.setWriteTimeout(timeout, TimeUnit.MILLISECONDS);
        }
        Response response;
        boolean isRedirect = false;
        String newUrl = this.url;
        int redirectCount = 0;
        do {
            Log.d(LOG_TAG, "Open connection" + (offset > 0 ? " with offset " + offset : "") + " to " + url);
            Request.Builder requestBuilder = new Request.Builder();
            requestBuilder.get();
            requestBuilder.url(newUrl);
            if (offset > 0) {
                requestBuilder.addHeader("Range", "bytes=" + offset + "-");
            }
            response = httpClient.newCall(requestBuilder.build()).execute();
            if (response.isRedirect()) {
                newUrl = response.header("Location");
                isRedirect = response.isRedirect();
                redirectCount++;
            }
            if (redirectCount > MAX_REDIRECTS) {
                throw new ProxyCacheException("Too many redirects: " + redirectCount);
            }
        } while (isRedirect);

        return response;
    }

    private void tryLoadMimeCache() {
        if (mimeCache != null) {
            UrlMime urlMime = mimeCache.getMime(url);
            if (urlMime != null && !TextUtils.isEmpty(urlMime.getMime()) && urlMime.getLength() != Integer.MIN_VALUE) {
                this.mime = urlMime.getMime();
                this.length = urlMime.getLength();
            }
        }
    }

    private void tryPutMimeCache() {
        if (mimeCache != null) {
            mimeCache.putMime(url, length, mime);
        }
    }
}