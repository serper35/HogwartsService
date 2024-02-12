package ru.hogwarts.school;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerSpringBootTest {

	@LocalServerPort
	private int port;

	@Autowired
	private StudentController studentController;

	@Autowired
	private StudentService studentService;

	@Autowired
	private TestRestTemplate testRestTemplate;

	private final String url = "http://localhost:" + port + "/student?idFac=1";

	@Test
	void contextLoad() throws Exception {
		Assertions.assertThat(studentController).isNotNull();
	}

	@Test
	public void testPost() throws Exception {
		String url = "http://localhost:" + port + "/student";
		Student student = new Student(100L, "Vlad", 27, 1);
		Assertions.assertThat(this.testRestTemplate.postForObject(url, student, String.class)).isNotEmpty();
	}

	@Test
	public void testGetAll() throws Exception {
		Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student", String.class)).isNotEmpty();
	}

	@Test
	public void testPut() throws Exception {
		String url = "http://localhost:" + port + "/student";
		Student student = new Student(100L, "Vladimir", 50, 1);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Student> requestEntity = new HttpEntity<>(student, headers);
		ResponseEntity<Student> response = this.testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, Student.class, 100L);
		Assertions.assertThat(response).isNotNull();
	}

	@Test
	public void testGet() throws Exception {
		String url = "http://localhost:" + port + "/student/{id}";
		Assertions.assertThat(testRestTemplate.getForObject(url, Student.class, 100L)).isNotNull();
	}

    @Test
    public void testGetStudentByAge() throws Exception {
        String url = "http://localhost:" + port + "/student/getAge?age=50";
        Assertions.assertThat(testRestTemplate.getForObject(url, List.class)).isNotNull();
    }

    @Test
    public void testGetAgeBetween() {
        String url = "http://localhost:" + port + "/student/getAgeBetween?minAge=26&maxAge=100";
        Assertions.assertThat(testRestTemplate.getForObject(url, List.class)).isNotNull();
    }

	@Test
	public void testGetFaculty() throws Exception {
		String url = "http://localhost:" + port + "/student/{id}/faculty";
		Assertions.assertThat(testRestTemplate.getForObject(url, Faculty.class, 1)).isNotNull();
	}

	@Test
	public void testDelete() throws Exception {
		String url = "http://localhost:" + port + "/student/{id}";
		ResponseEntity<Void> response = testRestTemplate.exchange(url, HttpMethod.DELETE, null, Void.class, 100L);
		boolean result = response.getStatusCode().is2xxSuccessful();
		Assertions.assertThat(result).isTrue();
	}
}
