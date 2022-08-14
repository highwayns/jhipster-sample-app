package io.github.jhipster.sample.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SiteUsersAccessMapperTest {

    private SiteUsersAccessMapper siteUsersAccessMapper;

    @BeforeEach
    public void setUp() {
        siteUsersAccessMapper = new SiteUsersAccessMapperImpl();
    }
}
