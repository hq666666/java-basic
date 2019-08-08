package com.person.basic.classloader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 模拟java-web端：
 *
 *      查看对应的classloader加载结构
 */
public class WebClassLoader extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        ClassLoader classLoader = this.getClass().getClassLoader();
        while ( null != classLoader){
            writer.write(classLoader.getClass().getName()+"<br/>");
            classLoader = classLoader.getParent();
            writer.write(String.valueOf(classLoader));
        }
        writer.flush();
        writer.close();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
