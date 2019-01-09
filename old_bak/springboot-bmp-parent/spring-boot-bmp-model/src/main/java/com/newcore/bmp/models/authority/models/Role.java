package com.newcore.bmp.models.authority.models;

import java.io.Serializable;

public class Role implements Serializable {

    private static final long serialVersionUID = -6244698984853956289L;
    /* 角色编号 */
    private String roleId;
    /* 角色名称 */
    private String roleName;
    /* 角色组 */
    private String roleGroup;
    /* 角色组 描述 */
    private String roleGroupDesc;
    /* 是否可用 */
    private String enableFlag;
    /* 是否可用 描述 */
    private String enableFlagDesc;
    /* 角色所属用户（授权是专用） */
    private String clerkNo;

    /**
     * @return the roleId
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     *            the roleId to set
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * @return the roleName
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @param roleName
     *            the roleName to set
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
   
    /**
     * @return the roleGroup
     */
    public String getRoleGroup() {
        return roleGroup;
    }

    /**
     * @param roleGroup the roleGroup to set
     */
    public void setRoleGroup(String roleGroup) {
        this.roleGroup = roleGroup;
    }

    /**
     * @return the roleGroupDesc
     */
    public String getRoleGroupDesc() {
        return roleGroupDesc;
    }

    /**
     * @param roleGroupDesc the roleGroupDesc to set
     */
    public void setRoleGroupDesc(String roleGroupDesc) {
        this.roleGroupDesc = roleGroupDesc;
    }

    /**
     * @return the enableFlag
     */
    public String getEnableFlag() {
        return enableFlag;
    }

    /**
     * @param enableFlag the enableFlag to set
     */
    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    /**
     * @return the enableFlagDesc
     */
    public String getEnableFlagDesc() {
        return enableFlagDesc;
    }

    /**
     * @param enableFlagDesc the enableFlagDesc to set
     */
    public void setEnableFlagDesc(String enableFlagDesc) {
        this.enableFlagDesc = enableFlagDesc;
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

}
