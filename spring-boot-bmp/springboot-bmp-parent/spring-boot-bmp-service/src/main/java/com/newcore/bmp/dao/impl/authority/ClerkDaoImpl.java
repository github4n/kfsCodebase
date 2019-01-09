package com.newcore.bmp.dao.impl.authority;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import com.halo.core.dao.util.DaoUtils;
import com.newcore.bmp.models.authority.models.Clerk;
import com.newcore.bmp.models.authority.models.ClerkResource;
import com.newcore.bmp.models.authority.models.SessionModel;
import com.newcore.bmp.models.authority.param.BranchRoleParam;
import com.newcore.bmp.dao.api.authority.ClerkDao;
import com.newcore.bmp.models.bo.ClerkBo;

@Repository("clerkDao")
public class ClerkDaoImpl implements ClerkDao {

    private static final String PERMISSION_SYS = "PERMISSION_SYS";
    private static final String PERMISSION_HTM = "PERMISSION_HTM"; 
    private static final String PERMISSION_BTN = "PERMISSION_BTN";
    private static final String SEPARATOR = ":";
    private static final String MATCHING = "*";
    @Autowired
    private JdbcOperations jdbcTemplate;
    private static final String SELECT_CHLERK_SQL = "SELECT A.*, ROWNUM AS RN FROM A_CLERK A WHERE A.BRANCH_NO IN (SELECT Branch_No FROM A_BRANCH t START WITH BRANCH_NO = ? CONNECT BY PRIOR BRANCH_NO = PRIO_BRANCH_NO)";

    @Override
    public boolean createClerk(final Clerk clerk) {

        final String sql = "insert into a_clerk(BRANCH_NO, DEPT_NO, CLERK_NO, NAME, PASSWORD, PASSWORD_SALT, TEL, MOBILE, EMAIL, LOCK_FLAG, ACCEPT_TASK_FLAG, CLERK_STATUS, CENTER_CODE) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        jdbcTemplate.update(sql, clerk.getBranchNo(), clerk.getDeptNo(), clerk.getUserName(), clerk.getName(),
                clerk.getPassword(), clerk.getPasswordSalt(), clerk.getTel(), clerk.getMobile(), clerk.getEmail(),
                clerk.getLockFlag(), clerk.getAcceptTaskFlag(), clerk.getClerkStatus(), clerk.getCenterCode());

        return true;
    }

    @Override
    public boolean updateClerk(Clerk clerk) {
        if("N".equals(clerk.getLockFlag())){
            String sql = "update a_clerk set BRANCH_NO=?, DEPT_NO=?, NAME=?, TEL=?, MOBILE=?, EMAIL=?, LOCK_FLAG=? , ACCEPT_TASK_FLAG=?, CLERK_STATUS=?, CENTER_CODE=?, LOGIN_FAIL_NUM=? where CLERK_NO=?";
            jdbcTemplate.update(sql, clerk.getBranchNo(), clerk.getDeptNo(), clerk.getName(), clerk.getTel(),
                    clerk.getMobile(), clerk.getEmail(), clerk.getLockFlag(), clerk.getAcceptTaskFlag(),
                    clerk.getClerkStatus(), clerk.getCenterCode(), 0, clerk.getUserName()); 
        }else{
            String sql = "update a_clerk set BRANCH_NO=?, DEPT_NO=?, NAME=?, TEL=?, MOBILE=?, EMAIL=?, LOCK_FLAG=? , ACCEPT_TASK_FLAG=?, CLERK_STATUS=?, CENTER_CODE=? where CLERK_NO=?";
            jdbcTemplate.update(sql, clerk.getBranchNo(), clerk.getDeptNo(), clerk.getName(), clerk.getTel(),
                    clerk.getMobile(), clerk.getEmail(), clerk.getLockFlag(), clerk.getAcceptTaskFlag(),
                    clerk.getClerkStatus(), clerk.getCenterCode(), clerk.getUserName()); 
        }
        return true;
    }

    @Override
    public boolean deleteClerk(String clerkNo) {

        String sql = "delete from a_clerk where clerk_no=?";

        jdbcTemplate.update(sql, clerkNo);

        return true;
    }

    @Override
    public Clerk findOne(String clerkNo) {
        //kfs20180516
        String sql = "select BRANCH_NO, DEPT_NO, CLERK_NO as userName, NAME, PASSWORD, PASSWORD_SALT, TEL, MOBILE, EMAIL, LOCK_FLAG, ACCEPT_TASK_FLAG, CLERK_STATUS, CENTER_CODE, LOGIN_FAIL_NUM from a_clerk where clerk_no=?";
        //kfs20180516

        List<Clerk> clerkList = jdbcTemplate.query(sql, DaoUtils.createRowMapper(Clerk.class), clerkNo);
        if (null == clerkList || clerkList.isEmpty()) {
            return null;
        }
        return clerkList.get(0);
    }

    @Override
    public List<Clerk> findAll() {
        String sql = "select BRANCH_NO, DEPT_NO, CLERK_NO, NAME, PASSWORD, PASSWORD_SALT, TEL, MOBILE, EMAIL, LOCK_FLAG, ACCEPT_TASK_FLAG, CLERK_STATUS, CENTER_CODE from a_clerk";
        return jdbcTemplate.query(sql, DaoUtils.createRowMapper(Clerk.class));
    }

    @Override
    public Clerk findByClerkNo(String branchNo, String clerkNo) {
        String sql = "select BRANCH_NO, DEPT_NO, CLERK_NO, NAME, PASSWORD, PASSWORD_SALT, TEL, MOBILE, EMAIL, LOCK_FLAG, ACCEPT_TASK_FLAG, CLERK_STATUS, CENTER_CODE "
                + "from a_clerk where branch_no=? and  clerk_no=?";
        List<Clerk> clerkList = jdbcTemplate.query(sql, DaoUtils.createRowMapper(Clerk.class), branchNo,
                clerkNo);
        if (null == clerkList || clerkList.isEmpty()) {
            return null;
        }
        return clerkList.get(0);
    }

//    @Override
//    public List<Clerk> findClerkByRoles(List<BranchRoleParam> branchRoleParams) {
//        String sql = "SELECT DISTINCT BRANCH_NO, DEPT_NO, A.CLERK_NO, NAME, PASSWORD, PASSWORD_SALT, TEL, MOBILE, EMAIL, LOCK_FLAG, ACCEPT_TASK_FLAG, CLERK_STATUS, CENTER_CODE FROM A_CLERK A JOIN A_CLERK_ROLE B ON A.CLERK_NO = B.CLERK_NO WHERE";
//        List<String> list = new ArrayList<>();
//        if(null == branchRoleParams || branchRoleParams.isEmpty()){
//            return new ArrayList<>();
//        }else if(branchRoleParams.size() == 1){
//            BranchRoleParam branchRoleParam = branchRoleParams.get(0);
//            return jdbcTemplate.query(sql + " A.BRANCH_NO = ? AND B.ROLE_ID = ?", DaoUtils.createRowMapper(Clerk.class),
//                    branchRoleParam.getBranchNo(), branchRoleParam.getRoleId());
//        }else{
//            StringBuilder sb = new StringBuilder(sql);
//            for (BranchRoleParam branchRoleParam : branchRoleParams) {
//                if(list.isEmpty()){
//                    sb.append(" (A.BRANCH_NO = ? AND B.ROLE_ID = ?)");
//                }else{
//                    sb.append(" OR (A.BRANCH_NO = ? AND B.ROLE_ID = ?)");
//                }
//                list.add(branchRoleParam.getBranchNo());
//                list.add(branchRoleParam.getRoleId());
//            }
//            return jdbcTemplate.query(sb.toString(), DaoUtils.createRowMapper(Clerk.class), list.toArray());
//            
//        }
//        
//    }

    @Override
    public SessionModel findSessionMsg(String clerkNo) {
        String sql = "SELECT A.CLERK_NO, A.NAME, B.*, C.DEPT_NO, C.DEPT_NAME, C.DEPT_CLASS"
                + " FROM A_CLERK A, A_BRANCH B, A_DEPARTMENT C where A.BRANCH_NO = B.BRANCH_NO "
                + "AND A.DEPT_NO=C.DEPT_NO AND A.BRANCH_NO=C.BRANCH_NO AND A.CLERK_NO = ?";
        List<SessionModel> sessionModels = jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<SessionModel>(SessionModel.class), clerkNo);
        if (null == sessionModels || sessionModels.isEmpty()) {
            return null;
        }
        return sessionModels.get(0);
    }
    
//    @Override
//    public Set<String> findPermissions(String clerkNo) {
//        String sql = "SELECT DISTINCT PERMISSION_SYS  ,PERMISSION_HTM ,PERMISSION_BTN FROM A_CLERK_ROLE A, A_ROLE_RESOURCE B WHERE A.ROLE_ID=B.ROLE_ID AND A.CLERK_NO=?";
//        List<Map<String, Object>> list = jdbcTemplate.query(sql, new ColumnMapRowMapper(), clerkNo);
//        Set<String> permissionSet = new HashSet<>();
//        for (Map<String, Object> m : list) {
//            String sys = m.get(PERMISSION_SYS)!=null?m.get(PERMISSION_SYS).toString():MATCHING;
//            String htm = m.get(PERMISSION_HTM)!=null?m.get(PERMISSION_HTM).toString():MATCHING;
//            String btn = m.get(PERMISSION_BTN)!=null?m.get(PERMISSION_BTN).toString():MATCHING;
//
//            String permission = sys + SEPARATOR + htm + SEPARATOR + btn ;
//            
//            permissionSet.add(permission);
//        }
//        return permissionSet;
//    }

    @Override
    public List<ClerkBo> selectClerk(String wheres, String order, Integer sortingCols, List<Object> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT A.* FROM (");
        sb.append(SELECT_CHLERK_SQL);
        sb.append(wheres);
        sb.append(" ORDER BY ");
        sb.append(sortingCols + " " + order);
        sb.append(") A WHERE RN BETWEEN ? AND ?  ");
        String sql = sb.toString();
        return jdbcTemplate.query(sql, DaoUtils.createRowMapper(ClerkBo.class), list.toArray());
    }

    @Override
    public Long selectClerkCount(String wheres, List<Object> list) {
        List<ClerkBo> clerks = jdbcTemplate.query(SELECT_CHLERK_SQL + wheres,
                DaoUtils.createRowMapper(ClerkBo.class), list.toArray());
        return Long.valueOf(clerks.size());
    }

//    @Override
//    public void clerkRoleAdd(List<String> roleIds, String clerkNo) {
//        final String sqlDel = "delete from a_clerk_role where clerk_no=?";
//        jdbcTemplate.update(sqlDel, clerkNo);
//        final String sqlInsert = "insert into a_clerk_role (clerk_no,role_id) values (?,?)";
//        for (int i = 0; i < roleIds.size(); i++) {
//            jdbcTemplate.update(sqlInsert, clerkNo, roleIds.get(i));
//        }
//    }

    @Override
    public void updateClerkUser(Clerk clerk) {
        final String sql = "update a_clerk set tel=?, mobile=?, email=? where clerk_no=?";
        jdbcTemplate.update(sql, clerk.getTel(), clerk.getMobile(), clerk.getEmail(), clerk.getUserName());
    }

    @Override
    public void passwordReset(Clerk clerk) {
        final String sql = "update a_clerk set PASSWORD=?, PASSWORD_SALT=? where clerk_no=?";
        jdbcTemplate.update(sql, clerk.getPassword(), clerk.getPasswordSalt(), clerk.getUserName());
    }

    @Override
    public void changePassword(String clerkNo, String newPassword) {
        final String sql = "update a_clerk set PASSWORD=? where clerk_no=?";
        jdbcTemplate.update(sql, newPassword, clerkNo);
    }

    @Override
    public void updateClerkATF(String clerkNo, String acceptTaskFlag) {
        final String sql = "update a_clerk set accept_task_flag=? where clerk_no=?";
        jdbcTemplate.update(sql, acceptTaskFlag, clerkNo);
    }

    @Override
    public ClerkResource findResByClerkNo(String clerkNo) {
        String sql = "SELECT A.ACCEPT_TASK_FLAG, A.BRANCH_NO,A.CLERK_NO, A.NAME, B.BRANCH_NAME"
                + " FROM A_CLERK A , A_BRANCH B where A.BRANCH_NO = B.BRANCH_NO " + "AND A.CLERK_NO = ?";
        List<ClerkResource> clerkResources = jdbcTemplate.query(sql,
                DaoUtils.createRowMapper(ClerkResource.class), clerkNo);
        if (null == clerkResources || clerkResources.isEmpty()) {
            return null;
        }
        return clerkResources.get(0);
    }

    @Override
    public void updateLionFailNum(String clerkNo, Integer loginFailNum, String lockFlag) {
        String sql = "UPDATE A_CLERK SET LOCK_FLAG=?, LOGIN_FAIL_NUM=? WHERE CLERK_NO=?";
        jdbcTemplate.update(sql, lockFlag, loginFailNum, clerkNo);
    }
    
	@Override
    public ClerkBo findInfoByClerkNo(String clerkNo) {
        String sql = "select A.CLERK_NO, A.NAME, A.TEL, A.MOBILE, A.EMAIL, A.BRANCH_NO from A_CLERK A where A.CLERK_NO = ?";
        List<ClerkBo> clerkBo = jdbcTemplate.query(sql,
                DaoUtils.createRowMapper(ClerkBo.class), clerkNo);
        if (null == clerkBo || clerkBo.isEmpty()) {
            return null;
        }
        return clerkBo.get(0);
    }
}
