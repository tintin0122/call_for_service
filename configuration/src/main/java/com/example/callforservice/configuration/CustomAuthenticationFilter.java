package com.example.callforservice.configuration;

import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class CustomAuthenticationFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      FilterChain filterChain)
      throws ServletException, IOException {
    // IMPORTANT: THIS IS NOT A SECURE WAY TO DO AUTHENTICATION AND JUST FOR DEMONSTRATION PURPOSE!
    final String user = httpServletRequest.getHeader("x-user");
    if (StringUtils.isNotBlank(user)) {
      final SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_CUSTOMER");
      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(user, null, Collections.singletonList(authority));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}
