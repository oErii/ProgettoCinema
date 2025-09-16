package org.elis.demo.DTO.response;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Data;

@Data
public class ValidationErrorMessageDTO {
	private LocalDateTime timestamp;
	private int statusCode;
	private String statusName;
	private String targetUrl;
	private Map<String, String> fieldErrors;
}