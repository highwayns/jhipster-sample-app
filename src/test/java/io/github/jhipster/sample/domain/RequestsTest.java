package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequestsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Requests.class);
        Requests requests1 = new Requests();
        requests1.setId(1L);
        Requests requests2 = new Requests();
        requests2.setId(requests1.getId());
        assertThat(requests1).isEqualTo(requests2);
        requests2.setId(2L);
        assertThat(requests1).isNotEqualTo(requests2);
        requests1.setId(null);
        assertThat(requests1).isNotEqualTo(requests2);
    }
}
