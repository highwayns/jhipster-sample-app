package io.github.jhipster.sample.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdminTabsMapperTest {

    private AdminTabsMapper adminTabsMapper;

    @BeforeEach
    public void setUp() {
        adminTabsMapper = new AdminTabsMapperImpl();
    }
}
