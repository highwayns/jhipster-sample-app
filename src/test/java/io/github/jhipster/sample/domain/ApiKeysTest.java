package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApiKeysTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiKeys.class);
        ApiKeys apiKeys1 = new ApiKeys();
        apiKeys1.setId(1L);
        ApiKeys apiKeys2 = new ApiKeys();
        apiKeys2.setId(apiKeys1.getId());
        assertThat(apiKeys1).isEqualTo(apiKeys2);
        apiKeys2.setId(2L);
        assertThat(apiKeys1).isNotEqualTo(apiKeys2);
        apiKeys1.setId(null);
        assertThat(apiKeys1).isNotEqualTo(apiKeys2);
    }
}
