package com.eyes.eyesspace.sync.common.config;

import com.eyes.eyesAuth.constant.AuthConfigConstant;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author eyesYeager
 * @date 2023/2/25 18:31
 */
@Component
public class ResponseHeaderFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
    List<String> exposeArr = new ArrayList<>();
    exposeArr.add(AuthConfigConstant.HEADER_TOKEN);
    exposeArr.add(AuthConfigConstant.HEADER_TOKEN_S);
    exposeArr.add(AuthConfigConstant.HEADER_TOKEN_L);
    httpServletResponse.setHeader("Access-Control-Expose-Headers", String.join(",", exposeArr));
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}
