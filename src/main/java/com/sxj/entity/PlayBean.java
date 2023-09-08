package com.sxj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayBean {
    private String ids;
    private String gameId;
    private String playUser;
    private String timeConsuming;
    private String statusType;
    private String Mess;
}
