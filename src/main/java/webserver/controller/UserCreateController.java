package webserver.controller;

import db.DataBase;
import model.User;
import webserver.View;
import webserver.http.HttpRequest;

public class UserCreateController extends AbstractController {
    public static final String PATH = "/user/create";

    @Override
    protected View doPost(HttpRequest request) {
        User user = User.Builder.anUser()
                .userId(request.getParam("userId"))
                .email(request.getParam("email"))
                .name(request.getParam("name"))
                .password(request.getParam("password"))
                .build();

        DataBase.addUser(user);

        return new View(REDIRECT_VIEW + "/index.html");
    }
}
