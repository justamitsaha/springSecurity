window.onload = function () {
    // Check login status
    fetch('/public/me')
        .then(res => res.ok ? res.json() : Promise.reject())
        .then(data => {
            document.getElementById('loginStatus').innerText = `✅ Logged in with username: ${data.username}`;
        })
        .catch(() => {
            document.getElementById('loginStatus').innerText = '❌ Not logged in';
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

    // Dropdown change handler
    document.getElementById('apiDropdown').addEventListener('change', function () {
        const selectedPath = this.value;
        if (selectedPath) {
            fetch(selectedPath, { credentials: "include" })
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

    // JSON login handler
    document.getElementById('jsonLoginBtn').addEventListener('click', function () {
        const username = document.getElementById('jsonUsername').value;
        const password = document.getElementById('jsonPassword').value;

        fetch('/api/login', {
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
            })
            .catch(err => {
                document.getElementById('jsonLoginStatus').innerText = '❌ Login failed';
            });
    });
};