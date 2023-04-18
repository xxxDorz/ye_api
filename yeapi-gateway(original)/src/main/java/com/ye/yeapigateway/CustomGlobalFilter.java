package com.ye.yeapigateway;

import com.ye.yeapiclientsdk.Utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 全局过滤
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    public static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        log.info("请求唯一标识"+request.getId());
        log.info("请求路径"+request.getPath().value());
        log.info("请求方法"+request.getMethod());
        log.info("请求参数"+request.getQueryParams());
        String sourceAddress = request.getLocalAddress().getHostString();
        log.info("请求来源地址"+sourceAddress);
        log.info("请求来源地址"+request.getRemoteAddress());
        ServerHttpResponse response = exchange.getResponse();

        // 2. 黑白名单
        if (!IP_WHITE_LIST.contains(sourceAddress)) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }

        // 3. 用户鉴权
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        String body = headers.getFirst("body");
        //此处为假数据，真实场景是去数据库查是否已将ak&sk分配给用户
        if (!accessKey.equals("qwer")) {
            return handleNoAuth(response);
        }
        if (Long.parseLong(nonce) > 10000L) {
            return handleNoAuth(response);
        }
        //时间不能超过五分钟
        Long currentTime = System.currentTimeMillis() / 1000;
        Long FIVE_MINEUTES = 60 * 5L;
        if ((currentTime - Long.parseLong(timestamp)) >= FIVE_MINEUTES) {
            return handleNoAuth(response);
        }
        //实际情况是从数据库中查出再赋值
        String serverSign = SignUtils.genSign(body, "rewq");
        if (!sign.equals(serverSign)) {
            return handleNoAuth(response);
        }
        // 4. 请求的模拟接口是否存在
        // 5. 请求转发，调用模拟接口
        //Mono<Void> filter = chain.filter(exchange);
        // 6. 响应日志
        return handleResponse(exchange, chain);
        // 7. 调用成功--接口调用次数+1 invokeCount
//        if (response.getStatusCode() == HttpStatus.OK) {
//
//        }else {
//            return handleInvokeError(response);
//        }
        // 8. 调用失败，返回一个规范的错误码
//        if (response.getStatusCode() != HttpStatus.OK) {
//            return handleInvokeError(response);
//        }
//        return chain.filter(exchange);

    }


    /**
     * 处理响应
     *
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            //从交换区拿响应对象
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓存数据的工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 拿到响应码
            HttpStatus statusCode = originalResponse.getStatusCode();

            if (statusCode == HttpStatus.OK) {
                // 装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 等调用完转发的接口后才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值里写数据
                            // 拼接字符串
                            return super.writeWith(
                                    fluxBody.map(dataBuffer -> {
                                        // 7. 调用成功，接口调用次数 + 1 invokeCount

                                        byte[] content = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(content);
                                        DataBufferUtils.release(dataBuffer);
                                        StringBuilder sb2 = new StringBuilder(200);
                                        sb2.append("<--- {} {] \n");
                                        List<Object> rspArgs = new ArrayList<>();
                                        rspArgs.add(originalResponse.getStatusCode());
                                        String data = new String(content, StandardCharsets.UTF_8);
                                        sb2.append(data);
                                        //打印日志
                                        log.info(sb2.toString(), rspArgs.toArray());
//                                        // 7. 调用成功，接口调用次数 + 1 invokeCount
//                                        try {
//                                            innerUserInterfaceInfoService.invokeCount(interfaceInfoId, userId);
//                                        } catch (Exception e) {
//                                            log.error("invokeCount error", e);
//                                        }
//                                        byte[] content = new byte[dataBuffer.readableByteCount()];
//                                        dataBuffer.read(content);
//                                        DataBufferUtils.release(dataBuffer);//释放掉内存
//                                        // 构建日志
//                                        StringBuilder sb2 = new StringBuilder(200);
//                                        List<Object> rspArgs = new ArrayList<>();
//                                        rspArgs.add(originalResponse.getStatusCode());
//                                        String data = new String(content, StandardCharsets.UTF_8); //data
//                                        sb2.append(data);
//                                        // 打印日志
//                                        log.info("响应结果：" + data);
                                        return bufferFactory.wrap(content);
                                    }));
                        } else {
                            // 8. 调用失败，返回一个规范的错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 设置 response 对象为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange); // 降级处理返回数据
        } catch (Exception e) {
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }
    private Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    public Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }

    public int getOrder(){
        return -1;
    }
}