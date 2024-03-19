$(document).ready(function() {
    // Fetch categories and render on page load
    fetchCategories();
    fetchProducts();
});

function fetchCategories() {
    $.ajax({
        url: '/category-management/categories',
        type: 'GET',
        headers: {          
            Accept: "application/json",         
        },
        success: function(response) {
            console.log(typeof response); // Log the type of response
            console.log(response); // Log the entire response object
            fillDropdownMenu(response); // Fill the dropdown menu with categories
            
        },
        error: function(xhr, status, error) {
            console.error('Failed to fetch categories:', error);
        }
    });
}

function fillDropdownMenu(categories) {
    var dropdownMenu = $('#categoriesDropdown'); // Get the dropdown menu element

    categories.forEach(function(category) {
       var li = $('<li class="dropdown-submenu"></li>'); // Create a list item for the category
        var categoryLink = $('<a class="dropdown-item" href="#">' + category.categoryName + '</a>'); // Create a link for the category
        li.append(categoryLink); // Append the category link to the list item
        dropdownMenu.append(li); // Append the list item to the dropdown menu
        
        categoryLink.click(function() {
            fetchSubcategories(category.categoryId); // Fetch subcategories when category link is clicked
        });
    });
}


function fetchSubcategories(categoryId) {
    $.ajax({
        url: '/category-management/' + categoryId + '/subcategories',
        type: 'GET',
        headers: {          
            Accept: "application/json",         
        },
        success: function(response) {
            renderSubcategories(response);
        },
        error: function(xhr, status, error) {
            console.error('Failed to fetch subcategories:', error);
        }
    });
}

function renderSubcategories(subcategories) {
    var subcategoryContainer = $('#subcategoriesDropdown');
    subcategoryContainer.empty(); // Clear previous content

    subcategories.forEach(function(subcategory) {
        var option = $('<option></option>'); // Create an option element for subcategory
        option.text(subcategory.subcategoryName); // Set the text of the option to the subcategory name
        option.attr('value', subcategory.subcategoryId); // Set the value attribute to the subcategory ID
        subcategoryContainer.append(option); // Append the option to the subcategory dropdown menu
    });
}


document.addEventListener("DOMContentLoaded", function () {
    fetchProducts();
});

function fetchProducts() {
    fetch("/product-management/products")
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch products');
            }
            return response.json();
        })
        .then(products => {
            displayProducts(products);
        })
        .catch(error => {
            console.error('Error fetching products:', error);
        });
}


function displayProducts(products) {
    const productsContainer = $('#productsContainer');

    products.forEach(product => {
        const productCard = createProductCard(product);
        productsContainer.append(productCard);

    });
}

function createProductCard(product) {
    const colDiv = $('<div></div>').addClass('col-md-4');
    const itemDiv = $('<div></div>').addClass('item');
    
    const title = $('<h4></h4>').text(product.productName);
    const price = $('<p></p>').addClass('text-primary').text(`$${product.price}`);
    const image = $('<img>').addClass('img-fluid').attr('src', product.image).attr('alt', 'Product Image');
    const overlay = $('<div></div>').addClass('overlay d-flex align-items-center justify-content-center');
    const viewDetailsBtn = $('<a></a>').addClass('btn btn-unique').attr({'href': `detail.html?id=${product.productId}`, 'data-abc': 'true'}).text('View Details');
    
    overlay.append(viewDetailsBtn);
    itemDiv.append(title, price, image, overlay);
    colDiv.append(itemDiv);
    
    return colDiv;
}

// Fetch brands and render dropdown menu on page load
$(document).ready(function () {
    fetchBrands();
});

// Fetch brands from the server
function fetchBrands() {
    $.ajax({
        url: '/brand-management/brands',
        type: 'GET',
        success: function (response) {
            fillBrandDropdown(response);
        },
        error: function (xhr, status, error) {
            console.error('Failed to fetch brands:', error);
        }
    });
}

// Fill the brand dropdown menu with fetched brands
function fillBrandDropdown(brands) {
    var dropdownMenu = $('#brandDropdownMenu');

    brands.forEach(function (brand) {
        var li = $('<li></li>');
        var brandLink = $('<a class="dropdown-item" href="#">' + brand.brandName + '</a>');

        li.append(brandLink);
        dropdownMenu.append(li);

        brandLink.click(function () {
            showProductsByBrand(brand.brandId);
        });
    });
}

// Show products for the selected brand
function showProductsByBrand(brandId) {
    window.location.href = '/products.html?brand=' + brandId; // Redirect to products page with selected brand ID
}



