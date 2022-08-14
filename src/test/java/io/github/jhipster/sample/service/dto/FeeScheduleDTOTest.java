package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FeeScheduleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeeScheduleDTO.class);
        FeeScheduleDTO feeScheduleDTO1 = new FeeScheduleDTO();
        feeScheduleDTO1.setId(1L);
        FeeScheduleDTO feeScheduleDTO2 = new FeeScheduleDTO();
        assertThat(feeScheduleDTO1).isNotEqualTo(feeScheduleDTO2);
        feeScheduleDTO2.setId(feeScheduleDTO1.getId());
        assertThat(feeScheduleDTO1).isEqualTo(feeScheduleDTO2);
        feeScheduleDTO2.setId(2L);
        assertThat(feeScheduleDTO1).isNotEqualTo(feeScheduleDTO2);
        feeScheduleDTO1.setId(null);
        assertThat(feeScheduleDTO1).isNotEqualTo(feeScheduleDTO2);
    }
}
