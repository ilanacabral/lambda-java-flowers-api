package com.serverless.handler;

import java.util.Collections;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.model.Flower;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.service.FlowerService;

public class UpdateFlowerHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse>{

    private static final Logger LOG = LogManager.getLogger(GetFlowerHandler.class);
	private final FlowerService flowerService = FlowerService.getInstance();

    @Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
    LOG.info("received: {}", input);		

		try {
			// get the 'pathParameters' from input
			Map<String,String> pathParameters = (Map<String, String>) input.get("pathParameters");
			String flowerId = pathParameters.get("id");

            // get the 'body' from input
            JsonNode body = new ObjectMapper().readTree((String) input.get("body"));

            // create the Flower object for post
			Flower updateFlower = Flower.builder().
			name(body.get("name").asText()).
			color(body.get("color").asText()).
			water(body.get("water").asText()).
			price(body.get("price").asDouble()).build();
	
			// update the Flower by id
			boolean success  = flowerService.update(flowerId,updateFlower);
	
			// send the response back
			if (success) {
			  return ApiGatewayResponse.builder()
						  .setStatusCode(200)
						  .setObjectBody(updateFlower)
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
			LOG.error("Error in updating flower: ", ex);
	
			// send the error response back
			return ApiGatewayResponse.builder()
					.setStatusCode(400)
					.setObjectBody("Error in updating flower: " + ex)
					.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"))
					.build();
		}
	}

}
