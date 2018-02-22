package cn.edu.dgut.pojo;

import java.util.Date;
import java.util.List;

public class TPatient {
    private Long id;

    private String patientId;

    private String name;

    private String sex;

    private String address;

    private Integer age;

    private String isFinished;

    private String mcName;

    private String personType;

    private String phone;

    private String loginName;

    private String loginPassword;

    private Date created;

    private Date updated;
  
    private TDiagnosis diagnosis;
    
    private TStayHospital stayHospital;
    
	public TDiagnosis getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(TDiagnosis diagnosis) {
		this.diagnosis = diagnosis;
	}

	public TStayHospital getStayHospital() {
		return stayHospital;
	}

	public void setStayHospital(TStayHospital stayHospital) {
		this.stayHospital = stayHospital;
	}

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIsFinished() {
		return isFinished;
	}

	public void setIsFinished(String isFinished) {
		this.isFinished = isFinished;
	}

	public String getMcName() {
        return mcName;
    }

    public void setMcName(String mcName) {
        this.mcName = mcName == null ? null : mcName.trim();
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType == null ? null : personType.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword == null ? null : loginPassword.trim();
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
		return "TPatient [id=" + id + ", patientId=" + patientId + ", name=" + name + ", sex=" + sex + ", address="
				+ address + ", age=" + age + ", isFinished=" + isFinished + ", mcName=" + mcName + ", personType="
				+ personType + ", phone=" + phone + ", loginName=" + loginName + ", loginPassword=" + loginPassword
				+ ", created=" + created + ", updated=" + updated + ", diagnosis=" + diagnosis + ", stayHospital="
				+ stayHospital + "]";
	}

}