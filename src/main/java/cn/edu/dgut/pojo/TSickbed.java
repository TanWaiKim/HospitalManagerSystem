package cn.edu.dgut.pojo;

import java.util.Date;

public class TSickbed {
    private Long id;

    private String isFree;

    private Date created;

    private Date updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

    public String getIsFree() {
		return isFree;
	}

	public void setIsFree(String isFree) {
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

	@Override
	public String toString() {
		return "TSickbed [id=" + id + ", isFree=" + isFree + ", created=" + created + ", updated=" + updated + "]";
	}
    
    
}