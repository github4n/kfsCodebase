package com.newcore.bmp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newcore.bmp.dao.api.authority.DepartmentDao;
import com.newcore.bmp.models.bo.DepartmentBo;
import com.newcore.bmp.models.webvo.DepartmentVo;
import com.newcore.bmp.service.api.authority.DepartmentService;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public boolean createDept(DepartmentVo department) {
        try {
            departmentDao.createDept(department);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    @Override
    public boolean updateDept(DepartmentVo department) {
        try {
            departmentDao.updateDept(department);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    @Override
    public boolean delDept(Integer deptNo, String branchNo) {

        try {
            if (departmentDao.seleDept(deptNo, branchNo) > 0L) {
                return false;
            }
            departmentDao.delDept(deptNo, branchNo);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

//    @Override
//    public Map<String, Object> selectDept(String branchNo, String order, Integer sortingCols, Long page, Integer rows) {
//        List<DepartmentBo> departmentBos = departmentDao.selectDept(branchNo, order, sortingCols, page, rows);
//        Long count = departmentDao.selectDeptCount(branchNo);
//        
//        List<DepartmentVo> departments = new ArrayList<>();
//        if(null != departmentBos && !departmentBos.isEmpty()){
//            for (DepartmentBo departmentBo : departmentBos) {
//                DepartmentVo department = new DepartmentVo();
//                BeanUtils.copyProperties(departmentBo, department);
//                department.setDeptClass(departmentBo.getDeptClass().getKey());
//                department.setDeptClassDesc(departmentBo.getDeptClass().getDescription());
//                department.setDeptStatus(departmentBo.getDeptStatus().getKey());
//                department.setDeptStatusDesc(departmentBo.getDeptStatus().getDescription());
//                departments.add(department);
//            }
//        }
//        
//        Map<String, Object> map = new HashMap<>();
//        map.put("list", departments);
//        map.put("count", count);
//        return map;
//    }

    @Override
    public List<DepartmentVo> selectCombo(String branchNo) {
        return departmentDao.selectCombo(branchNo);
    }

}
