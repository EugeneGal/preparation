package org.example.mongospringdata;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mongospringdata.document.GroceryItem;
import org.example.mongospringdata.repository.GroceryItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Executor.
 *
 * https://www.mongodb.com/resources/products/compatibilities/spring-boot
 *
 * @author Yauheni Halaidzin
 * @since 04.06.2024
 */
@Slf4j
@EnableMongoRepositories
@SpringBootApplication
@RequiredArgsConstructor
public class Executor implements CommandLineRunner {

    private final GroceryItemRepository repository;

    // docker pull mongo
    // docker run -p 27017:27017 --name my-mongo -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=root -d mongo
    // stop and remove running container: docker container rm -f my-mongo
    public static void main(String[] args) {
        SpringApplication.run(Executor.class, args);
    }

    @Override
    public void run(String... args) {
        // Delete existing items
        repository.deleteAll();

        // Create new items
        createGroceryItems();

        // Find all items
        log.info("All grocery items: {}", repository.findAll());

        // Find items by name
        log.info("Items found by name: {}", repository.findItemByName("Whole Wheat Biscuit"));

        // Find items by category
        log.info("Items found by category: {}", repository.findAll("snacks"));

        // Get count of all items
        log.info("Count of all items: {}", repository.count());

        // Update category
        updateCategory("snacks");
    }

    private void createGroceryItems() {
        repository.save(new GroceryItem("Whole Wheat Biscuit", "Whole Wheat Biscuit", 5, "snacks"));
        repository.save(new GroceryItem("Kodo Millet", "XYZ Kodo Millet healthy", 2, "millets"));
        repository.save(new GroceryItem("Dried Red Chilli", "Dried Whole Red Chilli", 2, "spices"));
        repository.save(new GroceryItem("Pearl Millet", "Healthy Pearl Millet", 1, "millets"));
        repository.save(new GroceryItem("Cheese Crackers", "Bonny Cheese Crackers Plain", 6, "snacks"));
    }

    private void updateCategory(String category) {
        String newCategory = "munchies";

        // Find all items with needed category
        List<GroceryItem> items = repository.findAll(category);

        items.forEach(item -> {
            // Update category in each document
            item.setCategory(newCategory);
        });

        // Save all items in database
        List<GroceryItem> updatedItems = repository.saveAll(items);

        log.info("Category was successfully updated: {}", updatedItems);
    }

}
