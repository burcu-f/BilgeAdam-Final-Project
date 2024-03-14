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
            fillDropdownMenu(response); // Fill the dropdown menu with categories
            renderCategoryLinks(response); // Render category links for subcategory listing
        },
        error: function(xhr, status, error) {
            console.error('Failed to fetch categories:', error);
        }
    });
}

function fillDropdownMenu(categories) {
    var dropdownMenu = $('#categoriesDropdown'); // Get the dropdown menu element

    categories.forEach(function(category) {
        var option = $('<option></option>'); // Create an option element
        option.text(category.categoryName); // Set the text of the option to the category name
        option.attr('value', category.categoryId); // Set the value attribute to the category ID
        dropdownMenu.append(option); // Append the option to the dropdown menu
    });
}

function renderCategoryLinks(categories) {
    var categoriesDropdown = $('#categoriesDropdown');

    categories.forEach(function(category) {
        var option = $('<option></option>'); // Create an option element
        option.text(category.categoryName); // Set the text of the option to the category name
        option.attr('value', category.categoryId); // Set the value attribute to the category ID
        categoriesDropdown.append(option); // Append the option to the dropdown menu

        var categoryLink = $('<a class="category-link"></a>'); // Create a link for the category
        categoryLink.attr('href', '#'); // Set the href attribute for the link
        categoryLink.text(category.categoryName); // Set the text of the link to the category name
        categoryLink.click(function() {
            fetchSubcategories(category.categoryId); // Fetch subcategories when category link is clicked
        });

        categoriesDropdown.after(categoryLink); // Append the category link after the dropdown menu
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
