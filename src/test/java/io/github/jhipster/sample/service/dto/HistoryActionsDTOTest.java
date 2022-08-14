package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HistoryActionsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistoryActionsDTO.class);
        HistoryActionsDTO historyActionsDTO1 = new HistoryActionsDTO();
        historyActionsDTO1.setId(1L);
        HistoryActionsDTO historyActionsDTO2 = new HistoryActionsDTO();
        assertThat(historyActionsDTO1).isNotEqualTo(historyActionsDTO2);
        historyActionsDTO2.setId(historyActionsDTO1.getId());
        assertThat(historyActionsDTO1).isEqualTo(historyActionsDTO2);
        historyActionsDTO2.setId(2L);
        assertThat(historyActionsDTO1).isNotEqualTo(historyActionsDTO2);
        historyActionsDTO1.setId(null);
        assertThat(historyActionsDTO1).isNotEqualTo(historyActionsDTO2);
    }
}
