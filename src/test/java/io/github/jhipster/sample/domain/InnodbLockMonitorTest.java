package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InnodbLockMonitorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InnodbLockMonitor.class);
        InnodbLockMonitor innodbLockMonitor1 = new InnodbLockMonitor();
        innodbLockMonitor1.setId(1L);
        InnodbLockMonitor innodbLockMonitor2 = new InnodbLockMonitor();
        innodbLockMonitor2.setId(innodbLockMonitor1.getId());
        assertThat(innodbLockMonitor1).isEqualTo(innodbLockMonitor2);
        innodbLockMonitor2.setId(2L);
        assertThat(innodbLockMonitor1).isNotEqualTo(innodbLockMonitor2);
        innodbLockMonitor1.setId(null);
        assertThat(innodbLockMonitor1).isNotEqualTo(innodbLockMonitor2);
    }
}
