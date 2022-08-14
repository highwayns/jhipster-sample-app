package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MonthlyReportsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MonthlyReportsDTO.class);
        MonthlyReportsDTO monthlyReportsDTO1 = new MonthlyReportsDTO();
        monthlyReportsDTO1.setId(1L);
        MonthlyReportsDTO monthlyReportsDTO2 = new MonthlyReportsDTO();
        assertThat(monthlyReportsDTO1).isNotEqualTo(monthlyReportsDTO2);
        monthlyReportsDTO2.setId(monthlyReportsDTO1.getId());
        assertThat(monthlyReportsDTO1).isEqualTo(monthlyReportsDTO2);
        monthlyReportsDTO2.setId(2L);
        assertThat(monthlyReportsDTO1).isNotEqualTo(monthlyReportsDTO2);
        monthlyReportsDTO1.setId(null);
        assertThat(monthlyReportsDTO1).isNotEqualTo(monthlyReportsDTO2);
    }
}
