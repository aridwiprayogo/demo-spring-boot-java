package com.example.demo.controller;

import com.example.demo.domain.News;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class NewsControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;

    /*@Test
    void save() throws Exception {
        //given
        final News news = new News("ari dwi prayogo", "belajar mock mvc", "implementasi mock mvc spring boot");
        //when
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/news/")
                        .content(asJson(news))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(asJson(news)));
    }*/

    @Test
    void getAll() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/news/")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        final List<News> news = fromJson(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {});
        assertThat(news).isNotNull();
    }

    @Test
    void getById() throws Exception {

        final MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/news/")
                        .param("id", "32c3ec8a-1353-43f2-ae5e-611e2e01a05b"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        final List<News> newsList = fromJson(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
        final News news = newsList.get(0);
        assertThat(news).isNotNull();
        assertThat(news.getAuthor()).isEqualTo("ari dwi prayogo");
        assertThat(news.getHead()).isEqualTo("belajar postgresql");
        assertThat(news.getBody()).isEqualTo("Alhamdulillah udah bisa tampil broh");

    }

    @Test
    void update() throws Exception {
        final News news = new News("ari dwi prayogo", "belajar mock mvc", "implementasi mock mvc spring boot");

        final MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .put("/api/news/")
                        .param("id", "32c3ec8a-1353-43f2-ae5e-611e2e01a05b")
                        .content(asJson(news))
                        .contentType(MediaType.APPLICATION_JSON);
        final MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        final News newsResponse = fromJson(mvcResult.getResponse().getContentAsString(), News.class);
        assertThat(newsResponse).isNotNull();
        assertThat(newsResponse.getAuthor()).isEqualTo(news.getAuthor());
        assertThat(newsResponse.getHead()).isEqualTo(news.getHead());
        assertThat(newsResponse.getBody()).isEqualTo(news.getBody());
    }

    @Test
    void delete() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/api/news/")
                        .param("id","72ffe219-b7e3-4f6a-b2bd-1a4f9ff98750"))
                .andExpect(status().isOk())
                .andReturn();

        final News news = fromJson(mvcResult.getResponse().getContentAsString(), News.class);
        assertThat(news).isNull();
    }

    private static String asJson(final Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> List<T> fromJson(String contentAsString, TypeReference<List<T>> typeReference)
            throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(contentAsString, typeReference);
    }

    private <T> T fromJson(String contentAsString, Class<T> tClass) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(contentAsString, tClass);
    }
}