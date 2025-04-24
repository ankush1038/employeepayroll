package com.bridgelabz.employeepayroll.service;

import com.bridgelabz.employeepayroll.dto.EmployeeDTO;
import com.bridgelabz.employeepayroll.exceptions.EmployeePayrollException;
import com.bridgelabz.employeepayroll.model.Employee;
import com.bridgelabz.employeepayroll.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class  EmployeeService implements IEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Create a new employee based on provided employee details.
     *
     * @param employeeDTO Data Transfer Object containing employee information.
     * @return The created {@link Employee} entity.
     */
    @Override
    public Employee createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee(employeeDTO);
        log.debug("employee data: {}", employee);
        return employeeRepository.save(employee);
    }

    /**
     * Fetch all employee records from the database.
     *
     * @return List of all {@link Employee} entities.
     */

    @Override
    public List<Employee> getAllEmployees() {
        log.info("Fetching all employees");
        return employeeRepository.findAll();
    }

    /**
     * Get an employee by their ID.
     *
     * @param id The ID of the employee.
     * @return The {@link Employee} entity with the specified ID.
     * @throws EmployeePayrollException if no employee is found with the given ID.
     */
    @Override
    public Employee getEmployeeById(Long id) {
        log.info("Fetching employee by Id: {}", id);
        return employeeRepository.findById(id).orElseThrow(()-> {log.error("No employee with id {} found", id);
        return new EmployeePayrollException("No employee with id " + id);});
    }

    /**
     * Update an existing employee's data using the provided DTO.
     *
     * @param id The ID of the employee to be updated.
     * @param employeeDTO New data for the employee.
     * @return The updated {@link Employee} entity.
     */
    @Override
    public Employee updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee employee = getEmployeeById(id);
        employee.updateEmployee(employeeDTO);
        return employeeRepository.save(employee);
    }

    /**
     * Delete an employee from the database using their ID.
     *
     * @param id The ID of the employee to delete.
     */
    @Override
    public void deleteEmployee(Long id) {
        log.info("Deleting employee by Id: {}", id);
        employeeRepository.deleteById(id);
    }

    /**
     * Get a list of employees who belong to a specific department.
     *
     * @param department The department name to filter employees by.
     * @return List of {@link Employee} entities in the specified department.
     */
    @Override
    public List<Employee> getEmployeesByDepartment(String department) {
        log.info("Fetching employees by department: {}", department);
        return employeeRepository.findAll().stream().filter(employee -> employee.getDepartment().contains(department)).collect(Collectors.toList());
    }
}
