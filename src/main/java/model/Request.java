package model;

import java.util.Map;
import java.util.Objects;

import exception.NotFoundElementException;

public class Request {
	private final Map<String, String> header;

	public Request(final Map<String, String> header) {
		this.header = header;
	}

	public String getRequestElement(String key) {
		String value = header.get(key);
		if (Objects.isNull(value)) {
			throw new NotFoundElementException();
		}
		return header.get(key);
	}
}