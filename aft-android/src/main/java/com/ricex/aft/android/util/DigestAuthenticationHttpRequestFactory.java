package com.ricex.aft.android.util;

import java.net.URI;

import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

public class DigestAuthenticationHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {

	
	@Override
	protected HttpContext createHttpContext(HttpMethod method, URI uri) {
		return null;

	}
	
}
