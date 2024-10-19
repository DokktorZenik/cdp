package com.taskmanager.cdp.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.taskmanager.cdp.config.ProjectContextHolder.setContext;

@Component
@RequiredArgsConstructor
public class OrgAndProjFilter implements Filter {

    private static final Pattern URL_PATTERN = Pattern.compile("\\/v1/organizations\\/([^\\/]+)\\/projects\\/([^\\/]+)(\\/.*)?$");

    private final SQLiteService sqLiteService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String path = httpRequest.getRequestURI();

            Matcher matcher = URL_PATTERN.matcher(path);
            if (matcher.matches()) {
                String orgName = matcher.group(1);
                String projectName = matcher.group(2);
                Integer[] ids = sqLiteService.getIds(orgName, projectName);
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                if(ids == null || ids[0] == null || ids[1] == null) {
                    httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
                    String error = "Wrong organization or project name";
                    httpServletResponse.getWriter().write(error);
                    return;
                }

                setContext(new ProjectContext(ids[0], ids[1]));
            }

            chain.doFilter(request, response);
        }

        try {

            chain.doFilter(request, response);
        } finally {

            ProjectContextHolder.clearContext();
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
