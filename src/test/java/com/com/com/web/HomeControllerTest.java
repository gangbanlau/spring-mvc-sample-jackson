package com.com.com.web;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"classpath*:root-context.xml", "classpath*:servlet-context.xml"})
public class HomeControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(HomeControllerTest.class);
	
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(this.wac).alwaysExpect(status().isOk()).build();
	}
	
	@Test
	public void test() throws Exception {
		logger.info("Execute test!");
		
		MvcResult result = this.mockMvc.perform(
				get("/getuser")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON+ ";charset=UTF-8") )
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		logger.info(content);
	}
	
	@Test
	public void testPost() throws Exception {
		String json = "{\"id\":1,\"name\":\"spring\",\"someTime\":1466650619386,\"anotherTime\":\"2016-06-23 10:56:59\",\"Password\":\"pwd\"}";
		this.mockMvc.perform(
			post("/createuser", "json")
				.contentType(MediaType.APPLICATION_JSON).content(json)
			  ).andExpect(content().string(json));
	}
}