package com.yiru.fundamental.web.http;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.Collections;
import java.util.Map;

/**
 * Created by Baowen on 2017/4/15.
 */
public class ReadableHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private byte[] rawData;
    private String contentType;
    private MultipartHttpServletRequest multipartRequest;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request
     * @throws IllegalArgumentException if the request is null
     */
    public ReadableHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        contentType = getHeader("Content-Type") == null ? "TEXT/HTML" : getHeader("Content-Type");
        if (contentType.toUpperCase().contains("MULTIPART/FORM-DATA")) {
            multipartRequest = new CommonsMultipartResolver().resolveMultipart(this);
        }
    }

    public String getWrappedParams() throws UnsupportedEncodingException {
        StringBuilder bodyParam = new StringBuilder();
        if (contentType.toUpperCase().contains("MULTIPART/FORM-DATA")) {
            for (String paramName : multipartRequest.getParameterMap().keySet()) {
                bodyParam.append(paramName).append("=").append(multipartRequest.getParameter(paramName)).append("&");
            }
        } else {
            for (String paramName : getParameterMap().keySet()) {
                bodyParam.append(paramName).append("=").append(getParameter(paramName)).append("&");
            }
        }
        return bodyParam.toString();
    }

    public String getWrappedJson() throws IOException {
        return getWrappedJson("");
    }

    public String getWrappedJson(String defaultValue) throws IOException {
        if (contentType.toUpperCase().contains("APPLICATION/JSON")) {
            return IOUtils.toString(getReader()).replace("\n", "").replace("\r", "");
        }
        return defaultValue == null ? "" : defaultValue;
    }

    public Map<String, MultipartFile> getWrappedFiles() {
        if (contentType.toUpperCase().contains("MULTIPART/FORM-DATA")) {
            return multipartRequest.getFileMap();
        }
        return Collections.emptyMap();
    }


    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (rawData == null) {
            rawData = IOUtils.toByteArray(getRequest().getInputStream());
        }
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(rawData);

        ServletInputStream servletInputStream = new ServletInputStream() {
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
        return servletInputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream(), "utf8"));
    }
}
