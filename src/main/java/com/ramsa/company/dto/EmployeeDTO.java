package com.ramsa.company.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Long id;
    private String name;
    private String position;

    public EmployeeDTO(Long id, @NotBlank(message = "Employee name is required") String name) {
    }
    // Add other fields as necessary
}