package cn.edu.dgut.pojo;

import java.util.Date;

public class TStayHospital {
    private Integer id;

    private String patientId;

    private String sickbedId;

    private Date time;

    private Date created;

    private Date updated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId == null ? null : patientId.trim();
    }

    public String getSickbedId() {
        return sickbedId;
    }

    public void setSickbedId(String sickbedId) {
        this.sickbedId = sickbedId == null ? null : sickbedId.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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
		return "TStayHospital [id=" + id + ", patientId=" + patientId + ", sickbedId=" + sickbedId + ", time=" + time
				+ ", created=" + created + ", updated=" + updated + "]";
	}
    
    
}