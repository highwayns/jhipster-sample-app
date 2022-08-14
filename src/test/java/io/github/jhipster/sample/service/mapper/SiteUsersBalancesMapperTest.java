package io.github.jhipster.sample.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SiteUsersBalancesMapperTest {

    private SiteUsersBalancesMapper siteUsersBalancesMapper;

    @BeforeEach
    public void setUp() {
        siteUsersBalancesMapper = new SiteUsersBalancesMapperImpl();
    }
}
