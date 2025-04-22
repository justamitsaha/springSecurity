// Initialize the login link with dynamic URL
$(document).ready(function () {
    // Build the login URL using config
    const authUrl = `${keycloakConfig.baseUrl}/realms/${keycloakConfig.realm}${keycloakConfig.authEndpoint}?` +
        `client_id=${keycloakConfig.clientId}&` +
        `response_type=code&` +
        `scope=${keycloakConfig.scopes}&` +
        `redirect_uri=${encodeURIComponent(keycloakConfig.redirectUri)}&` +
        `state=dfddfddddd`;

    $('#loginLink').attr('href', authUrl);

    // Set form values from config
    $('#client_id').val(keycloakConfig.clientId);
    $('#client_secret').val(keycloakConfig.clientSecret);
    $('#grant_type').val(keycloakConfig.grant_type);
    //$('#code').val(keycloakConfig.code);
    $('#redirect_uri').val(keycloakConfig.redirectUri);

    // Get access code from URL
    const accessCode = getParameterByName('code');
    $("#accessCode").html("Access code  --> " + accessCode);
    $("#code").val(accessCode);


    // Token request handler
    $('#submitButton').click(function (e) {
        e.preventDefault();
        const tokenUrl = `${keycloakConfig.baseUrl}/realms/${keycloakConfig.realm}${keycloakConfig.tokenEndpoint}`;

        $.ajax({
            url: tokenUrl,
            type: 'post',
            dataType: 'json',
            data: $('#myForm').serialize(),
            success: function (data) {
                console.log(data);
                accessToken = data.access_token;
                $("#access_token").html("Access code  --> " + data.access_token);
            },
            error: function (err) {
                alert("Error -->" + JSON.stringify(err.responseJSON));
            }
        });
    });

    // Event listeners for buttons
    protectedUpdate.addEventListener('click', () => {
        makeApiRequest("/private/protectedUpdate");
    });

    publicUpdate.addEventListener('click', () => {
        makeApiRequest("/public/publicUpdate");
    });

    // Dropdown change handler
    document.getElementById('apiDropdown').addEventListener('change', function () {
        const selectedPath = this.value;
        var config;

        if (selectedPath.includes("public")) {
            config = {
                credentials: "include"
            };
        } else {
            config = {
                credentials: "include",
                headers: {
                    'Authorization': 'Bearer ' + accessToken,
                    'Content-Type': 'application/json'
                }
            };
        }


        if (selectedPath) {
            fetch(selectedPath, config
            )
                .then(res => res.text())
                .then(data => {
                    document.getElementById('apiResponse').innerText = data;
                })
                .catch(err => {
                    document.getElementById('apiResponse').innerText = '❌ Error fetching API.';
                });
        } else {
            document.getElementById('apiResponse').innerText = '';
        }
    });
});


// Function to make API request
function makeApiRequest(uri) {

    const statusElement = document.getElementById("statusMessage");
    var XSRFTOKEN = getCookie("XSRF-TOKEN");
    // Update status to show request is in progress

    if (uri.includes("public")) {
        config = {
            method: 'POST',
            credentials: "include",
            headers: {
                'Content-Type': 'application/json',
                'X-XSRF-TOKEN': XSRFTOKEN
            }
        };
    } else {
        config = {
            method: 'POST',
            credentials: "include",
            headers: {
                'Content-Type': 'application/json',
                'X-XSRF-TOKEN': XSRFTOKEN,
                'Authorization': 'Bearer ' + accessToken
            }
        };
    }

    fetch(uri, config)
        .then(response => {
            // First check if the response is ok (status in the range 200-299)
            if (!response.ok) {
                return response.text().then(text => {
                    throw new Error(`Error ${response.status}: ${text || response.statusText}`);
                });
            }

            // Get the response as text
            return response.text();
        })
        .then(data => {
            // Success case - display the string response
            statusElement.textContent = data;
        })
        .catch(error => {
            // Error case
            statusElement.textContent = error.message;
            console.error("Request failed:", error);
        }).finally(() => {
            // This section will always execute regardless of success or failure
            XSRFTOKEN = getCookie("XSRF-TOKEN");
        });
}



// Utility function to get URL parameters
function getParameterByName(name, url = window.location.href) {
    name = name.replace(/[\[\]]/g, '\\$&');
    const regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)');
    const results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}

function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}