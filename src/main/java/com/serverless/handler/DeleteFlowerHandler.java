package com.serverless.handler;


import java.util.Collections;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.service.FlowerService;

public class DeleteFlowerHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {
    private static final Logger LOG = LogManager.getLogger(DeleteFlowerHandler.class);
	private final FlowerService flowerService = new FlowerService();

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
		LOG.info("received: {}", input);
		
		try {
			// get the 'pathParameters' from input
			Map<String,String> pathParameters =  (Map<String,String>)input.get("pathParameters");

			String flowerId = pathParameters.get("id");
	
			// delete the Flower by id
			Boolean success = flowerService.delete(flowerId);
	
			// send the response back
			if (success) {
			  return ApiGatewayResponse.builder()
						  .setStatusCode(204)
						  .setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"))
						  .build();
			} else {
			  return ApiGatewayResponse.builder()
						  .setStatusCode(404)
						  .setObjectBody("Flower with id: '" + flowerId + "' not found.")
						  .setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"))
						  .build();
			}
		} catch (Exception ex) {
			LOG.error("Error in deleting flower: ", ex);
	
			// send the error response back
			return ApiGatewayResponse.builder()
			.setStatusCode(400)
			.setObjectBody("Error in deleting flower: ")
			.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"))
			.build();
		}
	}
}
