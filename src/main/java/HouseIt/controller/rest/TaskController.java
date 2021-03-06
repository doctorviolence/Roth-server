package HouseIt.controller.rest;

import HouseIt.entities.Task;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
public class TaskController {

    @Autowired
    private ITaskService taskService;

    // Get task
    @GetMapping(value = "/tasks/find-by-id/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<Task> getTaskById(@PathVariable long id) throws MyEntityNotFoundException {
        Task t = taskService.findTask(id);
        return new ResponseEntity<Task>(t, HttpStatus.OK);
    }

    // Get all tasks
    @GetMapping(value = "/tasks")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Task>> getTodoTasks() throws MyEntityNotFoundException {
        List<Task> tasks = taskService.getTasks();
        return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
    }

    // Get tasks by type
    @GetMapping(value = "/tasks/tasks-by-subject/{subject}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Task>> getTasksBySubject(@PathVariable("subject") String subject) throws MyEntityNotFoundException {
        List<Task> tasks = taskService.getTasksBySubject(subject);
        return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
    }

    // Get tasks pertaining to tenant
    @GetMapping(value = "/tasks/tasks-by-tenant/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<List<Task>> getCompletedTasksByTenantId(@PathVariable("id") long id) throws MyEntityNotFoundException {
        List<Task> tasks = taskService.findTasksByTenantId(id);
        return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
    }

    // Create task
    @PostMapping(value = "/tasks/create-task", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<Task> createTask(@RequestBody Task t) {
        Task task = taskService.createTask(t);
        return new ResponseEntity<Task>(task, HttpStatus.CREATED);
    }

    // Update task
    @PutMapping(value = "/tasks/update-task", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<Task> updateTask(@RequestBody Task t) throws MyEntityNotFoundException {
        taskService.updateTask(t);
        return new ResponseEntity<Task>(HttpStatus.OK);
    }

    // Delete task
    @DeleteMapping(value = "/tasks/delete-task/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<Task> deleteTask(@PathVariable("id") long id) throws MyEntityNotFoundException {
        taskService.deleteTask(id);
        return new ResponseEntity<Task>(HttpStatus.NO_CONTENT);
    }

}
