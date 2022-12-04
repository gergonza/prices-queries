package com.product.price.query.controller;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.product.price.query.domain.dto.Response;
import com.product.price.query.service.QueryService;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Class that supports the unit tests related to the Service Layer.
 *
 * @author Germán González
 * @version 1.0
 * @since 2022-12-03
 *
 */
@ExtendWith(MockitoExtension.class)
class ControllerTest {

    @Mock private QueryService service;

    @InjectMocks private QueryController controller;

    /**
     * Test to evaluate the endpoint in a happy path scenario.
     *
     */
    @Test
    void testGetPriceDetailShouldReturnOk() {
        Response expected = Response
                .builder()
                .productId(35455)
                .brandId(1)
                .priceListId(1)
                .startDate(of(2022, 12, 3, 0, 0, 0))
                .endDate(of(2022, 12, 3, 23, 59, 59))
                .price(new BigDecimal("35.50"))
                .build();
        when(this.service.getPriceDetail(1, 1,
                of(2022, 12, 3, 14, 26, 0))).thenReturn(expected);
        assertThat(this.controller.getPriceDetail(1, 1,
                of(2022, 12, 3, 14, 26, 0)))
                .hasNoNullFieldsOrProperties()
                .isEqualTo(expected);
        verify(this.service).getPriceDetail(1, 1,
                of(2022, 12, 3, 14, 26, 0));
    }
}
