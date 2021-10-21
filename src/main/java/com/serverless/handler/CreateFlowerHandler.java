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
import com.serverless.response.Response;
import com.serverless.service.FlowerService;

public class CreateFlowerHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {
	private static final Logger LOG = LogManager.getLogger(CreateFlowerHandler.class);
	private final FlowerService flowerService = FlowerService.getInstance();

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
		LOG.info("received: {}", input);
		try {
			// get the 'body' from input
			JsonNode body = new ObjectMapper().readTree((String) input.get("body"));
			LOG.info(body);

			// create the Flower object for post
			Flower flower = Flower.builder().
			name(body.get("name").asText()).
			color(body.get("color").asText()).
			water(body.get("water").asText()).
			price(body.get("price").asDouble()).build();
					
			flowerService.save(flower);

			// send the response back
			return ApiGatewayResponse.builder().setStatusCode(201).setObjectBody(flower)
					.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless")).build();

		} catch (Exception ex) {
			LOG.error("Error in saving flower: ", ex);

			// send the error response back
			Response responseBody = new Response("Error in saving flower: ", input);
			return ApiGatewayResponse.builder().setStatusCode(400).setObjectBody(responseBody)
					.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless")).build();
		}
	}
}
