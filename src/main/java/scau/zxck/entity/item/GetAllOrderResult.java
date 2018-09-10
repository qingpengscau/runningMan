package scau.zxck.entity.item;

import scau.zxck.entity.market.Fetchorder;

import java.util.ArrayList;
import java.util.List;

public class GetAllOrderResult {
    private int status;

    private List<Fetchorder> data=new ArrayList<>();

     public  void addFetchOrder(Fetchorder fetchorder)
     {
         data.add(fetchorder);
     }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Fetchorder> getData() {
        return data;
    }

    public void setData(List<Fetchorder> data) {
        this.data = data;
    }
}
