package org.example.springjdbctemplate.model;

import lombok.Builder;
import lombok.Value;

/**
 * Customer model.
 *
 * @author Yauheni Halaidzin
 * @since 14.05.2024
 */
@Value
@Builder
public class Customer {

    private long id;

    private String firstName;

    private String lastName;

}
