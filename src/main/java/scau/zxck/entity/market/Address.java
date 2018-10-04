package scau.zxck.entity.market;

import scau.zxck.base.dao.entity.Unique;
import scau.zxck.base.dao.annotation.Column;
import scau.zxck.base.dao.annotation.Table;

@Table(name="address_info")
public class Address extends Unique {
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "cell")
    private String cell;
    @Column(name = "sex")
    private int sex;
    @Column(name = "user_id")
    private String user_id;
    @Column(name = "area")
    private String area;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}