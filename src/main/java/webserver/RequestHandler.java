package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private UrlMapper urlMapper;

    public RequestHandler(Socket connection, UrlMapper urlMapper) {
        this.connection = connection;
        this.urlMapper = urlMapper;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);

            HttpRequest httpRequest = HttpRequest.create(in);

            StaticResourceProcessor staticResourceProcessor = new StaticResourceProcessor();
            if (staticResourceProcessor.isSupported(httpRequest)) {
                staticResourceProcessor.process(dos, httpRequest);
                return;
            }

            Controller controller = urlMapper.service(httpRequest);
            View view = controller.service(httpRequest);
            HttpResponse httpResponse = new HttpResponse(httpRequest);
            ViewProcessor viewProcessor = ViewProcessorFactory.getInstance().getViewProcessor(view);
            viewProcessor.process(dos, view, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
