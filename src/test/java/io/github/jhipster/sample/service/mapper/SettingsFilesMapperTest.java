package io.github.jhipster.sample.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SettingsFilesMapperTest {

    private SettingsFilesMapper settingsFilesMapper;

    @BeforeEach
    public void setUp() {
        settingsFilesMapper = new SettingsFilesMapperImpl();
    }
}
