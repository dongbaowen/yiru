package com.yiru.fundamental.web.filter;

import com.yiru.fundamental.web.http.ReadableHttpServletRequestWrapper;
import com.yiru.fundamental.web.http.ReadableHttpServletResponseWrapper;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by chenyan on 2016/11/3.
 */
public class LogFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ReadableHttpServletRequestWrapper requestWrapper = new ReadableHttpServletRequestWrapper(request);
        ReadableHttpServletResponseWrapper responseWrapper = new ReadableHttpServletResponseWrapper(response);

        logger.info("收到http请求, url: {}, wrappedParams: {}, wrappedJson: {}, wrappedFiles: {}", requestWrapper.getRequestURL(),
                requestWrapper.getWrappedParams());

        filterChain.doFilter(requestWrapper, responseWrapper);

        logger.info("返回http响应耗时: {}ms, responseBody: {}", stopWatch.getTime(), responseWrapper.getResponseBody().replace("\r", "").replace("\n", ""));

    }

    protected String forLog(Map<String, MultipartFile> wrappedFiles) {
        StringBuilder result = new StringBuilder();
        result.append("{");

        for(Map.Entry<String, MultipartFile> entry : wrappedFiles.entrySet()){
            result.append(entry.getKey()).append("=").append(entry.getValue().getOriginalFilename()).append(",");
        }

        result.append("}");
        return result.toString();
    }
}