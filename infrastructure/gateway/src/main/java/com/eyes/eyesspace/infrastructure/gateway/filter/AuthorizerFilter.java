package com.eyes.eyesspace.infrastructure.gateway.filter;

import cn.hutool.json.JSONUtil;
import com.eyes.eyesspace.common.feign.clients.AuthClients;
import com.eyes.eyesspace.common.feign.module.dto.AuthDto;
import com.eyes.eyesspace.common.feign.module.vo.AuthVo;
import com.eyes.eyesspace.common.tool.context.ConfigContext;
import com.eyes.eyesspace.common.tool.result.ResultCode;
import com.eyes.eyesspace.common.tool.result.StandardResult;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
@Order(-1)
public class AuthorizerFilter implements GlobalFilter {
    private final String TOKEN_HEADER = ConfigContext.getString("TOKEN_HEADER");
    private final String ID_HEADER = ConfigContext.getString("ID_HEADER");
    private final String ROLE_HEADER = ConfigContext.getString("ROLE_HEADER");

    private final AuthClients authClients;

    public AuthorizerFilter(AuthClients authClients) {
        this.authClients = authClients;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String path = request.getURI().getPath();
        String token = Objects.equals(request.getHeaders().getFirst(TOKEN_HEADER), "undefined")
                ? null
                : request.getHeaders().getFirst(TOKEN_HEADER);

        StandardResult<AuthDto> authDtoDataResult = authClients.checkAuth(new AuthVo(path, token));
        // 权限不足
        if(!authDtoDataResult.getCode().equals(ResultCode.SUCCESS.getCode())) {
            byte[] bytes = JSONUtil.toJsonStr(authDtoDataResult).getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(buffer));
        }
        // 权限通过
        if(Objects.nonNull(authDtoDataResult.getData())) {
            request.mutate()
                    .header(ID_HEADER, authDtoDataResult.getData().getId().toString())
                    .header(ROLE_HEADER, authDtoDataResult.getData().getRole()).build();
            response.getHeaders().add(TOKEN_HEADER, authDtoDataResult.getData().getToken());
        }
        addHeaderExpose(response);

        return chain.filter(exchange);
    }

    /**
     * 添加向前端暴露的响应头
     * @param response
     */
    private void addHeaderExpose(ServerHttpResponse response) {
        response.getHeaders().add("Access-Control-Expose-Headers", TOKEN_HEADER);
    }
}