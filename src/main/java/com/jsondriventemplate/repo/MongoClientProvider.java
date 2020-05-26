package com.jsondriventemplate.repo;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class MongoClientProvider {

    private final MongoOperations mongoOperations;
    private static final String _ID = "_ID";
    private static final String URL = "url";

    public MongoClientProvider(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public Document save(Map<String,Object> dataMap, String collectionName) {
        collection(collectionName);
        Document document = new Document();

        if(!dataMap.containsKey(_ID) && StringUtils.isBlank((CharSequence) dataMap.get(_ID))){
            document.put(_ID, UUID());
        }
        document.putAll(dataMap);
        mongoOperations.save(document, collectionName);
        return document;
    }

    public void delete(String id, String collectionName) {
        Query query = Query.query(Criteria.where(_ID).is(id));
        mongoOperations.remove(query, collectionName);
    }

    public List<?> findAll(String collectionName) {
        return mongoOperations.findAll(Map.class, collectionName);
    }

    public Map findById(String id, String collectionName) {
        Query query = Query.query(Criteria.where(_ID).is(id));
        return mongoOperations.findOne(query, Map.class,collectionName);
    }

    public Map findByURL(String url, String collectionName) {
        Query query = Query.query(Criteria.where(URL).is(url));
        return mongoOperations.findOne(query, Map.class,collectionName);
    }
    public Map findByAtt(String attr,String value, String collectionName) {
        Query query = Query.query(Criteria.where(attr).is(value));
        return mongoOperations.findOne(query, Map.class,collectionName);
    }

    public List<?> search(Map<String,Object> searchMap, String collectionName) {
        Query query = new Query();
        for(Map.Entry<String, Object> entity:searchMap.entrySet()){
            String value = (String) entity.getValue();
            if(StringUtils.isNotBlank(value)){
                query.addCriteria(Criteria.where(entity.getKey()).regex(".*"+ value +".*","i"));
            }
        }
        return mongoOperations.find(query,Map.class,collectionName);
    }


    public void collection(String collectionName) {
        boolean collectionExists = mongoOperations.collectionExists(collectionName);
        if (collectionExists) {
            return;
        }
        mongoOperations.createCollection(collectionName);
    }
    public void dropCollection(String collectionName) {
        boolean collectionExists = mongoOperations.collectionExists(collectionName);
        if (!collectionExists) {
            return;
        }
        mongoOperations.dropCollection(collectionName);
    }

    private String UUID() {
        return UUID.randomUUID().toString();
    }

}
