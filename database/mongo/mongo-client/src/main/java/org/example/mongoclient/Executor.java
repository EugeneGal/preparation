package org.example.mongoclient;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

/**
 * Mongo client executor.
 *
 * https://www.baeldung.com/java-mongodb
 *
 * @author Yauheni Halaidzin
 * @since 29.05.2024
 */
@Slf4j
public class Executor {

    // docker pull mongo
    // docker run -p 27017:27017 --name my-mongo -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=root -d mongo
    // stop and remove running container: docker container rm -f my-mongo
    public static void main(String[] args) {
        try (MongoClient client = MongoClients.create("mongodb://root:root@localhost:27017")) {
            String dbNames = StreamSupport.stream(client.listDatabaseNames().spliterator(), false)
                .collect(Collectors.joining(", "));
            log.info("Initial DB names: {}", dbNames);

            // create database and collection
            MongoDatabase database = client.getDatabase("my-db");
            database.createCollection("employees");

            String dbNamesAfterCreatingCollection = StreamSupport.stream(client.listDatabaseNames().spliterator(), false)
                .collect(Collectors.joining(", "));
            log.info("DB names after adding new database: {}", dbNamesAfterCreatingCollection);

            String collectionNames = StreamSupport.stream(database.listCollectionNames().spliterator(), false)
                .collect(Collectors.joining(", "));
            log.info("Collection names: {}", collectionNames);

            // insert document into collection
            MongoCollection<Document> collection = database.getCollection("employees");
            Document document = new Document(Map.of("name", "Stuart", "age", 31, "company", "Google Inc"));
            collection.insertOne(document);

            log.info("All documents after first insert");
            collection.find().forEach(doc -> log.info("Document: {}", doc));

            // update document
            Document query = new Document(Map.of("age", 31));
            Document update = new Document();
            update.put("$set", new Document("age", 29));
            collection.updateOne(query, update);

            log.info("All documents after update");
            collection.find().forEach(doc -> log.info("Document: {}", doc));

            // delete document
            collection.deleteMany(new Document("name", "Stuart"));

            log.info("All documents after delete");
            collection.find().forEach(doc -> log.info("Document: {}", doc));
        }
    }

}
