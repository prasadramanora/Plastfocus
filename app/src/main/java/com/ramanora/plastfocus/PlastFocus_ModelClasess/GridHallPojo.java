package com.ramanora.plastfocus.PlastFocus_ModelClasess;

import java.io.Serializable;

/**
 * Created by Owner on 09/10/2017.
 */

public class GridHallPojo implements Serializable {

    private String mtxt;
    private int mImg;

    public GridHallPojo(String mtxt, int mImg) {
        this.mtxt = mtxt;
        this.mImg = mImg;
    }

    public GridHallPojo()
    {}

    public String getMtxt() {
        return mtxt;
    }

    public void setMtxt(String mtxt) {
        this.mtxt = mtxt;
    }

    public int getmImg() {
        return mImg;
    }

    public void setmImg(int mImg) {
        this.mImg = mImg;
    }
}
