package com.ranyk.security;

import com.alibaba.fastjson.JSON;
import lombok.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sun.misc.BASE64Decoder;

import java.io.*;

@SpringBootTest
class SpringSecurityApplicationTests {

    private BASE64Decoder base64Decoder = new BASE64Decoder();

    /**
     * 验证 Base64 解密时,被解密的字符串为空的时候的异常
     */
    @Test
    void contextLoads() {

        try {
            //Base64加密测试字符串
            byte[] bytes = base64Decoder.decodeBuffer("QmFzZTY05Yqg5a+G5rWL6K+V5a2X56ym5Liy");
            //String base64 = null;
            //byte[] bytes = base64Decoder.decodeBuffer(base64);
            String s = new String(bytes);
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    void method01() {
        String path = "C:\\Users\\ranyi\\Desktop\\1716159_20724473_new.dwg";
        File file = new File(path);
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(file);
            outputStream = new ByteArrayOutputStream();
            int i = 0;
            while ((i = inputStream.read()) != -1) {
                outputStream.write(i);
            }

            outputStream.flush();

            byte[] bytes = outputStream.toByteArray();


            System.out.println("================================================");

            StringBuilder sb = new StringBuilder();

            Vo vo = new Vo();
            vo.setFileName("aaa.jpg");
            vo.setContent(bytes);
            System.out.println(JSON.toJSONString(vo));

            System.out.println("================================================");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}


@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
class Vo{
    private String fileName;
    private byte[] content;


}