package com.pan.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author panzhigao
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class QuerySystemConfig extends QueryBase{
    /**
     * 变量名
     */
    private String paramName;
}
