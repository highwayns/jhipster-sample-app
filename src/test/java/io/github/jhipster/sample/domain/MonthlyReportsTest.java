package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MonthlyReportsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MonthlyReports.class);
        MonthlyReports monthlyReports1 = new MonthlyReports();
        monthlyReports1.setId(1L);
        MonthlyReports monthlyReports2 = new MonthlyReports();
        monthlyReports2.setId(monthlyReports1.getId());
        assertThat(monthlyReports1).isEqualTo(monthlyReports2);
        monthlyReports2.setId(2L);
        assertThat(monthlyReports1).isNotEqualTo(monthlyReports2);
        monthlyReports1.setId(null);
        assertThat(monthlyReports1).isNotEqualTo(monthlyReports2);
    }
}
