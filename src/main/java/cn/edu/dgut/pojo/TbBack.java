package cn.edu.dgut.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class TbBack {
    private Integer id;

    private String backType;

    private String backObject;

    private Integer drugId;

    private String batchNo;

    private Integer backSum;

    private BigDecimal backTotalPrice;

    private String backReason;

    private String operator;

    private Date createTime;

    private Date updateTime;
    
    private TbDrug drug;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBackType() {
        return backType;
    }

    public void setBackType(String backType) {
        this.backType = backType == null ? null : backType.trim();
    }

    public String getBackObject() {
        return backObject;
    }

    public void setBackObject(String backObject) {
        this.backObject = backObject == null ? null : backObject.trim();
    }

    public Integer getDrugId() {
        return drugId;
    }

    public void setDrugId(Integer drugId) {
        this.drugId = drugId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }

    public Integer getBackSum() {
        return backSum;
    }

    public void setBackSum(Integer backSum) {
        this.backSum = backSum;
    }

    public BigDecimal getBackTotalPrice() {
        return backTotalPrice;
    }

    public void setBackTotalPrice(BigDecimal backTotalPrice) {
        this.backTotalPrice = backTotalPrice;
    }

    public String getBackReason() {
        return backReason;
    }

    public void setBackReason(String backReason) {
        this.backReason = backReason == null ? null : backReason.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
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

	public TbDrug getDrug() {
		return drug;
	}

	public void setDrug(TbDrug drug) {
		this.drug = drug;
	}
}