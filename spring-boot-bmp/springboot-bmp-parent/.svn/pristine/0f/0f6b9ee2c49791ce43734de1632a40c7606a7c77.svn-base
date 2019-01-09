package com.newcore.bmp.service.api.authority;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.halo.core.fastjson.annotation.JsonConfig;
import com.newcore.bmp.models.authority.models.Branch;
import com.newcore.supports.models.service.bo.PageData;

/**
 * RESTFul branch 服务.
 * 
 * @author chenyong
 * @since 2016-10-25 09:45:21
 *
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface BranchService {

    /**
     * 创建分支机构
     * 
     * @param branch
     * @return
     * @author chenyong
     * @since 2016-10-25 09:34:07
     */
    @POST
    @Path("/createbranch")
    @JsonConfig(writeType = true)
    public Branch createBranch(@JsonConfig(writeType = true) Branch branch);

    /**
     * 更新分支机构.
     * 
     * @param branch
     * @return
     * @author chenyong
     * @since 2016-10-25 09:33:20
     */
    @POST
    @Path("/updatebranch")
    @JsonConfig(writeType = true)
    public Branch updateBranch(@JsonConfig(writeType = true) Branch branch);

    /**
     * 删除机构及所有下级机构.
     * 
     * @param branchNo
     * @return
     * @author chenyong
     * @since 2016-10-25 09:31:08
     */
    @GET
    @Path("/deletebranch")
    public boolean deleteBranch(@QueryParam("branchNo") String branchNo);

    /**
     * 根际机构号，查询本机构信息.
     * 
     * @param branchNo
     * @return
     * @author chenyong
     * @since 2016-10-25 09:34:46
     */
    @GET
    @Path("/findOne")
    public Branch findOne(@QueryParam("branchNo") String branchNo);

    /**
     * 根际机构号，查询下级机构列表.
     * 
     * @param branchNo
     * @return
     * @author chenyong
     * @since 2016-10-25 09:35:23
     */
    @GET
    @Path("/findsub")
    @JsonConfig(writeType = true)
    public List<Branch> findSubBranch(@QueryParam("branchNo") String branchNo);

    /**
     * 根际机构号，查询上下级机构列表.
     * 
     * @param branchNo
     * @param type
     * @return
     * @author chenyong
     * @since 2016-10-25 09:35:46
     */
    @GET
    @Path("/findSupSub")
    @JsonConfig(writeType = true)
    public List<Branch> findSupSubBranch(@QueryParam("branchNo") String branchNo, @QueryParam("branchLevelType") String branchLevelType);

    /**
     * 根据机构号查询查询该机构及下级所有机构信息
     * 
     * @param branchNo
     * @return
     * @author chenyong
     * @since 2016-10-25 09:39:52
     */
    @GET
    @Path("/findSubBranchAll")
    @JsonConfig(writeType = true)
    public Branch findSubBranchAll(@QueryParam("branchNo") String branchNo);

    /**
     * 根据登录人机构号查询机构下拉列表信息
     * 
     * @param branchNo
     * @param type
     * @return
     * @author chenyong
     * @since 2016-10-25 09:41:54
     */
    @GET
    @Path("/findSubBranchCombox")
    @JsonConfig(writeType = true)
    public List<Branch> findSubBranchCombox(@QueryParam("branchNo") String branchNo, @QueryParam("selBranchNo") String selBranchNo);

    /**
     * 根际机构号，查询本机构的省级机构信息.
     * 
     * @param branchNo
     * @return
     * @author chenyong
     * @since 2016-10-25 09:43:35
     */
    @GET
    @Path("/findprovince")
    public String findProvinceBranch(@QueryParam("branchNo") String branchNo);

    /**
     * 查询所有机构信息
     * 
     * @return
     * @author chenyong
     * @since 2016-10-25 09:44:30
     */
    @GET
    @Path("/findAll")
    @JsonConfig(writeType = true)
    public List<Branch> findAll();
    
//    /**
//     * 分页查询机构信息
//     * @param map 分页信息及查询结构信息
//     * @return
//     * @author chenyong
//     * @since  2016-11-23 08:49:42
//     */
//    @POST
//    @Path("/findPageBranch")
//    @JsonConfig(writeType = true)
//    public PageData<Branch> findPageBranch(@JsonConfig(writeType = true)Map<String, Object> map);
    
    /**
     * 查询所有的作业中心列表
     * @return 
     * @since  2017-01-16 09:49:42
     */
    @GET
    @Path("findAllJobCenters")
    public List<Branch> findAllJobCenters();

}
