package scau.zxck.entity.market;

import scau.zxck.base.dao.entity.Unique;

public class Receiver extends Unique {
    private String id;
    private String receiver_name;
    private String deliver_address;
    private String receiver_cell;
    private int receiver_sex;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
