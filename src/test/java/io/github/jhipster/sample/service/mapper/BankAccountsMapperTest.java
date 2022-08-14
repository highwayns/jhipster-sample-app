package io.github.jhipster.sample.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankAccountsMapperTest {

    private BankAccountsMapper bankAccountsMapper;

    @BeforeEach
    public void setUp() {
        bankAccountsMapper = new BankAccountsMapperImpl();
    }
}
