package io.github.jhipster.sample.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CurrenciesMapperTest {

    private CurrenciesMapper currenciesMapper;

    @BeforeEach
    public void setUp() {
        currenciesMapper = new CurrenciesMapperImpl();
    }
}
