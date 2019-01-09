package com.newcore.bmp.dao.impl.authority;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import com.halo.core.dao.util.DaoUtils;
import com.newcore.bmp.models.authority.models.Resource;
import com.newcore.bmp.models.authority.models.Role;
import com.newcore.bmp.models.authority.models.TreeModel;
import com.newcore.bmp.dao.api.authority.RoleDao;
import com.newcore.bmp.models.bo.RoleBo;

@Repository("roleDao")
public class RoleDaoImpl implements RoleDao {

    @Autowired
    private JdbcOperations jdbcTemplate;

    private static final String INSERT_ROLE_SQL = "INSERT INTO A_ROLE(ROLE_ID,ROLE_NAME,ROLE_GROUP, ENABLE_FLAG) VALUES(?,?,?,?)";

    private static final String DELETE_ROLE_BY_ID_SQL = "DELETE FROM A_ROLE WHERE ROLE_ID=?";

    private static final String UPDATE_ROLE_BY_ROLEID_SQL = "UPDATE A_ROLE SET ROLE_NAME=?,ROLE_GROUP=?,ENABLE_FLAG=? WHERE ROLE_ID=?";

    private static final String SELECT_ROLE_SQL = "SELECT ROLE_ID, ROLE_NAME,ROLE_GROUP, ENABLE_FLAG,ROWNUM AS RN FROM A_ROLE WHERE 1=1";

    private static final String DELETE_CLERK_ROLE_BY_ROLEID_SQL = "DELETE FROM A_CLERK_ROLE WHERE ROLE_ID=?";
    
    private static final String DELETE_ROLE_RESOURCE_BY_ROLEID_SQL = "DELETE FROM A_ROLE_RESOURCE WHERE ROLE_ID=?";

    private static final String SELECT_ROLEIDS_BY_BRANCHNO_AND_CLERKNO_SQL = "SELECT B.ROLE_ID FROM A_CLERK A, A_CLERK_ROLE B WHERE A.CLERK_NO=B.CLERK_NO AND A.BRANCH_NO=? AND A.CLERK_NO=?";

    private static final String ROLE_SELECT_BY_CLERKNO_SQL = "SELECT A.ROLE_ID, A.ROLE_NAME,B.CLERK_NO FROM A_ROLE A LEFT JOIN (SELECT * FROM A_CLERK_ROLE WHERE CLERK_NO=?) B ON A.ROLE_ID=B.ROLE_ID";

    private static final String SELECT_RESOUCE_TO_TREEMODEL_SQL = "  SELECT A.RESOURCE_ID AS ID, A.RESOURCE_PARENT_ID AS PID, A.RESOURCE_NAME AS NAME, A.RESOURCE_URL AS URL, NVL2(B.RESOURCE_ID, 1, 0) AS CHECKED FROM A_RESOURCE A LEFT JOIN (SELECT DISTINCT * FROM A_RESOURCE T START WITH RESOURCE_ID IN (SELECT T.RESOURCE_ID FROM V_CLERK_RESOURCE T WHERE T.ROLE_ID = ?) CONNECT BY PRIOR RESOURCE_PARENT_ID = RESOURCE_ID) B ON A.RESOURCE_ID = B.RESOURCE_ID";

    @Override
    public boolean createRole(final Role role) {

        jdbcTemplate.update(INSERT_ROLE_SQL, role.getRoleId(), role.getRoleName(), role.getRoleGroup(),
                role.getEnableFlag());
        return true;
    }

    @Override
    public boolean updateRole(Role role) {

        jdbcTemplate.update(UPDATE_ROLE_BY_ROLEID_SQL, role.getRoleName(), role.getRoleGroup(), role.getEnableFlag(),
                role.getRoleId());
        return true;
    }

    @Override
    public boolean deleteRole(String roleId) {

        jdbcTemplate.update(DELETE_CLERK_ROLE_BY_ROLEID_SQL, roleId);
        jdbcTemplate.update(DELETE_ROLE_RESOURCE_BY_ROLEID_SQL, roleId);
        jdbcTemplate.update(DELETE_ROLE_BY_ID_SQL, roleId);
        return true;
    }

    @Override
    public Role findOne(String roleId) {
        final String sql = "select role_id, role_name,role_group, ENABLE_FLAG from a_role where role_id=?";
        List<Role> roleList = jdbcTemplate.query(sql, DaoUtils.createRowMapper(Role.class), roleId);
        if (null == roleList || roleList.isEmpty()) {
            return null;
        }
        return roleList.get(0);
    }

    @Override
    public List<Role> findAll() {
        final String sql = "select role_id, role_name,role_group, ENABLE_FLAG from a_role";
        return jdbcTemplate.query(sql, DaoUtils.createRowMapper(Role.class));
    }

    @Override
    public List<Role> findByGroup(String roleGroup) {
        if (null == roleGroup || "".equals(roleGroup)) {
            final String sql = "select role_id, role_name,role_group, ENABLE_FLAG from a_role";
            return jdbcTemplate.query(sql, DaoUtils.createRowMapper(Role.class));
        } else {
            final String sql = "select role_id, role_name,role_group, ENABLE_FLAG from a_role where role_group=?";
            return jdbcTemplate.query(sql, DaoUtils.createRowMapper(Role.class), roleGroup);
        }
    }

    @Override
    public List<RoleBo> selectRole(String wheres, String order, Integer sortingCols, List<Object> list) {

        StringBuilder sb = new StringBuilder();
        sb.append("select a.* from (");
        sb.append(SELECT_ROLE_SQL);
        sb.append(wheres);
        sb.append(" ORDER BY ");
        sb.append(sortingCols + " " + order);
        sb.append(") a WHERE RN BETWEEN ? AND ?  ");

        String sql = sb.toString();
        return jdbcTemplate.query(sql, DaoUtils.createRowMapper(RoleBo.class), list.toArray());
    }

    @Override
    public Long selectRoleCount(String wheres, List<Object> list) {

        List<RoleBo> roles = jdbcTemplate.query(SELECT_ROLE_SQL + wheres, DaoUtils.createRowMapper(RoleBo.class),
                list.toArray());

        if (null == roles || roles.isEmpty()) {
            return 0L;
        }

        return Long.valueOf(roles.size());
    }

    @Override
    public Long selectUserRoleCount(String roleId) {
        final String sql = "select role_id from a_clerk_role where role_id=?";
        List<Role> roles = jdbcTemplate.query(sql, DaoUtils.createRowMapper(Role.class), roleId);
        if (null == roles || roles.isEmpty()) {
            return 0L;
        }
        return Long.valueOf(roles.size());
    }

    @Override
    public List<String> selectRoleIds(String branchNo, String clerkNo) {

        List<String> roleIds = new ArrayList<>();
        List<Role> roles = jdbcTemplate.query(SELECT_ROLEIDS_BY_BRANCHNO_AND_CLERKNO_SQL,
                DaoUtils.createRowMapper(Role.class), branchNo, clerkNo);
        if (null != roles && !roles.isEmpty()) {
            for (Role role : roles) {
                roleIds.add(role.getRoleId());
            }
        }
        return roleIds;
    }

    @Override
    public List<Role> roleSelect(String clertNo) {
        return jdbcTemplate.query(ROLE_SELECT_BY_CLERKNO_SQL, DaoUtils.createRowMapper(Role.class), clertNo);
    }

    @Override
    public List<TreeModel> roleResoucesTree(String roleId) {
        return jdbcTemplate.query(SELECT_RESOUCE_TO_TREEMODEL_SQL,
                new BeanPropertyRowMapper<TreeModel>(TreeModel.class), roleId);
    }

    @Override
    public void addRoleResouce(Map<String, String> map, String roleId) {
        final String sqlDel = "delete from a_role_resource where role_id=?";
        final String sqlInsert1 = "insert into a_role_resource (role_id,permission_sys) values (?,?)";
        final String sqlInsert2 = "insert into a_role_resource (role_id,permission_sys,permission_htm) values (?,?,?)";
        final String sqlInsert3 = "insert into a_role_resource (role_id,permission_sys,permission_htm,permission_btn) values (?,?,?,?)";
        jdbcTemplate.update(sqlDel, roleId);
        for(Map.Entry<String, String> entry : map.entrySet()){
            String key = entry.getKey();
            String keys[] = key.split(":");
            if(keys.length == 1 && "true".equals(entry.getValue())){
                jdbcTemplate.update(sqlInsert1, roleId, key);
            }else if(keys.length == 2 && "true".equals(entry.getValue()) && "false".equals(map.get(keys[0]))){
                jdbcTemplate.update(sqlInsert2, roleId, keys[0], keys[1]);
            }else if(keys.length == 3 && "true".equals(entry.getValue()) && "false".equals(map.get(keys[0]+":"+keys[1]))){
                jdbcTemplate.update(sqlInsert3, roleId, keys[0], keys[1], keys[2]);
            }
        }
        
    }

    @Override
    public List<Resource> selectSysRole(List<String> resources) {
       
        String sql = "SELECT A.*,NVL2(B.RESOURCE_ID, 1, 0) AS COMMONLY FROM (SELECT A.* FROM A_RESOURCE A WHERE A.PERMISSION_SYS IN (SELECT DISTINCT B.PERMISSION_SYS FROM A_RESOURCE B WHERE B.RESOURCE_ID IN(_questionMark_))) A LEFT JOIN (SELECT RESOURCE_ID FROM A_RESOURCE WHERE RESOURCE_ID IN(_questionMark_)) B ON A.RESOURCE_ID = B.RESOURCE_ID";
        StringBuilder sb = new StringBuilder();
        
        if(null != resources && !resources.isEmpty()){
            for (int i = 0; i < resources.size(); i++) {
                if(i == 0){
                    sb.append("?");
                }else{
                    sb.append(",?");  
                }
            }
            sql = sql.replace("_questionMark_", sb.toString()).replace("_questionMark_", sb.toString());
            List<String> resourcesTwo = new ArrayList<>();
            for (String resource : resources) {
                resourcesTwo.add(resource);
            }
            for (String resource : resources) {
                resourcesTwo.add(resource);
            }
            return jdbcTemplate.query(sql, DaoUtils.createRowMapper(Resource.class),
                    resourcesTwo.toArray());
        }
        
        return new ArrayList<>();
    }

}
