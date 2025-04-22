// Keycloak Configuration
const keycloakConfig = {
  url: 'http://192.168.0.143:8080',
  realm: 'master',
  clientId: 'springsecurityPKCE',
  redirectUri: 'http://localhost:8080/public/KeyCloakPKCE.html' // Must match Keycloak client settings
};

// Generate PKCE Code Verifier (43-128 chars)
function generateCodeVerifier() {
  const charset = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~';
  let verifier = '';
  for (let i = 0; i < 64; i++) {
    verifier += charset.charAt(Math.floor(Math.random() * charset.length));
  }
  return verifier;
}

// Generate Code Challenge (S256)
async function generateCodeChallenge(verifier) {
  const encoder = new TextEncoder();
  const data = encoder.encode(verifier);
  const digest = await crypto.subtle.digest('SHA-256', data);
  return btoa(String.fromCharCode(...new Uint8Array(digest)))
    .replace(/\+/g, '-')
    .replace(/\//g, '_')
    .replace(/=+$/, '');
}

// Start Login Flow
async function login() {
  const codeVerifier = generateCodeVerifier();
  const codeChallenge = await generateCodeChallenge(codeVerifier);

  // Store verifier for later use
  sessionStorage.setItem('pkce_verifier', codeVerifier);

  // Redirect to Keycloak
  const authUrl = new URL(`${keycloakConfig.url}/realms/${keycloakConfig.realm}/protocol/openid-connect/auth`);
  authUrl.searchParams.append('client_id', keycloakConfig.clientId);
  authUrl.searchParams.append('redirect_uri', keycloakConfig.redirectUri);
  authUrl.searchParams.append('response_type', 'code');
  authUrl.searchParams.append('scope', 'openid profile email');
  authUrl.searchParams.append('code_challenge', codeChallenge);
  authUrl.searchParams.append('code_challenge_method', 'S256');

  window.location.href = authUrl.href;
}

// Handle OAuth Callback
async function handleCallback() {
  const urlParams = new URLSearchParams(window.location.search);
  const code = urlParams.get('code');

  if (code) {
    const codeVerifier = sessionStorage.getItem('pkce_verifier');

    // Exchange code for tokens
    const tokenUrl = `${keycloakConfig.url}/realms/${keycloakConfig.realm}/protocol/openid-connect/token`;
    const response = await fetch(tokenUrl, {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: new URLSearchParams({
        grant_type: 'authorization_code',
        client_id: keycloakConfig.clientId,
        redirect_uri: keycloakConfig.redirectUri,
        code: code,
        code_verifier: codeVerifier
      })
    });

    const tokens = await response.json();
    sessionStorage.setItem('access_token', tokens.access_token);

    // Fetch user info
    const userInfo = await fetchUserInfo(tokens.access_token);
    updateUI(userInfo);

    // Clean URL
    window.history.replaceState({}, document.title, window.location.pathname);
  }
}

// Fetch User Profile
async function fetchUserInfo(token) {
  const response = await fetch(`${keycloakConfig.url}/realms/${keycloakConfig.realm}/protocol/openid-connect/userinfo`, {
    headers: { 'Authorization': `Bearer ${token}` }
  });
  return await response.json();
}

// Logout
function logout() {
  sessionStorage.clear();
  window.location.href = `${keycloakConfig.url}/realms/${keycloakConfig.realm}/protocol/openid-connect/logout?redirect_uri=${encodeURIComponent(window.location.href)}`;
}

// Update UI
function updateUI(user) {
  if (user) {
    document.getElementById('loginBtn').style.display = 'none';
    document.getElementById('logoutBtn').style.display = 'block';
    document.getElementById('userInfo').innerHTML = `Welcome, ${user.name || user.preferred_username}!`;
  } else {
    document.getElementById('loginBtn').style.display = 'block';
    document.getElementById('logoutBtn').style.display = 'none';
    document.getElementById('userInfo').innerHTML = '';
  }
}

// Event Listeners
document.getElementById('loginBtn').addEventListener('click', login);
document.getElementById('logoutBtn').addEventListener('click', logout);

// Check auth state on load
(async function init() {
  const accessToken = sessionStorage.getItem('access_token');
  if (accessToken) {
    try {
      const user = await fetchUserInfo(accessToken);
      updateUI(user);
    } catch (error) {
      sessionStorage.clear();
    }
  }
  handleCallback(); // Check for OAuth callback
})();