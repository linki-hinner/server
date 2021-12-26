package com.lin.server.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;

@Getter
@Setter
@Builder
public class FileResource {
    private Resource resource;
    private String name;
    private String type;
}
