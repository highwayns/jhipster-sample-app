package io.github.jhipster.sample.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdminCronMapperTest {

    private AdminCronMapper adminCronMapper;

    @BeforeEach
    public void setUp() {
        adminCronMapper = new AdminCronMapperImpl();
    }
}
