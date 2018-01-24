package cn.edu.dgut.pojo;

import java.util.Date;

public class TPrescription {
    private Integer id;

    private String prescriptionId;

    private String drugData;

    private Date created;

    private Date updated;

    private String patientId;

    private String doctorId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId == null ? null : prescriptionId.trim();
    }

    public String getDrugData() {
        return drugData;
    }

    public void setDrugData(String drugData) {
        this.drugData = drugData == null ? null : drugData.trim();
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

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId == null ? null : patientId.trim();
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId == null ? null : doctorId.trim();
    }
}