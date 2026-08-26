package com.nhatro.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;

/**
 * Du an dung spring.jpa.open-in-view=false (xem application.properties),
 * nghia la session Hibernate se dong ngay sau khi service/repository
 * tra ve, TRUOC khi Jackson serialize entity thanh JSON o tang controller.
 *
 * Neu mot quan he @ManyToOne/@OneToMany (FetchType.LAZY) chua duoc
 * "cham" toi (initialize) trong luc con session (vi du qua JOIN FETCH
 * hoac truy cap getter trong @Transactional), Jackson se co gang doc no
 * SAU KHI session da dong -> nem loi:
 *   "Could not write JSON: Could not initialize proxy [...] - no session"
 *
 * Hibernate6Module giup Jackson nhan biet proxy Hibernate: voi proxy
 * CHUA duoc initialize, no se serialize thanh null thay vi nem loi
 * (FORCE_LAZY_LOADING mac dinh la false, tuc KHONG ep load them du lieu
 * ngoai y muon - tranh N+1 query an). Day la luoi an toan chung cho toan
 * bo API tra ve entity JPA; noi nao thuc su can du lieu quan he, nen chu
 * dong fetch (JOIN FETCH / @Transactional) o tang service nhu
 * YeuCauThueService.duyet()/tuChoi() da lam.
 */
@Configuration
public class JacksonConfig {

    @Bean
    public Hibernate6Module hibernate6Module() {
        Hibernate6Module module = new Hibernate6Module();
        // Mac dinh (khong bat FORCE_LAZY_LOADING): proxy chua load -> null.
        // Neu sau nay muon Jackson tu dong load full quan he lazy khi serialize,
        // co the bat: module.enable(Hibernate6Module.Feature.FORCE_LAZY_LOADING);
        // nhung se can @Transactional bao quanh va de gay N+1 query, nen KHONG
        // bat mac dinh.
        return module;
    }
}
