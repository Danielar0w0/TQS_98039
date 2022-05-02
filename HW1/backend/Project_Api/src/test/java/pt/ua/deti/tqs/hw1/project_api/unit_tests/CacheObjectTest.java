package pt.ua.deti.tqs.hw1.project_api.unit_tests;

import org.junit.jupiter.api.Test;
import pt.ua.deti.tqs.hw1.project_api.cache.CacheObject;

import static org.junit.jupiter.api.Assertions.*;

class CacheObjectTest {

    @Test
    void isExpired() throws InterruptedException {
        CacheObject cacheObject = new CacheObject("Hello", System.currentTimeMillis() + 1000L);
        assertEquals(false, cacheObject.isExpired());
        Thread.sleep(1001L);
        assertEquals(true, cacheObject.isExpired());
    }
}