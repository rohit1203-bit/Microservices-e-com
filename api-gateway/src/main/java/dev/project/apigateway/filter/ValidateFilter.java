package dev.project.apigateway.filter;

import dev.project.apigateway.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component @Slf4j
public class ValidateFilter extends AbstractGatewayFilterFactory<AbstractGatewayFilterFactory.NameConfig> {
    private final JwtService jwtService;

    public ValidateFilter(JwtService jwtService) {
        super(NameConfig.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(NameConfig config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            var authHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION);

            if(authHeader == null) {
                log.info("Authorization Header not found");
                response.setStatusCode(HttpStatus.FORBIDDEN);
                return chain.filter(exchange.mutate().request(request).response(response).build());
            }

            var authComp = authHeader.get(0).split(" ");

            if(authComp.length < 2 || !authComp[0].equals("Bearer")) {
                log.info("Authorization header not in correct format");
                response.setStatusCode(HttpStatus.FORBIDDEN);
                return chain.filter(exchange.mutate().request(request).response(response).build());
            }

            String token  = authComp[1];

            // check if token expired
            if(jwtService.isExpired(token)) {
                log.info("Token Expired");
                response.setStatusCode(HttpStatus.REQUEST_TIMEOUT);
                return chain.filter(exchange.mutate().request(request).response(response).build());
            }

            var decoded = jwtService.decodeToken(token).split(":");

            if(decoded[0] == null || decoded[1] == null) {
                response.setStatusCode(HttpStatus.FORBIDDEN);
                return chain.filter(exchange.mutate().request(request).response(response).build());
            }

            // Add auth headers to the request
            return chain
                    .filter(
                            exchange.mutate().request(
                                    request.mutate()
                                            .header("X-userId", decoded[0])
                                            .header("X-userName", decoded[1])
                                            .build())
                                    .build());

        };
    }
}
