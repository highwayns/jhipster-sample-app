package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SiteUsersBalancesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiteUsersBalancesDTO.class);
        SiteUsersBalancesDTO siteUsersBalancesDTO1 = new SiteUsersBalancesDTO();
        siteUsersBalancesDTO1.setId(1L);
        SiteUsersBalancesDTO siteUsersBalancesDTO2 = new SiteUsersBalancesDTO();
        assertThat(siteUsersBalancesDTO1).isNotEqualTo(siteUsersBalancesDTO2);
        siteUsersBalancesDTO2.setId(siteUsersBalancesDTO1.getId());
        assertThat(siteUsersBalancesDTO1).isEqualTo(siteUsersBalancesDTO2);
        siteUsersBalancesDTO2.setId(2L);
        assertThat(siteUsersBalancesDTO1).isNotEqualTo(siteUsersBalancesDTO2);
        siteUsersBalancesDTO1.setId(null);
        assertThat(siteUsersBalancesDTO1).isNotEqualTo(siteUsersBalancesDTO2);
    }
}
