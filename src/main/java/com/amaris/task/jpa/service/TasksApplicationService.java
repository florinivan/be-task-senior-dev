package com.amaris.task.jpa.service;

import com.amaris.task.dto.TaskDTO;
import com.amaris.task.jpa.entity.Employee;
import com.amaris.task.jpa.entity.Task;
import com.amaris.task.jpa.repository.EmployeeRepository;
import com.amaris.task.jpa.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class TasksApplicationService {

    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;

    private final ModelMapper modelMapper;

    public TasksApplicationService(EmployeeRepository employeeRepository, TaskRepository taskRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
        modelMapper.addConverter(source -> {
            if (source.getSource() == null) {
                return null;
            }
            try {
                return new Date(new SimpleDateFormat("yyyy-MM-dd").parse(source.getSource()).getTime());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }, String.class, Date.class);
        modelMapper.addConverter((source) -> {
            if (source.getSource() == null) {
                return null;
            }
            Optional<Employee> optionalEmployee = employeeRepository.findById(source.getSource());
            return optionalEmployee.orElseThrow(() -> {
                String notFound = String.format("Employee not found with id %s", source.getSource());
                return new RuntimeException(new ParseException(notFound, 1));
            });
        }, Long.class, Employee.class);
    }

    public Task updateTask(Task task, TaskDTO taskUpdates) {
        modelMapper.map(taskUpdates, task);
        if(taskUpdates.getAssignee() == null && taskUpdates.getDueDate() == null){
            task.setAssignee(null);
        }
        return createTask(task);
    }

    @Transactional
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }
}

