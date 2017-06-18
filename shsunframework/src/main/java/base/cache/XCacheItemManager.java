package base.cache;

import java.io.File;

import android.os.Environment;

import base.utils.XBaseUtils;

/**
 * 缓存管理器
 */
public class XCacheItemManager {
    /**
     * 缓存文件路径
     */
    public static final String APP_CACHE_PATH = Environment.getExternalStorageDirectory().getPath() + "/YoungHeart/appdata/";

    /**
     * sdcard 最小空间，如果小于10M，不会再向sdcard里面写入任何数据
     */
    public static final long SDCARD_MIN_SPACE = 1024 * 1024 * 10;

    private static XCacheItemManager cacheManager;

    private XCacheItemManager() {
    }

    /**
     * 获取CacheManager实例
     *
     * @return
     */
    public static synchronized XCacheItemManager getInstance() {
        if (XCacheItemManager.cacheManager == null) {
            XCacheItemManager.cacheManager = new XCacheItemManager();
        }
        return XCacheItemManager.cacheManager;
    }

    /**
     * 从文件缓存中取出缓存，没有则返回空
     *
     * @param key
     * @return
     */
    public String getFileCache(final String key) {
        String md5Key = XBaseUtils.getMd5(key);
        if (contains(md5Key)) {
            final XCacheItem item = getFromCache(md5Key);
            if (item != null) {
                return item.getData();
            }
        }
        return null;
    }

    /**
     * API data 缓存到文件
     *
     * @param key
     * @param data
     * @param expiredTime
     */
    public void putFileCache(final String key, final String data, long expiredTime) {
        String md5Key = XBaseUtils.getMd5(key);

        final XCacheItem item = new XCacheItem(md5Key, data, expiredTime);
        putIntoCache(item);
    }

    /**
     * 查询是否有key对应的缓存文件
     *
     * @param key
     * @return
     */
    public boolean contains(final String key) {
        final File file = new File(APP_CACHE_PATH + key);
        return file.exists();
    }

    public void initCacheDir() {
        // sdcard已经挂载并且空间不小于10M，可以写入文件;小于10M时，清除缓存
        if (XBaseUtils.sdcardMounted()) {
            if (XBaseUtils.getSDSize() < SDCARD_MIN_SPACE) {
                clearAllData();
            } else {
                final File dir = new File(APP_CACHE_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
            }
        }
    }

    /**
     * 将CacheItem从磁盘读取出来
     *
     * @param key
     * @return 缓存数据CachItem
     */
    synchronized XCacheItem getFromCache(final String key) {
        XCacheItem cacheItem = null;
        Object findItem = XBaseUtils.restoreObject(APP_CACHE_PATH + key);
        if (findItem != null) {
            cacheItem = (XCacheItem) findItem;
        }

        // 缓存不存在
        if (cacheItem == null)
            return null;

        // 缓存过期
        long a = System.currentTimeMillis();
        if (System.currentTimeMillis() > cacheItem.getTimeStamp()) {
            return null;
        }

        return cacheItem;
    }

    /**
     * 将CacheItem缓存到磁盘
     *
     * @param item
     * @return 是否缓存，True：缓存成功，False：不能缓存
     */
    synchronized boolean putIntoCache(final XCacheItem item) {
        if (XBaseUtils.getSDSize() > SDCARD_MIN_SPACE) {
            XBaseUtils.saveObject(APP_CACHE_PATH + item.getKey(), item);
            return true;
        }

        return false;
    }

    /**
     * 清除缓存文件
     */
    void clearAllData() {
        File file = null;
        File[] files = null;
        if (XBaseUtils.sdcardMounted()) {
            file = new File(APP_CACHE_PATH);
            files = file.listFiles();
            if (files != null) {
                for (final File file2 : files) {
                    file2.delete();
                }
            }
        }
    }
}
