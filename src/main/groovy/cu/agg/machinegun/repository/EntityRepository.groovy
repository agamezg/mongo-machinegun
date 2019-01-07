package cu.agg.machinegun.repository

import org.bson.Document
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository

@Repository
class EntityRepository {
    @Autowired
    MongoTemplate mongoTemplate

    private static Logger logger = LoggerFactory.getLogger(EntityRepository.class)

    void insertMany() {

    }

    void insertOne(String collection, Document document) {
        mongoTemplate.getCollection(collection).insertOne(document)
    }
}
