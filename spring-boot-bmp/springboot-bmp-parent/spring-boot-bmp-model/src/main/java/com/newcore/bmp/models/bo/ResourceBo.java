package com.newcore.bmp.models.bo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ResourceBo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /* 资源ID */
    private String resourceId;
    /* 资源父ID */
    private String resourceParentId;
    /* 新一代系统平台代码 */
    private String sysCode;
    /* 图标类型 */
    private String iconType;
    /* 资源名称 */
    private String resourceName;
    /* 资源模块类型 */
    private String resourceModType;
    /* 资源类型 */
    private String resourceType;
    /* 资源地址 */
    private String resourceUrl;
    /* 权限系统级字符串 */
    private String permissionSys;
    /* 权限页面级级字符串 */
    private String permissionHtm;
    /* 权限按钮级字符串 */
    private String permissionBtn;
    /* 排序字段 */
    private Integer orderId;
    /* 常用菜单 */
    private boolean commonly;

    /**
     * @return the resourceId
     */
    public String getResourceId() {
        return resourceId;
    }

    /**
     * @param resourceId
     *            the resourceId to set
     */
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * @return the resourceParentId
     */
    public String getResourceParentId() {
        return resourceParentId;
    }

    /**
     * @param resourceParentId
     *            the resourceParentId to set
     */
    public void setResourceParentId(String resourceParentId) {
        this.resourceParentId = resourceParentId;
    }
    
    /**
     * @return the sysCode
     */
    public String getSysCode() {
        return sysCode;
    }

    /**
     * @param sysCode the sysCode to set
     */
    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    /**
     * @return the iconType
     */
    public String getIconType() {
        return iconType;
    }

    /**
     * @param iconType
     *            the iconType to set
     */
    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    /**
     * @return the resourceName
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * @param resourceName
     *            the resourceName to set
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * @return the resourceModType
     */
    public String getResourceModType() {
        return resourceModType;
    }

    /**
     * @param resourceModType
     *            the resourceModType to set
     */
    public void setResourceModType(String resourceModType) {
        this.resourceModType = resourceModType;
    }

    /**
     * @return the resourceType
     */
    public String getResourceType() {
        return resourceType;
    }

    /**
     * @param resourceType
     *            the resourceType to set
     */
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * @return the resourceUrl
     */
    public String getResourceUrl() {
        return resourceUrl;
    }

    /**
     * @param resourceUrl
     *            the resourceUrl to set
     */
    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }
    /**
     * @return the permissionSys
     */
    public String getPermissionSys() {
        return permissionSys;
    }

    /**
     * @param permissionSys the permissionSys to set
     */
    public void setPermissionSys(String permissionSys) {
        this.permissionSys = permissionSys;
    }

    /**
     * @return the permissionHtm
     */
    public String getPermissionHtm() {
        return permissionHtm;
    }

    /**
     * @param permissionHtm the permissionHtm to set
     */
    public void setPermissionHtm(String permissionHtm) {
        this.permissionHtm = permissionHtm;
    }

    /**
     * @return the permissionBtn
     */
    public String getPermissionBtn() {
        return permissionBtn;
    }

    /**
     * @param permissionBtn the permissionBtn to set
     */
    public void setPermissionBtn(String permissionBtn) {
        this.permissionBtn = permissionBtn;
    }

    /**
     * @return the orderId
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     *            the orderId to set
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the commonly
     */
    public boolean getCommonly() {
        return commonly;
    }

    /**
     * @param commonly
     *            the commonly to set
     */
    public void setCommonly(boolean commonly) {
        this.commonly = commonly;
    }

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
	}
    
}
