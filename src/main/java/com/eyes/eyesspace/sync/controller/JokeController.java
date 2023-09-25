package com.eyes.eyesspace.sync.controller;

import com.eyes.eyesAuth.limiter.Limiter;
import com.eyes.eyesAuth.permission.Permission;
import com.eyes.eyesAuth.permission.PermissionEnum;
import com.eyes.eyesTools.common.exception.CustomException;
import com.eyes.eyesTools.common.result.Result;
import com.eyes.eyesTools.utils.PageBind;
import com.eyes.eyesspace.sync.model.dto.JokeListDTO;
import com.eyes.eyesspace.sync.model.request.JokeAddRequest;
import com.eyes.eyesspace.sync.model.vo.FileUploadVO;
import com.eyes.eyesspace.sync.model.vo.JokeAddVO;
import com.eyes.eyesspace.sync.model.vo.JokeNoticeVO;
import com.eyes.eyesspace.sync.service.JokeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author eyesYeager
 * @date 2023/9/25 8:42
 */
@Api(tags = "梗图模块")
@RefreshScope
@Validated
@RestController
@RequestMapping("/joke")
public class JokeController {

  @Value("${business.page-size.joke:20}")
  private Integer jokePageSize;

  private final JokeService jokeService;

  public JokeController(JokeService jokeService) {
    this.jokeService = jokeService;
  }

  @ApiOperation("添加梗图")
  @Permission(PermissionEnum.ADMIN)
  @PostMapping("/addJoke")
  public Result<JokeAddVO> addJoke(@Validated @RequestBody JokeAddRequest jokeAddRequest) throws CustomException {
    if (CollectionUtils.isEmpty(jokeAddRequest.getUrlList())) {
      throw new CustomException("梗图图片链接不能为空");
    }
    return Result.success(jokeService.addJoke(jokeAddRequest));
  }

  @ApiOperation("上传梗图图片")
  @Permission(PermissionEnum.ADMIN)
  @PostMapping("/uploadJokePic")
  public Result<FileUploadVO> uploadJokePic(@RequestPart("file") MultipartFile multipartFile) throws CustomException {
    return Result.success(jokeService.uploadJokePic(multipartFile));
  }

  @ApiOperation("获取梗图说")
  @Permission
  @Limiter
  @GetMapping("/getJokeNotice")
  public Result<JokeNoticeVO> getJokeNotice() {
    return Result.success(jokeService.getJokeNotice());
  }

  @ApiOperation("获取梗图列表数据")
  @Permission
  @Limiter
  @GetMapping("/getJokeList")
  public Result<PageBind<JokeListDTO>> getJokeList(@RequestParam(required = false) Integer page) {
    page = (Objects.isNull(page) || page < 1) ? 1 : page;
    return Result.success(jokeService.getJokeList(page, jokePageSize));
  }
}