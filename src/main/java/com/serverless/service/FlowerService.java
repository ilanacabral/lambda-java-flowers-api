package com.serverless.service;

import com.serverless.model.Flower;
import com.serverless.repository.FlowerRepository;

import java.io.IOException;
import java.util.List;

public class FlowerService {

    private final FlowerRepository flowerRepository;

    private static FlowerService flowerService;

    private FlowerService() {
        flowerRepository = FlowerRepository.getInstance();
    }

    public static FlowerService getInstance() {
        if (flowerService == null) {
            flowerService = new FlowerService();
        }
        return flowerService;
    }

    public List<Flower> list() throws IOException {
        return flowerRepository.list();
    }

    public Flower get(String id) throws IOException {
        return flowerRepository.get(id);
    }

    public void save(Flower flower) throws IOException {
        flowerRepository.save(flower);
    }

    public Boolean update(String id, Flower updateFlower) throws IOException {
        return flowerRepository.update(id, updateFlower);
    }

    public Boolean delete(String id) throws IOException {
        return flowerRepository.delete(id);
    }

}
