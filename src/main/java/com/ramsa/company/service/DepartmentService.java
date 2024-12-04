package com.ramsa.company.service;

import com.ramsa.company.dto.DepartmentDTO;
import com.ramsa.company.entity.Department;
import com.ramsa.company.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public DepartmentDTO getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department department = convertToEntity(departmentDTO);
        Department savedDepartment = departmentRepository.save(department);
        return convertToDTO(savedDepartment);
    }

    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        Optional<Department> existingDepartment = departmentRepository.findById(id);
        if (existingDepartment.isPresent()) {
            Department department = convertToEntity(departmentDTO);
            department.setId(id); // Ensure the ID is set for the update
            Department updatedDepartment = departmentRepository.save(department);
            return convertToDTO(updatedDepartment);
        }
        return null; // Or throw an exception
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    public Page<DepartmentDTO> searchDepartments(String name, Pageable pageable) {
        return departmentRepository.findByNameContaining(name, pageable)
                .map(this::convertToDTO);
    }

    private DepartmentDTO convertToDTO(Department department) {
        return new DepartmentDTO(department.getId(), department.getName());
    }

    private Department convertToEntity(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setId(departmentDTO.getId());
        department.setName(departmentDTO.getName());
        return department;
    }
}