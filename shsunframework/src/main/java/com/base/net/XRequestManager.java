package com.base.net;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class XRequestManager {
    ArrayList<XHttpRequest> requestList = null;

    public XRequestManager() {
        // 异步请求列表
        requestList = new ArrayList<XHttpRequest>();
    }

    /**
     * 添加Request到列表
     */
    public void addRequest(final XHttpRequest request) {
        requestList.add(request);
    }

    /**
     * 取消网络请求
     */
    public void cancelRequest() {
        if ((requestList != null) && (requestList.size() > 0)) {
            for (final XHttpRequest request : requestList) {
                if (request.getRequest() != null) {
                    try {
                        request.getRequest().abort();
                        requestList.remove(request.getRequest());
                    } catch (final UnsupportedOperationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 无参数调用
     */
    public XHttpRequest createRequest(final XHttpURLData urlData,
                                      final XHttpRequestCallback requestCallback) {
        final XHttpRequest request = new XHttpRequest(urlData, null,
                requestCallback);
        addRequest(request);
        return request;
    }

    /**
     * 有参数调用
     */
    public XHttpRequest createRequest(final XHttpURLData urlData,
                                      final List<XHttpRequestParameter> params,
                                      final XHttpRequestCallback requestCallback) {
        final XHttpRequest request = new XHttpRequest(urlData, params,
                requestCallback);

        addRequest(request);
        return request;
    }
}
