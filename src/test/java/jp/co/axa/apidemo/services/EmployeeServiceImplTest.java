package jp.co.axa.apidemo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import jp.co.axa.apidemo.services.EmployeeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setUp() {
        employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");
        employee.setSalary(1000);
        employee.setDepartment("IT");
    }

    @Test
    @DisplayName("Test retrieveEmployees method")
    public void testRetrieveEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = employeeService.retrieveEmployees();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(employee.getName(), result.get(0).getName());
        assertEquals(employee.getSalary(), result.get(0).getSalary());
        assertEquals(employee.getDepartment(), result.get(0).getDepartment());

        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test getEmployee method")
    public void testGetEmployee() {
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        Employee result = employeeService.getEmployee(employee.getId());

        assertNotNull(result);
        assertEquals(employee.getName(), result.getName());
        assertEquals(employee.getSalary(), result.getSalary());
        assertEquals(employee.getDepartment(), result.getDepartment());

        verify(employeeRepository, times(1)).findById(employee.getId());
    }

    @Test
    @DisplayName("Test saveEmployee method")
    public void testSaveEmployee() {
        employeeService.saveEmployee(employee);

        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    @DisplayName("Test deleteEmployee method")
    public void testDeleteEmployee() {
        employeeService.deleteEmployee(employee.getId());

        verify(employeeRepository, times(1)).deleteById(employee.getId());
    }

    @Test
    @DisplayName("Test updateEmployee method")
    public void testUpdateEmployee() {
        Employee newEmployee = new Employee();
        newEmployee.setId(employee.getId());
        newEmployee.setName("Jane Doe");
        newEmployee.setSalary(2000);
        newEmployee.setDepartment("HR");

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        employeeService.updateEmployee(newEmployee);

        verify(employeeRepository, times(1)).findById(employee.getId());
        verify(employeeRepository, times(1)).save(newEmployee);
        assertFalse(newEmployee.getName().equals(employee.getName()));
        assertFalse(newEmployee.getSalary() == employee.getSalary());
        assertFalse(newEmployee.getDepartment().equals(employee.getDepartment()));
    }
}