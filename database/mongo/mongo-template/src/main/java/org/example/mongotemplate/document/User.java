package org.example.mongotemplate.document;

import java.util.Map;

import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * User document.
 *
 * @author Yauheni Halaidzin
 * @since 21.06.2024
 */
@Value
@Document
public class User {

    @Id
    private String userId;

    private String name;

    private Map<String, String> userSettings;

}
