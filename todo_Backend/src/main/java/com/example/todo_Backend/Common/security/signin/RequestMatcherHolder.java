package com.example.todo_Backend.Common.security.signin;

import com.example.todo_Backend.User.Member.entity.MemberRole;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RequestMatcherHolder {

    private static final List<RequestInfo> REQUEST_INFO_LIST = List.of(
            // sign-up, sign-in
            new RequestInfo(HttpMethod.POST, "/api/auth/user", null),
            new RequestInfo(HttpMethod.POST, "/api/auth/login", null),


            // swagger
            new RequestInfo(HttpMethod.GET, "/api-docs", null),
            new RequestInfo(HttpMethod.GET, "/api-docs/**", null),
            new RequestInfo(HttpMethod.GET, "/v3/api-docs/**", null),
            new RequestInfo(HttpMethod.GET, "/swagger-ui.html", null),
            new RequestInfo(HttpMethod.GET, "/swagger-ui/**", null)
    );
    private final ConcurrentHashMap<String, RequestMatcher> reqMatcherCacheMap = new ConcurrentHashMap<>();

    public RequestMatcher getRequestMatchersByMinRole(@Nullable MemberRole memberRole) {
        var key = getKeyByRole(memberRole);
        return reqMatcherCacheMap.computeIfAbsent(key, k ->
                new OrRequestMatcher(REQUEST_INFO_LIST.stream()
                        .filter(reqInfo -> Objects.equals(reqInfo.memberRole(), memberRole))
                        .map(reqInfo -> new AntPathRequestMatcher(reqInfo.pattern(),
                                reqInfo.method().name()))
                        .toArray(AntPathRequestMatcher[]::new)));
    }

    private String getKeyByRole(@Nullable MemberRole memberRole) {
        return memberRole == null ? "VISITOR" : memberRole.name();
    }

    private record RequestInfo(HttpMethod method, String pattern, MemberRole memberRole) {

    }

}
