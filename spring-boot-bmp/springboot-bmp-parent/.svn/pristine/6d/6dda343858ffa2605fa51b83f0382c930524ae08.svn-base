package com.newcore.bmp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newcore.bmp.models.authority.models.Branch;
import com.newcore.bmp.service.api.authority.BranchService;
import com.newcore.bmp.dao.api.authority.BranchDao;
import com.newcore.supports.models.service.bo.PageData;
import com.newcore.supports.models.service.bo.PageQuery;




@Service("branchService")
public class BranchServiceImpl implements BranchService {

    private static Logger LOGGER = LoggerFactory.getLogger(BranchServiceImpl.class);

    @Autowired
    private BranchDao branchDao;

    /**
     * 创建机构.
     *
     * @param branch
     * @return branch
     */
    @Override
    public Branch createBranch(Branch branch) {
        try {
            return branchDao.createBranch(branch);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 更新机构.
     *
     * @param branch
     * @return branch
     */
    @Override
    public Branch updateBranch(Branch branch) {
        try {
            return branchDao.updateBranch(branch.getBranchNo(), branch);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 删除机构.
     *
     * @param branch
     * @return branch
     */
    @Override
    public boolean deleteBranch(String branchNo) {
        try {
            return branchDao.deleteBranch(branchNo);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }

    }

    /**
     * 查找一个机构.
     *
     * @param branch
     * @return branch
     */
    @Override
    @Valid
    public Branch findOne(String branchNo) {
        return branchDao.findOne(branchNo);

    }

    /**
     * 列出所有机构.
     *
     * @param void
     * @return branch
     */
    @Override
    public List<Branch> findAll() {
        
        try {
            return branchDao.findAll();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * 列出机构的所有下级机构.
     *
     * @param branchNo
     * @return branch list
     */
    @Override
    @Valid
    public List<Branch> findSubBranch(String branchNo) {
        return branchDao.findSubBranch(branchNo);

    }

    @Override
    @Valid
    public String findProvinceBranch(String branchNo) {
        LOGGER.info("find provincebranch of " + branchNo);
        return branchDao.findProvinceBranch(branchNo);
    }
    
    
    @Override
    public Branch findSubBranchAll(String branchNo) {
        List<Branch> branchs = branchDao.findSubBranchAll(branchNo);
        Branch branch = null;
        for (Branch branch1 : branchs) {
            if (branchNo.equals(branch1.getBranchNo())) {
                branch = branch1;
                break;
            }
        }
        if(branch != null){
            this.setSubBranch(branch, branchs);
        }
        
        return branch;
    }

    @Override
    public List<Branch> findSubBranchCombox(String branchNo, String selBranchNo) {
        return branchDao.findSubBranchCombox(branchNo, selBranchNo);
    }

    private void setSubBranch(Branch branch, List<Branch> branchs) {
        for (int i = 0; i < branchs.size(); i++) {
            if (branch.getBranchNo().equals(branchs.get(i).getPrioBranchNo())) {
                Branch branch1 = branchs.get(i);
                this.setSubBranch(branch1, branchs);
                if (null == branch.getChildren() || branch.getChildren().isEmpty()) {
                    List<Branch> branchs1 = new ArrayList<>();
                    branchs1.add(branch1);
                    branch.setChildren(branchs1);
                } else {
                    branch.getChildren().add(branch1);
                }
            }
        }
    }

    @Override
    @Valid
    public List<Branch> findSupSubBranch(String branchNo, String branchLevelType) {
        return branchDao.findSupSubBranch(branchNo, branchLevelType);
    }

//    @SuppressWarnings("unchecked")
//    @Override
//    public PageData<Branch> findPageBranch( Map<String, Object> map) {
//        String branchNo = (String)map.get("branchNo");
//        PageQuery<Branch> pageQuery = (PageQuery<Branch>) map.get("pageQuery");
//        return branchDao.findPageBranch(branchNo, pageQuery);
//    }

    @Override
    public List<Branch> findAllJobCenters() {
        return branchDao.findAllJobCenters();
    }

}
