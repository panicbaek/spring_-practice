package com.example.board.upload;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "file") // 접두어 세팅
public class StorageProperties {

	private String uploadDir;
}
