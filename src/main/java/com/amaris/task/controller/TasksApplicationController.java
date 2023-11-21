package com.amaris.task.controller;

import com.amaris.task.dto.TaskDTO;
import com.amaris.task.jpa.entity.Employee;
import com.amaris.task.jpa.entity.Task;
import com.amaris.task.jpa.service.TasksApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class TasksApplicationController {

    private final TasksApplicationService taskApplicationService;
    public TasksApplicationController(TasksApplicationService taskApplicationService) {
        this.taskApplicationService = taskApplicationService;
    }

    @GetMapping(path = "/employees")
    public List<Employee> getAllEmployees() {
        return taskApplicationService.getAllEmployees();
    }

    @PostMapping(path = "/employees/new/")
    public Employee createEmployee(@RequestBody Employee employee) {
        return taskApplicationService.createEmployee(employee);
    }

    @GetMapping(path = "/tasks")
    public List<Task> getAllTasks() {
        return taskApplicationService.getAllTasks();
    }

    @GetMapping(path = "/tasks/task/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        Optional<Task> optionalTask = taskApplicationService.getTaskById(id);
        if (optionalTask.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optionalTask.get());
    }

    @PatchMapping("/tasks/update/{id}")
    public ResponseEntity<Task> patchUser(@PathVariable Long id, @RequestBody TaskDTO taskUpdates) {
        Optional<Task> optionalTask = taskApplicationService.getTaskById(id);
        if (optionalTask.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(taskApplicationService.updateTask(optionalTask.get(),taskUpdates));
    }

    @PostMapping(path = "/tasks/new/")
    public Task createTask(@RequestBody Task task) {
        return taskApplicationService.createTask(task);
    }
}
