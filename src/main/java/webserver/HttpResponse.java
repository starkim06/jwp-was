package webserver;

import webserver.httpRequest.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private Map<String, String> header = new HashMap<>();
    private HttpStatus httpStatus;

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getContentType() {
        String contentType = header.get("Content-Type");
        if (contentType == null) {
            return "text/plain";
        }
        return contentType;
    }

    public void setContentType(String contentType) {
        header.put("Content-Type", contentType);
    }

    public String getStatusCodeAndMessage() {
        return httpStatus.getCodeAndMessage();
    }
}
