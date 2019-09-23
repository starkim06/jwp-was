package webserver;

import webserver.viewProcessor.ErrorViewProcessor;
import webserver.viewProcessor.HtmlViewProcessor;
import webserver.viewProcessor.RedirectViewProcessor;
import webserver.viewProcessor.ResourceViewProcessor;

import java.util.ArrayList;
import java.util.List;

public class ViewProcessorFactory {
    private List<ViewProcessor> viewProcessors = new ArrayList<>();

    private ViewProcessorFactory() {
        initViewProcessorFactory();
    }

    public static ViewProcessorFactory getInstance() {
        return ViewProcessorFactory.LazyHolder.INSTANCE;
    }

    private void initViewProcessorFactory() {
        viewProcessors.add(new ErrorViewProcessor());
        viewProcessors.add(new RedirectViewProcessor());
        viewProcessors.add(new HtmlViewProcessor());
        viewProcessors.add(new ResourceViewProcessor());
    }

    public ViewProcessor getViewProcessor(String name) {
        for (ViewProcessor viewProcessor : viewProcessors) {
            if (viewProcessor.isSupported(name)) {
                return viewProcessor;
            }
        }

        throw new IllegalArgumentException("지원 하지 않는 파일 타입 입니다.");
    }

    private static class LazyHolder {
        private static final ViewProcessorFactory INSTANCE = new ViewProcessorFactory();
    }
}
