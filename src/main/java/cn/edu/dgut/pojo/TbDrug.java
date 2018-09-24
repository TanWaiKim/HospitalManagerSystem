package cn.edu.dgut.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class TbDrug {
    private Integer id;

    private Integer drugtypeId;

    private String drugName;

    private String drugNo;

    private String purpose;

    private String spec;

    private String unit;

    private String uneffect;

    private BigDecimal purchasePrice;

    private BigDecimal salePrice;

    private String produceTime;

    private String validTime;

    private Date createTime;

    private Date updateTime;
    
    private TbDrugtype drugtype;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDrugtypeId() {
        return drugtypeId;
    }

    public void setDrugtypeId(Integer drugtypeId) {
        this.drugtypeId = drugtypeId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName == null ? null : drugName.trim();
    }

    public String getDrugNo() {
        return drugNo;
    }

    public void setDrugNo(String drugNo) {
        this.drugNo = drugNo == null ? null : drugNo.trim();
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getUneffect() {
        return uneffect;
    }

    public void setUneffect(String uneffect) {
        this.uneffect = uneffect == null ? null : uneffect.trim();
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public String getProduceTime() {
        return produceTime;
    }

    public void setProduceTime(String produceTime) {
        this.produceTime = produceTime;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
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

	public TbDrugtype getDrugtype() {
		return drugtype;
	}

	public void setDrugtype(TbDrugtype drugtype) {
		this.drugtype = drugtype;
	}

}