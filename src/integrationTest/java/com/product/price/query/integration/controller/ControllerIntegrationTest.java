package com.product.price.query.integration.controller;

import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.product.price.query.QueryApplication;
import com.product.price.query.controller.QueryController;
import com.product.price.query.exception.ExceptionAdvice;
import com.product.price.query.repository.QueryRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
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
        @Sql(value = "classpath:test/sql/reset.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:test/sql/insert.sql", executionPhase = BEFORE_TEST_METHOD)
})
class ControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private QueryRepository repository;

    @Autowired
    private QueryController controller;

    @BeforeEach
    void setup(){
        this.mvc = standaloneSetup(this.controller).setControllerAdvice(new ExceptionAdvice()).build();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/test/csv/data.csv", numLinesToSkip = 1)
    void givenProductIdAndBrandIdAndApplicationDateWhenGetPriceDetailsThenStatusOk(int productId,
                                                                                   int brandId,
                                                                                   LocalDateTime applicationDateTime,
                                                                                   int priceListId,
                                                                                   LocalDateTime startDate,
                                                                                   LocalDateTime endDate,
                                                                                   BigDecimal price)
            throws Exception {
        final LocalDateTime dateTime = getFinalFormatDateTime(applicationDateTime);
        mvc.perform(get("/price/query/details/get/" + productId + "/" + brandId + "/" + dateTime)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(productId))
                .andExpect(jsonPath("$.brandId").value(brandId))
                .andExpect(jsonPath("$.priceListId").value(priceListId))
                .andExpect(jsonPath("$.price").value(price.doubleValue()))
                .andExpect(jsonPath("$.startDate").value(startDate.format(ofPattern("yyyy-MM-dd HH:mm:ss"))))
                .andExpect(jsonPath("$.endDate").value(endDate.format(ofPattern("yyyy-MM-dd HH:mm:ss"))));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/test/csv/not-found-data.csv", numLinesToSkip = 1)
    void givenProductIdAndBrandIdAndApplicationDateWhenGetPriceDetailsThenStatusNotFound(int productId,
                                                                                         int brandId,
                                                                                         LocalDateTime applicationDateTime)
            throws Exception {
        final LocalDateTime dateTime = getFinalFormatDateTime(applicationDateTime);
        mvc.perform(get("/price/query/details/get/" + productId + "/" + brandId + "/" + dateTime)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content()
                        .contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.priceDetail").value(
                        "There is no price information by this data: productId (" + productId + "), brandId (" + brandId + ") and applicationDateTime (" + dateTime + ")"));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/test/csv/invalid-data.csv", numLinesToSkip = 1)
    void givenProductIdAndBrandIdAndApplicationDateWhenGetPriceDetailsThenStatusBadRequest(int productId,
                                                                                           int brandId,
                                                                                           LocalDateTime applicationDateTime,
                                                                                           String field)
            throws Exception {
        final LocalDateTime dateTime = getFinalFormatDateTime(applicationDateTime);
        mvc.perform(get("/price/query/details/get/" + productId + "/" + brandId + "/" + dateTime)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$." + field).value("must be greater than or equal to 1"));
    }

    private LocalDateTime getFinalFormatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter pattern = ofPattern("yyyy-MM-dd-HH.mm.ss");
        return parse(dateTime.format(pattern), pattern);
    }
}
