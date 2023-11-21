package com.amaris.task.jpa.service;

import com.amaris.task.dto.TaskDTO;
import com.amaris.task.jpa.entity.Employee;
import com.amaris.task.jpa.entity.Task;
import com.amaris.task.jpa.repository.EmployeeRepository;
import com.amaris.task.jpa.repository.TaskRepository;
import org.modelmapper.AbstractConverter;
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

        modelMapper.addConverter(new AbstractConverter<String, Date>() {
            @Override
            protected Date convert(String source) {
                if(source == null){
                    return null;
                }
                try {
                    java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(source);
                    return new Date(date.getTime());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        modelMapper.addConverter(new AbstractConverter<Long, Employee>() {
            @Override
            protected Employee convert(Long source) {
                if(source == null){
                    return null;
                }
                try {
                    Optional<Employee> optionalEmployee = employeeRepository.findById(source);
                    if(optionalEmployee.isEmpty()){
                        String notFound = String.format("Employee not found with id %s",source);
                        throw new ParseException(notFound, 1);
                    }
                    return optionalEmployee.get();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public Task updateTask(Task task, TaskDTO taskUpdates) {
        modelMapper.map(taskUpdates, task);
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

