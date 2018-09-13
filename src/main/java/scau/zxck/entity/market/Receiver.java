package scau.zxck.entity.market;

import scau.zxck.base.dao.entity.Unique;
import scau.zxck.base.dao.annotation.Column;
import scau.zxck.base.dao.annotation.Table;

@Table(name="receiver_info")
public class Receiver extends Unique {
    @Column(name="receiver_name")
    private String receiver_name;
    @Column(name=" deliver_address")
    private String deliver_address;
    @Column(name="receiver_cell")
    private String receiver_cell;
    @Column(name="receiver_sex")
    private int receiver_sex;

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getDeliver_address() {
        return deliver_address;
    }

    public void setDeliver_address(String deliver_address) {
        this.deliver_address = deliver_address;
    }

    public String getReceiver_cell() {
        return receiver_cell;
    }

    public void setReceiver_cell(String receiver_cell) {
        this.receiver_cell = receiver_cell;
    }

    public int getReceiver_sex() {
        return receiver_sex;
    }

    public void setReceiver_sex(int receiver_sex) {
        this.receiver_sex = receiver_sex;
    }
}
