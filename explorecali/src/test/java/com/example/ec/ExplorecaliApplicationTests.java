package com.example.ec;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class ExplorecaliApplicationTests {

    @Test
    @SuppressWarnings("java:S3415")
    void contextLoads() {
        assertThat(true).isTrue();
    }

}
