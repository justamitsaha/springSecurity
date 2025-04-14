window.onload = function () {
    // Check login status
    fetch('/public/me')
        .then(res => res.ok ? res.json() : Promise.reject())
        .then(data => {
            document.getElementById('loginStatus').innerText = `✅ Logged in with username: ${data.username}`;
            document.getElementById('formLoginStatus').innerText = '✅ Login successful';
        })
        .catch(() => {
            document.getElementById('loginStatus').innerText = '❌ Not logged in';
            document.getElementById('formLoginStatus').innerText = '❌ Login failed';
        });

    const secureToken = getCookie("SECURE_TOKEN");
    const plainSession = getCookie("PLAIN_SESSION_ID");

    document.getElementById("secureCookieDisplay").innerText =
        secureToken ? `✅ Secure Cookie: ${secureToken}` : "❌ Secure Cookie not found";

    document.getElementById("plainCookieDisplay").innerText =
        plainSession ? `✅ Plain Cookie: ${plainSession}` : "❌ Plain Cookie not found";

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



    // JSON login handler
    document.getElementById('jsonLoginBtn').addEventListener('click', function () {
        const username = document.getElementById('jsonUsername').value;
        const password = document.getElementById('jsonPassword').value;

        fetch('/v1/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
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
            })
            .catch(err => {
                document.getElementById('jsonLoginStatus').innerText = '❌ Login failed';
            });
    });



    // JWT login handler
    document.getElementById('jwtLoginBtn').addEventListener('click', function () {
        const username = document.getElementById('jwtUsername').value;
        const password = document.getElementById('jwtPassword').value;

        fetch('/v2/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
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
            });
    });

    //DB login 
    document.getElementById('dbLoginBtn').addEventListener('click', function () {
        const username = document.getElementById('dbUsername').value;
        const password = document.getElementById('dbPassword').value;

        fetch('/v3/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
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
            });
    });


    // Dropdown change handler
    document.getElementById('apiDropdown').addEventListener('change', function () {
        const selectedPath = this.value;
        const jwt = localStorage.getItem('jwt');
        if (selectedPath) {
            fetch(selectedPath,
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


    function getCookie(name) {
        const value = `; ${document.cookie}`;
        const parts = value.split(`; ${name}=`);
        if (parts.length === 2) return parts.pop().split(';').shift();
    }

    window.onload = function () {
        const secureToken = getCookie("SECURE_TOKEN");
        const plainSession = getCookie("PLAIN_SESSION_ID");

        document.getElementById("secureCookieDisplay").innerText =
            secureToken ? `✅ Secure Cookie: ${secureToken}` : "❌ Secure Cookie not found";

        document.getElementById("plainCookieDisplay").innerText =
            plainSession ? `✅ Plain Cookie: ${plainSession}` : "❌ Plain Cookie not found";
    };
};