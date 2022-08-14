package io.github.jhipster.sample.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderTypesMapperTest {

    private OrderTypesMapper orderTypesMapper;

    @BeforeEach
    public void setUp() {
        orderTypesMapper = new OrderTypesMapperImpl();
    }
}
