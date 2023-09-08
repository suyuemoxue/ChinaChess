package com.sxj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageBean {
    private Integer seraVersionUID;
    private String userName;
    private String playUser;
    private String status;
    private String mess;
}
