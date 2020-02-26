package com.mncm.model.response;

import lombok.Data;

import java.util.Collection;

/**
 * OpenDigitalUniversity
 * Created by sonudhakar on 23/07/17.
 */
@Data
public class CollectionResponse<T> {

    private final Collection<T> items;
    private final String cursor;

    public static <T> Builder<T> builder() {
        return new Builder();
    }

    protected CollectionResponse(Collection<T> items, String cursor) {
        this.items = items;
        this.cursor = cursor;
    }

    public static class Builder<T> {
        private Collection<T> items;
        private String cursor;

        public Builder<T> setItems(Collection<T> items) {
            this.items = items;
            return this;
        }

        public Builder<T> setCursor(String cursor) {
            this.cursor = cursor;
            return this;
        }

        public CollectionResponse<T> build() {
            return new CollectionResponse<T>(this.items, this.cursor);
        }
    }
}
