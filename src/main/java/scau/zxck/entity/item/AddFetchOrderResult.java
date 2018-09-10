package scau.zxck.entity.item;

import scau.zxck.entity.market.Fetchorder;

public class AddFetchOrderResult {
     private int status;

     private Fetchorder data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Fetchorder getData() {
        return data;
    }

    public void setData(Fetchorder data) {
        this.data = data;
    }
}
