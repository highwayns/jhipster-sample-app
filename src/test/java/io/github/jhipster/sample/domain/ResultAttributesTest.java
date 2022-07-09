package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResultAttributesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultAttributes.class);
        ResultAttributes resultAttributes1 = new ResultAttributes();
        resultAttributes1.setId(1L);
        ResultAttributes resultAttributes2 = new ResultAttributes();
        resultAttributes2.setId(resultAttributes1.getId());
        assertThat(resultAttributes1).isEqualTo(resultAttributes2);
        resultAttributes2.setId(2L);
        assertThat(resultAttributes1).isNotEqualTo(resultAttributes2);
        resultAttributes1.setId(null);
        assertThat(resultAttributes1).isNotEqualTo(resultAttributes2);
    }
}
