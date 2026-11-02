package com.example.sales.report.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.flamingock.internal.core.external.store.CommunityAuditStore;
import io.flamingock.store.mongodb.sync.MongoDBSyncAuditStore;
import io.flamingock.targetsystem.mongodb.springdata.MongoDBSpringDataTargetSystem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class FlamingockConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().findAndRegisterModules();
    }

    @Bean
    public MongoDBSpringDataTargetSystem mongoDatabaseTargetSystem(MongoTemplate mongoTemplate) {
        return new MongoDBSpringDataTargetSystem("mongodb", mongoTemplate);
    }

    @Bean
    public CommunityAuditStore auditStore(MongoDBSpringDataTargetSystem mongoDBSpringDataTargetSystem) {
        return MongoDBSyncAuditStore.from(mongoDBSpringDataTargetSystem);
    }
}
