package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InnodbLockMonitorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InnodbLockMonitorDTO.class);
        InnodbLockMonitorDTO innodbLockMonitorDTO1 = new InnodbLockMonitorDTO();
        innodbLockMonitorDTO1.setId(1L);
        InnodbLockMonitorDTO innodbLockMonitorDTO2 = new InnodbLockMonitorDTO();
        assertThat(innodbLockMonitorDTO1).isNotEqualTo(innodbLockMonitorDTO2);
        innodbLockMonitorDTO2.setId(innodbLockMonitorDTO1.getId());
        assertThat(innodbLockMonitorDTO1).isEqualTo(innodbLockMonitorDTO2);
        innodbLockMonitorDTO2.setId(2L);
        assertThat(innodbLockMonitorDTO1).isNotEqualTo(innodbLockMonitorDTO2);
        innodbLockMonitorDTO1.setId(null);
        assertThat(innodbLockMonitorDTO1).isNotEqualTo(innodbLockMonitorDTO2);
    }
}
