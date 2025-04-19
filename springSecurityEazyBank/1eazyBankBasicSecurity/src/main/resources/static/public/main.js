window.onload = function () {

    //var domain = "http://localhost:8080";
    var domain = "";
    var XSRFTOKEN = "";

    checkLoginStatus();

    // Check login status after form post login
    function checkLoginStatus() {
        fetch(domain + '/public/me')
            .then(res => res.ok ? res.json() : Promise.reject())
            .then(data => {
                document.getElementById('loginStatus').innerText = `✅ Logged in with username: ${data.username}`;
                document.getElementById('formLoginStatus').innerText = '✅ Login successful';
            })
            .catch(() => {
                document.getElementById('loginStatus').innerText = '❌ Not logged in';
                document.getElementById('formLoginStatus').innerText = '❌ Login failed';
            }).finally(() => {
                // This section will always execute regardless of success or failure
                XSRFTOKEN = getCookie("XSRF-TOKEN");
            });

        // Handle error and logout messages
        const urlParams = new URLSearchParams(window.location.search);
        if (urlParams.has('error')) {
            document.getElementById('status').innerText = '❌ Login failed. Please try again.';
            document.getElementById('status').style.color = 'red';
        }
        if (urlParams.has('logout')) {
            document.getElementById('status').innerText = '✅ You have been logged out.';
            document.getElementById('status').style.color = 'green';
        }
    }



    document.getElementById('checkLoginStatus').addEventListener('click', function () {
        checkLoginStatus();
    });



    // JSON login handler
    document.getElementById('jsonLoginBtn').addEventListener('click', function () {
        const username = document.getElementById('jsonUsername').value;
        const password = document.getElementById('jsonPassword').value;

        fetch(domain + '/v1/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-XSRF-TOKEN': XSRFTOKEN
            },
            // credentials: 'include',
            body: JSON.stringify({ username, password })
        })
            .then(res => {
                if (res.ok) return res.json();
                else throw new Error('Login failed');
            })
            .then(data => {
                document.getElementById('jsonLoginStatus').innerText = '✅ Login successful';
                document.getElementById('loginStatus').innerText = `✅ Logged in as: ${username}`;
                const secureToken = getCookie("SECURE_TOKEN");
                const plainSession = getCookie("PLAIN_SESSION_ID");

                document.getElementById("secureCookieDisplay").innerText =
                    secureToken ? `✅ Secure Cookie: ${secureToken}` : "❌ Secure Cookie not found";


                document.getElementById("plainCookieDisplay").innerText =
                    plainSession ? `✅ Plain Cookie: ${plainSession}` : "❌ Plain Cookie not found";


                //For COORS scenario sine we can't get JSESSIONID in different domain hence reading from responses
                const sessionId = data.sessionId;
                console.log('Session ID:', sessionId);

                // For subsequent requests, use this session ID
                localStorage.setItem('sessionId', sessionId);
            })
            .catch(err => {
                document.getElementById('jsonLoginStatus').innerText = '❌ Login failed';
            }).finally(() => {
                // This section will always execute regardless of success or failure
                XSRFTOKEN = getCookie("XSRF-TOKEN");
            });
    });



    // JWT login handler
    document.getElementById('jwtLoginBtn').addEventListener('click', function () {
        const username = document.getElementById('jwtUsername').value;
        const password = document.getElementById('jwtPassword').value;

        fetch(domain + '/v2/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-XSRF-TOKEN': XSRFTOKEN
            },
            // credentials: 'include',
            body: JSON.stringify({ username, password })
        })
            .then(res => {
                if (res.ok) return res.json();
                else throw new Error('Login failed');
            })
            .then(data => {
                localStorage.setItem('jwt', data.token); // save token
                document.getElementById('jwtLoginStatus').innerText = '✅ Login successful';
                document.getElementById('loginStatus').innerText = `✅ Logged in as: ${username}`;
                const secureToken = getCookie("SECURE_TOKEN");
                const plainSession = getCookie("PLAIN_SESSION_ID");

                document.getElementById("secureCookieDisplay").innerText =
                    secureToken ? `✅ Secure Cookie: ${secureToken}` : "❌ Secure Cookie not found";

                document.getElementById("plainCookieDisplay").innerText =
                    plainSession ? `✅ Plain Cookie: ${plainSession}` : "❌ Plain Cookie not found";
            })
            .catch(err => {
                document.getElementById('jwtLoginStatus').innerText = '❌ Login failed';
            }).finally(() => {
                // This section will always execute regardless of success or failure
                XSRFTOKEN = getCookie("XSRF-TOKEN");
            });
    });

    //DB login
    document.getElementById('dbLoginBtn').addEventListener('click', function () {
        const username = document.getElementById('dbUsername').value;
        const password = document.getElementById('dbPassword').value;

        fetch(domain + '/v3/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-XSRF-TOKEN': XSRFTOKEN
            },
            body: JSON.stringify({ username, password })
        })
            .then(res => {
                if (!res.ok) throw new Error('Login failed');

                // ✅ Get JWT from response header
                const authHeader = res.headers.get('Authorization');
                if (authHeader && authHeader.startsWith('Bearer ')) {
                    const token = authHeader.substring(7); // remove "Bearer "
                    localStorage.setItem('jwt', token);

                    document.getElementById('dbLoginStatus').innerText = '✅ Login successful';
                    document.getElementById('loginStatus').innerText = `✅ Logged in as: ${username}`;
                } else {
                    throw new Error('JWT not found in header');
                }

                const secureToken = getCookie("SECURE_TOKEN");
                const plainSession = getCookie("PLAIN_SESSION_ID");

                document.getElementById("secureCookieDisplay").innerText =
                    secureToken ? `✅ Secure Cookie: ${secureToken}` : "❌ Secure Cookie not found";

                document.getElementById("plainCookieDisplay").innerText =
                    plainSession ? `✅ Plain Cookie: ${plainSession}` : "❌ Plain Cookie not found";
            })
            .catch(err => {
                document.getElementById('dbLoginStatus').innerText = '❌ Login failed';
                console.error(err);
            }).finally(() => {
                // This section will always execute regardless of success or failure
                XSRFTOKEN = getCookie("XSRF-TOKEN");
            });
    });


    // Dropdown change handler
    document.getElementById('apiDropdown').addEventListener('change', function () {
        const selectedPath = this.value;
        const jwt = localStorage.getItem('jwt');
        // Read the session ID from localStorage
        const sessionId = localStorage.getItem('sessionId');
        if (sessionId) {
            // Before making the request, set the cookie manually
            document.cookie = `JSESSIONID=${sessionId}; path=/`;
        }


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

        fetch(domain + uri, {
            method: 'POST',
            credentials: "include",
            headers: {
                'Content-Type': 'application/json',
                'X-XSRF-TOKEN': XSRFTOKEN
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