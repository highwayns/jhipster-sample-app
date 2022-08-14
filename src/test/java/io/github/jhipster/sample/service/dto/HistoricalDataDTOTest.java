package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HistoricalDataDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistoricalDataDTO.class);
        HistoricalDataDTO historicalDataDTO1 = new HistoricalDataDTO();
        historicalDataDTO1.setId(1L);
        HistoricalDataDTO historicalDataDTO2 = new HistoricalDataDTO();
        assertThat(historicalDataDTO1).isNotEqualTo(historicalDataDTO2);
        historicalDataDTO2.setId(historicalDataDTO1.getId());
        assertThat(historicalDataDTO1).isEqualTo(historicalDataDTO2);
        historicalDataDTO2.setId(2L);
        assertThat(historicalDataDTO1).isNotEqualTo(historicalDataDTO2);
        historicalDataDTO1.setId(null);
        assertThat(historicalDataDTO1).isNotEqualTo(historicalDataDTO2);
    }
}
