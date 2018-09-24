package cn.edu.dgut.common.vo;

import java.math.BigDecimal;
import java.sql.Time;

/**
 * @author TanWaiKim
 * @time 2018年5月9日 上午9:44:35
 * @version 1.0
 */
public class SaleCount {
	private String time;
	private Integer num;
	private BigDecimal price;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
}
