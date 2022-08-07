package com.smb.springcloudgateway.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

/**
 * https://spring.io/guides/gs/testing-web/
 *
 * @author Rashedul Islam
 * @since 2021-02-16
 */
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Rollback(false)
class CatalogsTest {

	@Autowired private MockMvc mvc;


	@Test @Order(0)
	public void contextLoads() throws Exception {
	}


	@Test @Order(1)
	void testGetAll() throws Exception {
		String json = mvc.perform(get("http://localhost:8081/catalogs/dockerMessages"))
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn().getResponse().getContentAsString();
		System.out.println(json);
	}
	@Test @Order(1)
	void testGetById() throws Exception {
		String json = mvc.perform(get("http://localhost:8081/catalogs/1"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		System.out.println(json);
	}
}
