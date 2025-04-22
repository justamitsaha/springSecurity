// Keycloak Configuration
const keycloakConfig = {
  baseUrl: 'http://192.168.0.143:8080',
  realm: 'master',
  clientId: 'springSecurityAuthorizationCode',
  clientSecret: 'c9hjtF46FwYv5DXmfOTLOiOFvTYMopVi',
  grant_type:'authorization_code',
  code: '456581ad-f46d-47ca-8002-675f3e467ea5.560ef2bd-d899-40d6-b54e-2aefc270fa10.e3b02828-16f9-4779-81ac-29ad22b91550',
  redirectUri: 'http://localhost:8080/public/AuthorizationCodeGrantflow.html',
  tokenEndpoint: '/protocol/openid-connect/token',
  authEndpoint: '/protocol/openid-connect/auth',
  scopes: 'openid'
};

// API Configuration
const apiConfig = {
  baseUrl: 'http://localhost:8081',
  endpoints: {
    myAccount: '/myAccount',
    myBalance: '/myBalance',
    myCards: '/myCards',
    contact: '/contact',
    myLoans: '/myLoans',
    notices: '/notices'
  }
};

// Global variables
let accessToken = "";
let id = "abc@xyx.com";