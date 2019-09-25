package com.homeBudget.test;

import com.homeBudget.Response;
import com.homeBudget.exception.CategoryNotFoundException;
import com.homeBudget.model.Category;
import com.homeBudget.restControllers.CategoryController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CategoryController.class, secure = false)
public class ApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	Response response=new Response(1,"successful","");

	@MockBean
	CategoryController categoryController;
	String userJson = "{\"result_code\":\"1\":\"message\",\"successful\"}";

	@Test
	public void contextLoads()throws Exception  {
//		Mockito.when(categoryController.getById(1)).thenReturn(category);
//
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
//				"/Category/1/").accept(
//				MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//		System.out.println(result.getResponse());



	}


}
