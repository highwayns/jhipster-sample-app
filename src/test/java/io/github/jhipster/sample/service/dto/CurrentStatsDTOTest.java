package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CurrentStatsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurrentStatsDTO.class);
        CurrentStatsDTO currentStatsDTO1 = new CurrentStatsDTO();
        currentStatsDTO1.setId(1L);
        CurrentStatsDTO currentStatsDTO2 = new CurrentStatsDTO();
        assertThat(currentStatsDTO1).isNotEqualTo(currentStatsDTO2);
        currentStatsDTO2.setId(currentStatsDTO1.getId());
        assertThat(currentStatsDTO1).isEqualTo(currentStatsDTO2);
        currentStatsDTO2.setId(2L);
        assertThat(currentStatsDTO1).isNotEqualTo(currentStatsDTO2);
        currentStatsDTO1.setId(null);
        assertThat(currentStatsDTO1).isNotEqualTo(currentStatsDTO2);
    }
}
