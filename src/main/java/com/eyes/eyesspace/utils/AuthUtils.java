package com.eyes.eyesspace.utils;

import com.eyes.eyesAuth.constant.AuthConfigConstant;
import com.eyes.eyesspace.constant.StatusEnum;

/**
 * 权限相关工具
 * @author eyesYeager
 * @date 2023/5/22 15:07
 */

public class AuthUtils {

  /**
   * 获取数据状态的SQL拼接字段
   * 默认字段名为status
   * @param role 角色
   * @return SQL
   */
  public static String statusSqlCondition(String role) {
    return statusSqlCondition("status", role);
  }

  /**
   * 获取数据状态的SQL拼接字段
   * 记得用${}拼接
   * @param status 状态字段名
   * @param role 角色
   * @return SQL
   */
  public static String statusSqlCondition(String status, String role) {
    if (AuthConfigConstant.ROLE_ADMIN.equals(role)) {
      return status + "!=" + StatusEnum.DELETE.getStatus();
    } else {
      return status + "=" + StatusEnum.PUBLIC.getStatus();
    }
  }
}
