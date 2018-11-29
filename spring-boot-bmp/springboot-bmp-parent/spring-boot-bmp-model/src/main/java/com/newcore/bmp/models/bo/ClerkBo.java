package com.newcore.bmp.models.bo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author lhq 操作员信息Model.
 */
@SuppressWarnings("serial")
public class ClerkBo implements Serializable {

    /* 机构代码 */
    private String branchNo;
    /* 部门号 */
    private Integer deptNo;
    /* 操作员工号 */
    private String clerkNo;
    /* 姓名 */
    private String name;
    /* 密码 */
    private String password;
    /* 加密密码的盐 */
    private String passwordSalt;
    /* 固定电话 */
    private String tel;
    /* 手机号 */
    private String mobile;
    /* 电子邮箱 */
    private String email;
    /* 是否锁定（N，Y） */
    private String lockFlag;
    /* 是否接收任务（N，Y） */
    private String acceptTaskFlag;
    /* 操作员状态 */
    private String clerkStatus;
    /* 核算单元 */
    private String centerCode;

    public String getCredentialsSalt() {
        return this.clerkNo + this.passwordSalt;
    }

    /**
     * @return the branchNo
     */
    public String getBranchNo() {
        return branchNo;
    }

    /**
     * @param branchNo
     *            the branchNo to set
     */
    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }
  
    /**
     * @return the deptNo
     */
    public Integer getDeptNo() {
        return deptNo;
    }

    /**
     * @param deptNo the deptNo to set
     */
    public void setDeptNo(Integer deptNo) {
        this.deptNo = deptNo;
    }

    /**
     * @return the clerkNo
     */
    public String getClerkNo() {
        return clerkNo;
    }

    /**
     * @param clerkNo
     *            the clerkNo to set
     */
    public void setClerkNo(String clerkNo) {
        this.clerkNo = clerkNo;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel
     *            the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile
     *            the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
   
    /**
     * @return the acceptTaskFlag
     */
    public String getAcceptTaskFlag() {
        return acceptTaskFlag;
    }

    /**
     * @param acceptTaskFlag the acceptTaskFlag to set
     */
    public void setAcceptTaskFlag(String acceptTaskFlag) {
        this.acceptTaskFlag = acceptTaskFlag;
    }

    /**
     * @return the clerkStatus
     */
    public String getClerkStatus() {
        return clerkStatus;
    }

    /**
     * @param clerkStatus the clerkStatus to set
     */
    public void setClerkStatus(String clerkStatus) {
        this.clerkStatus = clerkStatus;
    }

    /**
     * @return the centerCode
     */
    public String getCenterCode() {
        return centerCode;
    }

    /**
     * @param centerCode
     *            the centerCode to set
     */
    public void setCenterCode(String centerCode) {
        this.centerCode = centerCode;
    }

    /**
     * @return the passwordSalt
     */
    public String getPasswordSalt() {
        return passwordSalt;
    }

    /**
     * @param passwordSalt
     *            the passwordSalt to set
     */
    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    /**
     * @return the lockFlag
     */
    public String getLockFlag() {
        return lockFlag;
    }

    /**
     * @param lockFlag the lockFlag to set
     */
    public void setLockFlag(String lockFlag) {
        this.lockFlag = lockFlag;
    }

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
	}
    
}