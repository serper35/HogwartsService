package ru.hogwarts.school.service.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    Map<Long, Faculty> faculties = new HashMap<>();
    private static long idFac = 1;

    @PostConstruct
    public void init() {
        addFaculty(new Faculty("slytherin", "green"));
        addFaculty(new Faculty("gryffindor", "red"));
    }

    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(idFac++);
        return faculties.put(faculty.getId(), faculty);
    }

    public Faculty getFaculty(Long id) {
        return faculties.get(id);
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (faculties.containsKey(faculty.getId())) {
            Faculty updateFac = faculties.get(faculty.getId());
            updateFac.setName(faculty.getName());
            updateFac.setColor(faculty.getColor());
            return faculties.put(faculty.getId(), updateFac);
        }
        return null;
    }

    public Faculty removeFaculty(Long id) {
        if (faculties.containsKey(id)) {
            return faculties.remove(id);
        }
        return null;
    }

    public Collection<Faculty> getFaculties() {
        return Collections.unmodifiableCollection(faculties.values());
    }

    public List<Faculty> getFacultyByColor(String color) {
        return faculties.values().
                stream().
                filter(c -> c.getColor().equals(color)).
                collect(Collectors.toList());
    }
}
