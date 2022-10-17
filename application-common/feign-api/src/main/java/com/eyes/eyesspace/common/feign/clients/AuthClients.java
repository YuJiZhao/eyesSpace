package com.eyes.eyesspace.common.feign.clients;

import com.eyes.eyesspace.common.tool.result.Result;
import com.eyes.eyesspace.common.feign.module.dto.AuthDto;
import com.eyes.eyesspace.common.feign.module.vo.AuthVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "auth-server", path = "/auth")
public interface AuthClients {
    @PostMapping("/auth/checkAuth")
    Result.DataResult<AuthDto> checkAuth(@RequestBody AuthVo authVo);
}
