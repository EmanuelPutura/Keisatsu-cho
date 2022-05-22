package ubb.keisatsu.cms.security

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import ubb.keisatsu.cms.model.entities.Account
import ubb.keisatsu.cms.repository.AccountsRepository
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtTokenFilter(private val accountsRepository: AccountsRepository, private val jwtTokenUtil: JwtTokenUtil) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (header == null || header.isEmpty() || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        // Get jwt token and validate
        val token = header.split(" ".toRegex()).toTypedArray()[1]
        if (!jwtTokenUtil.validate(token)) {
            filterChain.doFilter(request, response)
            return
        }

        // Get user identity and set it on the spring security context
        val userDetails: Account? = accountsRepository
            .findByEmail(jwtTokenUtil.decodeJWT(token).subject)


        val authentication = UsernamePasswordAuthenticationToken(
            userDetails, null,
            userDetails?.authorities ?: listOf()
        )

        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

        SecurityContextHolder.getContext().authentication = authentication
        filterChain.doFilter(request, response)
    }
}