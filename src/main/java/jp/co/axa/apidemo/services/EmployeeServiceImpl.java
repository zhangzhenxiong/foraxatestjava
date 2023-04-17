package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Setter method for EmployeeRepository
     * @param employeeRepository - the repository to set
     */
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Retrieves all employees and caches the result
     * @return List<Employee> - the list of employees
     */
    @Cacheable("employees")
    public List<Employee> retrieveEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    /**
     * Retrieves an employee by employeeId and caches the result
     * @param employeeId - the id of the employee to retrieve
     * @return Employee - the retrieved employee
     */
    @Cacheable("employees")
    public Employee getEmployee(Long employeeId) {
        Optional<Employee> optEmp = employeeRepository.findById(employeeId);
        return optEmp.get();
    }

    /**
     * Saves an employee and evicts the "employees" cache
     * @param employee - the employee to save
     */
    @CacheEvict("employees")
    public void saveEmployee(Employee employee){
        employeeRepository.save(employee);
    }

    /**
     * Deletes an employee by employeeId and evicts the "employees" cache
     * @param employeeId - the id of the employee to delete
     */
    @CacheEvict("employees")
    public void deleteEmployee(Long employeeId){
        employeeRepository.deleteById(employeeId);
    }

    /**
     * Updates an employee and evicts the "employees" cache
     * @param employee - the employee to update
     */
    @CacheEvict("employees")
    public void updateEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
}