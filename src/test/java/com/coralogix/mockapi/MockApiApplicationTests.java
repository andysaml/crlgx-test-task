package com.coralogix.mockapi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MockApiApplicationTests {

	@Autowired
	private WebApplicationContext context;
	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.build();
	}

	@Test
	public void sendLogsTest() throws Exception {
		String json = "{\n" +
				"           \"privateKey\": \"6c343cd5-d33e-4370-8b0c-5b87c42b4bae\",\n" +
				"           \"applicationName\": \"*insert desired application name*\",\n" +
				"           \"subsystemName\": \"*insert desired subsystem name*\",\n" +
				"           \"computerName\": \"*insert computer name*\",\n" +
				"           \"logEntries\": [\n" +
				"             {\n" +
				"               \"timestamp\": 1457827957703.342, \n" +
				"               \"severity\": 4,\n" +
				"               \"text\": \"Encountered an error while registering the user john123\",\n" +
				"               \"category\": \"DAL\",\n" +
				"               \"className\": \"UserManager\",\n" +
				"               \"methodName\": \"RegisterUser\",\n" +
				"               \"threadId\": \"a-352\"\n" +
				"             }\n" +
				"           ]\n" +
				"        }";


		mockMvc.perform(post("/api/v1/logs")
				.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk());
	}

}
