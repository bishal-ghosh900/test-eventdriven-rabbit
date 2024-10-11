package com.example.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity.authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                                .anyExchange().permitAll()
//                        .pathMatchers("/myapp/test1/java/**").permitAll()
//                        .pathMatchers("/myapp/test1/build/**").permitAll()
//                        .pathMatchers("/myapp/test1/**").hasRole("NAKAMA")
//                        .pathMatchers("/myapp/test2/**").hasRole("KAMI")
                ).oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                    .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(new CustomAuthenticationConverter())))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .build();
    }

    static class CustomAuthenticationConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

        @SuppressWarnings("unchecked")
        @Override
        public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {

            Map<String, Object> realmAccess= (Map<String, Object>) jwt.getClaims().get("realm_access");

            if(realmAccess == null || realmAccess.isEmpty()) {
                return Mono.just(new JwtAuthenticationToken(jwt, List.of()));
            }

            Collection<GrantedAuthority> roles = ((List<String>) realmAccess.get("roles")).stream()
                    .map(role -> "ROLE_"+role)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            return Mono.just(new JwtAuthenticationToken(jwt, roles));
        }
    }

}
