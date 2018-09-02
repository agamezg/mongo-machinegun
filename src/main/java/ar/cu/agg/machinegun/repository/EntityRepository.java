package ar.cu.agg.machinegun.repository;

import ar.cu.agg.machinegun.domain.Entity;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EntityRepository {
    @Autowired
    MongoTemplate mongoTemplate;

    private static Logger logger = LoggerFactory.getLogger(EntityRepository.class);

    public void insertMany() {

    }

    public void insertOne() {

    }
}
