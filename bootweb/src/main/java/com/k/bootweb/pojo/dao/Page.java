package com.k.bootweb.pojo.dao;


import io.swagger.annotations.ApiModelProperty;

public class Page {
    // 当前页码
    @ApiModelProperty(value = "当前页码",position = 1)
    private int current;
    // 显示上限
    @ApiModelProperty(value = "显示上限",position = 2)
    private int limit ;
    // 数据总数(用于计算总页数)
    @ApiModelProperty(value = "数据总数(用于计算总页数)",position = 3)
    private int rows;
    // 查询路径(用于复用分页链接)
    @ApiModelProperty(value = "查询路径(用于复用分页链接)",position = 4)
    private String path;
    @ApiModelProperty(value = "指定页的第一条",position = 5)
    private int offset;
    @ApiModelProperty(value = "页总数",position = 6)
    private int total;

    public Page(){}

    public Page(int current,int limit){
        this.current=current;
        this.limit=limit;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if (current >= 1) {
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit >= 1 && limit <= 100) {
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if (rows >= 0) {
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取当前页的起始行
     *
     * @return
     */
    public int getOffset() {
        // current * limit - limit
        return (current - 1) * limit;
    }

    /**
     * 获取总页数
     *
     * @return
     */
    public int getTotal() {
        if(limit==0)
            return 0;
        return (int)Math.ceil(rows*1.0/limit);
    }
}
