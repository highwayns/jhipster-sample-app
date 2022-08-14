package io.github.jhipster.sample.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IsoCountriesMapperTest {

    private IsoCountriesMapper isoCountriesMapper;

    @BeforeEach
    public void setUp() {
        isoCountriesMapper = new IsoCountriesMapperImpl();
    }
}
