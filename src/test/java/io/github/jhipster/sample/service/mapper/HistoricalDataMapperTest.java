package io.github.jhipster.sample.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HistoricalDataMapperTest {

    private HistoricalDataMapper historicalDataMapper;

    @BeforeEach
    public void setUp() {
        historicalDataMapper = new HistoricalDataMapperImpl();
    }
}
