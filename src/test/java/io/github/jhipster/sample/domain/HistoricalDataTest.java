package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HistoricalDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistoricalData.class);
        HistoricalData historicalData1 = new HistoricalData();
        historicalData1.setId(1L);
        HistoricalData historicalData2 = new HistoricalData();
        historicalData2.setId(historicalData1.getId());
        assertThat(historicalData1).isEqualTo(historicalData2);
        historicalData2.setId(2L);
        assertThat(historicalData1).isNotEqualTo(historicalData2);
        historicalData1.setId(null);
        assertThat(historicalData1).isNotEqualTo(historicalData2);
    }
}
