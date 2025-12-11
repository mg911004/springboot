package com.ezace.board;

// import org.springdoc.core.annotations.OpenAPIDefinition;
// import org.springdoc.core.annotations.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @OpenAPIDefinition(
//        info = @Info(
//                title = "My Custom API",    // API 제목
//                version = "v1",            // API 버전
//                description = "This is a custom API documentation"  // API 설명
//        )
// )
public class BoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardApplication.class, args);
    }
}
