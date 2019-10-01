package webserver.controller;

import org.junit.jupiter.api.Test;
import webserver.View;
import webserver.WebTestForm;
import webserver.http.HttpRequest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class UserCreateControllerTest extends WebTestForm {

    UserCreateController userCreateController = new UserCreateController();

    @Test
    void 회원가입_GET_요청_에러_처리() throws IOException {
        HttpRequest httpRequest = getHttpGetRequest("/user/create");
        View view = userCreateController.service(httpRequest);
        assertThat(view.getName()).isEqualTo("/error:METHOD_NOT_ALLOWED");
    }

    @Test
    void 회원가입_성공_출력_테스트() throws IOException {
        HttpRequest httpRequest = getHttpPostRequestWithBody("/user/create");

        View view = userCreateController.service(httpRequest);
        assertThat(view.getName()).isEqualTo("/redirect:/index.html");
    }
}