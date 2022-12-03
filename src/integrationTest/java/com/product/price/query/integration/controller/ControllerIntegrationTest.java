package com.product.price.query.integration.controller;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.product.price.query.QueryApplication;
import com.product.price.query.repository.QueryRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(
        webEnvironment = MOCK,
        classes = QueryApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-test.properties")
@SqlGroup({
        @Sql(value = "classpath:test/reset.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:test/insert.sql", executionPhase = BEFORE_TEST_METHOD)
})
class ControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private QueryRepository repository;

    @ParameterizedTest
    @CsvFileSource(resources = "/test/data.csv", numLinesToSkip = 1)
    void givenProductIdAndBrandIdAndApplicationDateWhenGetPriceDetailsThenStatusOk(int productId,
                                                                                   int brandId,
                                                                                   LocalDateTime applicationDateTime,
                                                                                   int priceListId,
                                                                                   LocalDateTime startDate,
                                                                                   LocalDateTime endDate,
                                                                                   BigDecimal price)
            throws Exception {
        mvc.perform(get("/price/query/details/get/" + productId + "/" + brandId + "/" + applicationDateTime)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(productId))
                .andExpect(jsonPath("$.brandId").value(brandId))
                .andExpect(jsonPath("$.priceListId").value(priceListId))
                .andExpect(jsonPath("$.price").value(price.doubleValue()))
                .andExpect(jsonPath("$.startDate").value(startDate.format(ISO_DATE_TIME)))
                .andExpect(jsonPath("$.endDate").value(endDate.format(ISO_DATE_TIME)));
    }
}
