package com.newcore.bmp.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.halo.core.common.util.PropertiesUtils;
import com.newcore.bmp.dao.api.authority.ClerkDao;
import com.newcore.bmp.models.authority.models.Clerk;
import com.newcore.bmp.models.authority.models.SessionModel;
import com.newcore.bmp.models.bo.ClerkBo;
import com.newcore.bmp.models.webvo.QueryClerk;
import com.newcore.bmp.service.api.authority.ClerkService;
import com.newcore.bmp.utils.PasswordHelper;




@Service("clerkService")
public class ClerkServiceImpl implements ClerkService {
    

	
    private static final Logger LOGGER = LoggerFactory.getLogger(ClerkServiceImpl.class);
    @Autowired
    private ClerkDao clerkDao;
    @Autowired
    private PasswordHelper passwordHelper;
//    @Autowired
//    private RoleService roleService;
//    @Autowired
//    private ResourceDao resourceDao;

    /**
     * 创建用户
     * 
     * @param clerk
     */
    @Override
    public boolean createClerk(Clerk clerk) {
        // 加密密码
        try {
            passwordHelper.encryptPassword(clerk);
            return clerkDao.createClerk(clerk);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean updateClerk(Clerk clerk) {
        try {
            return clerkDao.updateClerk(clerk);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
        
    }

    @Override
    public boolean deleteClerk(String clerkNo) {
        
        try {
            return clerkDao.deleteClerk(clerkNo);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
        
    }

    /**
     * 修改密码
     * 
     * @param clerkNo
     * @param newPassword
     */
    @Override
    public boolean changePassword(String clerkNo, String password, String newPassword) {
        
        Clerk clerk = clerkDao.findOne(clerkNo);
        if (!passwordHelper.encryptPassword(password, clerk.getCredentialsSalt()).equals(clerk.getPassword())) {
            return false;
        }
        clerkDao.changePassword(clerkNo, passwordHelper.encryptPassword(newPassword, clerk.getCredentialsSalt()));
        return true;
    }

    @Override
    public Clerk findOne(String clerkNo) {
        return clerkDao.findOne(clerkNo);
    }

    @Override
    public List<Clerk> findAll() {
        return clerkDao.findAll();
    }

    /**
     * 根据用户名查找其角色
     * 
     * @param clerkNo
     * @return
     */
//    @Override
//    @Valid
//    public Set<String> findRoles(String clerkNo) {
//        Clerk clerk = clerkDao.findOne(clerkNo);
//        if(clerk == null){
//            throw new BusinessException(ErrorCode.ClerkIsNotFound.getCode(),""
//                    + String.format(ErrorCode.ClerkIsNotFound.getMsg(), clerkNo));
//        }
//        Set<String> roles = roleService.findRoles(clerk.getBranchNo(), clerkNo);
//        return roles;
//    }

//    @Override
//    public Set<String> findRolesByClerk(String branchNo, String clerkNo) {
//        return roleService.findRoles(branchNo, clerkNo);
//    }

    @Override
    public Clerk findByClerkNo(String branchNo, String clerkNo) {
        return clerkDao.findByClerkNo(branchNo, clerkNo);
    }

//    @Override
//    @Valid
//    public List<Clerk> findClerkByRoles(List<BranchRoleParam> branchRoleParams) {
//        return clerkDao.findClerkByRoles(branchRoleParams);
//    }

    /**
     * 根据用户名查找其权限
     * 
     * @param clerkNo
     * @return
     */
//    @Override
//    public Set<String> findPermissions(String clerkNo) {
//        return clerkDao.findPermissions(clerkNo);
//    }

    @Override
    public SessionModel findSessionMsg(String clerkNo) {
        return clerkDao.findSessionMsg(clerkNo);
    }

//    @Override
//    public Map<String, Object> selectClerk(Map<String, Object> map) {
//        
//        Clerk clerk = (Clerk) map.get("clerk");
//        QueryDt query = (QueryDt) map.get("query");
//        String order = query.getOrder();
//        Integer sortingCols = query.getSortingCols();
//        Long page = query.getPage();
//        Integer rows = query.getRows();
//        String clerkNo = (String) map.get("clerkNo");
//        
//        StringBuilder sb = new StringBuilder();
//        List<Object> list = new ArrayList<>();
//        List<Object> listCount = new ArrayList<>();
//        listCount.add(clerk.getBranchNo());
//        if (null != clerk.getDeptNo()) {
//            sb.append(" and dept_no = ? ");
//            listCount.add(clerk.getDeptNo());
//        }
//        if (null != clerk.getClerkStatus() && !"".equals(clerk.getClerkStatus())) {
//            sb.append(" and clerk_status = ? ");
//            listCount.add(clerk.getClerkStatus());
//        }
//        if (null != clerk.getClerkNo() && !"".equals(clerk.getClerkNo())) {
//            sb.append(" and clerk_no = ? ");
//            listCount.add(clerk.getClerkNo());
//        }
//        if (null != clerk.getName() && !"".equals(clerk.getName())) {
//            sb.append(" and name like ? ");
//            listCount.add("%"+clerk.getName()+"%");
//        }
//        sb.append(" and clerk_no <> ?");
//        listCount.add(clerkNo);
//        list.addAll(listCount);
//        list.add(page + 1);
//        list.add(page + rows);
//        List<ClerkBo> clerkBos = clerkDao.selectClerk(sb.toString(), order, sortingCols, list);
//        List<Clerk> clerks = new ArrayList<>();
//        if(null != clerkBos && !clerkBos.isEmpty()){
//            for (ClerkBo clerkBo : clerkBos) {
//                Clerk clerk1 = new Clerk();
//                BeanUtils.copyProperties(clerkBo, clerk1);
//                clerk1.setAcceptTaskFlag(clerkBo.getAcceptTaskFlag().getKey());
//                clerk1.setAcceptTaskFlagDesc(clerkBo.getAcceptTaskFlag().getDescription());
//                clerk1.setClerkStatus(clerkBo.getClerkStatus().getKey());
//                clerk1.setClerkStatusDesc(clerkBo.getClerkStatus().getDescription());
//                clerk1.setLockFlag(clerkBo.getLockFlag().getKey());
//                clerk1.setLockFlagDesc(clerkBo.getLockFlag().getDescription());
//                clerks.add(clerk1);
//            }
//            
//        }
//        Long count = clerkDao.selectClerkCount(sb.toString(), listCount);
//        Map<String, Object> outMap = new HashMap<>();
//        outMap.put("count", count);
//        outMap.put("list", clerks);
//        return outMap;
//    }

//    @Override
//    public boolean clerkRoleAdd(Clerk clerk) {
//        
//        try {
//            clerkDao.clerkRoleAdd(clerk.getRoleIds(), clerk.getClerkNo());
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage(), e);
//           return false; 
//        }
//        return true;
//    }

    @Override
    public boolean updateClerkUser(Clerk clerk) {
        
        try {
            clerkDao.updateClerkUser(clerk);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    @Override
    public String encryptPassword(String password, String salt) {
        return passwordHelper.encryptPassword(password, salt);
    }

    @Override
    public boolean passwordReset(String clerkNo) {
        
        try {
            Clerk clerk = clerkDao.findOne(clerkNo);
            String clerkDefaultPsd = PropertiesUtils.getProperty("web.auth.clerk.default.psd");
            clerk.setPassword(clerkDefaultPsd);
            passwordHelper.encryptPassword(clerk);
            clerkDao.passwordReset(clerk);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
       
        return true;
    }

    @Override
    public boolean updateClerkATF(String clerkNo, String acceptTaskFlag) {
        
        try {
            clerkDao.updateClerkATF(clerkNo, acceptTaskFlag);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

//    @Override
//    public ClerkResource findResByClerkNo(String clerkNo) {
//        ClerkResource clerkResource = clerkDao.findResByClerkNo(clerkNo);
//        List<Resource> resources = resourceDao.findResByClerkNo(clerkNo);
//        clerkResource.setResources(resources);
//        return clerkResource;
//    }

    @Override
    public boolean updateLionFailNum(String clerkNo, Integer loginFailNum, String lockFlag) {
        
        try {
            clerkDao.updateLionFailNum(clerkNo, loginFailNum, lockFlag);
        } catch (Exception e) {
            LOGGER.error("修改操作员["+clerkNo+"]连续登录次数失败！", e);
            return false;
        }
        
        return true;
    }

    //kfs20180427
	@Override	
	public boolean ChangePassword(QueryClerk qc) {
	    	String clerkNo =  qc.getUserName();
	    	String password = qc.getPassword(); 
	    	String newPassword = qc.getNewPassword();
	    	
//	    	System.out.println("clerkNo ="+clerkNo);
//	    	System.out.println("password ="+password);
//	    	System.out.println("newPassword ="+newPassword);

	        Clerk clerk = clerkDao.findOne(clerkNo);
	        if (!passwordHelper.encryptPassword(password, clerk.getCredentialsSalt()).equals(clerk.getPassword())) {
	            return false;
	        }
	        clerkDao.changePassword(clerkNo, passwordHelper.encryptPassword(newPassword, clerk.getCredentialsSalt()));
	        return true;
	}
	


	@Override
	public ClerkBo GetInfoByClerkNo(Clerk qc) {
		return clerkDao.findInfoByClerkNo(qc.getUserName());
	}


}
