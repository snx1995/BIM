package top.banyaoqiang.www.bim.data;

import java.util.ArrayList;
import java.util.List;

public class BimMsgList extends BimData {
    public static final int TYPE_MORMAL = 30;
    public static final int TYPE_HISTORY = 31;

    private List<BimMsg> msgs;

    public BimMsg get(int i) {
        return msgs.get(i);
    }

    public int size() {
        return msgs.size();
    }

    public BimMsgList() {
        this.msgs = new ArrayList<>();
    }

    public BimMsgList(List<BimMsg> msgs) {
        this.msgs = msgs;
    }

    public List<BimMsg> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<BimMsg> msgs) {
        this.msgs = msgs;
    }
}
