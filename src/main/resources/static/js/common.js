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

Common.handleAjaxError = (event, jqxhr, settings, thrownError) => {
	console.error('E:' + event + ' response: ' + jqxhr.responseText + ' thrownError:' + thrownError);
	if (jqxhr.status == 302) {
		// TODO çözülecek!!!
		let redirectUrl = jqxhr.getResponseHeader('redirect_url');
		window.location = redirectUrl ? redirectUrl : '/login';
		return;
	}
	if (jqxhr.responseJSON && jqxhr.responseJSON.message) {
		console.error(jqxhr.responseJSON.message);
	} else {
		console.error('Unexpected error! URL:' + settings.url + ' Cause:' + thrownError);
	}
}
Common.handleAjaxSuccess = (event, jqxhr, settings, thrownError) => {
	let contentType = jqxhr.getResponseHeader('content-type');
	if (contentType && contentType.includes('text/html') 
		&& jqxhr.responseText && jqxhr.responseText.includes('id="loginForm"')) {
		window.location = '/login';
		return;
	}
	console.error('E:' + event + ' response: ' + jqxhr.responseText + ' thrownError:' + thrownError);
	if (jqxhr.status == 302) {
		// TODO çözülecek!!!
		let redirectUrl = jqxhr.getResponseHeader('redirect_url');
		window.location = redirectUrl ? redirectUrl : '/login';
		return;
	}
	if (jqxhr.responseJSON && jqxhr.responseJSON.message) {
		console.error(jqxhr.responseJSON.message);
	} else {
		console.error('Unexpected error! URL:' + settings.url + ' Cause:' + thrownError);
	}
}

// Sistem genelinde document ready'de çalışacak fonksiyonlar burada 
$(document).ready(function() {
	$(document).ajaxError(Common.handleAjaxError);
	$(document).ajaxSuccess(Common.handleAjaxSuccess);
});
