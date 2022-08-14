package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequestTypesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestTypes.class);
        RequestTypes requestTypes1 = new RequestTypes();
        requestTypes1.setId(1L);
        RequestTypes requestTypes2 = new RequestTypes();
        requestTypes2.setId(requestTypes1.getId());
        assertThat(requestTypes1).isEqualTo(requestTypes2);
        requestTypes2.setId(2L);
        assertThat(requestTypes1).isNotEqualTo(requestTypes2);
        requestTypes1.setId(null);
        assertThat(requestTypes1).isNotEqualTo(requestTypes2);
    }
}
