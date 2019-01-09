package com.newcore.bmp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newcore.bmp.models.authority.models.Resource;
import com.newcore.bmp.models.authority.models.Role;
import com.newcore.bmp.models.authority.models.TreeModel;
import com.newcore.bmp.service.api.authority.RoleService;
import com.newcore.bmp.dao.api.authority.RoleDao;
import com.newcore.bmp.models.bo.RoleBo;
import com.newcore.supports.models.web.vo.QueryDt;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public boolean createRole(Role role) {
        return roleDao.createRole(role);
    }

    @Override
    public boolean updateRole(Role role) {
        return roleDao.updateRole(role);
    }

    @Override
    public boolean deleteRole(String roleId) {
        if (roleDao.selectUserRoleCount(roleId) > 0L) {
            return false;
        }
        return roleDao.deleteRole(roleId);
    }

    @Override
    public Set<String> findRoles(String branchNo, @Valid String clerkNo) {
        List<String> list = roleDao.selectRoleIds(branchNo, clerkNo);
        return new HashSet<>(list);
    }

    @Override
    public List<Role> findByGroup(@Valid String roleGroup) {
        return roleDao.findByGroup(roleGroup);
    }

//    @Override
//    public Map<String, Object> selectRole(Map<String, Object> map) {
//        
//        Role role = (Role) map.get("role");
//        QueryDt query = (QueryDt) map.get("query");
//        String order = query.getOrder();
//        Integer sortingCols = query.getSortingCols();
//        Long page = query.getPage();
//        Integer rows = query.getRows();
//        
//        StringBuilder sb = new StringBuilder();
//        List<Object> list = new ArrayList<>();
//        List<Object> listCount = new ArrayList<>();
//        if (null != role.getRoleGroup() && !"".equals(role.getRoleGroup())) {
//            sb.append(" and role_group = ? ");
//            listCount.add(role.getRoleGroup());
//        }
//
//        if (null != role.getRoleName() && !"".equals(role.getRoleName())) {
//            sb.append(" and role_name like ? ");
//            listCount.add("%" + role.getRoleName() + "%");
//        }
//
//        if (null != role.getEnableFlag() && !"".equals(role.getEnableFlag())) {
//            sb.append(" and ENABLE_FLAG = ? ");
//            listCount.add(role.getEnableFlag());    
//        }
//        list.addAll(listCount);
//        list.add(page + 1);
//        list.add(page + rows);
//        List<RoleBo> roleBos = roleDao.selectRole(sb.toString(), order, sortingCols, list);
//        List<Role> roles = new ArrayList<>();
//        
//        if(null != roleBos && !roleBos.isEmpty()){
//            for (RoleBo roleBo : roleBos) {
//                Role role1 = new Role();
//                BeanUtils.copyProperties(roleBo, role1);
//                role1.setEnableFlag(roleBo.getEnableFlag().getKey());
//                role1.setEnableFlagDesc(roleBo.getEnableFlag().getDescription());
//                role1.setRoleGroup(roleBo.getRoleGroup().getKey());
//                role1.setRoleGroupDesc(roleBo.getRoleGroup().getDescription());
//                roles.add(role1);
//            }
//        }
//        
//        Long count = roleDao.selectRoleCount(sb.toString(), listCount);
//        Map<String, Object> outMap = new HashMap<>();
//
//        outMap.put("count", count);
//        outMap.put("list", roles);
//        return outMap;
//    }

    @Override
    public List<Role> roleSelect(String clerkNo) {
        return roleDao.roleSelect(clerkNo);
    }

    @Override
    public List<TreeModel> roleResoucesTree(@Valid String roleId) {
        List<TreeModel> list = roleDao.roleResoucesTree(roleId);
        List<TreeModel> treeModels = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            TreeModel treeModel = list.get(i);
            if (null == treeModel.getPid() || "".equals(treeModel.getPid())) {
                setSubResouce(treeModel, list);
                treeModels.add(treeModel);
            }
        }
        return treeModels;
    }

    private void setSubResouce(TreeModel treeModel, List<TreeModel> treeModels) {
        for (int i = 0; i < treeModels.size(); i++) {
            if (treeModel.getId().equals(treeModels.get(i).getPid())) {
                TreeModel treeModel1 = treeModels.get(i);
                this.setSubResouce(treeModel1, treeModels);
                if (treeModel.getChildren() == null || treeModel.getChildren().isEmpty()) {
                    List<TreeModel> treeModels1 = new ArrayList<>();
                    treeModels1.add(treeModel1);
                    treeModel.setChildren(treeModels1);
                } else {
                    treeModel.getChildren().add(treeModel1);
                }
            }
        }
    }

    @Override
    public boolean addRoleResouce(Map<String, Object> map) {
        
        @SuppressWarnings("unchecked")
        List<String> resources = (List<String>) map.get("resources");
        
        String roleId = (String) map.get("roleId");
        
        List<Resource> list = roleDao.selectSysRole(resources);
        Map<String, String> mapParam = selectCheck(list);
        roleDao.addRoleResouce(mapParam, roleId);
        return true;
    }

    private Map<String, String> selectCheck(List<Resource> list){
        Map<String, String> map = new HashMap<>();
        if(null != list && !list.isEmpty()){
           for (Resource resource : list) {
               getMap(resource, map);
           }
        }
        return map;
    }
    
    private void getMap(Resource resource, Map<String, String> map){
        if(resource.getCommonly()){
            String key = resource.getPermissionSys();
            if(null == map.get(key) || "".equals(map.get(key))){
                map.put(key, "true");
            }
        }else{
            map.put(resource.getPermissionSys(), "false"); 
        }
        if(null != resource.getPermissionHtm() && !"".equals(resource.getPermissionHtm())){
            if(resource.getCommonly()){
                String key = resource.getPermissionSys() + ":" + resource.getPermissionHtm();
                if(null == map.get(key) || "".equals(map.get(key))){
                    map.put(key, "true");
                }
            }else{
                String key = resource.getPermissionSys() + ":" + resource.getPermissionHtm();
                map.put(key, "false"); 
            }
        }
        
        if(null != resource.getPermissionBtn() && !"".equals(resource.getPermissionBtn())){
            String key = resource.getPermissionSys() + ":" + resource.getPermissionHtm() + ":" + resource.getPermissionBtn();
            map.put(key, String.valueOf(resource.getCommonly()));
        }
     
    }
}
