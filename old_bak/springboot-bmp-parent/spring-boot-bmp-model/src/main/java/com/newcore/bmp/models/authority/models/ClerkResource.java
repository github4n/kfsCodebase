package com.newcore.bmp.models.authority.models;

import java.io.Serializable;
import java.util.List;

public class ClerkResource implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6533841429254848147L;
    /* 操作员工号 */
    private String clerkNo;
    /* 操作员姓名 */
    private String name;
    /* 机构代码 */
    private String branchNo;
    /* 机构名称 */
    private String branchName;
    /* 是否接收任务（N，Y） */
    private String acceptTaskFlag;
    /* 资源信息 */
    private List<Resource> resources;

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
     * @return the branchName
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * @param branchName
     *            the branchName to set
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    /**
     * @return the resources
     */
    public List<Resource> getResources() {
        return resources;
    }

    /**
     * @param resources
     *            the resources to set
     */
    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    /**
     * @return the acceptTaskFlag
     */
    public String getAcceptTaskFlag() {
        return acceptTaskFlag;
    }

    /**
     * @param acceptTaskFlag
     *            the acceptTaskFlag to set
     */
    public void setAcceptTaskFlag(String acceptTaskFlag) {
        this.acceptTaskFlag = acceptTaskFlag;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ClerkResource [clerkNo=" + clerkNo + ", name=" + name + ", branchNo=" + branchNo + ", branchName="
                + branchName + ", acceptTaskFlag=" + acceptTaskFlag + ", resources=" + resources + "]";
    }
}
