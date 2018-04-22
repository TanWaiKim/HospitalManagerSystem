package cn.edu.dgut.pojo;

//处理密码修改的类
public class Password {
	private String mpass;
	private String newpass;
	private String renewpass;
	public String getMpass() {
		return mpass;
	}
	public void setMpass(String mpass) {
		this.mpass = mpass;
	}
	public String getNewpass() {
		return newpass;
	}
	public void setNewpass(String newpass) {
		this.newpass = newpass;
	}
	public String getRenewpass() {
		return renewpass;
	}
	public void setRenewpass(String renewpass) {
		this.renewpass = renewpass;
	}
	@Override
	public String toString() {
		return "Password [mpass=" + mpass + ", newpass=" + newpass + ", renewpass=" + renewpass + "]";
	}
	
}
