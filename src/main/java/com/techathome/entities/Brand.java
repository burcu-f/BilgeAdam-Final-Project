package com.techathome.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Brand {

	 	@Id
	    @GeneratedValue(generator = "brand_id_generator")
	    @SequenceGenerator(name = "brand_id_generator", sequenceName = "brand_id_seq", allocationSize = 1)
	    private Long brandId;

	    private String brandName;

	    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
	    private List<Product> products;


    
}
