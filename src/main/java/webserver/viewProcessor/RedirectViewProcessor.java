package webserver.viewProcessor;

import webserver.HttpResponse;
import webserver.ViewProcessor;

public class RedirectViewProcessor implements ViewProcessor {

    @Override
    public boolean isSupported(String viewName) {
        return viewName.startsWith("/redirect:");
    }

    @Override
    public String process(String viewName, HttpResponse httpResponse) {
        httpResponse.sendRedirect();
        String[] split = viewName.split(":");
        return split[1];
    }
}