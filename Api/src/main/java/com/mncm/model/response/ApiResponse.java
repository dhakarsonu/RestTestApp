package com.mncm.model.response;

import com.api.common.enums.ErrorCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OpenDigitalUniversity
 * Created by sonudhakar on 23/07/17.
 */

@Data
@NoArgsConstructor
public class ApiResponse implements Serializable {

    private static final long serialVersionUID = -1377225889451120985L;

    private boolean ok;
    private String msg;

    private List<ApiErrorModel> errors;

    private Map<String, Object> data;

    public ApiResponse(boolean ok, ErrorCode code, String message) {

        this.ok = ok;
        this.addError(code, message);
    }

    /* helpers for errors */
    public void addError(ErrorCode code, String message){
     this.addError(new ApiErrorModel(code, message));
    }

    public void addError(ApiErrorModel errorModel){

        if (errorModel == null)
            return;

        if (this.errors == null)
            this.errors = new ArrayList<>();

        this.errors.add(errorModel);
    }

    /* helpers for data */
    public Object get(String key) {
        if (this.data == null)
            return null;
        return data.get(key);
    }

    public <T> T get(String key, Class<T> clazz) {
        if (this.data == null)
            return null;
        return (T) data.get(key);
    }

    public void add(String key, Object obj) {
        if (data == null)
            data = new HashMap<>();
        data.put(key, obj);
    }

    public void addNonNull(String key, Object obj) {

        if (obj != null)
            add(key, obj);
    }

    public void addAll(Map<String, Object> content) {
        if (content == null)
            return;
        if (data == null)
            data = new HashMap<>();
        data.putAll(content);
    }
}
