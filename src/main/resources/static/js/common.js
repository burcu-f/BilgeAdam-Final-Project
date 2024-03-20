Common = window.Common || {};
window.Common = Common;

Common.ajax = ({url, type, success, error, data, options}) => {
	let params = {
	    url: url,
	    type: type,
	    headers: options?.headers || 
	    {          
			Accept: "application/json",         
		},
		contentType: 'application/json',
	    success: function(response) {
			if (success) {
				success(response);
			}
		},
	    //error: error ? error(xhr, status, error) : function(xhr, status, error) {
			
		error: function(xhr, status, error) {  // Adjusted error handling function
            if (error) {
                error(xhr, status, error);  // Call the provided error function
            } else {
	        console.error("Error refreshing list: " + error);
	    }
	}
	};
	if (data) {
		params.data = data;
	}
	$.ajax(params);
}

Common.createElement = (element, content) => {
	return $(element, {
		text: content
	});
}

Common.createTd = (content) => {
	return Common.createElement('<td>', content);
}