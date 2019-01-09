package com.newcore.bmp.models.authority.models;

import java.io.Serializable;
import java.util.List;

public class TreeModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7048297326443176276L;

    private String id;
    private String pid;
    private String name;
    private boolean open = true;
    private String path;
    private boolean checked;
    private List<TreeModel> children;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
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
     * @return the open
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * @param open
     *            the open to set
     */
    public void setOpen(boolean open) {
        this.open = open;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     *            the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the checked
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * @param checked
     *            the checked to set
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    /**
     * @return the children
     */
    public List<TreeModel> getChildren() {
        return children;
    }

    /**
     * @param children
     *            the children to set
     */
    public void setChildren(List<TreeModel> children) {
        this.children = children;
    }

    /**
     * @return the pid
     */
    public String getPid() {
        return pid;
    }

    /**
     * @param pid
     *            the pid to set
     */
    public void setPid(String pid) {
        this.pid = pid;
    }
}
