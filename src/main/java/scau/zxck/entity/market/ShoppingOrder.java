package scau.zxck.entity.market;

import scau.zxck.base.dao.annotation.Column;
import scau.zxck.base.dao.annotation.Table;
import scau.zxck.base.dao.entity.Unique;

@Table(name = "shopping_order_info")
public class ShoppingOrder extends Unique {
    /*
    * `id` varchar(255) NOT NULL,
  `release_man_id` varchar(50) CHARACTER SET latin1 NOT NULL,
  `execute_man_id` varchar(50) CHARACTER SET latin1 DEFAULT NULL COMMENT '执行人，与用户表主键绑定',
  `release_time` varchar(20) CHARACTER SET latin1 NOT NULL COMMENT '订单发布时间',
  `shopping_address` varchar(255) NOT NULL,
  `destination_id` varchar(50) CHARACTER SET latin1 NOT NULL COMMENT '目的地，从address表中选取',
  `pick_time` varchar(20) NOT NULL,
  `prewait_time` varchar(20) NOT NULL,
  `comment` varchar(255) CHARACTER SET latin1 DEFAULT NULL COMMENT '订单备注',
  `price` double(10,0) NOT NULL,
  `order_start_time` varchar(20) CHARACTER SET latin1 DEFAULT NULL COMMENT '订单开始时间，从被接单开始算起',
  `order_finish_time` varchar(20) CHARACTER SET latin1 DEFAULT NULL COMMENT '订单完成时间',
  `fee` double(10,0) NOT NULL COMMENT '所需费用',
  `status` int(1) NOT NULL COMMENT '用来标记该订单是否被完成，0仅发布未有人接单，1已被接单但未完成，2已完成',
  `commodity` varchar(255) DEFAULT NULL,*/
    @Column(name="release_man_id")
    private String release_man_id;

    @Column(name="execute_man_id")
    private String execute_man_id;

    @Column(name="release_time")
    private String release_time;

    @Column(name="shopping_address")
    private String shopping_address;

    @Column(name="destination_id")
    private String destination_id;

    @Column(name="prewait_time")
    private String prewait_time;

    @Column(name="order_start_time")
    private String order_start_time;

    @Column(name="order_finish_time")
    private String order_finish_time;

    @Column(name="price")
    private double price;

    @Column(name="fee")
    private double fee;



    @Column(name="comment")
    private String comment;

    @Column(name="pick_time")

    private String pick_time;

    @Column(name=" commodity")
    private String  commodity;
    @Column(name = "state")
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

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

    public String getRelease_time() {
        return release_time;
    }

    public void setRelease_time(String release_time) {
        this.release_time = release_time;
    }

    public String getShopping_address() {
        return shopping_address;
    }

    public void setShopping_address(String shopping_address) {
        this.shopping_address = shopping_address;
    }

    public String getDestination_id() {
        return destination_id;
    }

    public void setDestination_id(String destination_id) {
        this.destination_id = destination_id;
    }


    public String getOrder_start_time() {
        return order_start_time;
    }

    public String getPrewait_time() {
        return prewait_time;
    }

    public void setPrewait_time(String prewait_time) {
        this.prewait_time = prewait_time;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPick_time() {
        return pick_time;
    }

    public void setPick_time(String pick_time) {
        this.pick_time = pick_time;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }
}
