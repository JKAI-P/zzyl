package com.zzyl.nursing.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 编号生成器
 * 生成16位编号：前12位为时间(yyyyMMddHHmmss)，后4位为递增序号(0001-9999)
 */
public class CodeGenerator {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static volatile String lastTimeStr = "";
    private static final AtomicInteger sequence = new AtomicInteger(0);

    /**
     * 生成合同编号
     * @return 16位合同编号
     */
    public static synchronized String generateContractNumber() {
        String currentTimeStr = LocalDateTime.now().format(FORMATTER);
        
        // 如果时间发生变化，重置序号
        if (!currentTimeStr.equals(lastTimeStr)) {
            lastTimeStr = currentTimeStr;
            sequence.set(1);
        } else {
            // 同一秒内，序号递增
            sequence.incrementAndGet();
        }
        
        // 确保序号在1-9999范围内
        int seq = sequence.get();
        if (seq > 9999) {
            seq = 1;
            sequence.set(1);
        }
        
        // 格式化序号为4位，不足补0
        String seqStr = String.format("%04d", seq);
        
        return currentTimeStr + seqStr;
    }
}
