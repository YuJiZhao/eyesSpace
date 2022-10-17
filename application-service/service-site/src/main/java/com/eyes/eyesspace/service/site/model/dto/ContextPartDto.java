package com.eyes.eyesspace.service.site.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ContextPartDto {
    private String spaceName;

    private String ownerName;

    private String ownerAvatar;

    private String ownerMotto;

    private List<FootprintDto> footprint;

    private String announce;

    private String announceVersion;

    private String buildTime;
}