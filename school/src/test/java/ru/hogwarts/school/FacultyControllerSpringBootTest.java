package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerSpringBootTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    public FacultyControllerSpringBootTest() {
    }

    @Test
    void contextLoad() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    public void testAdd() throws Exception {
        String url = "http://localhost:" + port + "/faculty";
        Faculty faculty = new Faculty(5L, "Ravenclaw", "brown");
        assertThat(testRestTemplate.postForObject(url, faculty, Faculty.class)).isNotNull();
    }

    @Test
    public void testGet() throws Exception {
        String url = "http://localhost:" + port + "/faculty/{id}";
        assertThat(testRestTemplate.getForObject(url, Faculty.class, 5)).isNotNull();
    }

    @Test
    public void testGetAll() throws Exception {
        String url = "http://localhost:" + port + "/faculty";
        assertThat(testRestTemplate.getForObject(url, List.class)).isNotNull();
    }

    @Test
    public void testUpdate() throws Exception {
        String url = "http://localhost:" + port + "/faculty";
        Faculty faculty = new Faculty(5L, "Ravenclaw", "black");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Faculty> entity = new HttpEntity<>(faculty, headers);

        ResponseEntity<Faculty> response = testRestTemplate.exchange(url, HttpMethod.PUT, entity, Faculty.class, 5);
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isEqualTo(faculty);
    }

    @Test
    public void testGetByColor() throws Exception {
        String url = "http://localhost:" + port + "/faculty/getByColor?color=black";

        ResponseEntity<List> response = testRestTemplate.getForEntity(url, List.class);
        assertThat(response).isNotNull();
    }

    @Test
    public void testGetByNameIgnoreCaseOrGetByColorIgnoreCase() throws Exception {
        String url = "http://localhost:" + port + "/faculty/getByNameIgnoreCaseOrGetByColorIgnoreCase?name=Ravenclaw&color=black";
        assertThat(testRestTemplate.getForObject(url, List.class)).isNotNull();
    }

    @Test
    public void testGetStudentsByFaculty() throws Exception {
        String url = "http://localhost:" + port + "/faculty/{id}/students";
        Assertions.assertThat(testRestTemplate.getForObject(url, List.class, 5)).isNotNull();

    }

    @Test
    public void testDelete() throws Exception {
        String url = "http://localhost:" + port + "/faculty";
        ResponseEntity<Void> response = (testRestTemplate.exchange(url, HttpMethod.DELETE, null, void.class, 5));

        assertThat(response).isNotNull();
    }
}
