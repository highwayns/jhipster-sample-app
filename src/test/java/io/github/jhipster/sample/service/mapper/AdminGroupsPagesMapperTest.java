package io.github.jhipster.sample.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdminGroupsPagesMapperTest {

    private AdminGroupsPagesMapper adminGroupsPagesMapper;

    @BeforeEach
    public void setUp() {
        adminGroupsPagesMapper = new AdminGroupsPagesMapperImpl();
    }
}
