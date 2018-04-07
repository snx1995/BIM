package top.banyaoqiang.www.bim.data;

import org.json.JSONObject;

import java.net.Socket;
import java.util.Date;

import top.banyaoqiang.www.bim.exception.MethodNotImplementException;

public class BimUser extends BimData {
    public static final int TYPE_SELF = 0;
    public static final int TYPE_FRIEND = 1;
    public static final int TYPE_STRANGER = 2;

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
    private String intro;
    private Date birthday;

    // TODO should improve this for more efficient.
    public BimUser() {}

    @Override
    public Object toJsonObject() throws MethodNotImplementException {
        return super.toJsonObject();
    }

    @Override
    public void fromJsonObject(JSONObject json) throws MethodNotImplementException {
        super.fromJsonObject(json);
    }

    public String stringId() {
        return id + "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
