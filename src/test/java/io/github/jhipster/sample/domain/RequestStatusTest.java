package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequestStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestStatus.class);
        RequestStatus requestStatus1 = new RequestStatus();
        requestStatus1.setId(1L);
        RequestStatus requestStatus2 = new RequestStatus();
        requestStatus2.setId(requestStatus1.getId());
        assertThat(requestStatus1).isEqualTo(requestStatus2);
        requestStatus2.setId(2L);
        assertThat(requestStatus1).isNotEqualTo(requestStatus2);
        requestStatus1.setId(null);
        assertThat(requestStatus1).isNotEqualTo(requestStatus2);
    }
}
