package cu.agg.machinegun.configuration

import com.mongodb.MongoClient
import com.mongodb.MongoClientOptions
import com.mongodb.MongoCredential
import com.mongodb.ReadPreference
import com.mongodb.ServerAddress
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoDbFactory

@Configuration
@PropertySource("classpath:mongo.properties")
class MongoConfig {
    private final Environment environment

    @Autowired
    MongoConfig(Environment environment) {
        this.environment = environment
    }

    @Bean
    MongoDbFactory mongoDbFactory(){
        MongoClient mongoClient
        if (getUsername() == null || getUsername().isEmpty()) {
            mongoClient = new MongoClient(getServerAddresses())
        } else {
            MongoClientOptions.Builder mongoClientOptionsBuilder = new MongoClientOptions.Builder()
            if (getReplicas() != null && !getReplicas().isEmpty())
                mongoClientOptionsBuilder.requiredReplicaSetName(environment.getProperty("mongodb.replicas"))
            if (getReadPreference() != null && !getReadPreference().isEmpty())
                mongoClientOptionsBuilder.readPreference(getReadPrefenrece(environment.getProperty("mongodb.read_preference")))
            mongoClientOptionsBuilder.connectionsPerHost(5)
            mongoClient = new MongoClient(getServerAddresses(), getMongoCredential(), mongoClientOptionsBuilder.build())
        }
        return new SimpleMongoDbFactory(mongoClient, environment.getProperty("mongodb.db_name"))
    }

    @Bean
    MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDbFactory())
    }

    private static ReadPreference getReadPrefenrece(String property) {
        ReadPreference readPreference
        switch (property){
            case "primary":
                readPreference = ReadPreference.primary()
                break
            case "secundary":
                readPreference = ReadPreference.secondary()
                break
            case "primaryPreferred":
                readPreference = ReadPreference.primaryPreferred()
                break
            case "secundaryPreferred":
                readPreference = ReadPreference.secondaryPreferred()
                break
            case "nearest":
                readPreference = ReadPreference.nearest()
                break
            default:
                readPreference = ReadPreference.primary()
                break
        }

        return readPreference
    }

    private MongoCredential getMongoCredential() {
        return MongoCredential.createCredential(
                environment.getProperty("mongodb.user_name"),
                environment.getProperty("mongodb.db_name"),
                environment.getProperty("mongodb.password").toCharArray())
    }

    private List<ServerAddress> getServerAddresses()  {
        List<ServerAddress> serverAddresses = new ArrayList<>()
        String[] hosts = environment.getProperty("mongodb.host", String[].class)
        int[] ports = environment.getProperty("mongodb.port", int[].class)
        for (int i = 0; i < hosts.length; i++) {
            serverAddresses.add(new ServerAddress(hosts[i], ports[i]))
        }
        return serverAddresses
    }

    String getHost() {
        return environment.getProperty("mongodb.host")
    }

    String getPort() {
        return environment.getProperty("mongodb.port")
    }

    String getDatabaseName(){
        return environment.getProperty("mongodb.db_name")
    }

    String getUsername() {
        return environment.getProperty("mongodb.user_name")
    }

    String getPassword() {
        return environment.getProperty("mongodb.password")
    }

    String getReplicas() {
        return environment.getProperty("mongodb.replicas")
    }

    String getReadPreference() {
        return environment.getProperty("mongodb.read_preference")
    }
}
