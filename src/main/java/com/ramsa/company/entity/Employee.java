package com.ramsa.company.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Employee name is required")
    @Column(nullable = false)
    private String name;

    @Min(value = 0, message = "Salary must be a positive number")
    @Column(nullable = false)
    private Double salary;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = true)
    private Department department;
}