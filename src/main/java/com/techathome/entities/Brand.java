package com.techathome.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
	    
	    @OneToMany(mappedBy = "brand")
	    private List<Product> products;

	    public Brand(String brandName) {
	        this.brandName = brandName;
	    }
    
}
