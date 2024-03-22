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
            fillDropdownMenu(response); // Fill the dropdown menu with categories
            
        },
        error: function(xhr, status, error) {
            console.error('Failed to fetch categories:', error);
        }
    });
}

function fillDropdownMenu(categories) {
    var dropdownMenu = $('#categoriesDropdownMenu'); // Get the dropdown menu element

    categories.forEach(function(category) {
       var li = $('<li class="nav-item dropdown"></li>');
        var categoryLink = $('<a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">' + category.categoryName + '</a>');
        var subcategoriesDropdown = $('<ul class="dropdown-menu" aria-labelledby="navbarDropdown"></ul>');

        //categoryLink.click(function() {
           // fetchSubcategories(category.categoryId, subcategoriesDropdown); // Fetch and fill subcategories when category link is clicked
       // });
        
        // Fill subcategories dropdown
        category.subcategories.forEach(function(subcategory) {
            var subcategoryLink = $('<a class="dropdown-item" href="#">' + subcategory.subcategoryName + '</a>'); // Create a link for the subcategory
            subcategoriesDropdown.append(subcategoryLink); // Append subcategory link to dropdown menu
        });
        
         li.append(categoryLink); // Append the category link to the list item
        li.append(subcategoriesDropdown); // Append subcategories dropdown to the list item
        dropdownMenu.append(li); // Append the list item to the dropdown menu
        
        // Add click event listener to category link to toggle subcategories dropdown
        categoryLink.click(function(event) {
            event.preventDefault(); // Prevent default link behavior
            $(this).next('.dropdown-menu').toggleClass('show'); // Toggle visibility of subcategories dropdown
        });

        //li.append(categoryLink, subcategoriesDropdown);
        //dropdownMenu.append(li);
    });
}


/*function fetchSubcategories(categoryId, subcategoriesDropdown) {
    $.ajax({
        url: '/category-management/' + categoryId + '/subcategories',
        type: 'GET',
        headers: {          
            Accept: "application/json",         
        },
        success: function(response) {
            renderSubcategories(response, subcategoriesDropdown);
        },
        error: function(xhr, status, error) {
            console.error('Failed to fetch subcategories:', error);
        }
    });
}

function renderSubcategories(subcategories, subcategoriesDropdown) {
    subcategoriesDropdown.empty(); // Clear previous content

    subcategories.forEach(function(subcategory) {
        var subcategoryItem = $('<li><a class="dropdown-item" href="#">' + subcategory.subcategoryName + '</a></li>');
        subcategoriesDropdown.append(subcategoryItem);
    });
}
*/

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

// Attach event listener to the brand dropdown button
    $('#brandDropdown').on('click', function() {
        $(this).next('.dropdown-menu').toggleClass('show');
    });


// Fetch brands and render dropdown menu on page load
$(document).ready(function () {
    fetchBrands();
});

// Fetch brands from the server
function fetchBrands() {
    $.ajax({
        url: '/brand-management/brands',
        type: 'GET',
        headers: {          
            Accept: "application/json",         
        },
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



