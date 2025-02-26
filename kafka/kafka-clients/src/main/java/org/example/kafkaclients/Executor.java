package org.example.kafkaclients;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.kafkaclients.config.ContainerWrapper;
import org.testcontainers.containers.KafkaContainer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

/**
 * Executor for running producer and consumer simultaneously.
 *
 * @author Yauheni Halaidzin
 * @since 28.08.2024
 */
@Slf4j
public class Executor {

    private static final String TOPIC = "my-topic";

    private static final AtomicBoolean isActive = new AtomicBoolean(true);

    @SneakyThrows
    public static void main(String[] args) {
        try (ContainerWrapper wrapper = new ContainerWrapper();
             Admin admin = Admin.create(getAdminProperties(wrapper.getContainer()));
             KafkaProducer<String, String> producer = new KafkaProducer<>(getProducerProperties(wrapper.getContainer()));
             KafkaConsumer<Object, Object> consumer = new KafkaConsumer<>(getConsumerProperties(wrapper.getContainer()))) {

            NewTopic topic = new NewTopic(TOPIC, 5, (short) 1);
            admin.createTopics(List.of(topic)).all().get(10, TimeUnit.SECONDS);

            consumer.subscribe(List.of(TOPIC));

            produceRecords(producer);

            CompletableFuture<Void> future = consumeRecords(consumer);
            future.join();
        }
    }

    private static void produceRecords(KafkaProducer<String, String> producer) {
        for (int i = 1; i <= 10; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, "key" + i, "my event: #" + i);

            producer.send(record,
                (metadata, exception) -> log.info("Record was sent to topic: offset={}, partition={}, timestamp={}, record={}",
                    metadata.offset(), metadata.partition(), metadata.timestamp(), record));
        }

        isActive.set(false);
    }

    private static CompletableFuture<Void> consumeRecords(KafkaConsumer<Object, Object> consumer) {
        return CompletableFuture.runAsync(() -> {
            while (isActive.get()) {
                consume(consumer, 1);
            }

            consume(consumer, 5);
        });
    }

    private static void consume(KafkaConsumer<Object, Object> consumer, int waitTimeSec) {
        ConsumerRecords<Object, Object> records = consumer.poll(Duration.ofSeconds(waitTimeSec));

        records.forEach(record -> log.info("Message received: offset={}, partition={}, timestamp={}, key={}, value={}",
            record.offset(), record.partition(), record.timestamp(), record.key(), record.value()));
    }

    private static Properties getAdminProperties(KafkaContainer container) {
        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, container.getBootstrapServers());

        return props;
    }

    private static Properties getProducerProperties(KafkaContainer container) {
        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, container.getBootstrapServers());
        props.put(ACKS_CONFIG, "all");
        props.put(LINGER_MS_CONFIG, 1);
        props.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        return props;
    }

    private static Properties getConsumerProperties(KafkaContainer container) {
        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, container.getBootstrapServers());
        props.put(GROUP_ID_CONFIG, "my_app_group_id");
        props.put(AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        return props;
    }

}
