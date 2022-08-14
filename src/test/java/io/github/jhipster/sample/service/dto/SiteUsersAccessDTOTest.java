package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SiteUsersAccessDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiteUsersAccessDTO.class);
        SiteUsersAccessDTO siteUsersAccessDTO1 = new SiteUsersAccessDTO();
        siteUsersAccessDTO1.setId(1L);
        SiteUsersAccessDTO siteUsersAccessDTO2 = new SiteUsersAccessDTO();
        assertThat(siteUsersAccessDTO1).isNotEqualTo(siteUsersAccessDTO2);
        siteUsersAccessDTO2.setId(siteUsersAccessDTO1.getId());
        assertThat(siteUsersAccessDTO1).isEqualTo(siteUsersAccessDTO2);
        siteUsersAccessDTO2.setId(2L);
        assertThat(siteUsersAccessDTO1).isNotEqualTo(siteUsersAccessDTO2);
        siteUsersAccessDTO1.setId(null);
        assertThat(siteUsersAccessDTO1).isNotEqualTo(siteUsersAccessDTO2);
    }
}
