package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ErrorReportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ErrorReport.class);
        ErrorReport errorReport1 = new ErrorReport();
        errorReport1.setId(1L);
        ErrorReport errorReport2 = new ErrorReport();
        errorReport2.setId(errorReport1.getId());
        assertThat(errorReport1).isEqualTo(errorReport2);
        errorReport2.setId(2L);
        assertThat(errorReport1).isNotEqualTo(errorReport2);
        errorReport1.setId(null);
        assertThat(errorReport1).isNotEqualTo(errorReport2);
    }
}
