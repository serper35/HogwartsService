package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.response.AvgAgeOfStudents;
import ru.hogwarts.school.response.SumOfStudents;
//import ru.hogwarts.school.response.LastFiveStudentsGroupByAge;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAgeBetween(int ageMin, int ageMax);

//    @Query(value = "select count(name) from student", nativeQuery = true)
//    int getSumOfStudents();

    @Query(value = "select count(name) as name from student", nativeQuery = true)
    List<SumOfStudents> getSumOfStudents();

    @Query(value = " select avg(age) as age from student", nativeQuery = true)
    List<AvgAgeOfStudents> getAvgAgeOfStudents();

    @Query(value = "select * from student order by id desc limit 5", nativeQuery = true)
    List<Student> getLastFiveStudentsGroupById();
}
