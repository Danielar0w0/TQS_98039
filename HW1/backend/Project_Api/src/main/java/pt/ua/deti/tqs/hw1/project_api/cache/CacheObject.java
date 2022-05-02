package pt.ua.deti.tqs.hw1.project_api.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class CacheObject {

    @Getter
    private Object content;
    private long expiryTime;

    boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}
