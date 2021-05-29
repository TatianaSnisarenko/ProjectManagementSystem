package jdbc.util;

import jdbc.dao.entity.DeveloperDao;
import jdbc.dto.ProjectTo;
import jdbc.service.services.ProjectService;

import java.util.List;
import java.util.stream.Collectors;

public class CommandUtil {

    ProjectService projectService;

    public CommandUtil(ProjectService projectService) {
        this.projectService = projectService;
    }

    public double getAllSalariesForProject(int id) {
        return getListOfDevelopersForProject(id).stream()
                .mapToDouble(DeveloperDao::getSalary)
                .sum();
    }

    public List<DeveloperDao> getListOfDevelopersForProject(int id) {
        ProjectTo projectTo = projectService.findById(id);
        return projectTo.getDevelopers();
    }

    public List<DeveloperDao> getListOfJavaDevelopers() {
        return RelationsUtils.getAllDevelopersWithSkillLanguage("Java");
    }

    public List<DeveloperDao> getListOfMiddleDevelopers() {
        return RelationsUtils.getAllDevelopersWithSkillLevel("Middle");
    }

    public List<String> getShortDescriptionOfAllProjects() {
        return projectService.findAll().stream()
                .map(p -> p.getDate().toString() + " - " + p.getName() + " - " + p.getDevelopers().size() + ".")
                .collect(Collectors.toList());
    }
}
