package ru.hogwarts.school.service.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    Map<Long, Student> students = new HashMap<>();
    private static long id = 1;

    @PostConstruct
    public void initStudents() {
        addStudent(new Student("Vlad", 27));
        addStudent(new Student("Gena", 17));
    }

    public Student addStudent(Student student) {
        student.setId(id++);
        return students.put(student.getId(), student);
    }

    public Student getStudent(Long id) {
        return students.get(id);
    }

    public Student updateStudent(Student student) {
        if (students.containsKey(student.getId())) {
            Student studentUpdate = students.get(student.getId());
            studentUpdate.setName(student.getName());
            studentUpdate.setAge(student.getAge());
            return studentUpdate;
        }
        return null;
    }

    public Student removeStudent(Long id) {
        if (students.containsKey(id)) {
            return students.remove(id);
        }
        return null;
    }

    public Collection<Student> getAll() {
        return Collections.unmodifiableCollection(students.values());
    }

    public List<Student> getStudentByAge(int age) {
        return students.values().
                stream().
                filter(s -> s.getAge() == age).
                collect(Collectors.toList());
    }
}
