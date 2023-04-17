package jp.co.axa.apidemo.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    void testGetEmployees() throws Exception {
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(new Employee(1L, "John", 1000, "IT"));
        employees.add(new Employee(2L, "Jane", 2000, "HR"));

        when(employeeService.retrieveEmployees()).thenReturn(employees);

        mockMvc.perform(get("/api/v1/employees"))
            .andExpect(status().isOk());
    }

    @Test
    void testGetEmployee() throws Exception {
        Employee employee = new Employee(1L, "John", 1000, "IT");

        when(employeeService.getEmployee(1L)).thenReturn(employee);

        mockMvc.perform(get("/api/v1/employees/1"))
            .andExpect(status().isOk());
    }

    @Test
    void testSaveEmployee() throws Exception {
        Employee employee = new Employee(1L, "John", 1000, "IT");

        mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employee)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteEmployee() throws Exception {
        mockMvc.perform(delete("/api/v1/employees/1"))
            .andExpect(status().isOk());
    }

    @Test
    void testUpdateEmployee() throws Exception {
        Employee employee = new Employee(1L, "John", 1000, "IT");

        when(employeeService.getEmployee(1L)).thenReturn(employee);

        employee.setSalary(2000);

        mockMvc.perform(put("/api/v1/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employee)))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}