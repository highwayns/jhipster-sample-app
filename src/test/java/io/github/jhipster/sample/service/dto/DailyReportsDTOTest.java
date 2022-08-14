package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DailyReportsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DailyReportsDTO.class);
        DailyReportsDTO dailyReportsDTO1 = new DailyReportsDTO();
        dailyReportsDTO1.setId(1L);
        DailyReportsDTO dailyReportsDTO2 = new DailyReportsDTO();
        assertThat(dailyReportsDTO1).isNotEqualTo(dailyReportsDTO2);
        dailyReportsDTO2.setId(dailyReportsDTO1.getId());
        assertThat(dailyReportsDTO1).isEqualTo(dailyReportsDTO2);
        dailyReportsDTO2.setId(2L);
        assertThat(dailyReportsDTO1).isNotEqualTo(dailyReportsDTO2);
        dailyReportsDTO1.setId(null);
        assertThat(dailyReportsDTO1).isNotEqualTo(dailyReportsDTO2);
    }
}
