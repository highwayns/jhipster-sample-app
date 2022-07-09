package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AbuseReportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AbuseReport.class);
        AbuseReport abuseReport1 = new AbuseReport();
        abuseReport1.setId(1L);
        AbuseReport abuseReport2 = new AbuseReport();
        abuseReport2.setId(abuseReport1.getId());
        assertThat(abuseReport1).isEqualTo(abuseReport2);
        abuseReport2.setId(2L);
        assertThat(abuseReport1).isNotEqualTo(abuseReport2);
        abuseReport1.setId(null);
        assertThat(abuseReport1).isNotEqualTo(abuseReport2);
    }
}
