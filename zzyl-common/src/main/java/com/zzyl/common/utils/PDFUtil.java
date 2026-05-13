package com.zzyl.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class PDFUtil {

    public static String pdfToString(InputStream inputStream) {
        try (
                InputStream is = inputStream;
                PDDocument document = PDDocument.load(is);
        ) {
            // 创建一个PDFTextStripper实例来提取文本
            PDFTextStripper pdfStripper = new PDFTextStripper();

            // 从PDF文档中提取文本
            String text = pdfStripper.getText(document);
            return text;
        } catch (IOException e) {
            log.error("解析pdf文件异常", e);
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) throws FileNotFoundException {
        String pdf = PDFUtil.pdfToString(new FileInputStream("D:\\work\\sz152\\下发资料\\项目一\\Day08. 智能评估-集成AI大模型\\资料\\体检报告样例\\体检报告-刘爱国-男-69岁.pdf"));
        System.out.println(pdf);
    }
}
