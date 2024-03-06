$(document).ready(function() {
    // Fetch categories and render on page load
    fetchCategories();
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

    var categoriesRow = $('<div class="row" id="categories-row"></div>'); // Create a new row for categories

    categories.forEach(function(category) {
        var categoryColumn = $('<div class="col-sm-4"></div>'); // Create a column for each category
        var categoryBox = $('<div class="category-box"></div>'); // Create a box for the category

        var categoryLink = $('<a class="category-link"></a>'); // Create a link for the category
        categoryLink.attr('href', '#'); // Set the href attribute for the link
        categoryLink.text(category.categoryName); // Set the text of the link to the category name

        var categoryDescription = $('<p></p>'); // Create a paragraph for the category description
        categoryDescription.text(category.description); // Set the text of the paragraph to the category description

        categoryBox.append(categoryLink); // Append the link to the category box
        categoryBox.append(categoryDescription); // Append the description to the category box
        categoryColumn.append(categoryBox); // Append the category box to the column
        categoriesRow.append(categoryColumn); // Append the column to the row
    });

    container.append(categoriesRow); // Append the row to the container
}


function fetchSubcategories(categoryId) {
    $.ajax({
        url: '/category-management/' + categoryId + '/subcategories',
        type: 'GET',
        headers: {          
                Accept: "application/json",         
            },
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
}

function redirectToLogin() {
    window.location.href = "login.html";
}
