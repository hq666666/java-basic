package com.person.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class IOTests {

    private static final Logger log = LoggerFactory.getLogger(IOTests.class);

    /**
     * File类操作
     * @throws IOException
     */
    @Test
    public void test() throws IOException {

        File file = new File("C:/operate.txt");
        String name = file.getName();
        String path = file.getPath();
        URI uri = file.toURI();
        System.out.println(name);
        System.out.println(path);
        System.out.println(uri);
        File operate = File.createTempFile("operate", ".txt", new File("C:/"));
        String absolutePath = operate.getAbsolutePath();
        System.out.println(absolutePath);
    }
    /**
     * 字节流:输入流inputStream；
     */
    @Test
    public  void test2() throws IOException, ClassNotFoundException {
         String msg = "ba";
         //ByteArrayInputStream字节流
        InputStream is = new ByteArrayInputStream(msg.getBytes());
        int len =0;
      while ((len = is.read()) != -1)
            System.out.print(len+"\t");

        System.out.println("==============");

        //FileInputStream字节流
        InputStream is2 = new FileInputStream("Data.txt");

         Object result = 0;
         //通过read方法获取数据
        while((result = is2.read()) != null){
            System.out.print(result+"\t");
        }
        //关闭流，释放资源
        is.close();
        is2.close();
    }

    /**
     * 字节流：输出流OutputStream
     */
    @Test
    public void test3() throws IOException {

        final String msg = "grep";
        //FileOutputStream文件输出流
        OutputStream os = new FileOutputStream("Data.txt");

        try {
            os.write(msg.getBytes());
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != os){
                os.close();
            }

        }
        //获取数据
        InputStream is = new FileInputStream("Data.txt");
        int result = 0;
        StringBuffer buffer = new StringBuffer();
        while ((result = is.read()) != -1){

            char a = (char) result;
            System.out.println(a);
            buffer.append(a);
        }
        //判断写出与读取的结果是否一致
        System.out.println(msg.equals(buffer.toString()));
    }

    /**
     * 字符流：输入流
     *       BufferedReader；
     *          Reader：
     *          InputStreamReader
     *          FileReader
     */
    @Test
    public void test4() throws IOException {
         //FileReader字符流
        Reader reader = new FileReader("Data.txt");
        //使用InputStreamReader字符流
       /* InputStream is = new FileInputStream("Data.txt");
        Reader reader = new InputStreamReader(is, "GBK");*/
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            long start = System.currentTimeMillis();

                String msg = null;
                while((msg =bufferedReader.readLine())!=null)
                System.out.println(msg);
            long end = System.currentTimeMillis();
            System.out.println((end -start)+"s");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bufferedReader != null && reader != null){
                //关闭流(先关大流)
                bufferedReader.close();
                reader.close();
            }
        }
    }

    /**
     * 字符流：输出流：
     *    BufferedWriter：
     *      Writer：
     *      OutputStreamWriter
     *       FileWriter
     *
     * @throws IOException
     */
    @Test
    public void test5() throws IOException {
        String start ="他强任他强,清风拂山岗";
        String end ="他横任他横，明月照大江";
        //输出数据
        //Writer writer = new FileWriter("Data.txt");
        OutputStream os = new FileOutputStream("Data.txt");
        Writer writer = new OutputStreamWriter(os,"utf-8");
        BufferedWriter bufferedWriter = new BufferedWriter(writer) ;

        bufferedWriter.write(start);
        bufferedWriter.newLine();
        bufferedWriter.write(end);
       /* for(int i=0;i<500;i++){
            bufferedWriter.write(end);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }*/
        writer.flush();

        bufferedWriter.close();
        writer.close();
        //获取数据
        Reader reader = new FileReader("Data.txt");
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            List<String> strings = new ArrayList<>();
            String result ="";
            while ((result =bufferedReader.readLine())!= null)
                //System.out.println(result);
                strings.add(result);
            System.out.println(strings.size());
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(bufferedReader != null && reader != null){
                bufferedReader.close();
                reader.close();
            }
        }
    }

    /**
     *导入Excel表格:简化版
     */
    @Test
    public void test6() throws IOException {

        OutputStream os = new FileOutputStream("C:/名人名言.cvs");
        Writer writer = new OutputStreamWriter(os,"GBK");
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        String title = "名人名言大全";
        String start ="他强任他强,清风拂山岗";
        String end ="他横任他横，明月照大江";
        try {
            bufferedWriter.write(title);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            for(int i=1;i<=20;i++){
                if((i%2) !=0){
                    bufferedWriter.write(start+"\r\n");
                    bufferedWriter.flush();
                }else {
                    bufferedWriter.write(end+"\r\n");
                    bufferedWriter.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != bufferedWriter && null != writer && null != os){
                bufferedWriter.close();
                writer.close();
                os.close();
            }
        }

    }

    @Test
    public void test7() throws Exception {
        InputStream in = new FileInputStream("Data.txt");
        //OutputStream out = new FileOutputStream("Data.txt");
        byte[] newBytes = new byte[1024];
        int len =0;
        while((len = in.read(newBytes))!= -1)
            System.out.println("读取的数据："+len);;
        String result = new String(newBytes,"utf-8");
        System.out.println(result);
        System.out.println(newBytes.length);



       /* OutputStream out = new FileOutputStream("Data.txt");
        String str = new String("hehe");
        byte[] bytes = str.getBytes();
        out.write(bytes);*/
    }


}
