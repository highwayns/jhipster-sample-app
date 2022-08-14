package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequestDescriptionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestDescriptions.class);
        RequestDescriptions requestDescriptions1 = new RequestDescriptions();
        requestDescriptions1.setId(1L);
        RequestDescriptions requestDescriptions2 = new RequestDescriptions();
        requestDescriptions2.setId(requestDescriptions1.getId());
        assertThat(requestDescriptions1).isEqualTo(requestDescriptions2);
        requestDescriptions2.setId(2L);
        assertThat(requestDescriptions1).isNotEqualTo(requestDescriptions2);
        requestDescriptions1.setId(null);
        assertThat(requestDescriptions1).isNotEqualTo(requestDescriptions2);
    }
}
