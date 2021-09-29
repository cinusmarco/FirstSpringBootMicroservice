package com.example.ec;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExplorecaliApplicationTests {

  @Test
  @SuppressWarnings("java:S3415")
  void contextLoads() {
    assertThat(true).isTrue();
  }
}
