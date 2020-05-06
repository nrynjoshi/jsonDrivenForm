package com.jsondrivenform.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface RecordRepository extends MongoRepository<Record, String> {

}