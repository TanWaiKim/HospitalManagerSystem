package cn.edu.dgut.pojo;

import java.util.Date;

public class TbDrugtype {
    private Integer id;

    private String drugtypeName;

    private String remarks;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDrugtypeName() {
        return drugtypeName;
    }

    public void setDrugtypeName(String drugtypeName) {
        this.drugtypeName = drugtypeName == null ? null : drugtypeName.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}