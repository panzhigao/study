package com.pan.mapper;


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
	}

	@Test
	public void testGetRoleIdsByUserId() {
	}

	@Test
	public void testFindByRoleIds() {
		Long[] roleIds={10006L,1001308L};
		List<Role> findByRoleIds = roleMapper.findByRoleIds(roleIds);
		System.out.println(findByRoleIds);
	}

	@Test
	public void testSelectByPrimaryKey() {
	}

	@Test
	public void testDeleteByPrimaryKey() {
	}

	@Test
	public void testInsertSelective() {
	}

	@Test
	public void testUpdateByPrimaryKeySelective() {
	}

	@Test
	public void testFindPagable() {
	}

	@Test
	public void testCountByParams() {
	}

}
