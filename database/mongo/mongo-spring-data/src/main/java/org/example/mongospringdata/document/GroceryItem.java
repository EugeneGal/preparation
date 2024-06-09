package org.example.mongospringdata.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Grocery item document.
 *
 * @author Yauheni Halaidzin
 * @since 09.06.2024
 */
@Data
@AllArgsConstructor
@Document("grocery")
public class GroceryItem {

    @Id
    private String id;

    private String name;

    private int quantity;

    private String category;

}
