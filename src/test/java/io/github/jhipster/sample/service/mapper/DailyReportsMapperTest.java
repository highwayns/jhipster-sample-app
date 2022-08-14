package io.github.jhipster.sample.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DailyReportsMapperTest {

    private DailyReportsMapper dailyReportsMapper;

    @BeforeEach
    public void setUp() {
        dailyReportsMapper = new DailyReportsMapperImpl();
    }
}
