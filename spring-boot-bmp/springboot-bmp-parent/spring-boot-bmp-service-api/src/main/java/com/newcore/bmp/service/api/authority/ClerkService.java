package com.newcore.bmp.service.api.authority;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.halo.core.fastjson.annotation.JsonConfig;
import com.newcore.bmp.models.authority.models.Clerk;
import com.newcore.bmp.models.authority.models.ClerkResource;
import com.newcore.bmp.models.authority.models.SessionModel;
import com.newcore.bmp.models.authority.param.BranchRoleParam;
import com.newcore.bmp.models.bo.ClerkBo;
import com.newcore.bmp.models.webvo.QueryClerk;
import com.newcore.bmp.models.webvo.QueryCond;

/**
 * RESTFul clerk 服务.
 * 
 * @author chenyong
 * @since 2016-10-25 09:45:44
 */

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ClerkService {

    /**
     * 根据机构号，工号查询角色清单.
     * 
     * @param branchNo
     * @param clerkNo
     * @return
     * @author chenyong
     * @since 2016-10-25 09:46:40
     */
//    @GET
//    @Path("/findroles")
//    public Set<String> findRolesByClerk(@QueryParam("branchNo") String branchNo, @QueryParam("clerkNo") String clerkNo);

    /**
     * 密码加密
     * 
     * @param password
     * @param passwordSalt
     * @return
     * @author chenyong
     * @since 2016-10-25 09:47:57
     */
    @GET
    @Path("/encryptPassword")
    public String encryptPassword(@QueryParam("password") String password,
            @QueryParam("passwordSalt") String passwordSalt);

    /**
     * 根据机构号,角色信息查询操作员清单.
     * 
     * @param branchandrole
     * @return
     * @author chenyong
     * @since 2016-10-25 09:48:24
     */
//    @POST
//    @Path("/findclerkbyroles")
//    @JsonConfig(writeType = true)
//    public List<Clerk> findClerkByRoles(@JsonConfig(writeType = true) List<BranchRoleParam> branchandrole);

    /**
     * 新增操作员信息
     * 
     * @param clerk
     * @return
     * @author chenyong
     * @since 2016-10-25 09:49:06
     */
    @POST
    @Path("/createClerk")
    public boolean createClerk(@JsonConfig(writeType = true) Clerk clerk);

//    //kfs20180427
//	@POST
//	@Path("/CreateClerk")
//    public boolean CreateClerk(Clerk clerk);
//	
    //kfs20180427
    
    /**
     * 修改操作员信息
     * 
     * @param clerk
     * @return
     * @author chenyong
     * @since 2016-10-25 09:49:38
     */
    @POST
    @Path("/updateClerk")
    public boolean updateClerk(@JsonConfig(writeType = true) Clerk clerk);

    /**
     * 修改操作员电话、邮箱
     * 
     * @param clerk
     * @return
     * @author chenyong
     * @since 2016-10-25 09:52:28
     */
    @POST
    @Path("/updateClerkUser")
    public boolean updateClerkUser(@JsonConfig(writeType = true) Clerk clerk);

    /**
     * 删除操作员信息
     * 
     * @param clerkNo
     * @return
     * @author chenyong
     * @since 2016-10-25 09:53:03
     */
    @POST
    @Path("/deleteClerk")
    public boolean deleteClerk(String clerkNo);

    /**
     * 修改操作员密码
     * 
     * @param clerkNo
     * @param password
     * @param newPassword
     * @return
     * @author chenyong
     * @since 2016-10-25 09:53:38
     */
    @GET
    @Path("/changePassword")
    public boolean changePassword(@QueryParam("clerkNo") String clerkNo, @QueryParam("password") String password,
            @QueryParam("newPassword") String newPassword);
    
    //kfs20180427
    
	@POST
	@Path("/ChangePassword")
    public boolean ChangePassword(QueryClerk qc);
	
    //kfs20180427

    /**
     * 根据操作员工号查询操作员信息
     * 
     * @param clerkNo
     * @return
     * @author chenyong
     * @since 2016-10-25 09:54:21
     */
    @GET
    @Path("/findOne")
    @JsonConfig(writeType = true)
    public Clerk findOne(@QueryParam("clerkNo") String clerkNo);

    /**
     * 根据操作员工号及机构号查询操作员信息
     * 
     * @param branchNo
     * @param clerkNo
     * @return
     * @author chenyong
     * @since 2016-10-25 09:55:01
     */
    @GET
    @Path("/findByClerkNo")
    @JsonConfig(writeType = true)
    public Clerk findByClerkNo(@QueryParam("branchNo") String branchNo, @QueryParam("clerkNo") String clerkNo);

    /**
     * 查询所有操作员信息
     * 
     * @return
     * @author chenyong
     * @since 2016-10-25 09:55:37
     */
    @GET
    @Path("/findAll")
    @JsonConfig(writeType = true)
    public List<Clerk> findAll();

    /**
     * 根据用户名查找其角色
     * 
     * @param clerkname
     * @return
     * @author chenyong
     * @since 2016-10-25 09:56:08
     */
//    @GET
//    @Path("/findRolesByClerkName")
//    public Set<String> findRoles(@QueryParam("clerkNo") String clerkNo);

    /**
     * 根据操作员工号查询权限
     * 
     * @param clerkNo
     * @return
     * @author chenyong
     * @since 2016-10-25 09:56:58
     */
//    @GET
//    @Path("/findPermissions")
//    public Set<String> findPermissions(@QueryParam("clerkNo") String clerkNo);

    /**
     * 根据操作员工号查询操作员session信息
     * 
     * @param clerkNo
     * @return
     * @author chenyong
     * @since 2016-10-25 09:57:54
     */
    @GET
    @Path("/findSession")
    public SessionModel findSessionMsg(@QueryParam("clerkNo") String clerkNo);

//    /**
//     * 分页查询操作员信息
//     * 
//     * @param map
//     * @return
//     * @author chenyong
//     * @since 2016-10-25 09:59:26
//     */
//    @POST
//    @Path("/selectDept")
//    @JsonConfig(writeType = true)
//    public Map<String, Object> selectClerk(@JsonConfig(writeType = true) Map<String, Object> map);

    /**
     * 给操作员授权角色
     * 
     * @param clerk
     * @return
     * @author chenyong
     * @since 2016-10-25 10:00:04
     */
//    @POST
//    @Path("/clerkRoleAdd")
//    public boolean clerkRoleAdd(@JsonConfig(writeType = true) Clerk clerk);

    /**
     * 根据操作员工号重置操作员密码
     * 
     * @param clerkNo
     * @return
     * @author chenyong
     * @since 2016-10-25 10:01:02
     */
    @GET
    @Path("/passwordReset")
    public boolean passwordReset(@QueryParam("clerkNo") String clerkNo);

    /**
     * 修改操作员是否接收任务状态
     * 
     * @param clerkNo
     * @param acceptTaskFlag
     * @return
     * @author chenyong
     * @since 2016-10-25 10:05:58
     */
    @GET
    @Path("/updateClerkATF")
    boolean updateClerkATF(@QueryParam("clerkNo") String clerkNo, @QueryParam("acceptTaskFlag") String acceptTaskFlag);

    /**
     * 根据操作员工号查询操作员信息及资源权限信息
     * 
     * @param clerkNo
     * @return
     * @author chenyong
     * @since 2016-10-25 10:09:59
     */
//    @GET
//    @Path("/findResByClerkNo")
//    @JsonConfig(writeType = true)
//    public ClerkResource findResByClerkNo(@QueryParam("clerkNo") String clerkNo);
    
    /**
     * 修改操作员连续登录失败次数、是否锁定
     * @param clerkNo      操作员工号
     * @param lockFlag     是否锁定
     * @param lionFailNum  连续登录失败次数
     * @return
     */
    @GET
    @Path("/updateLionFailNum")
    @JsonConfig(writeType = true)
    public boolean updateLionFailNum(@QueryParam("clerkNo") String clerkNo, @QueryParam("lionFailNum") Integer loginFailNum, @QueryParam("lockFlag") String lockFlag);

    //kfs20180428
	@POST
	@Path("/GetInfoByClerkNo")
	ClerkBo GetInfoByClerkNo(Clerk qc);
    //kfs20180428

    
}
