package com.example.george.eatme.Area;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 2017/6/20.
 */

public class Area {
    List<String> arealist;


    public List<String> getlist(){
        arealist = new ArrayList<>();
        arealist.add("台北市");
        arealist.add("新北市");
        arealist.add("桃園市");
        arealist.add("新竹縣");
        arealist.add("苗栗縣");
        arealist.add("台中市");
        arealist.add("彰化縣");
        arealist.add("南投縣");
        arealist.add("雲林縣");
        arealist.add("嘉義縣");
        arealist.add("台南市");
        arealist.add("高雄市");
        arealist.add("屏東縣");
        arealist.add("台東縣");
        arealist.add("花蓮縣");
        arealist.add("宜蘭縣");
        return  arealist;
    }

}
