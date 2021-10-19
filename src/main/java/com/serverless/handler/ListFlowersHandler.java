package com.serverless.handler;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.model.Flower;
import com.serverless.response.ApiGatewayResponse;
import com.serverless.response.Response;
import com.serverless.service.FlowerService;

public class ListFlowersHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private static final Logger LOG = LogManager.getLogger(ListFlowersHandler.class);
	private final FlowerService flowerService = new FlowerService();

    @Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
		LOG.info("received: {}", input);
		
		List<Flower> flowers;
		try {
			flowers = flowerService.list();
		} catch (IOException ex) {
			String error = "Error in listing flowers: ";
			LOG.error(error,ex);		
			return ApiGatewayResponse.builder()
				.setStatusCode(400)
				.setObjectBody(new Response(error + ex, input))
				.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
				.build();
		}
		
		return ApiGatewayResponse.builder()
				.setStatusCode(200)
				.setObjectBody(flowers)
				.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
				.build();
	}
}
