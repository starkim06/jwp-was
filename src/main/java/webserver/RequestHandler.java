package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.httpResponse.HttpResponse;
import webserver.viewProcessor.ViewProcessor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);

            HttpRequest httpRequest = new HttpRequestFactory().getHttpRequest(in);
            HttpResponse httpResponse = new HttpResponse();

            UrlMapper urlMapper = new UrlMapper();
            String view = urlMapper.service(httpRequest);

            ViewProcessor viewProcessor = new ViewProcessorFactory().getViewProcessor(view);
            String resolve = viewProcessor.process(view, httpResponse);

            HttpResponseProcessor httpResponseProcessor = new HttpResponseProcessorFactory().getHttpResponseProcessor(httpResponse);
            httpResponseProcessor.process(dos, resolve, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }
}
