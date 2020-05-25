package dataconsumer.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

public class MongoConfig {

    private MongoClient mongoClient;

    @Bean
    public MongoClient mongoClient() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        return mongoClient;
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient, "outlier");
    }
}
