package ar.cu.agg.machinegun.config;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource("classpath:mongo.properties")
public class MongoConfig{

    private final Environment environment;

    @Autowired
    public MongoConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public MongoDbFactory mongoDbFactory(){
        MongoClient mongoClient;
        if (getUsername() == null || getUsername().isEmpty()) {
            mongoClient = new MongoClient(getServerAddresses());
        } else {
            MongoClientOptions.Builder mongoClientOptionsBuilder = new MongoClientOptions.Builder();
            if (getReplicas() != null && !getReplicas().isEmpty())
                mongoClientOptionsBuilder.requiredReplicaSetName(environment.getProperty("mongodb.replicas"));
            if (getReadPreference() != null && !getReadPreference().isEmpty())
                mongoClientOptionsBuilder.readPreference(getReadPrefenrece(environment.getProperty("mongodb.read_preference")));
            mongoClientOptionsBuilder.connectionsPerHost(5);
            mongoClient = new MongoClient(getServerAddresses(), getMongoCredential(), mongoClientOptionsBuilder.build());
        }
        return new SimpleMongoDbFactory(mongoClient, environment.getProperty("mongodb.db_name"));
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDbFactory());
    }

    private ReadPreference getReadPrefenrece(String property) {
        ReadPreference readPreference;
        switch (property){
            case "primary":
                readPreference = ReadPreference.primary();
                break;
            case "secundary":
                readPreference = ReadPreference.secondary();
                break;
            case "primaryPreferred":
                readPreference = ReadPreference.primaryPreferred();
                break;
            case "secundaryPreferred":
                readPreference = ReadPreference.secondaryPreferred();
                break;
            case "nearest":
                readPreference = ReadPreference.nearest();
                break;
            default:
                readPreference = ReadPreference.primary();
                break;
        }

        return readPreference;
    }

    private MongoCredential getMongoCredential() {
        return MongoCredential.createCredential(
                environment.getProperty("mongodb.user_name"),
                environment.getProperty("mongodb.db_name"),
                environment.getProperty("mongodb.password").toCharArray());
    }

    private List<ServerAddress> getServerAddresses()  {
        List<ServerAddress> serverAddresses = new ArrayList<>();
        String[] hosts = environment.getProperty("mongodb.host", String[].class);
        int[] ports = environment.getProperty("mongodb.port", int[].class);
        for (int i = 0; i < hosts.length; i++) {
            serverAddresses.add(new ServerAddress(hosts[i], ports[i]));
        }
        return serverAddresses;
    }

    public String getHost() {
        return environment.getProperty("mongodb.host");
    }

    public String getPort() {
        return environment.getProperty("mongodb.port");
    }

    public String getDatabaseName(){
        return environment.getProperty("mongodb.db_name");
    }

    public String getUsername() {
        return environment.getProperty("mongodb.user_name");
    }

    public String getPassword() {
        return environment.getProperty("mongodb.password");
    }

    public String getReplicas() {
        return environment.getProperty("mongodb.replicas");
    }

    public String getReadPreference() {
        return environment.getProperty("mongodb.read_preference");
    }
}
