package top.ywwxhz.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import top.ywwxhz.entry.TaskBaseInfo;
import top.ywwxhz.entry.TaskInfo;
import top.ywwxhz.exception.ServiceException;
import top.ywwxhz.service.TaskServiceImpl;
import top.ywwxhz.utils.GSONTools;
import top.ywwxhz.utils.R;

/**
 * 任务管理
 * 
 * @author lance
 */
@Slf4j
@Controller
@RequestMapping("/jobs")
public class TaskManageController {
	@Autowired
	private TaskServiceImpl taskServiceImpl;
	private static final String RESOURCE_PATTERN = "**/*.class";
	private List<String> jobs;
	
	public TaskManageController() throws IOException {
		findJobs("top.ywwxhz.job");
	}

	/**
	 * 获取符合要求的Controller名称
	 * 
	 * @ComponentScan就是使用这些代码扫描包，然后通过TypeFilter过滤想要的
	 * @ComponentScan扫描时添加了一个AnnotationTypeFilter(Component.class, false)的类型过滤
	 *
	 * @param basePackage
	 * @return
	 * @throws IOException
	 */
	private void findJobs(String basePackage) throws IOException {
		if (log.isDebugEnabled()) {
			log.debug("开始扫描包[" + basePackage + "]下的所有类");
		}
		jobs = new ArrayList<String>();
		String packageSearchPath = replaceDotByDelimiter(basePackage) + '/' + RESOURCE_PATTERN;
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		MetadataReaderFactory readerFactory = new SimpleMetadataReaderFactory(resourceLoader);
		Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
				.getResources(packageSearchPath);
		for (Resource resource : resources) {
			MetadataReader reader = readerFactory.getMetadataReader(resource);
			jobs.add(reader.getClassMetadata().getClassName());
		}
		
	}

	/**
	 * 用"/"替换包路径中"."
	 *
	 * @param path
	 * @return
	 */
	private String replaceDotByDelimiter(String path) {
		return StringUtils.replace(path, ".", "/");
	}

	/**
	 * 任务列表
	 * 
	 * @return 2016年10月9日上午11:36:03
	 */
	@ResponseBody
	@GetMapping(value = "avalibleJob")
	@ApiOperation("列出所有可用的Job")
	public R avalibleJob() {
		return R.success(jobs);
	}

	/**
	 * 任务列表
	 * 
	 * @return 2016年10月9日上午11:36:03
	 */
	@ResponseBody
	@GetMapping(value = "list")
	@ApiOperation("列出所有添加的 Job")
	public String list() {
		Map<String, Object> map = new HashMap<>();
		List<TaskInfo> infos = taskServiceImpl.list();
		map.put("rows", infos);
		map.put("total", infos.size());
		return GSONTools.toJson(map);
	}

	/**
	 * 保存定时任务
	 * 
	 * @param info
	 *            2016年10月9日下午1:36:59
	 */
	@ResponseBody
	@PutMapping(value = "save", produces = "application/json; charset=UTF-8")
	@ApiOperation("添加或修改 Job")
	public R save(@RequestBody TaskBaseInfo info) {
		try {
			if (info.getId() == 0) {
				taskServiceImpl.addJob(info);
			} else {
				taskServiceImpl.edit(info);
			}
		} catch (ServiceException e) {
			return R.error(e.getMessage());
		}
		return R.success();
	}

	/**
	 * 删除定时任务
	 * 
	 * @param jobName
	 * @param jobGroup
	 *            2016年10月9日下午1:52:20
	 */
	@ResponseBody
	@DeleteMapping(value = "delete/{jobName}/{jobGroup}", produces = "application/json; charset=UTF-8")
	@ApiOperation("删除 Job")
	public R delete(@PathVariable String jobName, @PathVariable String jobGroup) {
		try {
			taskServiceImpl.delete(jobName, jobGroup);
		} catch (ServiceException e) {
			return R.error(e.getMessage());
		}
		return R.success();
	}

	/**
	 * 暂停定时任务
	 * 
	 * @param jobName
	 * @param jobGroup
	 *            2016年10月10日上午9:41:25
	 */
	@ResponseBody
	@PostMapping(value = "pause/{jobName}/{jobGroup}", produces = "application/json; charset=UTF-8")
	@ApiOperation("暂停 Job")
	public R pause(@PathVariable String jobName, @PathVariable String jobGroup) {
		try {
			taskServiceImpl.pause(jobName, jobGroup);
		} catch (ServiceException e) {
			return R.error(e.getMessage());
		}
		return R.success();
	}

	/**
	 * 重新开始定时任务
	 * 
	 * @param jobName
	 * @param jobGroup
	 *            2016年10月10日上午9:41:40
	 */
	@ResponseBody
	@PostMapping(value = "resume/{jobName}/{jobGroup}", produces = "application/json; charset=UTF-8")
	@ApiOperation("重启 Job")
	public R resume(@PathVariable String jobName, @PathVariable String jobGroup) {
		try {
			taskServiceImpl.resume(jobName, jobGroup);
		} catch (ServiceException e) {
			return R.error(e.getMessage());
		}
		return R.success();
	}
}
