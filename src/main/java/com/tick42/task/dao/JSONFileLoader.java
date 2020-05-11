package com.tick42.task.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tick42.task.model.Company;
import com.tick42.task.model.CompanyAddress;
import com.tick42.task.model.Employee;
import com.tick42.task.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
public class JSONFileLoader {
    Logger logger = LoggerFactory.getLogger(JSONFileLoader.class);
    private final Map<String, Company> companyMap = new HashMap<>();
    private final List<Company> companies = new ArrayList<>();
    private final List<Employee> employees = new ArrayList<>();
    private final List<CompanyAddress> companyAddresses = new ArrayList<>();
    private final List<Project> projects = new ArrayList<>();

    @PostConstruct
    public void postConstruct() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            InputStream companiesStream = JSONFileLoader.getResourceFileAsInputStream("static/data/companies.json");
            companies.addAll(mapper.readValue(companiesStream, new TypeReference<List<Company>>() {
            }));
            logger.info("static/data/companies.json loaded");
            InputStream employeesStream = JSONFileLoader.getResourceFileAsInputStream("static/data/employees.json");
            employees.addAll(mapper.readValue(employeesStream, new TypeReference<List<Employee>>() {
            }));
            logger.info("static/data/employees.json loaded");
            InputStream companiesAddressesStream = JSONFileLoader.getResourceFileAsInputStream("static/data/company-addresses.json");
            companyAddresses.addAll(mapper.readValue(companiesAddressesStream, new TypeReference<List<CompanyAddress>>() {
            }));
            logger.info("static/data/companies-addresses.json loaded");
            InputStream projectsStream = JSONFileLoader.getResourceFileAsInputStream("static/data/projects.json");
            projects.addAll(mapper.readValue(projectsStream, new TypeReference<List<Project>>() {
            }));
            logger.info("static/data/projects.json loaded");


        } catch (Exception error) {
            logger.error(error.toString());
            throw error;
        }
    }

    private static InputStream getResourceFileAsInputStream(String fileName) {
        ClassLoader classLoader = JSONFileLoader.class.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }

    public Collection<Company> getAllCompanies() throws IOException {
        return companies;
    }

    public Collection<CompanyAddress> getAllCompaniesAddresses() throws IOException {
        return companyAddresses;
    }

    public Collection<Project> getAllProjects() throws IOException {
        return projects;
    }

    public Collection<Employee> getAllEmployees() throws IOException {
        return employees;
    }

    public Collection<Project> saveProject(Project project) {
        if (project.getId() == null) {
            project.setId(UUID.randomUUID().toString());
        }
        projects.removeIf(prj -> prj.getId().equals(project.getId()));

        projects.add(project);
        return projects;
    }

    public Collection<Project> deleteProject(String projectId) {
        projects.removeIf(prj -> prj.getId().equals(projectId));
        return projects;
    }
}
