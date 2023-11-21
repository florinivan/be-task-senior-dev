package com.amaris.task;

import com.amaris.task.dto.TaskDTO;
import com.amaris.task.jpa.entity.Employee;
import com.amaris.task.jpa.entity.Task;
import com.amaris.task.jpa.repository.EmployeeRepository;
import com.amaris.task.jpa.repository.TaskRepository;
import com.amaris.task.jpa.service.TasksApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TasksApplicationServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TasksApplicationService tasksApplicationService;

    @BeforeEach
    void setUp() {

        // Mock behavior for modelMapper
        when(modelMapper.map(any(), any())).thenCallRealMethod();

        // Mock behavior for employeeRepository
        Employee newEmployee = new Employee();
        newEmployee.setId(1L);
        newEmployee.setName("John Doe");
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(newEmployee));

        // Mock behavior for taskRepository
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Task newTask1= new Task();
        newTask1.setDescription("Task 1 for John Doe");
        newTask1.setDueDate(Date.valueOf("2023-01-10"));
        newTask1.setAssignee(newEmployee);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(newTask1));
        when(taskRepository.findAll()).thenReturn(List.of(newTask1));
    }

    @Test
    void updateTask_ShouldUpdateTask() {
        // Arrange
        Optional<Task> existingTask = tasksApplicationService.getTaskById(1L);
        assertThat(existingTask).isNotEmpty();
        TaskDTO taskUpdates = new TaskDTO();
        taskUpdates.setDueDate("2023-10-10");

        // Act
        Task updatedTask = tasksApplicationService.updateTask(existingTask.get(), taskUpdates);

        // Assert
        assertEquals(taskUpdates.getDueDate(), updatedTask.getDueDate().toString());
    }

    @Test
    void createTask_ShouldCreateTask() {
        // Arrange
        Task newTask = new Task();

        // Act
        Task createdTask = tasksApplicationService.createTask(newTask);

        // Assert
        verify(taskRepository, times(1)).save(newTask);
        assertEquals(newTask, createdTask);
    }

    @Test
    void getAllEmployees_ShouldReturnListOfEmployees() {
        // Act
        tasksApplicationService.getAllEmployees();

        // Assert
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void createEmployee_ShouldCreateEmployee() {
        // Arrange
        Employee newEmployee = new Employee();

        // Act
        tasksApplicationService.createEmployee(newEmployee);

        // Assert
        verify(employeeRepository, times(1)).save(newEmployee);
    }

    @Test
    void getAllTasks_ShouldReturnListOfTasks() {
        // Act
        tasksApplicationService.getAllTasks();

        // Assert
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getTaskById_ShouldReturnTaskById() {
        // Arrange
        long taskId = 1L;

        // Act
        tasksApplicationService.getTaskById(taskId);

        // Assert
        verify(taskRepository, times(1)).findById(taskId);
    }
}

