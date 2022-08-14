package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HistoryActionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistoryActions.class);
        HistoryActions historyActions1 = new HistoryActions();
        historyActions1.setId(1L);
        HistoryActions historyActions2 = new HistoryActions();
        historyActions2.setId(historyActions1.getId());
        assertThat(historyActions1).isEqualTo(historyActions2);
        historyActions2.setId(2L);
        assertThat(historyActions1).isNotEqualTo(historyActions2);
        historyActions1.setId(null);
        assertThat(historyActions1).isNotEqualTo(historyActions2);
    }
}
