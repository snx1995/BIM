package top.banyaoqiang.www.bim.network;

import java.util.HashMap;
import java.util.Map;

public class BimRequest implements Acceptable {
    private static final String MAP_BODY_REGEX = "^.+=.+(&.+=.+)*$";
    private static final String CONNECT_KEEP_ALIVE = "keep-live";
    private static final String CONNECT_CLOSE = "close";

    private String action = "test";
    private String protocolVersion = "BIMP/1.0";
    private String appVersion = "BIM/1.0.0";
    private String body = "this is a default body just for debug";
    private String connect = CONNECT_CLOSE;


    public Map<String, String> mapBody() throws Exception {
        if (!body.matches(MAP_BODY_REGEX)) throw new Exception("request body can not convert to map.");
        String[] params = body.split("[=&]");
        if (params.length %2 != 0) throw new Exception("request body params did not match.");
        Map<String, String> bodyMap = new HashMap<>();
        for (int i=0;i<params.length;i+=2)
            bodyMap.put(params[i], params[i+1]);
        return bodyMap;
    }

    @Override
    public String toString() {
        String s = "";
        s += TYPE_REQUEST;
        s += "\n";
        s += action;
        s += "\n";
        s += protocolVersion + " " + appVersion;
        s += "\n";
        s += connect;
        s += "\n";
        s += body;
        s += "\n";
        return s;
    }

    @Override
    public String type() {
        return TYPE_REQUEST;
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

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
