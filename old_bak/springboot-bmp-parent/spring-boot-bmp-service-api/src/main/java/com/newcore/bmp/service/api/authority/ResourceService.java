package com.newcore.bmp.service.api.authority;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.newcore.bmp.models.authority.models.Resource;

/**
 * RESTFul resource服务.
 * 
 * @author chenyong
 * @since 2016-10-25 10:10:38
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ResourceService {

    /**
     * 新增资源信息
     * 
     * @param resource
     * @return
     * @author chenyong
     * @since 2016-10-25 10:11:07
     */
    @POST
    @Path("/createresource")
    public Resource createResource(Resource resource);

    /**
     * 修改资源信息
     * 
     * @param resource
     * @return
     * @author chenyong
     * @since 2016-10-25 10:11:31
     */
    @POST
    @Path("/updateResource")
    public Resource updateResource(Resource resource);

    /**
     * 根据资源id删除资源信息
     * 
     * @param resourceId
     * @author chenyong
     * @since 2016-10-25 10:12:10
     */
    @POST
    @Path("/deleteResource")
    public void deleteResource(String resourceId);

    /**
     * 根据资源id查询资源信息
     * 
     * @param resourceId
     * @return
     * @author chenyong
     * @since 2016-10-25 10:12:41
     */
    @POST
    @Path("/findOne")
    public Resource findOne(String resourceId);

    /**
     * 查询所有资源信息
     * 
     * @return
     * @author chenyong
     * @since 2016-10-25 10:13:07
     */
    @GET
    @Path("/findAll")
    public List<Resource> findAll();

    /**
     * 根据操作员工号查询资源信息
     * 
     * @param clerkNo
     * @return
     * @author chenyong
     * @since 2016-10-25 10:13:48
     */
    @GET
    @Path("/findResByClerkNo")
    public List<Resource> findResByClerkNo(@QueryParam("clerkNo") String clerkNo);

    /**
     * 根据操作员工号查询常用菜单资源信息
     * 
     * @param clerkNo
     * @return
     * @author chenyong
     * @since 2016-10-25 10:15:04
     */
    @GET
    @Path("/comMenuSearch")
    public List<Resource> comMenuSearch(@QueryParam("clerkNo") String clerkNo);

    /**
     * 根据操作员工号查询工具条资源信息
     * 
     * @param clerkNo
     * @return
     * @author chenyong
     * @since 2016-10-25 10:15:55
     */
    @GET
    @Path("/toolsSearch")
    public List<Resource> toolsSearch(@QueryParam("clerkNo") String clerkNo);

    /**
     * 根据操作员工号查询工具条资源下来框信息
     * 
     * @param clerkNo
     * @return
     * @author chenyong
     * @since 2016-10-25 10:17:16
     */
    @GET
    @Path("/toolsCombo")
    public List<Resource> toolsCombo(@QueryParam("clerkNo") String clerkNo);

    /**
     * 根据操作员工号及资源名称模糊查询页面资源信息
     * 
     * @param clerkNo
     * @param resourceName
     * @return
     * @author chenyong
     * @since 2016-10-25 10:18:02
     */
    @GET
    @Path("/sidebarMenuSearch")
    public List<Resource> sidebarMenuSearch(@QueryParam("clerkNo") String clerkNo,
            @QueryParam("resourceName") String resourceName);
}
