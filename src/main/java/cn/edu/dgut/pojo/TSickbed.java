package cn.edu.dgut.pojo;

import java.util.Date;

public class TSickbed {
    private Integer id;

    private String sickbedId;

    private String type;

    private Byte isFree;

    private Date created;

    private Date updated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSickbedId() {
        return sickbedId;
    }

    public void setSickbedId(String sickbedId) {
        this.sickbedId = sickbedId == null ? null : sickbedId.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Byte getIsFree() {
        return isFree;
    }

    public void setIsFree(Byte isFree) {
        this.isFree = isFree;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}