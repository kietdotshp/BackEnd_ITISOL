package com.itsol.recruit_managerment.dto;

public class Metadata {

    private int limit;

    private Long total;

    private int page;

    public Metadata() {

    }

    public Metadata(int limit, Long total, int page) {
        this.limit = limit;
        this.total = total;
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}