/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.configserver.util;

import io.debezium.configserver.rest.client.KafkaConnectClientFactory;
import io.debezium.configserver.rest.client.jolokia.JolokiaClient;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractInfrastructureTestResourceLifecycleManager implements QuarkusTestResourceLifecycleManager {

    @Override
    public Map<String, String> start() {
        Map<String, String> config = new HashMap<>();
        config.put(
                KafkaConnectClientFactory.PROPERTY_KAFKA_CONNECT_URIS,
                "http://" + Infrastructure.getDebeziumContainer().getHost() + ":" + Infrastructure.getDebeziumContainer().getMappedPort(8083)
        );
        config.put(JolokiaClient.PROPERTY_CONNECT_JOLOKIA_PORT,
                String.valueOf(Infrastructure.getDebeziumContainer().getMappedPort(JolokiaClient.DEFAULT_JOLOKIA_PORT)));
        return config;
    }

    @Override
    public void stop() {
        Infrastructure.getNetwork().close();
    }

    // optional
    @Override
    public void inject(Object testInstance) {
    }

    // optional
    @Override
    public int order() {
        return 0;
    }

}

