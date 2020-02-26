package com.api.common.services.cache;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

public final class MCacheService {
    private final MemcacheService _memCache;
    private int expSeconds = 0;

    public MCacheService() {
        this._memCache = MemcacheServiceFactory.getMemcacheService();
        this._memCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
    }

    public MCacheService(int expirationSeconds) {
        this();
        this.expSeconds = expirationSeconds;
    }

    public MCacheService(String namespace) {
        this._memCache = MemcacheServiceFactory.getMemcacheService(namespace);
    }

    public MCacheService(String namespace, int expSeconds) {
        this._memCache = MemcacheServiceFactory.getMemcacheService(namespace);
        this.expSeconds = expSeconds;
    }

    public MemcacheService cache(){
        return _memCache;
    }

    public void setExpSeconds(int expSec) {
        this.expSeconds = expSec;
    }

    public Object get(Object key) {
        return key == null ? null : this._memCache.get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Object key, Class<T> clazz) {
        return (T) get(key);
    }

    public Map<String, Object> getAll(Collection<String> keys) {
        if (keys == null || keys.size() <= 0)
            return Collections.emptyMap();
        Map<String, Object> all = this._memCache.getAll(keys);
        if (all == null)
            all = Collections.emptyMap();
        return all;
    }

    public void put(Object key, Object value) {
        this.put(key, value, expSeconds);
    }

    public void put(Object key, Object value, int expSecs) {
        if (key == null)
            return;
        if (expSecs > 0)
            this._memCache.put(key, value, Expiration.byDeltaSeconds(expSecs));
        else
            this._memCache.put(key, value);
    }

    public void putAll(Map<?, ?> values) {
        this.putAll(values, expSeconds);
    }

    private void putAll(Map<?, ?> values, int expSeconds) {
        if (values == null || values.isEmpty())
            return;
        if (expSeconds <= 0)
            this._memCache.putAll(values);
        else
            this._memCache.putAll(values, Expiration.byDeltaSeconds(expSeconds));
    }

    public boolean remove(Object key) {
        return key != null && this._memCache.delete(key);
    }

    public <T> Set<?> removeAll(Collection<T> keys) {
        if (keys == null || keys.isEmpty())
            return Collections.emptySet();
        return this._memCache.deleteAll(keys);
    }

    public boolean contains(Object key) {
        return key != null && _memCache.contains(key);
    }


}
