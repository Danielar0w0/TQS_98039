package pt.ua.deti.tqs.hw1.project_api.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CacheManager {

    private static CacheManager instance;
    private static Object monitor = new Object();

    private static final long TIMETOLIVE = 100L;

    private Map<String, CacheObject> cache = Collections.synchronizedMap(new HashMap<>());

    private CacheManager() {
        Thread cleanerThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(TIMETOLIVE * 1000L);
                    cache.entrySet().removeIf(entry -> entry.getValue().isExpired());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        cleanerThread.setDaemon(true);
        cleanerThread.start();
    }

    public void put(String cacheKey, Object value) {
        if (cacheKey == null)
            return;
        if (value == null) {
            cache.remove(cacheKey);
        } else {
            long expiryTime = System.currentTimeMillis() + TIMETOLIVE;
            cache.put(cacheKey, new CacheObject(value, expiryTime));
        }
    }

    public CacheObject get(String cacheKey) {
        return cache.get(cacheKey);
    }

    public void remove(String cacheKey) {
        cache.remove(cacheKey);
    }

    public int size() {
        return cache.size();
    }

    public void clear() {
        cache.clear();
    }

    public static CacheManager getInstance() {
        synchronized (monitor) {
            if (instance == null) {
                instance = new CacheManager();
            }
        }
        return instance;
    }

    public Map<String, CacheObject> getCache() {
        return cache;
    }
}
