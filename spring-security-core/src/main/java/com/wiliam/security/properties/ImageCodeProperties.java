package com.wiliam.security.properties;

import lombok.Data;

/**
 * @author: liwiliam
 */
@Data
public class ImageCodeProperties {
    private int width = 67;
    private int height = 23;
    private int length = 4;
    private int expireIn = 60;
}
