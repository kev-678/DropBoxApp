package com.typeface.dropbox.serv.common;


import lombok.Getter;

import java.util.Arrays;


public enum AllowedContentType {
    TEXT_PLAIN("text/plain"),
    IMAGE_PNG("image/png"),
    IMAGE_JPEG("image/jpeg"),
    APPLICATION_JSON("application/json");

    private final String type;

    AllowedContentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static boolean isAllowed(String contentType) {
        return Arrays.stream(values())
                .anyMatch(t -> t.type.equals(contentType));
    }
}
