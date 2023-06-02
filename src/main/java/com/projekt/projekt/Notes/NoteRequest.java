package com.projekt.projekt.Notes;
public class NoteRequest {
    private String sortBy;
    private String pageSize;
    private String page;
    private String sortDir;
    private String startDate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    private String endDate;
    private String[] selectedCategory;



    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String [] getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String [] selectedCategory) {
        this.selectedCategory = selectedCategory;
    }
}
