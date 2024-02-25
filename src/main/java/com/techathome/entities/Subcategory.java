package com.techathome.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
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
