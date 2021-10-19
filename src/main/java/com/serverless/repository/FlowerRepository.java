package com.serverless.repository;

import com.serverless.model.Flower;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class FlowerRepository {

    private static final Logger logger = LogManager.getLogger(FlowerRepository.class);

    private static final String FLOWERS_TABLE_NAME = System.getenv("FLOWERS_TABLE_NAME");
    
    private DynamoDBMapper mapper;


    public FlowerRepository() {
        DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder()
                .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(FLOWERS_TABLE_NAME)).build();
        DynamoDBAdapter dbAdapter = DynamoDBAdapter.getInstance();
        //AmazonDynamoDB client = dbAdapter.getDbClient();
        this.mapper = dbAdapter.createDbMapper(mapperConfig);
    }

    public List<Flower> list() throws IOException {
        DynamoDBScanExpression scanExp = new DynamoDBScanExpression();
        List<Flower> results = this.mapper.scan(Flower.class, scanExp);
        results.stream().forEach(f->logger.info("Flowers - list(): " + f.toString()));       
        return results;
    }
}