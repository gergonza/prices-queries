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

@ExtendWith(MockitoExtension.class)
class ServiceTest {

    @Mock private QueryRepository repository;

    @InjectMocks private QueryServiceImpl service;

    @Test
    void testGetPriceDetailShouldReturnOk() {
        List<Detail> details = List.of(
                new Detail(1, 35455, 1, 1, 0, "EUR",
                        LocalDateTime.of(2020, 6, 14, 0, 0, 0),
                        LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                        new BigDecimal("35.50")),
                new Detail(2, 35455, 1, 2, 1, "EUR",
                        LocalDateTime.of(2020, 6, 14, 15, 0, 0),
                        LocalDateTime.of(2020, 6, 14, 18, 30, 0),
                        new BigDecimal("25.45")),
                new Detail(3, 35455, 1, 3, 1, "EUR",
                        LocalDateTime.of(2020, 6, 15, 0, 0, 0),
                        LocalDateTime.of(2020, 6, 15, 11, 0, 0),
                        new BigDecimal("30.50")),
                new Detail(4, 35455, 1, 4, 1, "EUR",
                        LocalDateTime.of(2020, 6, 15, 16, 0, 0),
                        LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                        new BigDecimal("38.95"))
        );
        Response expected = Response.builder()
                .productId(35455)
                .brandId(1)
                .priceListId(1)
                .startDate(of(2020, 6, 14, 0, 0, 0))
                .endDate(of(2020, 12, 31, 23, 59, 59))
                .totalPrice(new BigDecimal("35.50"))
                .build();
        when(this.repository.findAll()).thenReturn(details);
        assertThat(this.service.getPriceDetail(35455, 1,
                of(2020, 6, 14, 12, 0, 0)))
                .hasNoNullFieldsOrProperties()
                .isEqualTo(expected);
        verify(this.repository).findAll();
    }

    @Test
    void testGetPriceDetailShouldReturnOkWithTheMajorPriority() {
        List<Detail> details = List.of(
                new Detail(1, 35455, 1, 1, 0, "EUR",
                        LocalDateTime.of(2020, 6, 14, 0, 0, 0),
                        LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                        new BigDecimal("35.50")),
                new Detail(2, 35455, 1, 2, 1, "EUR",
                        LocalDateTime.of(2020, 6, 14, 15, 0, 0),
                        LocalDateTime.of(2020, 6, 14, 18, 30, 0),
                        new BigDecimal("25.45")),
                new Detail(3, 35455, 1, 3, 1, "EUR",
                        LocalDateTime.of(2020, 6, 15, 0, 0, 0),
                        LocalDateTime.of(2020, 6, 15, 11, 0, 0),
                        new BigDecimal("30.50")),
                new Detail(4, 35455, 1, 4, 1, "EUR",
                        LocalDateTime.of(2020, 6, 15, 16, 0, 0),
                        LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                        new BigDecimal("38.95"))
        );
        Response expected = Response.builder()
                .productId(35455)
                .brandId(1)
                .priceListId(2)
                .startDate(of(2020, 6, 14, 15, 0, 0))
                .endDate(of(2020, 6, 14, 18, 30, 0))
                .totalPrice(new BigDecimal("25.45"))
                .build();
        when(this.repository.findAll()).thenReturn(details);
        assertThat(this.service.getPriceDetail(35455, 1,
                of(2020, 6, 14, 16, 0, 0)))
                .hasNoNullFieldsOrProperties()
                .isEqualTo(expected);
        verify(this.repository).findAll();
    }

    @Test
    void testGetPriceDetailShouldReturnNotFoundException() {
        List<Detail> details = List.of(
                new Detail(1, 35455, 1, 1, 0, "EUR",
                        LocalDateTime.of(2020, 6, 14, 0, 0, 0),
                        LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                        new BigDecimal("35.50")),
                new Detail(2, 35455, 1, 2, 1, "EUR",
                        LocalDateTime.of(2020, 6, 14, 15, 0, 0),
                        LocalDateTime.of(2020, 6, 14, 18, 30, 0),
                        new BigDecimal("25.45")),
                new Detail(3, 35455, 1, 3, 1, "EUR",
                        LocalDateTime.of(2020, 6, 15, 0, 0, 0),
                        LocalDateTime.of(2020, 6, 15, 11, 0, 0),
                        new BigDecimal("30.50")),
                new Detail(4, 35455, 1, 4, 1, "EUR",
                        LocalDateTime.of(2020, 6, 15, 16, 0, 0),
                        LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                        new BigDecimal("38.95"))
        );
        when(this.repository.findAll()).thenReturn(details);
        assertThrows(NotFoundException.class, () -> this.service.getPriceDetail(1, 1,
                of(2020, 6, 14, 12, 0, 0)));
        verify(this.repository).findAll();
    }
}
