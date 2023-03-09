package com.fantasy.controller.admin;

import com.fantasy.entity.CityVisitor;
import com.fantasy.model.Result.Result;
import com.fantasy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理仪表盘
 */
@RestController
@RequestMapping("/admin")
public class DashboardAdminController {

	@Autowired
	private IVisitLogService visitLogService;
	
	@Autowired
	private IBlogService blogService;
	
	@Autowired
	private ICommentService commentService;
	
	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private IBlogTagService blogTagService;
	
	@Autowired
	private IVisitRecordService visitRecordService;
	
	@Autowired
	private ICityVisitorService cityVisitorService;


	@GetMapping("/dashboard")
	public Result dashboard() {
		int todayPV = visitLogService.countVisitLogByToday();
		//TODO 使用Redis
		//int todayUV = redisService.countBySet(RedisKeyConfig.IDENTIFICATION_SET);
		int todayUV = 0;
		int blogCount = blogService.getBlogCount();
		int commentCount = commentService.getCommentCount();
		Map<String, List> categoryBlogCountMap = categoryService.getCategoryBlogCountMap();
		Map<String, List> tagBlogCountMap = blogTagService.getTagBlogCountMap();
		Map<String, List> visitRecordMap = visitRecordService.getVisitRecordMap();
		List<CityVisitor> cityVisitorList = cityVisitorService.getCityVisitorList();

		Map<String, Object> map = new HashMap<>();
		map.put("pv", todayPV);
		map.put("uv", todayUV);
		map.put("blogCount", blogCount);
		map.put("commentCount", commentCount);
		map.put("category", categoryBlogCountMap);
		map.put("tag", tagBlogCountMap);
		map.put("visitRecord", visitRecordMap);
		map.put("cityVisitor", cityVisitorList);
		return Result.ok("获取成功", map);
	}
}
