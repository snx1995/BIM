package top.banyaoqiang.www.bim.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import top.banyaoqiang.www.bim.exception.MethodNotImplementException;

import static top.banyaoqiang.www.bim.Manager.TAG;

public abstract class BimData {
    public String toJsonString() throws MethodNotImplementException {
        return this.toJsonObject().toString();
    }

    /***
     * @return it should return JSONArray if subclass is a kind of list
     *         or array, and return JSONObject if subclass is an object.
     * @throws MethodNotImplementException
     */

    public Object toJsonObject() throws MethodNotImplementException {
        throw new MethodNotImplementException();
    }

    public void fromJsonString(String json) {
        try {
            this.fromJsonObject(new JSONObject(json));
        } catch (Exception e) {
            Log.d(TAG, "fromJsonString: " + e.getMessage());
        }
    }

    public void fromJsonObject(JSONObject json) throws MethodNotImplementException {
        throw new MethodNotImplementException();
    }
}

