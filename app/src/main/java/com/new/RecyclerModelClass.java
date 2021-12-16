package com.example;

public class RecyclerModelClass {
    private String itemTitle;
    private String itemHour;
    RecyclerModelClass(String itemTitleCons,String itemHourCons){
        itemTitle = itemTitleCons;
        itemHour=itemHourCons;
    }
    public String getItemTitle() {
        return itemTitle;
    }

    public String getItemHour() {
        return itemHour;
    }
}
