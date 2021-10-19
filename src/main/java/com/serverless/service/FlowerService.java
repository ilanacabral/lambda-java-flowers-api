package com.serverless.service;

import com.serverless.model.Flower;
import com.serverless.repository.FlowerRepository;

import java.io.IOException;
import java.util.List;

public class FlowerService {
    
        private final FlowerRepository flowerRepository = new FlowerRepository();

        public List<Flower> list() throws IOException {
            return flowerRepository.list();
        }
    
}
