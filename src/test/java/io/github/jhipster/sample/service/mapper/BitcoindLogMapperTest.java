package io.github.jhipster.sample.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BitcoindLogMapperTest {

    private BitcoindLogMapper bitcoindLogMapper;

    @BeforeEach
    public void setUp() {
        bitcoindLogMapper = new BitcoindLogMapperImpl();
    }
}
