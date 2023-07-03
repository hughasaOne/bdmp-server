package com.rhy.bdmp.gateway.security;

import cn.hutool.core.util.StrUtil;
import com.rhy.bcp.common.constant.AuthConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

/**
 * @author: lipeng
 * @Date: 2021/1/4
 * @description: 鉴权管理器
 * 用于判断是否有资源的访问权限（在WebFluxSecurity中自定义鉴权操作需要实现ReactiveAuthorizationManager接口）
 * @version: 1.0.0
 */
@Component
@AllArgsConstructor
@Slf4j
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private RedisTemplate redisTemplate;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        String path = request.getURI().getPath();
        PathMatcher pathMatcher = new AntPathMatcher();
        log.info("=======开始进行鉴权，请求path={}", path);
        // 对应跨域的预检请求直接放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // 非管理端路径无需鉴权直接放行
//        if (!pathMatcher.match(AuthConstants.BBCP_URL_PATTERN, path)) {
//            return Mono.just(new AuthorizationDecision(true));
//        }

        // token为空拒绝访问
        String token = request.getHeaders().getFirst(AuthConstants.JWT_TOKEN_HEADER);
        if (StrUtil.isBlank(token)) {
            return Mono.just(new AuthorizationDecision(false));
        }

        // 从Redis中获取资源权限角色关系列表(暂时先放开所有资源权限)
//        Map<Object, Object> resourceRolesMap = redisTemplate.opsForHash().entries(AuthConstants.RESOURCE_ROLES_KEY);
//
//        //测试代码
////        if(resourceRolesMap.isEmpty()){
////            Map<String, List<String>> resource = new TreeMap<>();
////            resource.put("/bbcp/basic/user/1", CollUtil.toList("ROLE_1"));
////            resource.put("/bbcp/basic/user/updateRoleIdsByUserId", CollUtil.toList("ROLE_1", "ROLE_2"));
////            redisTemplate.opsForHash().putAll(AuthConstants.RESOURCE_ROLES_KEY, resource);
////        }
////        resourceRolesMap = redisTemplate.opsForHash().entries(AuthConstants.RESOURCE_ROLES_KEY);
//
//        Iterator<Object> iterator = resourceRolesMap.keySet().iterator();
//
//        // 请求路径匹配到的资源需要的角色权限集合authorities统计
//        Set<String> authorities = new HashSet<>();
//        while (iterator.hasNext()) {
//            String pattern = (String) iterator.next();
//            if (pathMatcher.match(pattern, path)) {
//                authorities.addAll(Convert.toList(String.class, resourceRolesMap.get(pattern)));
//            }
//        }
//
//        Mono<AuthorizationDecision> authorizationDecisionMono = mono
//                .filter(Authentication::isAuthenticated)
//                .flatMapIterable(Authentication::getAuthorities)
//                .map(GrantedAuthority::getAuthority)
//                .any(roleId -> {
//                    // roleId是请求用户的角色(格式:ROLE_{roleId})，authorities是请求资源所需要角色的集合
//                    log.info("访问路径：{}", path);
//                    log.info("用户角色信息：{}", roleId);
//                    log.info("资源需要权限authorities：{}", authorities);
//                    return authorities.contains(roleId);
//                })
//                .map(AuthorizationDecision::new)
//                .defaultIfEmpty(new AuthorizationDecision(false));
//        return authorizationDecisionMono;

        return Mono.just(new AuthorizationDecision(true));
    }
}
