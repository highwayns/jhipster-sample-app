package io.github.jhipster.sample.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChangeSettingsMapperTest {

    private ChangeSettingsMapper changeSettingsMapper;

    @BeforeEach
    public void setUp() {
        changeSettingsMapper = new ChangeSettingsMapperImpl();
    }
}
