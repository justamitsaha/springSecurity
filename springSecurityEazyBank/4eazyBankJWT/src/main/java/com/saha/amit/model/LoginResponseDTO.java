package com.saha.amit.model;

public record LoginResponseDTO(String status, String jwtToken, Object userDetails) {
}
