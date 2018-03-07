package top.banyaoqiang.www.bim;

import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.ls.LSInput;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static top.banyaoqiang.www.bim.BimValues.TAG;

/**
 * Created by byq on 18-2-27.
 */

public abstract class BimData {
    public String toJSONString(){
        return this.toJSONObject().toString();
    }
    public abstract JSONObject toJSONObject();
    public Boolean fromJSONString(String json){
        try{
            this.fromJSONObject(new JSONObject(json));
        }catch (Exception e){
            Log.d(TAG, "fromJSONString: " + e.getMessage());
            return false;
        }
        return true;
    }
    public abstract Boolean fromJSONObject(JSONObject json);
}

class BimUser extends BimData{
    public static final int FRIEND = 0;
    public static final int STRANGER = 1;
    public static final int SELF = 2;

    private static final String NAME = "name";
    private static final String ID = "id";
    private static final String IMAGE = "image";
    private static final String TYPE = "type";

    private String name;
    private int id;
    private int image;
    private int type;


    BimUser(){};

    BimUser(String name, int id, int image) {
        this.name = name;
        this.id = id;
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getStringId(){
        return id + "";
    }

    int getImage() {
        return image;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        try{
            json.put("name",name);
            json.put("id",id);
            json.put("image",image);
            json.put("type",type);
            return json;
        }catch (Exception e){
            Log.d(TAG, "BimUser.toJSONObject: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean fromJSONObject(JSONObject json) {
        try{
            name = json.getString("name");
            id = json.getInt("id");
            image = json.getInt("image");
            type = json.getInt("type");
        }catch (Exception e){
            Log.d(TAG, "BimUser.fromJSONObject: " + e.getMessage());
            return false;
        }
        return true;
    }

    void putToIntent(Intent intent){
        intent.putExtra("name", name);
        intent.putExtra("id", id);
        intent.putExtra("image", image);
        intent.putExtra("type", type);
    }

    void fromIntent(Intent intent){
        name = intent.getStringExtra("name");
        id = intent.getIntExtra("id", -1);
        image = intent.getIntExtra("image", -1);
        type = intent.getIntExtra("type", -1);
    }
}

class BimUserDetail extends BimData{
    private static final String NAME = "name";
    private static final String ID = "id";
    private static final String IMAGE = "image";
    private static final String TYPE = "type";
    private static final String EMAIL = "email";
    private static final String SEX = "sex";
    private static final String BIRTHDAY = "birthday";
    private static final String INTRO = "intro";

    private String name;
    private int id;
    private int image;
    private int type;

    private String email;
    private int sex;
    private String intro;
    private Date birthday;

    public BimUserDetail() {}

    public BimUserDetail(BimUser user){
        name = user.getName();
        id = user.getId();
        image = user.getImage();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getImage() {
        return image;
    }

    public int getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }

    public int getSex() {
        return sex;
    }

    public String getIntro() {
        return intro;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getStringId(){
        return id + "";
    }

    public BimUser toBimUser(){
        return new BimUser(name, id, image);
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        try{
            json.put(ID, id);
            json.put(NAME, name);
            json.put(IMAGE, image);
            json.put(TYPE, type);
            json.put(EMAIL, email);
            json.put(SEX, sex);
            json.put(BIRTHDAY, birthday.toString());
            json.put(INTRO, intro);
            return json;
        }catch (Exception e){
            Log.d(TAG, "BimUSerDetail.toJSONObject: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean fromJSONObject(JSONObject json) {
        try{
            name = json.getString(NAME);
            id = json.getInt(ID);
            image = json.getInt(IMAGE);
            sex = json.getInt(SEX);
            birthday = new Date(json.getString(BIRTHDAY));
            email = json.getString(EMAIL);
            intro = json.getString(INTRO);
            type = json.getInt(TYPE);
        }catch (Exception e){
            Log.d(TAG, "BimUserDetail.fromJSONObject: " + e.getMessage());
            return false;
        }
        return true;
    }
}

class BimUserDetailList extends BimData {

    private List<BimUserDetail> userDetailList = new ArrayList<>();


    public JSONArray toJSONArray(){
        JSONArray jsonArray = new JSONArray();
        try{
            for(BimUserDetail user : userDetailList){
                jsonArray.put(user.toJSONObject());
            }
        }catch (Exception e){
            Log.d(TAG, "BimUserDetailList.toJSONArray: " + e.getMessage());
        }
        return jsonArray;
    }

    public Boolean fromJSONArray(JSONArray jsonArray){
        userDetailList = new ArrayList<>();
        try{
            for(int i=0;i<jsonArray.length();i++){
                BimUserDetail user = new BimUserDetail();
                user.fromJSONObject(jsonArray.getJSONObject(i));
                userDetailList.add(user);
            }
        }catch (Exception e){
            Log.d(TAG, "BimUserDetailList.fromJSONArray: " + e.getMessage());
            return false;
        }
        return true;
    }

    public int size(){
        return userDetailList.size();
    }

    public BimUserDetail get(int i){
        return userDetailList.get(i);
    }

    @Override
    public JSONObject toJSONObject() {
        return null;
    }

    @Override
    public Boolean fromJSONObject(JSONObject json) {
        try{
            JSONArray jsonArray = json.getJSONArray("friends");
            fromJSONArray(jsonArray);
        }catch (Exception e){
            Log.d(TAG, "BimUserDetailList.fromJSONObject: " + e.getMessage());
            return false;
        }
        return true;
    }
}

class BimMsg extends BimData{
    public static final int TYPE_SEND = 0;
    public static final int TYPE_RECEIVE =1;
    public static final int TYPE_HISTORY = 2;

    private BimUser sender;
    private BimUser receiver;
    private String text;
    private Date date;
    private int type = 3;

    public BimMsg(){}

    public BimMsg(BimUser sender, BimUser receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public BimMsg(BimUser sender, BimUser receiver, String text, int type) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.date = new Date();
        this.type = type;
    }

    public BimMsg(BimUser sender, BimUser receiver, String text, int type, Date date) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.date = date;
        this.type = type;
    }

    public BimUser getSender() {
        return sender;
    }

    public BimUser getReceiver() {
        return receiver;
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BimUser getFriend(){
        return sender.getType() == BimUser.FRIEND? sender : receiver;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        try{
            json.put("sender",sender.toJSONObject());
            json.put("receiver",receiver.toJSONObject());
            json.put("text",text);
            json.put("type",type);
            json.put("date",date.toString());
            return json;
        }catch (Exception e){
            Log.d(TAG, "BimMsg.toJSONObject: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean fromJSONObject(JSONObject json) {
        if(sender == null) sender = new BimUser();
        if(receiver == null) receiver = new BimUser();
        try{
            sender.fromJSONObject(json.getJSONObject("sender"));
            receiver.fromJSONObject(json.getJSONObject("receiver"));
            text = json.getString("text");
            type = json.getInt("type");
            date = new Date(json.getString("date"));
        }catch (Exception e){
            Log.d(TAG, "BimMsg.fromJSONObject: " + e.getMessage());
            return false;
        }
        return true;
    }
}

class BimMsgList extends BimData implements Iterator<BimMsg>{
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_HISTORY = 1;

    private BimUser user;
    private int iterateIndex = 0;

    private List<BimMsg> msgList = new ArrayList<>();

    BimMsgList(){}

    BimMsgList(BimUser user) {
        this.user = user;
    }

    public BimMsgList(List<BimMsg> msgList) {
        this.msgList = msgList;
    }

    public BimMsgList(BimUser user, List<BimMsg> msgList) {
        this.user = user;
        this.msgList = msgList;
    }

    public void setMsgList(List<BimMsg> msgList) {
        this.msgList = msgList;
    }

    public BimUser getUser() {
        return user;
    }

    public List<BimMsg> getMsgList() {
        return msgList;
    }

    void add(BimMsg msg){
        msgList.add(msg);
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        try{
            json.put("user",user.toJSONObject());
            JSONArray jsonArray = new JSONArray();
            for(BimMsg msg : msgList){
                jsonArray.put(msg.toJSONObject());
            }
            json.put("msgs",jsonArray);
        }catch (Exception e){
            Log.d(TAG, "BimMsgList.toJSONObject: " + e.getMessage());
        }
        return json;
    }

    @Override
    public Boolean fromJSONObject(JSONObject json) {
        if(user == null) user = new BimUser();
        try{
            user.fromJSONObject(json.getJSONObject("user"));
            JSONArray jsonArray = json.getJSONArray("msgs");
            msgList.clear();
            for(int i=0;i<jsonArray.length();i++){
                BimMsg msg = new BimMsg();
                msg.fromJSONObject(jsonArray.getJSONObject(i));
                msgList.add(msg);
            }
        }catch (Exception e){
            Log.d(TAG, "BimMsgList.fromJSONObject: " + e.getMessage());
            return false;
        }
        return true;
    }

    void initForDebug(){
        String[] texts = {"按时","请问请问","阿萨","等等","二娃","发给","许","打算","王企鹅","阿萨"};
        BimUser send,receive;
        send = new BimUser("Tom",123456,R.drawable.send_tmp);
        receive = new BimUser("Jerry",654321,R.drawable.receive_tmp);

        this.user = receive;

        for(int i=0;i<texts.length;i++){
            BimMsg msg = new BimMsg(send,receive,texts[i],i%2==0?BimMsg.TYPE_SEND:BimMsg.TYPE_RECEIVE);
            add(msg);
        }
    }

    void initForDebug(int flag){
        String[] texts = {"按时","请问请问","阿萨","等等","二娃","发给","许","打算","王企鹅","阿萨"};
        BimUser user1,friend;
        user1 = new BimUser("Tom",123456,R.drawable.send_tmp);
        friend = new BimUser("Jerry",654321,R.drawable.receive_tmp);
        user1.setType(BimUser.SELF);
        friend.setType(BimUser.FRIEND);

        this.user = user1;

        if(flag == TYPE_NORMAL){
            for(int i=0;i<texts.length;i++){
                BimMsg msg;
                if(i%2 == 0) msg = new BimMsg(friend,user1,texts[i],BimMsg.TYPE_SEND);
                else msg = new BimMsg(user1,friend,texts[i],BimMsg.TYPE_RECEIVE);
                add(msg);
            }
        }
        else if(flag == TYPE_HISTORY){
            String[] names = {"张二狗","张三","李四","王五","赵六","柳宗元","陶渊明","李白","杜甫","李商隐","战德臣","战神"};
            for(int i=0;i<texts.length;i++){
                friend = new BimUser(names[i],1234,R.drawable.send_tmp);
                BimMsg msg = new BimMsg(friend,user1,texts[i],BimMsg.TYPE_HISTORY);
                add(msg);
            }
        }
    }

    @Override
    public boolean hasNext() {
        return msgList.size()>iterateIndex;
    }

    @Override
    public BimMsg next() {
        return msgList.get(iterateIndex);
    }

    BimMsg get(int i){
        return msgList.get(i);
    }

    int size(){
        return msgList.size();
    }
}

class BimUserList extends BimData{
    private List<BimUser> userList = new ArrayList<>();
    private BimUser user;

    public BimUserList() {
    }

    public BimUserList(BimUser user) {
        this.user = user;
    }

    public List<BimUser> getUserList() {
        return userList;
    }

    public void setUserList(List<BimUser> userList) {
        this.userList = userList;
    }

    public BimUser getUser() {
        return user;
    }

    public void setUser(BimUser user) {
        this.user = user;
    }

    BimUser get(int i){
        return userList.get(i);
    }

    void add(BimUser user){
        userList.add(user);
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        try{
            json.put("user",user.toJSONObject());
            JSONArray jsonArray = new JSONArray();
            for(BimUser bimUser : userList){
                jsonArray.put(bimUser.toJSONObject());
            }
            json.put("friends", jsonArray);
            return json;
        }catch (Exception e){
            Log.d(TAG, "BimUserList toJSONObject: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean fromJSONObject(JSONObject json) {
        if(user == null) user = new BimUser();
        try{
            user.fromJSONObject(json.getJSONObject("user"));
            JSONArray jsonArray = json.getJSONArray("friends");
            for(int i=0;i<jsonArray.length();i++){
                BimUser bUser = new BimUser();
                bUser.fromJSONObject(jsonArray.getJSONObject(i));
                userList.add(bUser);
            }
        }catch (Exception e){
            Log.d(TAG, "BimUserList fromJSONObject: " + e.getMessage());
            return false;
        }
        return true;
    }

    void initForDebug(){
        user = new BimUser("Tom", 123456, R.drawable.send_tmp);
        String[] names = {"张二狗","张三","李四","王五","赵六","柳宗元","陶渊明","李白","杜甫","李商隐","战德臣","战神"};
        Random random = new Random();
        for(String name : names){
            BimUser friend = new BimUser(name,random.nextInt(),R.drawable.receive_tmp);
            friend.setType(BimUser.FRIEND);
            userList.add(friend);
        }
    }

    int size(){
        return userList.size();
    }
}
