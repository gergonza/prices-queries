package com.product.price.query.service;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.product.price.query.domain.dto.Response;
import com.product.price.query.domain.entity.Detail;
import com.product.price.query.exception.NotFoundException;
import com.product.price.query.repository.QueryRepository;
import com.product.price.query.service.impl.QueryServiceImpl;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Class that supports the unit tests related to the Controller Layer.
 *
 * @author Germán González
 * @version 1.0
 * @since 2022-12-03
 *
 */
@ExtendWith(MockitoExtension.class)
class ServiceTest {

    @Mock private QueryRepository repository;

    @InjectMocks private QueryServiceImpl service;

    /**
     * Test to evaluate the endpoint in a happy path scenario.
     *
     */
    @Test
    void testGetPriceDetailShouldReturnOk() {
        List<Detail> details = List.of(
                Detail.builder()
                        .id(1)
                        .productId(35455)
                        .brandId(1)
                        .priceListId(1)
                        .priority(0)
                        .curr("EUR")
                        .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
                        .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                        .price(new BigDecimal("35.50"))
                        .build(),
                Detail.builder()
                        .id(2)
                        .productId(35455)
                        .brandId(1)
                        .priceListId(2)
                        .priority(1)
                        .curr("EUR")
                        .startDate(LocalDateTime.of(2020, 6, 14, 15, 0, 0))
                        .endDate(LocalDateTime.of(2020, 6, 14, 18, 30, 0))
                        .price(new BigDecimal("25.45"))
                        .build(),
                Detail.builder()
                        .id(4)
                        .productId(35455)
                        .brandId(1)
                        .priceListId(4)
                        .priority(1)
                        .curr("EUR")
                        .startDate(LocalDateTime.of(2020, 6, 15, 16, 0, 0))
                        .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                        .price(new BigDecimal("38.95"))
                        .build()
        );
        Response expected = Response.builder()
                .productId(35455)
                .brandId(1)
                .priceListId(1)
                .startDate(of(2020, 6, 14, 0, 0, 0))
                .endDate(of(2020, 12, 31, 23, 59, 59))
                .price(new BigDecimal("35.50"))
                .build();
        when(this.repository.findAll()).thenReturn(details);
        assertThat(this.service.getPriceDetail(35455, 1,
                of(2020, 6, 14, 12, 0, 0)))
                .hasNoNullFieldsOrProperties()
                .isEqualTo(expected);
        verify(this.repository).findAll();
    }

    /**
     * Test to evaluate the endpoint in a happy path scenario when there are two or more rows sorted by priority.
     * It should retrieve the row with the major priority.
     *
     */
    @Test
    void testGetPriceDetailShouldReturnOkWithTheMajorPriority() {
        List<Detail> details = List.of(
                Detail.builder()
                        .id(1)
                        .productId(35455)
                        .brandId(1)
                        .priceListId(1)
                        .priority(0)
                        .curr("EUR")
                        .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
                        .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                        .price(new BigDecimal("35.50"))
                        .build(),
                Detail.builder()
                        .id(2)
                        .productId(35455)
                        .brandId(1)
                        .priceListId(2)
                        .priority(1)
                        .curr("EUR")
                        .startDate(LocalDateTime.of(2020, 6, 14, 15, 0, 0))
                        .endDate(LocalDateTime.of(2020, 6, 14, 18, 30, 0))
                        .price(new BigDecimal("25.45"))
                        .build(),
                Detail.builder()
                        .id(4)
                        .productId(35455)
                        .brandId(1)
                        .priceListId(4)
                        .priority(1)
                        .curr("EUR")
                        .startDate(LocalDateTime.of(2020, 6, 15, 16, 0, 0))
                        .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                        .price(new BigDecimal("38.95"))
                        .build()
        );
        Response expected = Response.builder()
                .productId(35455)
                .brandId(1)
                .priceListId(2)
                .startDate(of(2020, 6, 14, 15, 0, 0))
                .endDate(of(2020, 6, 14, 18, 30, 0))
                .price(new BigDecimal("25.45"))
                .build();
        when(this.repository.findAll()).thenReturn(details);
        assertThat(this.service.getPriceDetail(35455, 1,
                of(2020, 6, 14, 16, 0, 0)))
                .hasNoNullFieldsOrProperties()
                .isEqualTo(expected);
        verify(this.repository).findAll();
    }

    /**
     * Test to evaluate the endpoint in a scenario where there is no products.
     *
     */
    @Test
    void testGetPriceDetailShouldReturnNotFoundException() {
        List<Detail> details = List.of(
                Detail.builder()
                        .id(1)
                        .productId(35455)
                        .brandId(1)
                        .priceListId(1)
                        .priority(0)
                        .curr("EUR")
                        .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
                        .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                        .price(new BigDecimal("35.50"))
                        .build(),
                Detail.builder()
                        .id(2)
                        .productId(35455)
                        .brandId(1)
                        .priceListId(2)
                        .priority(1)
                        .curr("EUR")
                        .startDate(LocalDateTime.of(2020, 6, 14, 15, 0, 0))
                        .endDate(LocalDateTime.of(2020, 6, 14, 18, 30, 0))
                        .price(new BigDecimal("25.45"))
                        .build(),
                Detail.builder()
                        .id(4)
                        .productId(35455)
                        .brandId(1)
                        .priceListId(4)
                        .priority(1)
                        .curr("EUR")
                        .startDate(LocalDateTime.of(2020, 6, 15, 16, 0, 0))
                        .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                        .price(new BigDecimal("38.95"))
                        .build()
        );
        when(this.repository.findAll()).thenReturn(details);
        assertThrows(NotFoundException.class, () -> this.service.getPriceDetail(1, 1,
                of(2020, 6, 14, 12, 0, 0)));
        verify(this.repository).findAll();
    }
}
