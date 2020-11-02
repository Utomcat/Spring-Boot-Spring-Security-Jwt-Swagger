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
    protected byte errno;

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
        String path = "C:\\Users\\ranyi\\Desktop\\058.dwg";
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

    @Test
    void method02() {
        String[] results = new String[2];
        String url = "http://172.16.2.106:81/group1/M00/00/00/CgBkBV9q7POAA13RAADlt9RrGrs689.dwg";
        //String url = "fastdfs://group1/M00/00/00/CgBkBV9q7POAA13RAADlt9RrGrs689.dwg";

        String replaceAfterUrl = url.replace("fastdfs://", "");


        System.out.println(" replace fastdfs:// after of url : replaceAfterUrl ==> " + replaceAfterUrl);

        int pos = replaceAfterUrl.indexOf("/");
        int length = replaceAfterUrl.length();

        System.out.println(" the index within this string of the first occurrence of the specified substring (/) : pos ==> " + pos);
        System.out.println(" the length of this string : length ==> " + length);

        results[0] = replaceAfterUrl.substring(0, pos); //group name
        results[1] = replaceAfterUrl.substring(pos + 1); //file name

        System.out.println(" results[0] ==> " + results[0]);
        System.out.println(" results[1] ==> " + results[1]);

    }

    @Test
    void method03() {
        String fileId = "http://172.16.2.106:81/group1/M00/00/00/CgBkBV9q7POAA13RAADlt9RrGrs689.dwg";

        write(getFastDfsFileId(fileId));

    }


    private String getFastDfsFileId(String fileId) {
        return fileId.replace("fastdfs://", "");
    }

    public void write(String fileId) {
        try {
            int rt = download_file1(fileId);
            assertResultSuccess(rt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int download_file1(String file_id) throws IOException {
        final long file_offset = 0;
        final long download_bytes = 0;

        return this.download_file1(file_id, file_offset, download_bytes);
    }

    public int download_file1(String file_id, long file_offset, long download_bytes) throws IOException {
        String[] parts = new String[2];
        this.errno = this.split_file_id(file_id, parts);
        if (this.errno != 0) {
            return this.errno;
        }

        return 0;
    }

    public static byte split_file_id(String file_id, String[] results) {
        int pos = file_id.indexOf("/");
        if ((pos <= 0) || (pos == file_id.length() - 1)) {
            return 22;
        }

        results[0] = file_id.substring(0, pos); //group name
        results[1] = file_id.substring(pos + 1); //file name
        return 0;
    }

    private void assertResultSuccess(int rt) throws Exception {
        if (rt != 0) {
            throw new Exception("dfs error,code:" + rt);
        }
    }


}


@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
class Vo {
    private String fileName;
    private byte[] content;


}