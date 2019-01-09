package com.newcore.bmp.dao.api.authority;

import java.util.List;
import java.util.Set;

import com.newcore.bmp.models.authority.models.Clerk;
import com.newcore.bmp.models.authority.models.ClerkResource;
import com.newcore.bmp.models.authority.models.SessionModel;
import com.newcore.bmp.models.authority.param.BranchRoleParam;
import com.newcore.bmp.models.bo.ClerkBo;

public interface ClerkDao {

    public boolean createClerk(Clerk clerk);

    public boolean updateClerk(Clerk clerk);

    public boolean deleteClerk(String clerkNo);

    public void changePassword(String clerkNo, String newPassword);

    public Clerk findOne(String clerkNo);

    public List<Clerk> findAll();

    public Clerk findByClerkNo(String branchNo, String clerkNo);

//    public List<Clerk> findClerkByRoles(List<BranchRoleParam> branchRoleParams);

//    public Set<String> findPermissions(String clerkNo);

    public SessionModel findSessionMsg(String clerkNo);

    public List<ClerkBo> selectClerk(String wheres, String order, Integer sortingCols, List<Object> list);

    public Long selectClerkCount(String wheres, List<Object> list);

//    public void clerkRoleAdd(List<String> roleIds, String clerkNo);

    public void updateClerkUser(Clerk clerk);

    public void passwordReset(Clerk clerk);

    public void updateClerkATF(String clerkNo, String acceptTaskFlag);

    public ClerkResource findResByClerkNo(String clerkNo);

    public void updateLionFailNum(String clerkNo, Integer loginFailNum, String lockFlag);

	ClerkBo findInfoByClerkNo(String clerkNo);

}
