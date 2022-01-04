package com.k.bootweb.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class Assessment {
    @Value("${pythonexe-location}")
    private String pythonexe;

    @Value("${path_python}")
    private String path_python;

    @Value("${path_py}")
    private String path_py;
    public double getassessment(String content){
        //创建路径
        try {
            //选择解释器和文件路径，带上文章和模型数据文件的路径
            String[] args = new String[]{pythonexe, path_py, content+" ",path_python};
            Process proc = Runtime.getRuntime().exec(args);// 执行py文件

            //获得python输出的结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = in.readLine();
            //取数组的第二个参数，保留六位有效数字
            int index1,index2;
            index1 = line.indexOf(" ",0);
            index2 = line.indexOf("]",0);
            double result=Double.parseDouble(line.substring(index1,index2));
            BigDecimal temp = new BigDecimal(result);
            result= temp.setScale(6, RoundingMode.HALF_UP).doubleValue();

            in.close();
            proc.waitFor();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
