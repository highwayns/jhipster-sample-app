package io.github.jhipster.sample.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionTypesMapperTest {

    private TransactionTypesMapper transactionTypesMapper;

    @BeforeEach
    public void setUp() {
        transactionTypesMapper = new TransactionTypesMapperImpl();
    }
}
