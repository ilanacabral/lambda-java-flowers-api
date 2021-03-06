package com.serverless.repository;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;


public class DynamoDBAdapter  {
       
    private AmazonDynamoDB client;
    private DynamoDBMapper mapper;
    private static DynamoDBAdapter dbAdapter;
    
    
    private DynamoDBAdapter() {
        this.client = AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.US_EAST_1)
            .build();
    }

    public static DynamoDBAdapter getInstance() {
        if (dbAdapter == null){
        dbAdapter = new DynamoDBAdapter();
        }

        return dbAdapter;
    }

    public AmazonDynamoDB getDbClient(){
        return this.client;
    }
  
    public DynamoDBMapper createDbMapper(DynamoDBMapperConfig mapperConfig) {
        if (this.client != null){
            mapper = new DynamoDBMapper(this.client, mapperConfig);
        }

        return this.mapper;
    }
}
