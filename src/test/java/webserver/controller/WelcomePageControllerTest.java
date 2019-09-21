package webserver.controller;

import org.junit.jupiter.api.Test;
import webserver.HttpRequest;
import webserver.httpRequest.HttpRequestBody;
import webserver.httpRequest.HttpRequestHeader;
import webserver.httpRequest.HttpStartLine;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WelcomePageControllerTest {

    @Test
    void GET_정상처리() {
        WelcomePageController welcomePageController = new WelcomePageController();

        String startLine = "GET / HTTP1.1";
        HttpStartLine httpStartLine = HttpStartLine.of(startLine);

        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(new HashMap<>());

        String body = "userId=starkim06&email=alswns@naver.com&name=alswns&password=temp";
        HttpRequestBody httpRequestBody = HttpRequestBody.of(body);

        HttpRequest httpRequest = new HttpRequest(httpStartLine, httpRequestHeader, httpRequestBody);
        assertThat(welcomePageController.service(httpRequest)).isEqualTo("/index.html");
    }

    @Test
    void POST_예외처리() {
        WelcomePageController welcomePageController = new WelcomePageController();

        String startLine = "POST / HTTP1.1";
        HttpStartLine httpStartLine = HttpStartLine.of(startLine);

        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(new HashMap<>());

        String body = "userId=starkim06&email=alswns@naver.com&name=alswns&password=temp";
        HttpRequestBody httpRequestBody = HttpRequestBody.of(body);

        HttpRequest httpRequest = new HttpRequest(httpStartLine, httpRequestHeader, httpRequestBody);
        assertThrows(IllegalArgumentException.class, () -> welcomePageController.service(httpRequest));
    }
}