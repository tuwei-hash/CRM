package com.bjpowernode.crm.utils;

import java.util.List;

public class PaginationVo<T> {

    private List<T> dataList;

    int total;

    public PaginationVo() {
    }

    public PaginationVo(List<T> dataList, int total) {
        this.dataList = dataList;
        this.total = total;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
