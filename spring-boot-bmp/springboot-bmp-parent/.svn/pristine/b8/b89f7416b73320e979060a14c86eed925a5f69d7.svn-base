package com.newcore.bmp.dao.api.authority;

import java.util.List;

import com.newcore.bmp.models.authority.models.Branch;
//import com.newcore.supports.models.service.bo.PageData;
//import com.newcore.supports.models.service.bo.PageQuery;

public interface BranchDao {

    public Branch createBranch(Branch branch);

    public Branch updateBranch(String branchNo, Branch branch);

    public boolean deleteBranch(String branchNo);

    Branch findOne(String branchNo);

    List<Branch> findAll();

    List<Branch> findSubBranch(String branchNo);

    List<Branch> findSubBranchAll(String branchNo);

    List<Branch> findSubBranchCombox(String branchNo, String selBranchNo);

    String findProvinceBranch(String branchNo);

    public List<Branch> findSupSubBranch(String branchNo, String type);

//    public PageData<Branch> findPageBranch(String branchNo, PageQuery<Branch> pageQuery);

    public List<Branch> findAllJobCenters();

}
