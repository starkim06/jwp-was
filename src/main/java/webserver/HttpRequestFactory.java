package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.httpRequest.HttpRequestBody;
import webserver.httpRequest.HttpRequestHeader;
import webserver.httpRequest.HttpStartLine;

import java.io.*;
import java.net.URLDecoder;

public class HttpRequestFactory {
    private static final Logger log = LoggerFactory.getLogger(HttpRequestFactory.class);
    private static final String TAG = "HttpRequestFactory";

    private HttpRequestFactory() {
    }

    public static HttpRequestFactory getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final HttpRequestFactory INSTANCE = new HttpRequestFactory();
    }

    public HttpRequest getHttpRequest(InputStream in) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("{} 클래스 에러 발생", TAG);
            throw new IllegalArgumentException("올바르지 않은 요청입니다.");
        }

        String startLine = parseStartLine(br);
        HttpStartLine httpStartLine = HttpStartLine.of(startLine);

        String header = parseHeader(br);

        HttpRequestHeader httpRequestHeader = HttpRequestHeader.of(header);

        HttpRequestBody httpRequestBody = null;
        if (httpStartLine.checkMethod(HttpMethod.GET)) {
            httpRequestBody = HttpRequestBody.empty();
        }

        if (httpStartLine.checkMethod(HttpMethod.POST)) {
            String body = parseBody(br, httpRequestHeader.getContentLength());
            httpRequestBody = HttpRequestBody.of(body);
        }

        return new HttpRequest(httpStartLine, httpRequestHeader, httpRequestBody);
    }

    private String parseStartLine(BufferedReader br) throws IOException {
        return br.readLine();
    }

    private String parseHeader(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        String headerLine = br.readLine();
        while (!"".equals(headerLine)) {
            sb.append(headerLine);
            sb.append("\n");
            headerLine = br.readLine();
        }
        sb.append("\n");
        return sb.toString();
    }

    private String parseBody(BufferedReader br, int contentLength) {
        String body = readBody(br, contentLength);
        return decodeBody(body);
    }

    private String readBody(BufferedReader br, int contentLength) {
        try {
            return IOUtils.readData(br, contentLength);
        } catch (IOException e) {
            log.error("{} 클래스 {} 메서드 오류", TAG, "parseBody");
            throw new IllegalArgumentException();
        }
    }

    private String decodeBody(String body) {
        try {
            String decode = URLDecoder.decode(body, "UTF-8");
            return decode;
        } catch (UnsupportedEncodingException e) {
            log.error("{} 클래스 {} 메서드 오류", TAG, "decodeBody");
            throw new IllegalArgumentException();
        }
    }
}
