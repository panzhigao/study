package com.pan.common.config;

import com.pan.util.SystemConfigUtils;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import java.io.IOException;
import java.io.Writer;


/**
 * @author 作者
 * @version 创建时间：2018年4月2日 下午2:19:22 类说明
 */
public class SystmConfigDirective extends Directive {

	@Override
	public String getName() {
		return "config";
	}

	@Override
	public int getType() {
		return BLOCK;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer,
			Node node) throws IOException, ResourceNotFoundException,
			ParseErrorException, MethodInvocationException {
		String paramName = (String) getMarcoParam(context, node, 0);
		String paramValue = SystemConfigUtils.getParamValue(paramName);
		writer.write(paramValue);
		return true;
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
