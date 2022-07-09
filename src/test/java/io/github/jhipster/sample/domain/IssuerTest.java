package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IssuerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Issuer.class);
        Issuer issuer1 = new Issuer();
        issuer1.setId("id1");
        Issuer issuer2 = new Issuer();
        issuer2.setId(issuer1.getId());
        assertThat(issuer1).isEqualTo(issuer2);
        issuer2.setId("id2");
        assertThat(issuer1).isNotEqualTo(issuer2);
        issuer1.setId(null);
        assertThat(issuer1).isNotEqualTo(issuer2);
    }
}
