package com.newcore.bmp.models.bo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class RoleBo implements Serializable {

    private static final long serialVersionUID = -6244698984853956289L;
    /* 角色编号 */
    private String roleId;
    /* 角色名称 */
    private String roleName;
    /* 角色组 */
    private String roleGroup;
    /* 是否可用 */
    private String enableFlag;
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

    
	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
	}
}
