package com.louis.shopsecurity.jwt.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class PayloadDto {

    private String sub;         // 主題
    private Long iat;           // 簽發時間
    private Long exp;           // 過期時間
    private String jti;         // JWT的ID
    private String username;    // 使用者名稱
    private List<String> authorities;    // 使用者擁有的許可權
}
