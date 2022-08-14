package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DailyReportsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DailyReports.class);
        DailyReports dailyReports1 = new DailyReports();
        dailyReports1.setId(1L);
        DailyReports dailyReports2 = new DailyReports();
        dailyReports2.setId(dailyReports1.getId());
        assertThat(dailyReports1).isEqualTo(dailyReports2);
        dailyReports2.setId(2L);
        assertThat(dailyReports1).isNotEqualTo(dailyReports2);
        dailyReports1.setId(null);
        assertThat(dailyReports1).isNotEqualTo(dailyReports2);
    }
}
