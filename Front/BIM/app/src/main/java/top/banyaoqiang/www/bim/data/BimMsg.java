package top.banyaoqiang.www.bim.data;

import java.util.Date;

public class BimMsg extends BimData {
    public static final int TYPE_SEND = 10;
    public static final int TYPE_RECEIVE = 11;
    public static final int TYPE_HISTORY = 12;
    public static final int READ = 20;
    public static final int UNREAD = 21;

    private static final String SENDER = "sender";
    private static final String RECEIVER = "receiver";
    private static final String TEXT = "text";
    private static final String DATE = "date";
    private static final String TYPE = "type";

    private BimUser sender;
    private BimUser receiver;
    private String text;
    private Date date;
    private int type;

    public BimMsg() {
        this.date = new Date();
    }

    public BimMsg(BimUser sender, BimUser receiver, int type) {
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
        this.date = new Date();
    }

    public BimMsg(BimUser sender, BimUser receiver, String text, int type) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.type = type;
        this.date = new Date();
    }

    public BimUser getSender() {
        return sender;
    }

    public void setSender(BimUser sender) {
        this.sender = sender;
    }

    public BimUser getReceiver() {
        return receiver;
    }

    public void setReceiver(BimUser receiver) {
        this.receiver = receiver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
