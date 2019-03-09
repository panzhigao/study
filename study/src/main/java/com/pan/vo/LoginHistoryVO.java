package com.pan.vo;

import java.util.Date;
import lombok.Data;

@Data
public class LoginHistoryVO {
	
	private String ipStr;
	
	private Date createTime;
	
	private String userAgent;
}
