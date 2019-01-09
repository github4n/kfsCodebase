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
import com.newcore.bmp.models.webvo.DepartmentVo;


/**
 * RESTFul branch 服务.
 *
 * @author lhq
 * @date 2016-7-5 13 :46:56
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface DepartmentService {

//    /**
//     * 查询机构下部门信息
//     * @param branchNo
//     * @param order
//     * @param sortingCols
//     * @param page
//     * @param rows
//     * @return Map<String, Object>
//     */
//    @GET
//    @Path("/selectDept")
//    @JsonConfig(writeType = true)
//    public Map<String, Object> selectDept(@QueryParam("branchNo") String branchNo, @QueryParam("order") String order,
//            @QueryParam("sortingCols") Integer sortingCols, @QueryParam("page") Long page,
//            @QueryParam("rows") Integer rows);

    /**
     * 创建部门信息
     * @param department
     * @return boolean
     */
    @POST
    @Path("/createDept")
    public boolean createDept(DepartmentVo department);

    /**
     * 更新部门信息
     * @param department
     * @return boolean
     */
    @POST
    @Path("/updateDept")
    public boolean updateDept(DepartmentVo department);

    /**
     * 删除部门信息
     * @param deptNo
     * @param branchNo
     * @return boolean
     */
    @GET
    @Path("/delDept")
    public boolean delDept(@QueryParam("deptNo") Integer deptNo, @QueryParam("branchNo")String branchNo);

    /**
     * 根据机构号查询部门列表菜单
     * @param branchNo
     * @return List<DepartmentVo>
     */
    @GET
    @Path("/selectCombo")
    public List<DepartmentVo> selectCombo(@QueryParam("branchNo") String branchNo);

}
