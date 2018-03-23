package com.pan.init;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import com.pan.entity.Role;
import com.pan.service.PermissionService;
import com.pan.service.RoleService;

public class InitTheContext implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PermissionService permissionService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() == null){
			List<Role> roles = roleService.findAll();
			for (Role role : roles) {
				String roleId=role.getRoleId();
				roleService.recachePermissionByRoleId(roleId);
			}
		}
	}

}
