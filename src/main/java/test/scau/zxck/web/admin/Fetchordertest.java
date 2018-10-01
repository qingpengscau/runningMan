package test.scau.zxck.web.admin;

import scau.zxck.base.dao.annotation.Column;
import scau.zxck.base.dao.annotation.Table;
import scau.zxck.base.dao.entity.Unique;

@Table(name = "fetch_order_info")
public class Fetchordertest extends Unique {
    @Column(name = "release_man_id")
    private String release_man_id;
    @Column(name = "execute_man_id")
    private String execute_man_id;
    @Column(name = "receive_man_id")
    private String receive_man_id;
    @Column(name = "release_time")
    private String release_time;
    @Column(name = "departure")
    private String departure;
    @Column(name = "destination")
    private String destination;
    @Column(name = "weight")
    private String weight;
    @Column(name = "type")
    private int type;
    @Column(name = "prewait_time")
    private int prewait_time;
    @Column(name = "express_company")
    private String express_company;
    @Column(name = "order_start_time")
    private String order_start_time;
    @Column(name = "order_finish_time")
    private String order_finish_time;
    @Column(name = "fee")
    private double fee;
    @Column(name = "issue_descri")
    private String issue_descri;
    @Column(name = "status")
    private int status;
    @Column(name = "comment")
    private String comment;
    @Column(name = "remark")
    private int remark;



    public String getRelease_man_id() {
        return release_man_id;
    }

    public void setRelease_man_id(String release_man_id) {
        this.release_man_id = release_man_id;
    }

    public String getExecute_man_id() {
        return execute_man_id;
    }

    public void setExecute_man_id(String execute_man_id) {
        this.execute_man_id = execute_man_id;
    }

    public String getReceive_man_id() {
        return receive_man_id;
    }

    public void setReceive_man_id(String receive_man_id) {
        this.receive_man_id = receive_man_id;
    }

    public String getRelease_time() {
        return release_time;
    }

    public void setRelease_time(String release_time) {
        this.release_time = release_time;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPrewait_time() {
        return prewait_time;
    }

    public void setPrewait_time(int prewait_time) {
        this.prewait_time = prewait_time;
    }

    public String getExpress_company() {
        return express_company;
    }

    public void setExpress_company(String express_company) {
        this.express_company = express_company;
    }

    public String getOrder_start_time() {
        return order_start_time;
    }

    public void setOrder_start_time(String order_start_time) {
        this.order_start_time = order_start_time;
    }

    public String getOrder_finish_time() {
        return order_finish_time;
    }

    public void setOrder_finish_time(String order_finish_time) {
        this.order_finish_time = order_finish_time;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getIssue_descri() {
        return issue_descri;
    }

    public void setIssue_descri(String issue_descri) {
        this.issue_descri = issue_descri;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRemark() {
        return remark;
    }

    public void setRemark(int remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Fetchordertest{" +
                "release_man_id='" + release_man_id + '\'' +
                ", execute_man_id='" + execute_man_id + '\'' +
                ", receive_man_id='" + receive_man_id + '\'' +
                ", release_time='" + release_time + '\'' +
                ", departure='" + departure + '\'' +
                ", destination='" + destination + '\'' +
                ", weight='" + weight + '\'' +
                ", type=" + type +
                ", prewait_time=" + prewait_time +
                ", express_company='" + express_company + '\'' +
                ", order_start_time='" + order_start_time + '\'' +
                ", order_finish_time='" + order_finish_time + '\'' +
                ", fee=" + fee +
                ", issue_descri='" + issue_descri + '\'' +
                ", status=" + status +
                ", comment='" + comment + '\'' +
                ", remark=" + remark +
                '}';
    }
}
