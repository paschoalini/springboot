package com.paschoalini.springboot.handler;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paschoalini.springboot.error.ErrorDetails;

@ControllerAdvice
public class RestResponseExceptionHandler extends DefaultResponseErrorHandler {
	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		System.out.println("\n== ENTROU NO hasError ==\n");
		return super.hasError(response);
	}
	
	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		ErrorDetails eDetails = objectMapper.readValue(IOUtils.toString(response.getBody(), "UTF-8"), ErrorDetails.class);
		System.out.printf("\n\n== Doing something with status code %d ==\n", response.getStatusCode().value());
		System.out.printf("\n\n== Doing something with status body ==\n");
		System.out.println("Title............. " + eDetails.getTitle());
		System.out.println("Status............ " + eDetails.getStatus());
		System.out.println("Detail............ " + eDetails.getDetail());
		System.out.println("Timestamp......... " + eDetails.getTimestamp());
		System.out.println("Developer Message. " + eDetails.getDeveloperMessage());
		System.out.println();
	}
}
