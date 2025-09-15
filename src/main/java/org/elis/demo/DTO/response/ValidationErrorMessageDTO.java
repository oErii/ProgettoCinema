package org.elis.demo.DTO.response;

import java.util.Map;

import lombok.Data;

@Data
public class ValidationErrorMessageDTO {
	  public String timestamp = java.time.LocalDateTime.now().toString();
	  public int statusCode;
	  public String statusSpring;
	  public String targetUrl;
	  public Map<String, String> fieldErrors;
}