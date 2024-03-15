package com.techathome.entities;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Subcategory {

    @Id
    @GeneratedValue(generator = "subcategory_id_generator")
    @SequenceGenerator(name = "subcategory_id_generator", sequenceName = "subcategory_id_seq", allocationSize = 1)
    private Long subcategoryId;
    private String subcategoryName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
