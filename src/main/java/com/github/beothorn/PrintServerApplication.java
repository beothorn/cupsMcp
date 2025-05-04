package com.github.beothorn;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PrintServerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(PrintServerApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Bean
    public ToolCallbackProvider toolCallbackProvider(final PrintService printService) {
        return  MethodToolCallbackProvider.builder().toolObjects(printService).build();
    }
}