package com.tick42.task.controller;

import com.tick42.task.dao.JSONFileLoader;
import com.tick42.task.model.Company;
import com.tick42.task.model.CompanyAddress;
import com.tick42.task.model.Employee;
import com.tick42.task.model.Project;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@CrossOrigin(origins = "*")
@RestController
public class CompaniesController {
    private final JSONFileLoader jsonFileLoader;

    public CompaniesController(JSONFileLoader jsonFileLoader) {
        this.jsonFileLoader = jsonFileLoader;
    }


    @GetMapping("/companies")
    public Collection<Company> getCompanies() {
        try {
            return jsonFileLoader.getAllCompanies();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @GetMapping("/addresses")
    public Collection<CompanyAddress> getAddresses() {
        try {
            return jsonFileLoader.getAllCompaniesAddresses();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @GetMapping("/projects")
    public Collection<Project> getProjects() {
        try {
            return jsonFileLoader.getAllProjects();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @GetMapping("/employees")
    public Collection<Employee> getEmployees() {
        try {

            return jsonFileLoader.getAllEmployees();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @PostMapping("/save-project")
    public Collection<Project> saveProject(@RequestBody Project project) {
        return jsonFileLoader.saveProject(project);
    }

    @PostMapping("/delete-project")
    public Collection<Project> deleteProject(@RequestBody String projectId) throws Exception {
        return jsonFileLoader.deleteProject(projectId);
    }
}
