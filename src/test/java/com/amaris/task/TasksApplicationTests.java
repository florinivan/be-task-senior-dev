package com.amaris.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class TasksApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
	}

	@BeforeEach
	void setUp() {
	}

	private String asJsonString(Object obj) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}
	@Test
	void patchTask() throws Exception {
		Long taskId = 1L;
		String url = "/tasks/update/" + taskId;
		String requestJson = "{\"dueDate\": \"2023-10-10\"}";
		String expectedTaskInfo = "{\"id\":1,\"description\":\"Task 1 for John Doe\",\"dueDate\":\"2023-10-10\",\"assignee\":{\"id\":1,\"name\":\"John Doe\"}}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> requestEntity = new HttpEntity<>(requestJson, headers);

		/*String actualTaskInfo = restTemplate.exchange(
				url,
				HttpMethod.PATCH,
				requestEntity,
				String.class
		).getBody();

		assertEquals(expectedTaskInfo, actualTaskInfo);*/
	}

	@Test
	void testGetTaskInfo() {
		Long taskId = 1L;
		String expectedTaskInfo = "{\"id\":1,\"description\":\"Task 1 for John Doe\",\"dueDate\":\"2023-01-15\",\"assignee\":{\"id\":1,\"name\":\"John Doe\"}}";

		String actualTaskInfo = restTemplate.getForObject("/tasks/task/" + taskId, String.class);

		assertEquals(expectedTaskInfo, actualTaskInfo);
	}

	@Configuration
	class TestConfig {

		@Bean
		public RestTemplateBuilder restTemplateBuilder() {
			return new RestTemplateBuilder().requestFactory(this::clientHttpRequestFactory);
		}

		private ClientHttpRequestFactory clientHttpRequestFactory() {
			SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
			requestFactory.setOutputStreaming(false); // Set to false if necessary
			return requestFactory;
		}
	}
}
