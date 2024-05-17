package org.example.hibernate.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Student entity.
 *
 * @author Yauheni Halaidzin
 * @since 25.04.2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "StudentCore")
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Uncomment two lines below for sequence generation type
    // @SequenceGenerator(name = "seqGenerator", allocationSize = 100)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGenerator")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "group_number")
    private Integer groupNumber;

}
