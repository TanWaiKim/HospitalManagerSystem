package cn.edu.dgut.pojo;

import java.util.Date;

public class TDiagnosis {
    private Long diagnosisId;

    private String symptom;

    private String doctorId;

    private String patientId;

    private String disease;

    private String bodyStatus;

    private Date created;

    private Date updated;

    private TPatient patient;
    
    public TPatient getPatient() {
		return patient;
	}

	public void setPatient(TPatient patient) {
		this.patient = patient;
	}

	public Long getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(Long diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom == null ? null : symptom.trim();
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId == null ? null : doctorId.trim();
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId == null ? null : patientId.trim();
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease == null ? null : disease.trim();
    }

    public String getBodyStatus() {
        return bodyStatus;
    }

    public void setBodyStatus(String bodyStatus) {
        this.bodyStatus = bodyStatus == null ? null : bodyStatus.trim();
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
		return "TDiagnosis [diagnosisId=" + diagnosisId + ", symptom=" + symptom + ", doctorId=" + doctorId
				+ ", patientId=" + patientId + ", disease=" + disease + ", bodyStatus=" + bodyStatus + ", created="
				+ created + ", updated=" + updated + ", patient=" + patient + "]";
	}
    
}