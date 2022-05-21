package ubb.keisatsu.cms.security

import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.core.GrantedAuthorityDefaults
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import ubb.keisatsu.cms.repository.AccountsRepository
import javax.servlet.http.HttpServletResponse


@EnableWebSecurity
@EnableGlobalMethodSecurity(
    prePostEnabled = true
)
class SecurityConfig(val accountRepo: AccountsRepository, var jwtTokenFilter: JwtTokenFilter) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder): Unit {
        auth.userDetailsService(UserDetailsService { email: String ->
            this.accountRepo.findByEmail(email)?: throw UsernameNotFoundException("Email not found in database")
        })
    }

    override fun configure(httpSecurity: HttpSecurity): Unit {
        var http: HttpSecurity = httpSecurity;

        http = http.cors().and().csrf().disable()

        http = http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()

        http = http
            .exceptionHandling()
            .authenticationEntryPoint(AuthenticationEntryPoint { request, response, ex ->
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.message)
            }).and()

        http.authorizeHttpRequests()
            .anyRequest().authenticated()


        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    override fun configure(web: WebSecurity): Unit {
        web.ignoring().antMatchers("/accounts/sign-up", "/accounts/login")
    }

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun grantedAuthorityDefaults(): GrantedAuthorityDefaults {
        return GrantedAuthorityDefaults("") // Remove the ROLE_ prefix
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}