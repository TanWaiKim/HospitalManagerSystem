package cn.edu.dgut.pojo;

import java.util.Date;

public class TStayHospital {
    private Long id;

    private String patientId;

    private Long sickbedId;

    private Date created;

    private Date updated;
    
    private TPatient patient;
    
    private TSickbed sickbed;

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId == null ? null : patientId.trim();
    }

    public Long getSickbedId() {
        return sickbedId;
    }

    public void setSickbedId(Long sickbedId) {
        this.sickbedId = sickbedId;
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

	public TPatient getPatient() {
		return patient;
	}

	public void setPatient(TPatient patient) {
		this.patient = patient;
	}

	public TSickbed getSickbed() {
		return sickbed;
	}

	public void setSickbed(TSickbed sickbed) {
		this.sickbed = sickbed;
	}

	@Override
	public String toString() {
		return "TStayHospital [id=" + id + ", patientId=" + patientId + ", sickbedId=" + sickbedId + ", created="
				+ created + ", updated=" + updated + ", patient=" + patient + ", sickbed=" + sickbed + "]";
	}


    
}