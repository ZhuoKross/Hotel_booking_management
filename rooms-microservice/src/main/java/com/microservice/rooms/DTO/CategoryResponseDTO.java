package com.microservice.rooms.DTO;

import lombok.Builder;

import java.util.List;

@Builder
public record CategoryResponseDTO(
   List<CategoryDTO> categories
) {}
