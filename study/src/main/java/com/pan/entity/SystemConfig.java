package com.pan.entity;

import com.pan.common.annotation.LogMeta;
import com.pan.common.annotation.UnescapeHtml;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * @author panzhigao
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class SystemConfig extends BaseEntity{

    private static final long serialVersionUID = -6991864577573601166L;
    /**
     * 变量名
     */
    @NotEmpty(message="变量名不能为空")
    @Size(max = 100,message = "变量名不能超过100的字符")
    @LogMeta(fieldDesc="变量名")
    private String paramName;
    /**
     * 变量值
     */
    @NotEmpty(message="变量值不能为空")
    @Size(max = 2000,message = "变量值不能超过2000的字符")
    @LogMeta(fieldDesc="变量值")
    @UnescapeHtml
    private String paramValue;
    /**
     * 变量备注
     */
    @Size(max = 100,message = "变量备注不能超过100的字符")
    @LogMeta(fieldDesc="变量备注")
    private String remark;
}