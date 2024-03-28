package com.techathome.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddToCartPayload {

	private Long productId;
	private int quantity;
}
