package com.newcore.bmp.dao.impl.authority;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import com.newcore.bmp.models.authority.constants.Constants;
import com.newcore.bmp.models.authority.models.Resource;
import com.newcore.bmp.dao.api.authority.ResourceDao;

@Repository("resourceDao")
public class ResourceDaoImpl implements ResourceDao {

    @Autowired
    private JdbcOperations jdbcTemplate;

    @Override
    public Resource createResource(final Resource resource) {

        final String sql = "insert into a_resource(pid, icon, name, type, url, permission_sys, permission_htm, permission_btn) values(?,?,?,?,?,?,?,?)";

        jdbcTemplate.update(sql, resource.getResourceParentId(), resource.getIconType(), resource.getResourceName(),
                resource.getResourceType(), resource.getResourceUrl(), resource.getPermissionSys(), resource.getPermissionHtm(), resource.getPermissionBtn());

        return resource;
    }

    @Override
    public Resource updateResource(Resource resource) {
        final String sql = "update a_resource set pid=?,icon=?,name=?, type=?, url=?, permission_sys=?, permission_htm=?, permission_btn=? where resource_id=?";
        jdbcTemplate.update(sql, resource.getResourceParentId(), resource.getIconType(), resource.getResourceName(),
                resource.getResourceType(), resource.getResourceUrl(), resource.getPermissionSys(), resource.getPermissionHtm(), resource.getPermissionBtn(),
                resource.getResourceId());
        return resource;
    }

    @Override
    public void deleteResource(String resourceId) {
        final String deleteSelfSql = "delete from a_resource where resource_id=?";
        jdbcTemplate.update(deleteSelfSql, resourceId);
    }

    @Override
    public Resource findOne(String resourceId) {
        final String sql = "select resource_id,pid,icon, name, type, url, permission,commonly from a_resource where resource_id=?";
        List<Resource> resourceList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resource.class), resourceId);
        if (null == resourceList || resourceList.isEmpty()) {
            return null;
        }
        return resourceList.get(0);
    }

    @Override
    public List<Resource> findAll() {
        final String sql = "SELECT * FROM A_RESOURCE ORDER BY CONCAT(RESOURCE_PARENT_ID, RESOURCE_ID) ASC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resource.class));
    }

    @Override
    public List<Resource> comMenu(String clerkNo) {
        final String sql = "SELECT A.*, NVL2(P.CLERK_NO, 1, 0) AS COMMONLY FROM (SELECT DISTINCT * FROM A_RESOURCE T START WITH RESOURCE_ID IN (SELECT T.RESOURCE_ID FROM V_CLERK_RESOURCE T WHERE  T.CLERK_NO = ?) CONNECT BY PRIOR RESOURCE_PARENT_ID = RESOURCE_ID) A LEFT JOIN (SELECT * FROM P_COMMON_MENU WHERE CLERK_NO = ?) P ON A.RESOURCE_ID = P.RESOURCE_ID where A.RESOURCE_MOD_TYPE = ? AND A.RESOURCE_TYPE = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resource.class), clerkNo, clerkNo,
                Constants.RESOURCE_MOD_TYPE_MENU_MODULE, Constants.RESOURCE_TYPE_HTML);
    }

    @Override
    public List<Resource> toolsSearch(String clerkNo) {
        final String sql = "SELECT A.RESOURCE_ID, A.RESOURCE_PARENT_ID,A.SYS_CODE,A.ICON_TYPE,A.RESOURCE_NAME,A.RESOURCE_MOD_TYPE,A.RESOURCE_TYPE,A.RESOURCE_URL,A.PERMISSION_SYS,A.PERMISSION_HTM,A.PERMISSION_BTN,P.ORDER_ID, NVL2(P.CLERK_NO, 1, 0) AS COMMONLY FROM (SELECT DISTINCT * FROM A_RESOURCE T START WITH RESOURCE_ID IN (SELECT T.RESOURCE_ID FROM V_CLERK_RESOURCE T WHERE  T.CLERK_NO = ?) CONNECT BY PRIOR RESOURCE_PARENT_ID = RESOURCE_ID) A LEFT JOIN (SELECT * FROM P_COMMON_MENU WHERE CLERK_NO = ?) P ON A.RESOURCE_ID = P.RESOURCE_ID where A.RESOURCE_MOD_TYPE = ?";
        List<Resource> resources =  jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resource.class), clerkNo, clerkNo,
                Constants.RESOURCE_MOD_TYPE_TOOLS_MODULE);
        
        List<Resource> data = new ArrayList<>();
        
        if(null != resources && !resources.isEmpty()){
            List<String> resourceIds = new ArrayList<>();;
            for (Resource resource : resources) {
                if(Constants.RESOURCE_TYPE_SYSTEM.equals(resource.getResourceType())){
                    resourceIds.add(resource.getResourceId());
                }
                
            }
            if(!resourceIds.isEmpty()){
                getResource(false, resources, data, resourceIds);
            }
            
        }
        
        return data;
    }
    
    @Override
    public List<Resource> toolsCombo(String clerkNo) {
        final String sql = "SELECT A.RESOURCE_ID, A.RESOURCE_PARENT_ID,A.SYS_CODE,A.ICON_TYPE,A.RESOURCE_NAME,A.RESOURCE_MOD_TYPE,A.RESOURCE_TYPE,A.RESOURCE_URL,A.PERMISSION_SYS,A.PERMISSION_HTM,A.PERMISSION_BTN,P.ORDER_ID, NVL2(P.CLERK_NO, 1, 0) AS COMMONLY FROM (SELECT DISTINCT * FROM A_RESOURCE T START WITH RESOURCE_ID IN (SELECT T.RESOURCE_ID FROM V_CLERK_RESOURCE T WHERE  T.CLERK_NO = ?) CONNECT BY PRIOR RESOURCE_PARENT_ID = RESOURCE_ID) A LEFT JOIN (SELECT * FROM P_COMMON_MENU WHERE CLERK_NO = ?) P ON A.RESOURCE_ID = P.RESOURCE_ID where A.RESOURCE_MOD_TYPE = ?";
        List<Resource> resources =  jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resource.class), clerkNo, clerkNo,
                Constants.RESOURCE_MOD_TYPE_TOOLS_MODULE);
        
        List<Resource> data = new ArrayList<>();
        if(null != resources && !resources.isEmpty()){
            List<String> resourceIds = new ArrayList<>();;
            for (Resource resource : resources) {
                if(Constants.RESOURCE_TYPE_SYSTEM.equals(resource.getResourceType())){
                    resourceIds.add(resource.getResourceId());
                }
                
            }
            if(!resourceIds.isEmpty()){
                getResource(true, resources, data, resourceIds);
            }
        }
        
        return data;
    }

    @Override
    public List<Resource> findResByClerkNo(String clerkNo) {
        
        final String sqlMenu = "SELECT LEVEL ID, C.* FROM (SELECT A.RESOURCE_ID, A.RESOURCE_PARENT_ID,A.SYS_CODE,A.ICON_TYPE,A.RESOURCE_NAME,A.RESOURCE_MOD_TYPE,A.RESOURCE_TYPE,A.RESOURCE_URL,A.PERMISSION_SYS,A.PERMISSION_HTM,A.PERMISSION_BTN,P.ORDER_ID,A.ORDER_ID AS ORDER_ID_1, NVL2(P.CLERK_NO, 1, 0) AS COMMONLY  FROM (SELECT DISTINCT * FROM A_RESOURCE T START WITH RESOURCE_ID IN (SELECT T.RESOURCE_ID FROM V_CLERK_RESOURCE T WHERE T.CLERK_NO = ?) CONNECT BY PRIOR RESOURCE_PARENT_ID = RESOURCE_ID) A LEFT JOIN (SELECT * FROM P_COMMON_MENU WHERE CLERK_NO=?) P ON A.RESOURCE_ID=P.RESOURCE_ID WHERE A.PERMISSION_BTN IS NULL) C START WITH C.RESOURCE_PARENT_ID IS NULL CONNECT BY PRIOR C.RESOURCE_ID = C.RESOURCE_PARENT_ID ORDER BY ID, C.ORDER_ID_1";
        List<Resource> listMenu = jdbcTemplate.query(sqlMenu, new BeanPropertyRowMapper<>(Resource.class), clerkNo, clerkNo);
        final String sqlBtn = "SELECT A.*, NVL2(B.RESOURCE_ID, 1, 0) AS COMMONLY FROM (SELECT DISTINCT B.RESOURCE_ID, B.RESOURCE_PARENT_ID,B.SYS_CODE,B.ICON_TYPE,B.RESOURCE_NAME,B.RESOURCE_MOD_TYPE,B.RESOURCE_TYPE,B.RESOURCE_URL,B.PERMISSION_SYS,B.PERMISSION_HTM,B.PERMISSION_BTN FROM V_CLERK_RESOURCE T, A_RESOURCE B WHERE T.CLERK_NO = ? AND T.PERMISSION_SYS = B.PERMISSION_SYS AND T.PERMISSION_HTM = B.PERMISSION_HTM AND B.PERMISSION_BTN IS NOT NULL) A LEFT JOIN (SELECT DISTINCT T.RESOURCE_ID FROM V_CLERK_RESOURCE T WHERE T.CLERK_NO = ? AND T.PERMISSION_BTN IS NOT NULL) B ON A.RESOURCE_ID = B.RESOURCE_ID";
        List<Resource> listBtn = jdbcTemplate.query(sqlBtn, new BeanPropertyRowMapper<>(Resource.class), clerkNo, clerkNo);
        listMenu.addAll(listBtn);
        return listMenu;
    }

    @Override
    public List<Resource> sidebarMenuSearch(String clerkNo, String resourceName) {
        
        final String sql = "SELECT A.* FROM (SELECT DISTINCT * FROM A_RESOURCE T START WITH RESOURCE_ID IN (SELECT T.RESOURCE_ID FROM V_CLERK_RESOURCE T WHERE T.CLERK_NO = ?) CONNECT BY PRIOR RESOURCE_PARENT_ID = RESOURCE_ID) A WHERE A.RESOURCE_MOD_TYPE = ? AND A.RESOURCE_TYPE = ? AND A.RESOURCE_NAME LIKE ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resource.class), clerkNo,
                Constants.RESOURCE_MOD_TYPE_MENU_MODULE, Constants.RESOURCE_TYPE_HTML, "%"+resourceName+"%");
    }
    
    /**
     * 拼接数据
     * @param commonly
     * @param resources
     * @param data
     * @param resourceIds
     * @author chenyong
     * @since  2016-11-10 16:56:17
     */
    private void getResource(boolean commonly, List<Resource> resources, List<Resource> data, List<String> resourceIds){
        
        if(commonly){
            for (Resource resource : resources) {
                if(resourceIds.contains(resource.getResourceParentId()) && !resource.getCommonly()){
                    data.add(resource);
                }
            }
        }else{
            for (Resource resource : resources) {
                if(resourceIds.contains(resource.getResourceParentId())){
                    data.add(resource);
                }
            }
        }
    }

}
