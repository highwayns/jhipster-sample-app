package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LinksTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Links.class);
        Links links1 = new Links();
        links1.setId(1L);
        Links links2 = new Links();
        links2.setId(links1.getId());
        assertThat(links1).isEqualTo(links2);
        links2.setId(2L);
        assertThat(links1).isNotEqualTo(links2);
        links1.setId(null);
        assertThat(links1).isNotEqualTo(links2);
    }
}
