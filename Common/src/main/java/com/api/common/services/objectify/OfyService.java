package com.api.common.services.objectify;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.common.collect.Lists;
import com.googlecode.objectify.*;
import com.googlecode.objectify.cmd.Query;
import com.api.common.entity.AbstractBaseEntity;
import com.api.common.exception.runtime.IllegalArgException;
import com.api.common.model.response.CollectionResponse;
import com.api.common.utils.GaeUtil;
import com.api.common.utils.Preconditions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * OpenDigitalUniversity createdBy sonudhakar on 17/07/2017.
 */
public class OfyService {

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }

    public <T> T get(Class<T> clazz, String key) {
        return isNullOrEmpty(key) ? null : ofy().load().type(clazz).id(key).now();
    }

    public <T> T get(Class<T> clazz, long key) {
        return key <= 0 ? null : ofy().load().type(clazz).id(key).now();
    }

    public <T> T get(Class<T> clazz, String key, boolean cache) {
        return isNullOrEmpty(key) ? null : ofy().cache(cache).load().type(clazz).id(key).now();
    }

    public <T> T get(Ref<T> ref) {
        return ref == null ? null : get(ref.getKey());
    }

    public <T> Key<T> getKey(Class<T> clazz, String id) {
        return Key.create(clazz, id);
    }

    public <T> T get(Key<T> key) {
        return key == null ? null : ofy().load().key(key).now();
    }

    public <T, S> List<T> get(Class<T> clazz, Iterable<S> ids) {
        if (ids == null)
            return new ArrayList<>();
        Map<S, T> result = ofy().load().type(clazz).ids(ids);
        if (result == null)
            return new ArrayList<>();
        return new ArrayList<T>(result.values());
    }

    public <T> T getByFilter(Class<T> clazz, String filterBy, Object value) {

        if (value == null || isNullOrEmpty(filterBy))
            throw new IllegalArgumentException("Invalid Argument value/filterBy");

        return ofy().load().type(clazz).filter(filterBy, value).first().now();
    }

    public <T> List<T> getEntitiesByFilter(Class<T> clazz, String filterBy, Object value) {
        if (value == null || isNullOrEmpty(filterBy))
            throw new IllegalArgumentException("Invalid Argument value / filterBy");
        return ofy().load().type(clazz).filter(filterBy, value).list();
    }

    public <T> Key<T> getKeyByFilter(Class<T> clazz, String filterBy, Object value) {
        return ofy().load().type(clazz).filter(filterBy, value).keys().first().now();
    }


    public <T> CollectionResponse<T> fetchCursorQuery(Query<T> query, int limit, String cursor) {

        Preconditions.checkArgument(query == null, "invalid query");

        try {
            if (cursor != null)
                query = query.startAt(Cursor.fromWebSafeString(cursor));

            query = query.limit(limit);

            QueryResultIterator<T> iterator = query.iterator();
            List<T> items = Lists.newArrayList(iterator);

            String cursorString = null;
            if (iterator.getCursor() != null)
                cursorString = items.size() < limit ? null : iterator.getCursor().toWebSafeString();

            return CollectionResponse.<T>builder()
                    .setItems(items)
                    .setCursor(cursorString)
                    .build();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgException(e.getMessage(), e);
        }
    }

    public <T> CollectionResponse<Key<T>> fetchCursorKeyQuery(Query<T> query, int limit, String cursor) {

        Query<T> temp = query;
        if (cursor != null)
            temp = temp.startAt(Cursor.fromWebSafeString(cursor));

        temp = temp.limit(limit);

        QueryResultIterator<Key<T>> iterator = temp.keys().iterator();

        List<Key<T>> items = Lists.newArrayList(iterator);
        String cursorString = null;

        if (iterator.getCursor() != null)
            cursorString = items.size() < limit ? null : iterator.getCursor().toWebSafeString();

        return CollectionResponse.<Key<T>>builder()
                .setItems(items)
                .setCursor(cursorString)
                .build();
    }

    public <T> int deleteAllEntities(Query<T> query, int batchSize, boolean sync) {

        String cursor = null;
        int total = 0;
        do {
            CollectionResponse<Key<T>> response = fetchCursorKeyQuery(query, batchSize, cursor);
            cursor = response.getCursor();

            Result<Void> result = ofy().delete().keys(response.getItems());
            if (sync)
                result.now();

            total += response.getItems().size();

        } while (cursor != null);

        return total;
    }

    public <T> Key<T> save(T entity) {
        if (entity == null)
            return null;
        return ofy().save().entity(entity).now();
    }

    public <T> Map<Key<T>, T> save(Collection<T> entities) {
        return isNullOrEmpty(entities) ? null : ofy().save().entities(entities).now();
    }

    public boolean delete(Collection<?> entities) {

        if (isNullOrEmpty(entities))
            return false;

        ofy().delete().entities(entities);
        //TODO delete entity etag cache data as well
        return true;
    }

    public <T> boolean delete(T entity) {
        if (entity == null)
            return false;

        ofy().delete().entity(entity);

        if (entity instanceof AbstractBaseEntity) {
            GaeUtil.deleteEntityETagInCache((AbstractBaseEntity) entity);
        }

        return true;
    }

    static boolean isNullOrEmpty(String value) {
        return (value == null || value.length() <= 0);
    }

    static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }

    static boolean isNullOrEmpty(Collection<?> obj) {
        return (obj == null || obj.isEmpty());
    }

    static boolean isNullOrEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }
}
