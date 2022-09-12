package brc.studybuddy.backend.gateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource


@Configuration
class CorsConfig : CorsConfiguration() {

    @Bean
    fun corsFilter(): CorsWebFilter {
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.addAllowedOrigin("*")
        corsConfiguration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
        corsConfiguration.addAllowedHeader("origin")
        corsConfiguration.addAllowedHeader("content-type")
        corsConfiguration.addAllowedHeader("accept")
        corsConfiguration.addAllowedHeader("authorization")
        corsConfiguration.addAllowedHeader("cookie")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfiguration)

        return CorsWebFilter(source)
    }

}
