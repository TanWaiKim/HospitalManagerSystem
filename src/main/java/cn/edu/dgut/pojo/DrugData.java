package cn.edu.dgut.pojo;

//药品数据处理类
public class DrugData {
	private String drugName;
	private String drugNum;
	private String unit;
	private String drugUsage;
	public String getDrugName() {
		return drugName;
	}
	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}
	public String getDrugNum() {
		return drugNum;
	}
	public void setDrugNum(String drugNum) {
		this.drugNum = drugNum;
	}
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getDrugUsage() {
		return drugUsage;
	}
	public void setDrugUsage(String drugUsage) {
		this.drugUsage = drugUsage;
	}
	@Override
	public String toString() {
		return "DrugData [drugName=" + drugName + ", drugNum=" + drugNum + ", drugUsage=" + drugUsage + "]";
	}
	
}
