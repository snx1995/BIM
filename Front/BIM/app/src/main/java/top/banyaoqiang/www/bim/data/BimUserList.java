package top.banyaoqiang.www.bim.data;

import java.util.ArrayList;
import java.util.List;

public class BimUserList extends BimData {
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

    public BimUser get(int i){
        return userList.get(i);
    }

    public void add(BimUser user){
        userList.add(user);
    }

    public int size() {
        return userList.size();
    }
}
