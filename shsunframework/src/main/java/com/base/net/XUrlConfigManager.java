package com.base.net;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.res.XmlResourceParser;


/**
 * @author shsun
 */
public class XUrlConfigManager {

    private static ArrayList<XHttpURLData> urlList;

    private static void fetchUrlDataFromXml(final Activity activity, XmlResourceParser xmlParser) {
        urlList = new ArrayList<XHttpURLData>();

//        final XmlResourceParser xmlParser = activity.getApplication()
//                .getResources().getXml(R.xml.url);


        int eventCode;
        try {
            eventCode = xmlParser.getEventType();
            while (eventCode != XmlPullParser.END_DOCUMENT) {
                switch (eventCode) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if ("Node".equals(xmlParser.getName())) {
                            final String key = xmlParser.getAttributeValue(null,
                                    "Key");
                            final XHttpURLData urlData = new XHttpURLData();
                            urlData.setKey(key);
                            urlData.setExpires(Long.parseLong(xmlParser
                                    .getAttributeValue(null, "Expires")));
                            urlData.setNetType(xmlParser.getAttributeValue(null,
                                    "NetType"));
                            urlData.setMockClass(xmlParser.getAttributeValue(null,
                                    "MockClass"));
                            urlData.setUrl(xmlParser.getAttributeValue(null, "Url"));
                            urlList.add(urlData);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                eventCode = xmlParser.next();
            }
        } catch (final XmlPullParserException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            xmlParser.close();
        }
    }

    public static XHttpURLData findURL(final Activity activity,
                                       final String findKey,
                                       final XmlResourceParser xmlParser) {

        XHttpURLData result = null;

        // 如果urlList还没有数据（第一次），或者被回收了，那么（重新）加载xml
        if (urlList == null || urlList.isEmpty()) {
            fetchUrlDataFromXml(activity, xmlParser);
        }

        for (XHttpURLData data : urlList) {
            if (findKey.equals(data.getKey())) {
                result = data;
                break;
            }
        }

        return result;
    }
}