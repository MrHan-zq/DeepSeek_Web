package com.example.deepseek.model;

import lombok.Data;

/**
 * @author hanjun
 */
@Data
public class EmailModel {

    private String sendAccount;
    private String sendPassword;
    private String SMTPHost;
}
