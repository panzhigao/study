package com.pan.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pan.common.vo.ResultMsg;
import com.pan.entity.Permission;
import com.pan.service.PermissionService;

/**
 * 权限管理
 * @author Administrator
 *
 */
@Controller
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping(method=RequestMethod.POST,value="/user/permission/add")
	@ResponseBody
	public ResultMsg addPermission(Permission permission){
		permissionService.addPermission(permission);
		return ResultMsg.ok("新增权限成功");
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/user/permission/get_permissions")
	@ResponseBody
	public Map<String,Object> loadPermissions(Integer pageSize,Integer pageNo,String permissionName){
		Map<String,Object> params=new HashMap<String, Object>(5);
		Integer offset=(pageNo-1)*pageSize;
		params.put("offset", offset);
		params.put("row", pageSize);
		params.put("permissionName", permissionName);
		Map<String,Object> pageData=permissionService.findByParams(params);
		return pageData;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/user/permission/delete")
	@ResponseBody
	public ResultMsg deletePermissions(String permissionId){
		permissionService.deletePermission(permissionId);
		return ResultMsg.ok("删除权限成功");
	}
}
