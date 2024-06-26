$(document).ready(function() {
	function populateOrderModal(order) {
		$('#orderId').val(order.id);
		$('#orderDate').val(order.orderDate);
		let totalAmount = 0;
		order.orderDetails.forEach(function(orderDetail) {
			totalAmount += (orderDetail.quantity * orderDetail.itemPrice);
		});
		$('#orderAmount').val(totalAmount);
		$('#city').val(order.address.city);
		$('#district').val(order.address.district);
		$('#addressLine').val(order.address.addressLine);
	}
	
	let orderId = location.search.split('completedOrderId=')[1];
	if (orderId) {
		Common.ajax({
			url: 'order/getOrderById/' + orderId,
			type: 'GET',
			contentType: "application/json",
			headers: {
				Accept: "application/json",
			},
			success: function(response) {
				populateOrderModal(response);
				$('#completedOrderModal').modal('show');
			},
			error: function(xhr, status, error) {
				console.error("Error fetching products: " + error);
			}
		});
	}
	
	if ($('#message').val()) {
		alertify.succes($('#message').val());
	}
	
	// Function to fetch categories via Ajax
	function fetchCategories() {
		Common.ajax({
			url: '/category/list', // Endpoint to fetch categories
			type: 'GET',
			dataType: 'json',
			success: function(response) {
				// Clear existing categories
				$('#categoriesList').empty();

				if (!response || response.length == 0) {
					return;
				}
				// Append fetched categories
				response.forEach(function(category) {
					let cat = $("<a>", {
						class: "dropdown-item",
						text: category.categoryName,
						href: "?categoryId=" + category.categoryId,
						"data-category-id": category.categoryId // Add data attribute for category ID
					});
					$('#categoriesList').append(cat);
					if (category.subcategories) {
						category.subcategories.forEach(function(subcategory) {
							let subCat = $("<a>", {
								class: "dropdown-item",
								style: "padding-left: 50px;",
								text: subcategory.subcategoryName,
								href: "?subcategoryId=" + subcategory.subcategoryId ,
								"data-subcategory-id": subcategory.subcategoryId // Add data attribute for subcategory ID
							});
							$('#categoriesList').append(subCat);
						});
					}
					let divider = $("<div>", {
						class: "dropdown-divider"
					});
					$('#categoriesList').append(divider);
				});
			},
			error: function(xhr, status, error) {
				console.error('Error fetching categories:', error);
			}
		});
	}

	// Function to fetch all products
	function fetchAllProducts() {
		let categoryId = $("#categoryId").val();
		let subcategoryId = $("#subcategoryId").val();
		let url = '/product/list?';
		if (categoryId) {
			url += 'categoryId=' + categoryId + '&';
		}
		if (subcategoryId) {
			url += 'subcategoryId=' + subcategoryId;
		}
		
		Common.ajax({
			url: url,
			type: 'GET',
			contentType: "application/json",
			headers: {
				Accept: "application/json",
			},
			success: function(response) {
				displayProducts(response);
			},
			error: function(xhr, status, error) {
				console.error("Error fetching products: " + error);
			}
		});
	}

	// Fetch categories when the page loads
	fetchCategories();

	// Fetch all products and display them when the page loads
	fetchAllProducts();

	// Function to fetch products by category ID
	function fetchProductsByCategory(categoryId) {
		$.ajax({
			url: '/product/list?category=' + categoryId, // Endpoint to fetch products by category
			type: 'GET',
			dataType: 'json',
			success: function(response) {
				displayProducts(response);
			},
			error: function(xhr, status, error) {
				console.error('Error fetching products by category:', error);
			}
		});
	}

	// Function to fetch products by subcategory ID
	function fetchProductsBySubcategory(subcategoryId) {
		$.ajax({
			url: '/product/list?subcategory=' + subcategoryId, // Endpoint to fetch products by subcategory
			type: 'GET',
			dataType: 'json',
			success: function(response) {
				displayProducts(response);
			},
			error: function(xhr, status, error) {
				console.error('Error fetching products by subcategory:', error);
			}
		});
	}

	// Function to display products
	function displayProducts(products) {
		if (products && products.length > 0) {
			$('#productsRow').empty(); // Clear existing products
			products.forEach(function(product) {
				let card = '<div class="col-md-4 mb-4">' +
					'<div class="card">' +
					'<img src="' + product.image + '" class="card-img-top" alt="' + product.productName + '">' +
					'<div class="card-body">' +
					'<h5 class="card-title">' + product.productName + '</h5>' +
					'<p class="card-text">Price: ' + product.price + '</p>' +
					'<a href="/product/product-details/' + product.productId + '" class="btn btn-primary">View Product</a>';
				if (product.stock > 0) {
					card += '<button class="btn btn-success addToCartBtn" data-productId="' + product.productId + '">Add to Cart</button>';
				}	
					card += '</div>' +
					'</div>' +
					'</div>';
				$('#productsRow').append(card);
			});
			// Attach event handler for Add to Cart buttons
			$('.addToCartBtn').click(function() {
				let productId = $(this).data('productid');
				Cart.addToCart(productId);
			});
		} else {
			console.log("No products found.");
		}
	}
});
