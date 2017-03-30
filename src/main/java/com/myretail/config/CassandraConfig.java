package com.myretail.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Cassandra Configuration
 */
@Configuration
@PropertySource(value = { "classpath:cassandra.properties" })
public class CassandraConfig {
    private static final String KEYSPACE = "cassandra.keyspace";
    private static final String CONTACTPOINTS = "cassandra.contactpoints";

    @Autowired
    private Environment environment;

    /**
     * Session to connect Cassandra
     */
    @Bean
    Session session() {
        Cluster  cluster = Cluster.builder().withProtocolVersion(ProtocolVersion.V3)
                .addContactPoint(environment.getProperty(CONTACTPOINTS)).build();
        Session session = cluster.connect(environment.getProperty(KEYSPACE));

        return session;
    }

}
