package com.isep.acme.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewRequest {
    private String sku;
    private String user;
    private String reviewText;
    private Double rating;    
}
