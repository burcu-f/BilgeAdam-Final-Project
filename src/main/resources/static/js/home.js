$(document).ready(function() {
    // Fetch categories and render on page load
    fetchCategories();
});

function fetchCategories() {
    $.ajax({
        url: '/category-management/categories',
        type: 'GET',
        success: function(response) {
            renderCategories(response);
        },
        error: function(xhr, status, error) {
            console.error('Failed to fetch categories:', error);
        }
    });
}

function renderCategories(categories) {
    var container = $('#category-container');
    container.empty(); // Clear previous content

    categories.forEach(function(category) {
        var categoryHTML = '<div class="row">' +
                               '<div class="col-sm-12">' +
                                   '<h3>' + category.categoryName + '</h3>' +
                                   '<div id="subcategory-container-' + category.categoryId + '">' +
                                       // Subcategories will be dynamically rendered here
                                   '</div>' +
                               '</div>' +
                           '</div>';
        container.append(categoryHTML);

        fetchSubcategories(category.categoryId); // Fetch subcategories for this category
    });
}

function fetchSubcategories(categoryId) {
    $.ajax({
        url: '/category-management/categories/' + categoryId + '/subcategories',
        type: 'GET',
        success: function(response) {
            renderSubcategories(categoryId, response);
        },
        error: function(xhr, status, error) {
            console.error('Failed to fetch subcategories:', error);
        }
    });
}

function renderSubcategories(categoryId, subcategories) {
    var subcategoryContainer = $('#subcategory-container-' + categoryId);
    subcategoryContainer.empty(); // Clear previous content

    subcategories.forEach(function(subcategory) {
        var subcategoryHTML = '<p>' + subcategory.subcategoryName + '</p>';
        subcategoryContainer.append(subcategoryHTML);
    });
    
function redirectToLogin() {
    window.location.href = "login.html";
}
}
