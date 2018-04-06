package top.banyaoqiang.www.bim.network;

import java.util.HashMap;
import java.util.Map;

public class BimResponse implements Acceptable {

    private static final String MAP_BODY_REGEX = "^.+=.+(&.+=.+)*$";

    private String action = "test";
    private String protocolVersion = "BIMP/1.0";
    private String appVersion = "BIM/1.0.0";
    private String code = "403";
    private String body = "this is a default body just for debug";

    public BimResponse() {}
    public BimResponse(String code) {
        this.code = code;
    }

    public BimResponse(String action, String body) {

    }

    public BimResponse(String action, String code, String body) {

    }

    @Override
    public String toString() {
        String s = "";
        s += TYPE_RESPONSE;
        s += "\n";
        s += action;
        s += "\n";
        s += protocolVersion + " " + appVersion;
        s += "\n";
        s += code;
        s += "\n";
        s += body;
        s += "\n";
        return s;
    }

    public Map<String, String> mapBody() throws Exception {
        if (!body.matches(MAP_BODY_REGEX)) throw new Exception("response body can not convert to map.");
        String[] params = body.split("[=&]");
        if (params.length %2 != 0) throw new Exception("response body params did not match.");
        Map<String, String> bodyMap = new HashMap<>();
        for (int i=0;i<params.length;i+=2)
            bodyMap.put(params[i], params[i+1]);
        return bodyMap;
    }

    @Override
    public String type() {
        return TYPE_RESPONSE;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
