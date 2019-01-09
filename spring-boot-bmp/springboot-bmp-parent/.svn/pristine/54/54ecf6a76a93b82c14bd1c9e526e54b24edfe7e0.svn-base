package com.newcore.bmp.dao.impl.authority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import com.halo.core.dao.util.DaoUtils;
import com.newcore.bmp.models.authority.constants.Constants;
import com.newcore.bmp.models.authority.models.Branch;
import com.newcore.bmp.dao.api.authority.BranchDao;
import com.newcore.bmp.models.bo.BranchBo;
import com.newcore.supports.models.OrderType;
import com.newcore.supports.models.service.bo.OrderInfo;
import com.newcore.supports.models.service.bo.PageData;
import com.newcore.supports.models.service.bo.PageQuery;
import com.newcore.supports.service.api.PageQueryService;

@Repository("branchDao")
public class BranchDaoImpl implements BranchDao {

    /* 有效机构状态 */
    public static final String VALID_BRANCH_STATUS = "1";
    
    /* 作业中心标示 */
    public static final String JOB_CENTER_FLAG = "Y";
    
    @Autowired
    private JdbcOperations jdbcTemplate;
    
    /**
     * 分页辅助工具
     */
    //@Autowired
    //PageQueryService pageQueryService;

    @Override
    public Branch createBranch(final Branch branch) {

        final String sql = "insert into a_branch(BRANCH_NO,BRANCH_NAME,PRIO_BRANCH_NO,"
                + "POST_CODE,TEL,ADDRESS,EMAIL,PROCESSING_CENTER,"
                + "ARC_BRANCH_NO,PRINT_BRANCH_NO,PRINT_BRANCH_NAME,BRANCH_STATUS,JOB_CENTER_FLAG)"
                + " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, branch.getBranchNo(), branch.getBranchName(), branch.getPrioBranchNo(),
                branch.getPostCode(), branch.getTel(), branch.getAddress(), branch.getEmail(),
                branch.getProcessingCenter(), branch.getArcBranchNo(), branch.getPrintBranchNo(),
                branch.getPrintBranchName(), branch.getBranchStatus(),branch.getJobCenterFlag());
        return branch;
    }

    @Override
    public Branch updateBranch(String branchNo, Branch branch) {
        final String sql = "update a_branch set branch_name=?,prio_branch_no=?,"
                + "post_code=?,tel=?,address=?,email=?,"
                + "processing_center=?,arc_branch_no=?,print_branch_no=?,print_branch_name=?,"
                + "branch_status=?,JOB_CENTER_FLAG=? where branch_no=?";
        jdbcTemplate.update(sql, branch.getBranchName(), branch.getPrioBranchNo(), branch.getPostCode(),
                branch.getTel(), branch.getAddress(), branch.getEmail(), branch.getProcessingCenter(),
                branch.getArcBranchNo(), branch.getPrintBranchNo(), branch.getPrintBranchName(),
                branch.getBranchStatus(),branch.getJobCenterFlag(), branchNo);
        return branch;
    }

    @Override
    public boolean deleteBranch(String branchNo) {
        final String deleteSelfSql = "delete from a_branch where branchNo=?";
        jdbcTemplate.update(deleteSelfSql, branchNo);
        final String deleteDescendantsSql = "delete from a_branch where prio_branch_no=?";
        jdbcTemplate.update(deleteDescendantsSql, branchNo);
        return true;
    }

    @Override
    public Branch findOne(String branchNo) {
        final String sql = "select branch_no,branch_name,prio_branch_no,post_code,tel,address,email,processing_center,print_branch_no,arc_branch_no,print_branch_name,BRANCH_STATUS from a_branch where branch_no=?";
        List<Branch> branchList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Branch>(Branch.class), branchNo);
        if (null == branchList || branchList.isEmpty()) {
            return null;
        }
        return branchList.get(0);
    }

    @Override
    public List<Branch> findAll() {
        final String sql = "select branch_no,branch_name,prio_branch_no,post_code,tel,address,email,processing_center,print_branch_no,arc_branch_no,print_branch_name,BRANCH_STATUS from a_branch";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Branch>(Branch.class));
    }

    @Override
    public List<Branch> findSubBranch(String branchNo) {
        final String sql = "select branch_no,branch_name,prio_branch_no,post_code,tel,address,email,processing_center,print_branch_no,arc_branch_no,print_branch_name,BRANCH_STATUS from a_branch where prio_branch_no=?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Branch>(Branch.class), branchNo);
    }

    @Override
    public String findProvinceBranch(String branchNo) {

        final String sql = "SELECT A.BRANCH_NO FROM (SELECT T.BRANCH_NO, T.PRIO_BRANCH_NO FROM A_BRANCH T START WITH BRANCH_NO = ? CONNECT BY PRIOR PRIO_BRANCH_NO = BRANCH_NO) A WHERE A.PRIO_BRANCH_NO=?";

        List<Branch> branchList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Branch>(Branch.class), branchNo, Constants.HEADQUARTERS_BRACNCH_NO);

        if (null == branchList || branchList.isEmpty()) {
            return null;
        } else {
            return branchList.get(0).getBranchNo();
        }
    }

    @Override
    public List<Branch> findSubBranchAll(String branchNo) {
        final String sql = "SELECT level id ,t.BRANCH_NO,t.BRANCH_NAME,"
                + "t.PRIO_BRANCH_NO,t.POST_CODE,t.TEL,t.ADDRESS,t.EMAIL,"
                + "t.PROCESSING_CENTER,t.ARC_BRANCH_NO,t.PRINT_BRANCH_NO,"
                + "t.PRINT_BRANCH_NAME,t.BRANCH_STATUS "
                + "FROM A_BRANCH t START WITH Branch_No = ? "
                + "CONNECT BY PRIOR Branch_No = prio_branch_no order by 1";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Branch>(Branch.class), branchNo);
    }

    @Override
    public List<Branch> findSubBranchCombox(String branchNo, String selBranchNo) {
        if(null == selBranchNo || "".equals(selBranchNo)){
            final String sql = "SELECT t.BRANCH_NO, t.BRANCH_NAME FROM A_BRANCH t START WITH Branch_No = ? CONNECT BY PRIOR Branch_No = prio_branch_no order by 1";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Branch>(Branch.class), branchNo); 
        }else{
            final String sql = "SELECT BRANCH_NO, BRANCH_NAME FROM (SELECT t.BRANCH_NO, t.BRANCH_NAME FROM A_BRANCH t START WITH Branch_No = ? CONNECT BY PRIOR Branch_No = prio_branch_no) WHERE BRANCH_NO NOT IN (SELECT t.BRANCH_NO FROM A_BRANCH t START WITH Branch_No = ? CONNECT BY PRIOR Branch_No = prio_branch_no) UNION ALL SELECT BRANCH_NO, BRANCH_NAME FROM (SELECT level id , t.BRANCH_NO, t.BRANCH_NAME FROM A_BRANCH t START WITH Branch_No = ? CONNECT BY PRIOR  prio_branch_no=Branch_No) WHERE ID = 2";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Branch>(Branch.class), branchNo, selBranchNo, branchNo); 
        }
        
    }

    @Override
    public List<Branch> findSupSubBranch(String branchNo, String branchLevelType) {

        if (Constants.SELECT_SUB_BRANCH_TYPE.equals(branchLevelType)) {
            String sql = "SELECT t.BRANCH_NO,t.BRANCH_NAME,t.PRIO_BRANCH_NO,"
                    + "t.POST_CODE,t.TEL,t.ADDRESS,t.EMAIL,t.PROCESSING_CENTER,"
                    + "t.ARC_BRANCH_NO,t.PRINT_BRANCH_NO,t.PRINT_BRANCH_NAME,t.BRANCH_STATUS "
                    + "FROM A_BRANCH t START WITH Branch_No = ? CONNECT BY PRIOR Branch_No = prio_branch_no";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Branch>(Branch.class), branchNo);
        } else if (Constants.SELECT_SUP_BRANCH_TYPE.equals(branchLevelType)) {
            String sql = "SELECT t.BRANCH_NO,t.BRANCH_NAME,t.PRIO_BRANCH_NO,"
                    + "t.POST_CODE,t.TEL,t.ADDRESS,t.EMAIL,t.PROCESSING_CENTER,"
                    + "t.ARC_BRANCH_NO,t.PRINT_BRANCH_NO,t.PRINT_BRANCH_NAME,"
                    + "t.BRANCH_STATUS "
                    + "FROM A_BRANCH t START WITH Branch_No = ? "
                    + "CONNECT BY PRIOR prio_branch_no = Branch_No";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Branch>(Branch.class), branchNo);
        } else if (Constants.SELECT_ALL_BRANCH_TYPE.equals(branchLevelType)) {
            String sql = "SELECT DISTINCT A.BRANCH_NO,A.BRANCH_NAME,A.PRIO_BRANCH_NO,"
                    + "A.POST_CODE,A.TEL,A.ADDRESS,A.EMAIL,A.PROCESSING_CENTER,"
                    + "A.ARC_BRANCH_NO,A.PRINT_BRANCH_NO,A.PRINT_BRANCH_NAME,A.BRANCH_STATUS "
                    + "FROM (SELECT t.BRANCH_NO,t.BRANCH_NAME,t.PRIO_BRANCH_NO,"
                    + "t.POST_CODE,t.TEL,t.ADDRESS,t.EMAIL,t.PROCESSING_CENTER,"
                    + "t.ARC_BRANCH_NO,t.PRINT_BRANCH_NO,t.PRINT_BRANCH_NAME,"
                    + "t.BRANCH_STATUS FROM A_BRANCH t START WITH Branch_No = ? "
                    + "CONNECT BY PRIOR Branch_No = prio_branch_no "
                    + "union all SELECT t.BRANCH_NO,t.BRANCH_NAME,t.PRIO_BRANCH_NO,"
                    + "t.POST_CODE,t.TEL,t.ADDRESS,t.EMAIL,t.PROCESSING_CENTER,"
                    + "t.ARC_BRANCH_NO,t.PRINT_BRANCH_NO,t.PRINT_BRANCH_NAME,"
                    + "t.BRANCH_STATUS "
                    + "FROM A_BRANCH t START WITH Branch_No = ? "
                    + "CONNECT BY PRIOR prio_branch_no = Branch_No) A";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Branch>(Branch.class), branchNo, branchNo);
        }

        return new ArrayList<>();
    }

//    @Override
//    public PageData<Branch> findPageBranch(String branchNo, PageQuery<Branch> pageQuery) {
//        
//        String sql_list = "SELECT * FROM (SELECT T.BRANCH_NO,T.BRANCH_NAME,T.PRIO_BRANCH_NO,T.POST_CODE,T.TEL,T.ADDRESS,T.EMAIL,T.PROCESSING_CENTER,T.ARC_BRANCH_NO,T.PRINT_BRANCH_NO,T.BRANCH_STATUS,T.PRINT_BRANCH_NAME,T.JOB_CENTER_FLAG FROM A_BRANCH T START WITH BRANCH_NO = ? CONNECT BY PRIOR BRANCH_NO = PRIO_BRANCH_NO) T WHERE 1=1";
//        String sql_count = "SELECT COUNT(BRANCH_NO) FROM (SELECT T.BRANCH_NO,T.BRANCH_NAME,T.PRIO_BRANCH_NO,T.POST_CODE,T.TEL,T.ADDRESS,T.EMAIL,T.PROCESSING_CENTER,T.ARC_BRANCH_NO,T.PRINT_BRANCH_NO,T.BRANCH_STATUS,T.PRINT_BRANCH_NAME FROM A_BRANCH T START WITH BRANCH_NO = ? CONNECT BY PRIOR BRANCH_NO = PRIO_BRANCH_NO) T WHERE 1=1";
//        if (CollectionUtils.isEmpty(pageQuery.getOrderInfoList())) {
//            OrderInfo pkOrder = new OrderInfo();
//            pkOrder.setOrderType(OrderType.ASC.name());
//            pkOrder.setColumnName("BRANCH_NO");
//            pageQuery.setOrderInfoList(Collections.singletonList(pkOrder));
//        }else {
//            /** 对排序条件属性做数据库字段转换 */
//            pageQuery.getOrderInfoList()
//                    .forEach(orderInfo -> {
//                        if("jobCenterFlagDesc".equals(orderInfo.getColumnName())){
//                            orderInfo.setColumnName("jobCenterFlag");
//                        }
//                        orderInfo.setColumnName(DaoUtils.toColumnName(orderInfo.getColumnName()));
//                     });
//        }
//        
//        List<String> params = new ArrayList<>();
//        params.add(branchNo);
//        Branch branch = pageQuery.getCondition();
//        if(branch != null){
//            String sql = getPageSql(params, branch);
//            sql_list += sql;
//            sql_count += sql;
//        }
//        
//        String pageSql = pageQueryService.buildPageQuerySql(sql_list, pageQuery);
//        List<BranchBo> branchBos = jdbcTemplate.query(pageSql, DaoUtils.createRowMapper(BranchBo.class),
//                params.toArray());
//        long totalCount = jdbcTemplate.queryForObject(sql_count, Long.class, params.toArray());
//        
//        List<Branch> branchs = new ArrayList<>();
//        if(null != branchBos && !branchBos.isEmpty()){
//            for (BranchBo branchBo : branchBos) {
//                Branch branch1 = new Branch();
//                BeanUtils.copyProperties(branchBo, branch1);
//                branch1.setBranchStatus(branchBo.getBranchStatus());
//                branch1.setBranchStatusDesc(branchBo.getBranchStatus().getDescription());
//                if(branchBo.getJobCenterFlag()!=null && YES_NO_FLAG.YES.getKey().equals(branchBo.getJobCenterFlag())){
//                    branch1.setJobCenterFlag(YES_NO_FLAG.valueOfKey(branchBo.getJobCenterFlag()).getKey());
//                    branch1.setJobCenterFlagDesc(YES_NO_FLAG.valueOfKey(branchBo.getJobCenterFlag()).getDescription());
//                }else if(YES_NO_FLAG.NO.getKey().equals(branchBo.getJobCenterFlag())){
//                    branch1.setJobCenterFlag(YES_NO_FLAG.NO.getKey());
//                    branch1.setJobCenterFlagDesc(YES_NO_FLAG.NO.getDescription());
//                }
//                branchs.add(branch1);
//            }
//        }
//        
//        PageData<Branch> pageData = new PageData<>();
//        pageData.setData(branchs);
//        pageData.setTotalCount(totalCount);
//        
//        return pageData;
//    }
    
//    private String getPageSql(List<String> params, Branch branch){
//        StringBuilder sb = new StringBuilder("");
//        if(null != branch.getBranchNo() && !"".equals(branch.getBranchNo())){
//            sb.append(" AND T.BRANCH_NO=?");
//            params.add(branch.getBranchNo());
//        }
//        if(null != branch.getBranchName() && !"".equals(branch.getBranchName())){
//            sb.append(" AND T.BRANCH_NAME LIKE ?");
//            params.add("%" + branch.getBranchName() + "%");
//        }
//        return sb.toString();
//    }

    @Override
    public List<Branch> findAllJobCenters() {
        final String sql = "select branch_no,branch_name,prio_branch_no,"
                + "post_code,tel,address,email,processing_center,"
                + "print_branch_no,arc_branch_no,print_branch_name,"
                + "BRANCH_STATUS,JOB_CENTER_FLAG "
                + "from a_branch where JOB_CENTER_FLAG = ? and BRANCH_STATUS = ?";
        String jobCenterFlag = JOB_CENTER_FLAG;
        String branchStatus = VALID_BRANCH_STATUS;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Branch>(Branch.class),jobCenterFlag,branchStatus);
    }

}
