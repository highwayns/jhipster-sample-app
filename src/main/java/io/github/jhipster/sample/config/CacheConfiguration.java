package io.github.jhipster.sample.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, io.github.jhipster.sample.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, io.github.jhipster.sample.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, io.github.jhipster.sample.domain.User.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.Authority.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.User.class.getName() + ".authorities");
            createCache(cm, io.github.jhipster.sample.domain.BankAccount.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.BankAccount.class.getName() + ".operations");
            createCache(cm, io.github.jhipster.sample.domain.Label.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.Label.class.getName() + ".operations");
            createCache(cm, io.github.jhipster.sample.domain.Operation.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.Operation.class.getName() + ".labels");
            createCache(cm, io.github.jhipster.sample.domain.AbuseReport.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.AbuseTrigger.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.Parameters.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.Address.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.Capture.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.CardTokenData.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.Entry.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.ErrorReport.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.Identity.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.Issuer.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.Links.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.Link.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.Order.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.OrderLine.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.Payment.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.PaymentAttributes.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.PaymentJobAttributes.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.PaymentJob.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.PaymentStep.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.PaymentMethods.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.PaymentMethodInfo.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.Refund.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.RefundStep.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.ResultAttributes.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.RecurrenceCriteria.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.Currencys.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.TokenisedCard.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.AbuseTrigger.class.getName() + ".abuseReports");
            createCache(cm, io.github.jhipster.sample.domain.Parameters.class.getName() + ".abuseTriggers");
            createCache(cm, io.github.jhipster.sample.domain.Parameters.class.getName() + ".entries");
            createCache(cm, io.github.jhipster.sample.domain.Capture.class.getName() + ".payments");
            createCache(cm, io.github.jhipster.sample.domain.CardTokenData.class.getName() + ".paymentMethodInfos");
            createCache(cm, io.github.jhipster.sample.domain.Issuer.class.getName() + ".paymentMethodInfos");
            createCache(cm, io.github.jhipster.sample.domain.OrderLine.class.getName() + ".orders");
            createCache(cm, io.github.jhipster.sample.domain.Payment.class.getName() + ".paymentJobs");
            createCache(cm, io.github.jhipster.sample.domain.PaymentStep.class.getName() + ".payments");
            createCache(cm, io.github.jhipster.sample.domain.PaymentMethods.class.getName() + ".payments");
            createCache(cm, io.github.jhipster.sample.domain.PaymentMethods.class.getName() + ".paymentSteps");
            createCache(cm, io.github.jhipster.sample.domain.PaymentMethods.class.getName() + ".paymentJobs");
            createCache(cm, io.github.jhipster.sample.domain.Refund.class.getName() + ".payments");
            createCache(cm, io.github.jhipster.sample.domain.RefundStep.class.getName() + ".refunds");
            createCache(cm, io.github.jhipster.sample.domain.Currencys.class.getName() + ".paymentMethodInfos");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
