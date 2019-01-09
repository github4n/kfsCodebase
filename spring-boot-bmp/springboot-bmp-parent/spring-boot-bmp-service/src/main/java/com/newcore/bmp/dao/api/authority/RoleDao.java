package com.newcore.bmp.dao.api.authority;

import java.util.List;
import java.util.Map;

import com.newcore.bmp.models.authority.models.Resource;
import com.newcore.bmp.models.authority.models.Role;
import com.newcore.bmp.models.authority.models.TreeModel;
import com.newcore.bmp.models.bo.RoleBo;

public interface RoleDao {

    public boolean createRole(Role role);

    public boolean updateRole(Role role);

    public boolean deleteRole(String roleId);

    public Role findOne(String roleId);

    public List<Role> findAll();

    public List<Role> findByGroup(String roleGroup);

    public List<RoleBo> selectRole(String wheres, String order, Integer sortingCols, List<Object> list);

    public List<String> selectRoleIds(String branchNo, String clerkNo);

    public Long selectRoleCount(String wheres, List<Object> list);

    public Long selectUserRoleCount(String roleId);

    public List<Role> roleSelect(String clertNo);

    public List<TreeModel> roleResoucesTree(String roleId);

    public void addRoleResouce(Map<String, String> map, String roleId);

    public List<Resource> selectSysRole(List<String> resources);
}
