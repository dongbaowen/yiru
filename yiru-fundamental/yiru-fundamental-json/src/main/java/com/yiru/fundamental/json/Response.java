package com.yiru.fundamental.json;

import java.io.Serializable;
import com.google.common.collect.Maps;
import java.util.Map;

/**
 * Created by Baowen on 2017/4/15.
 */
public class Response<T> implements Serializable {

    private Result result = new Result<T>();
    private Map<String, Object> extraData = Maps.newHashMap();
    private boolean serializeNulls = false;

    public Response<T> serializeNulls() {
        serializeNulls = true;
        return this;
    }

    public Response<T> fail() {
        result.setSuccess(false);
        return this;
    }

    public Response<T> success() {
        result.setSuccess(true);
        return this;
    }

    public Response<T> errCode(String errCode) {
        result.setCode(errCode);
        return this;
    }

    public Response<T> msg(String msg) {
        result.setMessage(msg);
        return this;
    }

    public Response data(T data) {
        result.setData(data);
        return this;
    }

    public Response put(String key, Object value) {
        extraData.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        if (extraData.isEmpty()) {
            return serializeNulls ? GsonUtils.toJsonWithSerializeNulls(result) : GsonUtils.toJson(result);
        } else {
            Map<String, Object> extraResult = GsonUtils.fromJson(GsonUtils.toJson(result), Map.class);
            extraResult.putAll(extraData);
            return serializeNulls ? GsonUtils.toJsonWithSerializeNulls(extraResult) : GsonUtils.toJson(extraResult);
        }
    }

    private class Result<T> {

        private boolean success = true;

        private String code = "E000000";

        private String message = "请求成功";

        private T data;

        public Result() {
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }
}
