/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.sergeivisotsky.metadata.engine.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration;
import org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.r2dbc.R2dbcDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;
import org.springframework.boot.autoconfigure.influx.InfluxDbAutoConfiguration;
import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration;
import org.springframework.boot.autoconfigure.neo4j.Neo4jAutoConfiguration;
import org.springframework.boot.autoconfigure.netty.NettyAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.boot.autoconfigure.rsocket.RSocketMessagingAutoConfiguration;
import org.springframework.boot.autoconfigure.rsocket.RSocketRequesterAutoConfiguration;
import org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration;
import org.springframework.boot.autoconfigure.rsocket.RSocketStrategiesAutoConfiguration;
import org.springframework.boot.autoconfigure.security.rsocket.RSocketSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.sendgrid.SendGridAutoConfiguration;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.autoconfigure.webservices.WebServicesAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.reactive.WebSocketReactiveAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketMessagingAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Class which excludes not needed Spring Boot auto-configuration.
 *
 * @author Sergei Visotsky
 */
@Configuration
@EnableAutoConfiguration(exclude = {
        JpaRepositoriesAutoConfiguration.class,
        RabbitAutoConfiguration.class,
        BatchAutoConfiguration.class,
        CassandraAutoConfiguration.class,
        JdbcRepositoriesAutoConfiguration.class,
        R2dbcAutoConfiguration.class,
        R2dbcDataAutoConfiguration.class,
        LdapAutoConfiguration.class,
        MongoAutoConfiguration.class,
        ElasticsearchDataAutoConfiguration.class,
        CouchbaseAutoConfiguration.class,
        CouchbaseDataAutoConfiguration.class,
        FlywayAutoConfiguration.class,
        FreeMarkerAutoConfiguration.class,
        GroovyTemplateAutoConfiguration.class,
        GsonAutoConfiguration.class,
        HazelcastAutoConfiguration.class,
        InfluxDbAutoConfiguration.class,
        IntegrationAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        JmsAutoConfiguration.class,
        JooqAutoConfiguration.class,
        KafkaAutoConfiguration.class,
        MailSenderAutoConfiguration.class,
        MustacheAutoConfiguration.class,
        Neo4jAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        NettyAutoConfiguration.class,
        QuartzAutoConfiguration.class,
        RSocketMessagingAutoConfiguration.class,
        RSocketRequesterAutoConfiguration.class,
        RSocketServerAutoConfiguration.class,
        RSocketStrategiesAutoConfiguration.class,
        RSocketSecurityAutoConfiguration.class,
        SendGridAutoConfiguration.class,
        RedisAutoConfiguration.class,
        RedisReactiveAutoConfiguration.class,
        RedisRepositoriesAutoConfiguration.class,
        SolrAutoConfiguration.class,
        TaskExecutionAutoConfiguration.class,
        ThymeleafAutoConfiguration.class,
        TransactionAutoConfiguration.class,
        WebServicesAutoConfiguration.class,
        WebSocketReactiveAutoConfiguration.class,
        WebSocketMessagingAutoConfiguration.class,
        WebSocketServletAutoConfiguration.class
})
public class ExclusionConfiguration {
}
