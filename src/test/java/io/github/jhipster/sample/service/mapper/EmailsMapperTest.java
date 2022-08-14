package io.github.jhipster.sample.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmailsMapperTest {

    private EmailsMapper emailsMapper;

    @BeforeEach
    public void setUp() {
        emailsMapper = new EmailsMapperImpl();
    }
}
