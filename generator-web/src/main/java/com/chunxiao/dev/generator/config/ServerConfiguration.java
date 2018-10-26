package com.chunxiao.dev.generator.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by chunxiaoli on 7/6/17.
 */
@Configuration
@MapperScan("com.chunxiao.dev.mapper")
public class ServerConfiguration {
}
