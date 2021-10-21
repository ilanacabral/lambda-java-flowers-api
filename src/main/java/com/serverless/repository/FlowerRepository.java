package com.serverless.repository;

import com.serverless.model.Flower;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class FlowerRepository {

    private static final Logger logger = LogManager.getLogger(FlowerRepository.class);

    private static final String FLOWERS_TABLE_NAME = System.getenv("FLOWERS_TABLE_NAME");

    private DynamoDBMapper mapper;

    private static FlowerRepository flowerRepository;

    private FlowerRepository() {
        DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder()
                .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(FLOWERS_TABLE_NAME)).build();
        DynamoDBAdapter dbAdapter = DynamoDBAdapter.getInstance();
        this.mapper = dbAdapter.createDbMapper(mapperConfig);
    }

    public static FlowerRepository getInstance(){
        if (flowerRepository == null){
           flowerRepository = new FlowerRepository();
        }
        return flowerRepository;
    }

    public List<Flower> list() throws IOException {
        DynamoDBScanExpression scanExp = new DynamoDBScanExpression();
        List<Flower> results = this.mapper.scan(Flower.class, scanExp);
        results.stream().forEach(f -> logger.info("Flowers - list(): " + f.toString()));
        return results;
    }

    public Flower get(String id) throws IOException {
        Flower flower = null;

        HashMap<String, AttributeValue> av = new HashMap<>();
        av.put(":v1", new AttributeValue().withS(id));

        DynamoDBQueryExpression<Flower> queryExp = new DynamoDBQueryExpression<Flower>()
                .withKeyConditionExpression("id = :v1").withExpressionAttributeValues(av);

        PaginatedQueryList<Flower> result = this.mapper.query(Flower.class, queryExp);
        if (!result.isEmpty()) {
            flower = result.get(0);
            logger.info("Flowers - get(): flower - " + flower.toString());
        } else {
            logger.info("Flowers - get(): flower - Not Found.");
        }
        return flower;
    }

    public void save(Flower flower) throws IOException {
        this.mapper.save(flower);
        logger.info("Flowers - save(): " + flower.toString());
    }

    public Boolean delete(String id) throws IOException {
        Flower flower = null;

        // get flower if exists
        flower = get(id);
        if (flower != null) {
            this.mapper.delete(flower);
            logger.info("Flowers - delete(): " + flower.toString());
        } else {
            logger.info("Flowers - delete(): flower id " + id + " - does not exist.");
            return false;
        }
        return true;
    }

    public Boolean update(String id, Flower updateFlower) throws IOException {
        // get flower if exists
        Flower flower = get(id);
        if (flower != null) {
            updateFlower.setId(id);
            this.mapper.save(updateFlower);
            logger.info("Flowers - update(): " + flower.toString());
        } else {
            logger.info("Flowers - update(): flower id " + id + " - does not exist.");
            return false;
        }
        return true;
    }
}
