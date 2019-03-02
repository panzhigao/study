package com.pan.mapper;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pan.entity.Role;
import com.pan.test.base.BaseTest;

public class RoleMapperTest extends BaseTest {
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Test
	public void testGetRoleSelectedByUserId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRoleIdsByUserId() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindByRoleIds() {
		String[] roleIds={"r10006","r1001308"};
		List<Role> findByRoleIds = roleMapper.findByRoleIds(roleIds);
		System.out.println(findByRoleIds);
	}

	@Test
	public void testSelectByPrimaryKey() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteByPrimaryKey() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertSelective() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateByPrimaryKeySelective() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindPagable() {
		fail("Not yet implemented");
	}

	@Test
	public void testCountByParams() {
		fail("Not yet implemented");
	}

}
