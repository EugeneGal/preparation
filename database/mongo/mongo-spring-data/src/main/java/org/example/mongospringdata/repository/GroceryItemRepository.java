package org.example.mongospringdata.repository;

import java.util.List;

import org.example.mongospringdata.document.GroceryItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * Grocery item repository.
 *
 * @author Yauheni Halaidzin
 * @since 09.06.2024
 */
public interface GroceryItemRepository extends MongoRepository<GroceryItem, String> {

    @Query("{name:'?0'}")
    List<GroceryItem> findItemByName(String name);

    @Query(value="{category:'?0'}", fields="{'name' : 1, 'quantity' : 1}")
    List<GroceryItem> findAll(String category);

    long count();

}
