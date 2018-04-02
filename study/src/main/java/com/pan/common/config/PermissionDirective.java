package com.pan.common.config;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import com.pan.entity.Permission;
import com.pan.util.CookieUtils;
import com.pan.util.JedisUtils;
import com.pan.util.JsonUtils;

/**
 * @author 作者
 * @version 创建时间：2018年4月2日 下午2:19:22 类说明
 */
public class PermissionDirective extends Directive {

	@Override
	public String getName() {
		return "hasPermission";
	}

	@Override
	public int getType() {
		return BLOCK;
	}
	
	/**
	 * 是否有指定url的权限
	 */
	@Override
	public boolean render(InternalContextAdapter context, Writer writer,
			Node node) throws IOException, ResourceNotFoundException,
			ParseErrorException, MethodInvocationException {
		String url = (String) getMarcoParam(context, node, 0);  
        Node nodeParent = null;
        String loginUserId = CookieUtils.getLoginUserId();
		String roles = JedisUtils.getString("user_roles:"+loginUserId);
		String[] arr=(String[]) JsonUtils.fromJson(roles, String[].class);
		for (int i = 0; i < arr.length; i++) {
			Map<String, String> hgetAll = JedisUtils.hgetAll("role_permissions:"+arr[i]);
			List<Permission> list = JsonUtils.mapToList(hgetAll,Permission.class);
			for (Permission permission : list) {
				if(StringUtils.equals(permission.getUrl(),url)){
					nodeParent = node.jjtGetChild(1);
					nodeParent.render(context, writer);  
					return true;
				}
			}	
		}	
	    return false;
	}

	private static Object getMarcoParam(InternalContextAdapter context, Node node,
			int paramIndx) {
		if (node.jjtGetChild(paramIndx) != null) {
			Node n = node.jjtGetChild(paramIndx);
			if (n == null) {
				return null;
			}
			return n.value(context);
		}
		return null;
	}
}
