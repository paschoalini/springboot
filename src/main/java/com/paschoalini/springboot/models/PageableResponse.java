package com.paschoalini.springboot.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class PageableResponse<T> extends PageImpl<T> {
	private static final long serialVersionUID = 1L;

	/*
	   Solução do vídeo está depreciada... utilizar a outra.
	
	public PageableResponse(
			@JsonProperty("content") List<T> content, 
			@JsonProperty("number") int page,
			@JsonProperty("size") int size, 
			@JsonProperty("totalElements") long totalElements) {
		
		// Solução do vído não funciona pq construtor do PageRequest está depreciado
		//super(content, new PageRequest(page, size), totalElements);
		super(content, PageRequest.of(page, size), totalElements);
	}
	*/
	
	  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	  public PageableResponse(@JsonProperty("content") List<T> content,
			  @JsonProperty("number") int number,
			  @JsonProperty("size") int size,
			  @JsonProperty("totalElements") Long totalElements,
			  @JsonProperty("pageable")JsonNode pageable,
			  @JsonProperty("first") boolean first,
			  @JsonProperty("last") boolean last,
			  @JsonProperty("totalPages") int totalPages,
			  @JsonProperty("sort") JsonNode sort,
			  @JsonProperty("numberOfElements") int numberOfElements) {
		  super(content, PageRequest.of(number, size), totalElements);
	    }

	// Construtor necessário para o parser do JSON
	public PageableResponse() {
		super(new ArrayList<>());
	}
}
