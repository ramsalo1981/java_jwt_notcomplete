package com.ramsa.company.service;
import com.ramsa.company.dto.EmployeeDTO;
import com.ramsa.company.dto.UserDTO;
import com.ramsa.company.entity.Employee;
import com.ramsa.company.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = convertToEntity(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return convertToDTO(savedEmployee);
    }

    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if (existingEmployee.isPresent()) {
            Employee employee = convertToEntity(employeeDTO);
            employee.setId(id); // Ensure the ID is set for the update
            Employee updatedEmployee = employeeRepository.save(employee);
            return convertToDTO(updatedEmployee);
        }
        return null; // Or throw an exception
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public Page<EmployeeDTO> searchEmployees(String name, Pageable pageable) {
        return employeeRepository.findByNameContaining(name, pageable)
                .map(this::convertToDTO);
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        return new EmployeeDTO(employee.getId(), employee.getName());
    }

    private Employee convertToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        return employee;
    }
}
