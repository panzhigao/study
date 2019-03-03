package com.pan.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author panzhigao
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class QueryLoginHistory extends QueryBase{
    /**
     * 用户id
     */
    private String userId;
}
