package com.pan.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author panzhigao
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class QueryExceptionLog extends QueryBase{
    /**
     * 用户名
     */
    private String username;
}
