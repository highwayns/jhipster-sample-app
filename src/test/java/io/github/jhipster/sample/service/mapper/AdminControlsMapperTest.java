package io.github.jhipster.sample.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdminControlsMapperTest {

    private AdminControlsMapper adminControlsMapper;

    @BeforeEach
    public void setUp() {
        adminControlsMapper = new AdminControlsMapperImpl();
    }
}
