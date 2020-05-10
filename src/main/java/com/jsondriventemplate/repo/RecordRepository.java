package com.jsondriventemplate.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public interface RecordRepository extends MongoRepository<HashMap, String> {

}