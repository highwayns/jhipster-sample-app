package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransactionTypesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionTypes.class);
        TransactionTypes transactionTypes1 = new TransactionTypes();
        transactionTypes1.setId(1L);
        TransactionTypes transactionTypes2 = new TransactionTypes();
        transactionTypes2.setId(transactionTypes1.getId());
        assertThat(transactionTypes1).isEqualTo(transactionTypes2);
        transactionTypes2.setId(2L);
        assertThat(transactionTypes1).isNotEqualTo(transactionTypes2);
        transactionTypes1.setId(null);
        assertThat(transactionTypes1).isNotEqualTo(transactionTypes2);
    }
}
