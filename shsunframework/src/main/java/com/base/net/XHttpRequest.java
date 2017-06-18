package com.base.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;

import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.base.cache.XCacheItemManager;
import com.base.utils.XBaseUtils;
import com.base.utils.XFrameConstants;

/**
 * @author shsun
 */
public class XHttpRequest implements Runnable {

    private final static String cookiePath = "/data/data/com.youngheart/cache/cookie";

    public enum RequestMethod {
        GET("get"), POST("post"), DELETE("delete"), PUT("put");

        private String method;

        private RequestMethod(String method) {
            this.method = method;
        }

        public String getMethod() {
            return method;
        }
    }

    private RequestMethod method;

    private HttpUriRequest request = null;
    private XHttpURLData data = null;

    private XHttpRequestCallback requestCallback = null;
    private List<XHttpRequestParameter> parameter = null;

    private String url = null; // 原始url
    private String newUrl = null; // 拼接key-value后的url

    private HttpResponse response = null;
    private DefaultHttpClient httpClient;

    // 切换回UI线程
    protected Handler handler;

    protected boolean cacheRequestData = true;

    // 头信息
    protected HashMap<String, String> headers;

    // 服务器时间和客户端时间的差值
    static long deltaBetweenServerAndClientTime;

    /**
     * 
     * @param data
     * @param params
     * @param callBack
     */
    public XHttpRequest(final XHttpURLData data, final List<XHttpRequestParameter> params, final XHttpRequestCallback callBack) {
        this.data = data;

        url = this.data.getUrl();
        this.parameter = params;
        requestCallback = callBack;

        if (httpClient == null) {
            httpClient = new DefaultHttpClient();
        }

        handler = new Handler();
        headers = new HashMap<String, String>();
    }

    /**
     * 获取HttpUriRequest请求
     *
     * @return
     */
    public HttpUriRequest getRequest() {
        return request;
    }

    @Override
    public void run() {
        try {
            if (RequestMethod.GET.getMethod().equalsIgnoreCase(data.getRequestMethod())) {
                // 添加参数
                final StringBuffer paramBuffer = new StringBuffer();
                if ((parameter != null) && (parameter.size() > 0)) {

                    // 这里要对key进行排序
                    sortKeys();

                    for (final XHttpRequestParameter p : parameter) {
                        if (paramBuffer.length() == 0) {
                            paramBuffer.append(p.getName() + "=" + XBaseUtils.UrlEncodeUnicode(p.getValue()));
                        } else {
                            paramBuffer.append("&" + p.getName() + "=" + XBaseUtils.UrlEncodeUnicode(p.getValue()));
                        }
                    }
                    newUrl = url + "?" + paramBuffer.toString();
                } else {
                    newUrl = url;
                }
                // 如果这个get的API有缓存时间（大于0）
                if (data.getExpires() > 0) {
                    final String content = XCacheItemManager.getInstance().getFileCache(newUrl);
                    if (content != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                requestCallback.onSuccess(content);
                            }

                        });

                        return;
                    }
                }
                request = new HttpGet(newUrl);
            } else if (RequestMethod.POST.getMethod().equalsIgnoreCase(data.getRequestMethod())) {
                request = new HttpPost(url);
                // 添加参数
                if ((parameter != null) && (parameter.size() > 0)) {
                    final List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
                    for (final XHttpRequestParameter p : parameter) {
                        list.add(new BasicNameValuePair(p.getName(), p.getValue()));
                    }
                    ((HttpPost) request).setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
                }
            } else {
                return;
            }

            request.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
            request.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);

            /**
             * 添加必要的头信息
             */
            setHttpHeaders(request);

            // 添加Cookie到请求头中
            addCookie();

            // 发送请求
            response = httpClient.execute(request);
            // 获取状态
            final int statusCode = response.getStatusLine().getStatusCode();
            // 设置回调函数，但如果requestCallback，说明不需要回调，不需要知道返回结果
            if ((requestCallback != null)) {
                if (statusCode == HttpStatus.SC_OK) {
                    // 更新服务器时间和本地时间的差值
                    updateDeltaBetweenServerAndClientTime();

                    final ByteArrayOutputStream content = new ByteArrayOutputStream();

                    // 根据是否支持gzip来使用不同的解析方式
                    String strResponse = "";
                    if ((response.getEntity().getContentEncoding() != null) && (response.getEntity().getContentEncoding().getValue() != null)) {
                        if (response.getEntity().getContentEncoding().getValue().contains("gzip")) {
                            final InputStream in = response.getEntity().getContent();
                            final InputStream is = new GZIPInputStream(in);
                            strResponse = XHttpRequest.inputStreamToString(is);
                            is.close();
                        } else {
                            response.getEntity().writeTo(content);
                            strResponse = new String(content.toByteArray()).trim();
                        }
                    } else {
                        response.getEntity().writeTo(content);
                        strResponse = new String(content.toByteArray()).trim();
                        strResponse =
                                "{'isError':false,'errorType':0,'errorMessage':'','result':{'city':'北京','cityid':'101010100','temp':'17','WD':'西南风','WS':'2级','SD':'54%','WSE':'2','time':'23:15','isRadar':'1','Radar':'JC_RADAR_AZ9010_JB','njd':'暂无实况','qy':'1016'}}";
                    }

                    final XHttpResponse responseInJson = JSON.parseObject(strResponse, XHttpResponse.class);
                    if (responseInJson.hasError()) {
                        if (responseInJson.getErrorType() == 1) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    requestCallback.onCookieExpired();
                                }
                            });
                        } else {
                            handleNetworkError(responseInJson.getErrorMessage());
                        }
                    } else {
                        // 把成功获取到的数据记录到缓存
                        if (RequestMethod.GET.getMethod().equalsIgnoreCase(data.getRequestMethod()) && data.getExpires() > 0) {
                            XCacheItemManager.getInstance().putFileCache(newUrl, responseInJson.getResult(), data.getExpires());
                        }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                requestCallback.onSuccess(responseInJson.getResult());
                            }

                        });

                        // 保存Cookie
                        saveCookie();
                    }
                } else {
                    handleNetworkError("网络异常");
                }
            } else {
                handleNetworkError("网络异常");
            }
        } catch (final java.lang.IllegalArgumentException e) {
            handleNetworkError("网络异常");
        } catch (final UnsupportedEncodingException e) {
            handleNetworkError("网络异常");
        } catch (final IOException e) {
            handleNetworkError("网络异常");
        }
    }

    public void handleNetworkError(final String errorMsg) {
        if (requestCallback != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    requestCallback.onFail(errorMsg);
                }
            });
        }
    }

    static String inputStreamToString(final InputStream is) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }

    void sortKeys() {
        for (int i = 1; i < parameter.size(); i++) {
            for (int j = i; j > 0; j--) {
                XHttpRequestParameter p1 = parameter.get(j - 1);
                XHttpRequestParameter p2 = parameter.get(j);
                if (compare(p1.getName(), p2.getName())) {
                    // 交互p1和p2这两个对象，写的超级恶心
                    String name = p1.getName();
                    String value = p1.getValue();

                    p1.setName(p2.getName());
                    p1.setValue(p2.getValue());

                    p2.setName(name);
                    p2.setValue(value);
                }
            }
        }
    }

    // 返回true说明str1大，返回false说明str2大
    boolean compare(String str1, String str2) {
        String uppStr1 = str1.toUpperCase();
        String uppStr2 = str2.toUpperCase();

        boolean str1IsLonger = true;
        int minLen = 0;

        if (str1.length() < str2.length()) {
            minLen = str1.length();
            str1IsLonger = false;
        } else {
            minLen = str2.length();
            str1IsLonger = true;
        }

        for (int index = 0; index < minLen; index++) {
            char ch1 = uppStr1.charAt(index);
            char ch2 = uppStr2.charAt(index);
            if (ch1 != ch2) {
                if (ch1 > ch2) {
                    return true; // str1大
                } else {
                    return false; // str2大
                }
            }
        }

        return str1IsLonger;
    }

    /**
     * cookie列表保存到本地
     *
     * @return
     */
    public synchronized void saveCookie() {
        // 获取本次访问的cookie
        final List<Cookie> cookies = httpClient.getCookieStore().getCookies();
        // 将普通cookie转换为可序列化的cookie
        List<XSerializableCookie> serializableCookies = null;

        if ((cookies != null) && (cookies.size() > 0)) {
            serializableCookies = new ArrayList<XSerializableCookie>();

            for (final Cookie c : cookies) {
                serializableCookies.add(new XSerializableCookie(c));
            }
        }

        XBaseUtils.saveObject(cookiePath, serializableCookies);
    }

    /**
     * 从本地获取cookie列表
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public void addCookie() {
        List<XSerializableCookie> cookieList = null;
        Object cookieObj = XBaseUtils.restoreObject(cookiePath);
        if (cookieObj != null) {
            cookieList = (ArrayList<XSerializableCookie>) cookieObj;
        }

        if ((cookieList != null) && (cookieList.size() > 0)) {
            final BasicCookieStore cs = new BasicCookieStore();
            cs.addCookies(cookieList.toArray(new Cookie[] {}));
            httpClient.setCookieStore(cs);
        } else {
            httpClient.setCookieStore(null);
        }
    }

    void setHttpHeaders(final HttpUriRequest httpMessage) {
        headers.clear();
        headers.put(XFrameConstants.ACCEPT_CHARSET, "UTF-8,*");
        headers.put(XFrameConstants.USER_AGENT, "shsun-android-app");
        headers.put(XFrameConstants.ACCEPT_ENCODING, "gzip");
        if ((httpMessage != null) && (headers != null)) {
            for (final Entry<String, String> entry : headers.entrySet()) {
                if (entry.getKey() != null) {
                    httpMessage.addHeader(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    /**
     * 更新服务器时间和本地时间的差值
     */
    void updateDeltaBetweenServerAndClientTime() {
        if (response != null) {
            final Header header = response.getLastHeader("Date");
            if (header != null) {
                final String strServerDate = header.getValue();
                try {
                    if ((strServerDate != null) && !strServerDate.equals("")) {
                        final SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);
                        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
                        Date serverDateUAT = sdf.parse(strServerDate);
                        deltaBetweenServerAndClientTime = serverDateUAT.getTime() + 8 * 60 * 60 * 1000 - System.currentTimeMillis();
                    }
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Date getServerTime() {
        return new Date(System.currentTimeMillis() + deltaBetweenServerAndClientTime);
    }
}