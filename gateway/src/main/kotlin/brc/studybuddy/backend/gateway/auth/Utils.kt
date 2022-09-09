package brc.studybuddy.backend.gateway.auth

import org.springframework.http.HttpHeaders
import java.util.*
import java.util.function.Predicate
import java.util.regex.Matcher
import java.util.regex.Pattern

private const val AUTHORIZATION_HEADER = "Authorization"
private val BEARER_PATTERN: Pattern = Pattern.compile("^Bearer (.+?)$")

internal fun getHeaderAuthToken(headers: HttpHeaders): Optional<String> =
    Optional.ofNullable(headers.getFirst(AUTHORIZATION_HEADER))
        .filter(Predicate.not(String::isEmpty))
        .map(BEARER_PATTERN::matcher)
        .filter(Matcher::find)
        .map { m -> m.group(1) }
