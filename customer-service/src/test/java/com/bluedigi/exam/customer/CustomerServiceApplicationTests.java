package com.bluedigi.exam.customer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CustomerServiceApplicationTests {

	@Test
	void applicationClassShouldBeAvailable() {
		assertThat(CustomerServiceApplication.class).isNotNull();
	}

}
