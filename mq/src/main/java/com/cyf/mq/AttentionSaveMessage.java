package com.cyf.mq;

import com.cyf.entity.Attention;
import lombok.Data;

/**
 * @author by cyf
 * @date 2020/10/29.
 */
@Data
public class AttentionSaveMessage {
    public static final String TOPIC = "attentionSave";

    private Attention attention;
}
