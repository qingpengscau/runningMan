package scau.zxck.entity.market;

import org.springframework.context.annotation.Bean;
import scau.zxck.base.dao.annotation.Table;
import scau.zxck.base.dao.entity.Unique;
@Table(name = "user_info")
public class User extends Unique {
    private String user_name;
    private String user_password;
    private int user_sex;
    private String location;
    private int gold;
    private String user_cell;
    private String user_regtime;
    private int user_mark;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public int getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(int user_sex) {
        this.user_sex = user_sex;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public String getUser_cell() {
        return user_cell;
    }

    public void setUser_cell(String user_cell) {
        this.user_cell = user_cell;
    }

    public String getUser_regtime() {
        return user_regtime;
    }

    public void setUser_regtime(String user_regtime) {
        this.user_regtime = user_regtime;
    }

    public int getUser_mark() {
        return user_mark;
    }

    public void setUser_mark(int user_mark) {
        this.user_mark = user_mark;
    }
}
