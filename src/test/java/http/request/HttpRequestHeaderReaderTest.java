package http.request;

import java.io.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestHeaderReaderTest {
	private String fileDirectory = "./src/test/resources/";

	@DisplayName("GET요청 시 request header 읽기")
	@Test
	void request_GET() throws IOException {
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(fileDirectory + "request_GET.txt"))))) {
			HttpRequest httpRequest = HttpRequestReader.readHttpRequest(bufferedReader);

			assertThat(httpRequest.getRequestHeaderElement("Method")).isEqualTo("GET");
			assertThat(httpRequest.getRequestHeaderElement("Path")).isEqualTo("/index.html");
			assertThat(httpRequest.getRequestHeaderElement("Http")).isEqualTo("HTTP/1.1");
			assertThat(httpRequest.getRequestHeaderElement("Host")).isEqualTo("localhost:8080");
			assertThat(httpRequest.getRequestHeaderElement("Connection")).isEqualTo("keep-alive");
			assertThat(httpRequest.getRequestHeaderElement("Accept")).isEqualTo("*/*");
		}
	}

	@DisplayName("POST요청 시 request header 읽기")
	@Test
	void request_POST() throws IOException {
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileDirectory + "request_POST.txt")))) {
			HttpRequest httpRequest = HttpRequestReader.readHttpRequest(bufferedReader);

			assertThat(httpRequest.getRequestHeaderElement("Method")).isEqualTo("POST");
			assertThat(httpRequest.getRequestHeaderElement("Path")).isEqualTo("/user/create");
			assertThat(httpRequest.getRequestHeaderElement("Http")).isEqualTo("HTTP/1.1");
			assertThat(httpRequest.getRequestHeaderElement("Host")).isEqualTo("localhost:8080");
			assertThat(httpRequest.getRequestHeaderElement("Connection")).isEqualTo("keep-alive");
			assertThat(httpRequest.getRequestHeaderElement("Content-Length")).isEqualTo("46");
			assertThat(httpRequest.getRequestHeaderElement("Content-Type")).isEqualTo("application/x-www-form-urlencoded");
			assertThat(httpRequest.getRequestHeaderElement("Accept")).isEqualTo("*/*");
		}
	}
}