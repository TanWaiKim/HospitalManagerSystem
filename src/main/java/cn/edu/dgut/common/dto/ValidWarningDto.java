package cn.edu.dgut.common.dto;

/**
 * @author TanWaiKim
 * @time 2018年5月4日 下午6:44:31
 * @version 1.0
 */
public class ValidWarningDto {
	private String warehouseName;
	private String drugtypeName;
	private String drugName;
	private String drugNo;
	private Integer drugId;
	private String produceTime;
	private String validTime;
	private String waringMsg;
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getDrugtypeName() {
		return drugtypeName;
	}
	public void setDrugtypeName(String drugtypeName) {
		this.drugtypeName = drugtypeName;
	}
	public String getDrugName() {
		return drugName;
	}
	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}
	public String getDrugNo() {
		return drugNo;
	}
	public void setDrugNo(String drugNo) {
		this.drugNo = drugNo;
	}
	public Integer getDrugId() {
		return drugId;
	}
	public void setDrugId(Integer drugId) {
		this.drugId = drugId;
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
	public String getWaringMsg() {
		return waringMsg;
	}
	public void setWaringMsg(String waringMsg) {
		this.waringMsg = waringMsg;
	}
	
	
}
