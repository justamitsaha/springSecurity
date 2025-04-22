window.onload = function () {

    //var domain = "http://localhost:8080";
    var domain = "";
    var XSRFTOKEN = "";

    checkLoginStatus();

    // Check login status after form post login
    function checkLoginStatus() {
        const jwt = sessionStorage.getItem('access_token');
        fetch(domain + '/public/me',
            {
                credentials: "include",
                headers: {
                    'Authorization': 'Bearer ' + jwt,
                    'Content-Type': 'application/json'
                }
            })
            .then(res => res.ok ? res.json() : Promise.reject())
            .then(data => {
                document.getElementById('loginStatus').innerText = `✅ Logged in with username: ${data.username}`;
            })
            .catch(() => {
                document.getElementById('loginStatus').innerText = '❌ Not logged in';
            }).finally(() => {
                // This section will always execute regardless of success or failure
                XSRFTOKEN = getCookie("XSRF-TOKEN");
            });
    }



    document.getElementById('checkLoginStatus').addEventListener('click', function () {
        checkLoginStatus();
    });



    // Dropdown change handler
    document.getElementById('apiDropdown').addEventListener('change', function () {
        const selectedPath = this.value;
        const jwt = sessionStorage.getItem('access_token');

        if (selectedPath) {
            fetch(domain + selectedPath,
                {
                    credentials: "include",
                    headers: {
                        'Authorization': 'Bearer ' + jwt,
                        'Content-Type': 'application/json'
                    }
                })
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


    // Function to make API request
    function makeApiRequest(uri) {

        const statusElement = document.getElementById("statusMessage");

        // Update status to show request is in progress
        statusElement.textContent = "Making request...";
        const jwt = sessionStorage.getItem('access_token');

        fetch(domain + uri, {
            method: 'POST',
            credentials: "include",
            headers: {
                'Content-Type': 'application/json',
                'X-XSRF-TOKEN': XSRFTOKEN,
                'Authorization': 'Bearer ' + jwt
            }
        })
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

    // Event listeners for buttons
    protectedUpdate.addEventListener('click', () => {
        makeApiRequest("/private/protectedUpdate");
    });

    publicUpdate.addEventListener('click', () => {
        makeApiRequest("/public/publicUpdate");
    });


    function getCookie(name) {
        const value = `; ${document.cookie}`;
        const parts = value.split(`; ${name}=`);
        if (parts.length === 2) return parts.pop().split(';').shift();
    }

    // window.onload = function () {
    //     const secureToken = getCookie("SECURE_TOKEN");
    //     const plainSession = getCookie("PLAIN_SESSION_ID");

    //     document.getElementById("secureCookieDisplay").innerText =
    //         secureToken ? `✅ Secure Cookie: ${secureToken}` : "❌ Secure Cookie not found";

    //     document.getElementById("plainCookieDisplay").innerText =
    //         plainSession ? `✅ Plain Cookie: ${plainSession}` : "❌ Plain Cookie not found";
    // };
};