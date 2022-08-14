package io.github.jhipster.sample.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LangMapperTest {

    private LangMapper langMapper;

    @BeforeEach
    public void setUp() {
        langMapper = new LangMapperImpl();
    }
}
