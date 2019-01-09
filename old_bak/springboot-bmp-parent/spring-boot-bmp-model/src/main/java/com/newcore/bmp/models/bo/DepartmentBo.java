package com.newcore.bmp.models.bo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DepartmentBo implements Serializable {

    private static final long serialVersionUID = -6348095424457124037L;
    /* 机构代码 */
    private String branchNo;
    /* 部门号 */
    private Integer deptNo;
    /* 部门名称 */
    private String deptName;
    /* 部门类别代码 */
    private String deptClass;
    /* 部门状态 */
    private String deptStatus;

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
     * @return the deptName
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * @param deptName
     *            the deptName to set
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /**
     * @return the deptClass
     */
    public String getDeptClass() {
        return deptClass;
    }

    /**
     * @param deptClass the deptClass to set
     */
    public void setDeptClass(String deptClass) {
        this.deptClass = deptClass;
    }

    /**
     * @return the deptStatus
     */
    public String getDeptStatus() {
        return deptStatus;
    }

    /**
     * @param deptStatus the deptStatus to set
     */
    public void setDeptStatus(String deptStatus) {
        this.deptStatus = deptStatus;
    }

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
	}
    
}
