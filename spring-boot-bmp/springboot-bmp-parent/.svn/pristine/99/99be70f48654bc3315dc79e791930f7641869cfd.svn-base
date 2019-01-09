package com.newcore.bmp.dao.api.authority;

import java.util.List;

import com.newcore.bmp.models.bo.DepartmentBo;
import com.newcore.bmp.models.webvo.DepartmentVo;

public interface DepartmentDao {

    public List<DepartmentBo> selectDept(String branchNo, String order, Integer sortingCols, Long page, Integer rows);

    public Long selectDeptCount(String branchNo);

    public boolean createDept(DepartmentVo department);

    public boolean updateDept(DepartmentVo department);

    public boolean delDept(Integer deptNo, String branchNo);

    public Long seleDept(Integer deptNo, String branchNo);

    public List<DepartmentVo> selectCombo(String branchNo);

}
