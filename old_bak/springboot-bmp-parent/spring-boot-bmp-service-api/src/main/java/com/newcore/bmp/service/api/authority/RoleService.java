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

import com.halo.core.fastjson.annotation.JsonConfig;
import com.newcore.bmp.models.authority.models.Role;
import com.newcore.bmp.models.authority.models.TreeModel;

/**
 * RESTFul role服务.
 * 
 * @author chenyong
 * @since 2016-10-25 10:20:28
 */

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RoleService {

    /**
     * 新增角色信息
     * 
     * @param role
     * @return
     * @author chenyong
     * @since 2016-10-25 10:21:03
     */
    @POST
    @Path("/createrole")
    public boolean createRole(Role role);

    /**
     * 更新角色.
     * 
     * @param role
     * @return
     * @author chenyong
     * @since 2016-10-25 10:21:33
     */
    @POST
    @Path("/updaterole")
    public boolean updateRole(Role role);

    /**
     * 根据角色id删除角色信息
     * 
     * @param roleId
     * @return
     * @author chenyong
     * @since 2016-10-25 10:22:03
     */
    @POST
    @Path("/deleteRole")
    public boolean deleteRole(String roleId);
//
//    /**
//     * 分页查询角色信息列表
//     * 
//     * @param map
//     * @return
//     * @author chenyong
//     * @since 2016-10-25 10:22:47
//     */
//    @POST
//    @Path("/selectDept")
//    @JsonConfig(writeType = true)
//    public Map<String, Object> selectRole(@JsonConfig(writeType = true) Map<String, Object> map);

    /**
     * 根据角色编号得到角色标识符列表
     * 
     * @param branchNo
     * @param clerkNo
     * @return
     * @author chenyong
     * @since 2016-10-25 10:23:15
     */
    @GET
    @Path("/findRoles")
    Set<String> findRoles(@QueryParam("branchNo") String branchNo, @QueryParam("clerkNo") String clerkNo);

    /**
     * 根据角色组查询角色列表.
     * 
     * @param roleGroup
     * @return
     * @author chenyong
     * @since 2016-10-25 10:23:39
     */
    @GET
    @Path("/findbygroup")
    List<Role> findByGroup(@QueryParam("roleGroup") String roleGroup);

    /**
     * 根据操作员工号查询角色信息列表
     * 
     * @param clerkNo
     * @return
     * @author chenyong
     * @since 2016-10-25 10:24:17
     */
    @GET
    @Path("/roleSelect")
    List<Role> roleSelect(@QueryParam("clerkNo") String clerkNo);

    /**
     * 根据角色id查询该角色下的资源树信息
     * 
     * @param roleId
     * @return
     * @author chenyong
     * @since 2016-10-25 10:25:06
     */
    @GET
    @Path("/roleResoucesTree")
    @JsonConfig(writeType = true)
    public List<TreeModel> roleResoucesTree(@QueryParam("roleId") String roleId);

    /**
     * 给角色添加资源信息
     * 
     * @param map
     * @return
     * @author chenyong
     * @since 2016-10-25 10:25:53
     */
    @POST
    @Path("/addRoleResouce")
    public boolean addRoleResouce(@JsonConfig(writeType = true) Map<String, Object> map);
}
