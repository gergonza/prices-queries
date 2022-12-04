package com.product.price.query;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Class that supports the unit tests related to the Spring Boot Main Application Layer.
 *
 * @author Germán González
 * @version 1.0
 * @since 2022-12-03
 *
 */
@SpringBootTest
class QueryApplicationTests {

	/**
	 * Test to evaluate the scenario when the application context are loaded.
	 *
	 */
	@Test
	void contextLoads() {
	}

	/**
	 * Test to evaluate the scenario when the application is launched.
	 *
	 */
	@Test
	public void main() {
		QueryApplication.main(new String[] {});
	}

}
