package base.net;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class XRequestManager {

    protected ArrayList<XHttpRequest> requestList = null;

    public XRequestManager() {
        requestList = new ArrayList<XHttpRequest>();
    }

    /**
     *
     * @param request
     * @return true indicate add success, otherwise there is duplicate request.
     */
    public Boolean addRequest(final XHttpRequest request) {
        Boolean result = false;
        if (!this.requestList.contains(request)) {
            requestList.add(request);
            result = true;
        }
        return result;
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
    public XHttpRequest createHttpRequest(final XHttpURLData data, final XHttpRequestCallback callback) {
        return this.createHttpRequest(data, null, callback);
    }

    /**
     * 有参数调用
     */
    public XHttpRequest createHttpRequest(final XHttpURLData data, final List<XHttpRequestParameter> params, final XHttpRequestCallback callback) {
        final XHttpRequest request = new XHttpRequest(data, params, callback);
        this.addRequest(request);
        return request;
    }
}
