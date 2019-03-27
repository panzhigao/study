package com.pan.entity;

import com.pan.common.annotation.LogMeta;
import com.pan.common.annotation.UnescapeHtml;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统配置
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class SystemConfig extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3773213796788663104L;
	/**
	 * 网站名
	 */
	@LogMeta(fieldDesc="网站名")
	private String webName;
	/**
	 * 网站标题
	 */
	@LogMeta(fieldDesc="网站标题")
    private String webTitle;
	/**
	 * 网站关键字
	 */
	@LogMeta(fieldDesc="网站关键字")
    private String keywords;
	/**
	 * 网站描述
	 */
	@LogMeta(fieldDesc="网站描述")
    private String description;
	/**
	 * 图片上传路径
	 */
	@LogMeta(fieldDesc="图片上传路径")
    private String imageUploadDir;
	/**
	 * 备案信息
	 */
	@LogMeta(fieldDesc="备案信息")
	@UnescapeHtml
    private String recordInfo;
	/**
	 * 网站公共代码
	 */
	@LogMeta(fieldDesc="网站公共代码")
	@UnescapeHtml
    private String webCode;
	/**
	 * 网站埋点代码
	 */
	@LogMeta(fieldDesc="网站埋点代码")
	@UnescapeHtml
    private String buryingPointCode;
}