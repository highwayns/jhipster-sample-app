package io.github.jhipster.sample.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RequestDescriptionsMapperTest {

    private RequestDescriptionsMapper requestDescriptionsMapper;

    @BeforeEach
    public void setUp() {
        requestDescriptionsMapper = new RequestDescriptionsMapperImpl();
    }
}
