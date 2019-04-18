package com.pan.common.filter;

import com.pan.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author panzhigao
 */
public class XssHttpServletRequestWraper extends HttpServletRequestWrapper{


	public XssHttpServletRequestWraper(HttpServletRequest request) {
		super(request);
	}
	
    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = StringUtils.clearXss(values[i]);
        }
        return encodedValues;
    }
    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        return StringUtils.clearXss(value);
    }
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return StringUtils.clearXss(value);
    }
}
