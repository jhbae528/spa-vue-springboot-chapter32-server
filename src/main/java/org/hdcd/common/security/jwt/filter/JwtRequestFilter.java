package org.hdcd.common.security.jwt.filter;

import lombok.RequiredArgsConstructor;
import org.hdcd.common.security.jwt.constants.SecurityConstants;
import org.hdcd.common.security.jwt.provider.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(SecurityConstants.TOKEN_HEADER);

        if( (isEmpty(header) == true) || (header.startsWith(SecurityConstants.TOKEN_PREFIX) != true)) {
            filterChain.doFilter(request, response);
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(header);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private boolean isEmpty(final CharSequence cs){
        return (cs == null) || (cs.length() == 0);
    }
}
