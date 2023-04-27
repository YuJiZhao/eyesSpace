package com.eyes.eyesspace.sync.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author eyesYeager
 * @date 2023/2/10 15:34
 */
@Data
@ApiModel
public class UserInfoUpdateRequest {
  @ApiModelProperty("用户昵称")
  @NotNull(message = "用户昵称不能为空")
  @Length(min = 2, max = 10, message = "昵称必须在2-10个字符之间")
  private String name;
}
