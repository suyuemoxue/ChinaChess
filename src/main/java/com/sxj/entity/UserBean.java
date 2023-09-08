package com.sxj.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBean {
    private Integer uid;
    private String uname;
    private String upwd;
}
