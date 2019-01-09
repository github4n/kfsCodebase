package com.newcore.bmp.dao.impl.authority;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import com.halo.core.dao.util.DaoUtils;
import com.newcore.bmp.models.authority.models.Clerk;
import com.newcore.bmp.dao.api.authority.DepartmentDao;
import com.newcore.bmp.models.bo.DepartmentBo;
import com.newcore.bmp.models.webvo.DepartmentVo;

@Repository("departmentDao")
public class DepartmentDaoImpl implements DepartmentDao {

    @Autowired
    private JdbcOperations jdbcTemplate;

    private static final String SELECT_DEPT_BY_BRANCHNO_SQL = "SELECT * FROM (SELECT T.*,ROWNUM AS RN FROM A_DEPARTMENT T WHERE BRANCH_NO=?) A WHERE RN BETWEEN ? AND ? ORDER BY ";

    private static final String SELECT_DEPT_COUNT_BY_BRANCHNO_SQL = "SELECT * FROM A_DEPARTMENT T WHERE BRANCH_NO=?";

    private static final String INSERT_DEPT_SQL = "INSERT INTO A_DEPARTMENT (BRANCH_NO,DEPT_NO,DEPT_NAME,DEPT_CLASS,DEPT_STATUS) VALUES (?,?,?,?,?)";

    private static final String UPDATE_DEPT_BY_DEPTNO_SQL = "UPDATE A_DEPARTMENT SET DEPT_NAME=?,DEPT_CLASS=?,DEPT_STATUS=? WHERE DEPT_NO=? AND BRANCH_NO=?";

    private static final String DELETE_DEPT_BY_DEPTNO_SQL = "DELETE FROM A_DEPARTMENT WHERE DEPT_NO=? AND BRANCH_NO=?";

    private static final String SELECT_DEPT_COUNT_BY_DEPTNO_SQL = "SELECT T.* FROM A_CLERK T WHERE DEPT_NO=? AND T.BRANCH_NO=?";

    private static final String SELECT_DEPT_BY_BRANCHNO_COMBOSQL = "SELECT DEPT_NO,CONCAT(DEPT_NO,DEPT_NAME) AS DEPT_NAME,DEPT_CLASS,DEPT_STATUS FROM A_DEPARTMENT WHERE BRANCH_NO=?";

    @Override
    public List<DepartmentBo> selectDept(String branchNo, String order, Integer sortingCols, Long page, Integer rows) {
        String sql = SELECT_DEPT_BY_BRANCHNO_SQL + (sortingCols + " " + order);
        return jdbcTemplate.query(sql, DaoUtils.createRowMapper(DepartmentBo.class), branchNo, page + 1,
                page + rows);
    }

    @Override
    public Long selectDeptCount(String branchNo) {
        List<DepartmentBo> departments = jdbcTemplate.query(SELECT_DEPT_COUNT_BY_BRANCHNO_SQL,
                DaoUtils.createRowMapper(DepartmentBo.class), branchNo);
        if (departments == null) {
            return 0L;
        }
        return Long.valueOf(departments.size());
    }

    @Override
    public boolean createDept(DepartmentVo department) {

        jdbcTemplate.update(INSERT_DEPT_SQL, department.getBranchNo(), department.getDeptNo(), department.getDeptName(),
                department.getDeptClass(), department.getDeptStatus());
        return true;
    }

    @Override
    public boolean updateDept(DepartmentVo department) {

        jdbcTemplate.update(UPDATE_DEPT_BY_DEPTNO_SQL, department.getDeptName(),
                department.getDeptClass(), department.getDeptStatus(), department.getDeptNo(), department.getBranchNo());
        return true;
    }

    @Override
    public boolean delDept(Integer deptNo, String branchNo) {
        jdbcTemplate.update(DELETE_DEPT_BY_DEPTNO_SQL, deptNo, branchNo);
        return true;
    }

    @Override
    public Long seleDept(Integer deptNo, String branchNo) {
        List<Clerk> departments = jdbcTemplate.query(SELECT_DEPT_COUNT_BY_DEPTNO_SQL,
                DaoUtils.createRowMapper(Clerk.class), deptNo, branchNo);
        if (departments == null) {
            return 0L;
        }
        return Long.valueOf(departments.size());
    }

    @Override
    public List<DepartmentVo> selectCombo(String branchNo) {
        return jdbcTemplate.query(SELECT_DEPT_BY_BRANCHNO_COMBOSQL,
                DaoUtils.createRowMapper(DepartmentVo.class), branchNo);
    }

}
