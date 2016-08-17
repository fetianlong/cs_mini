/**
 * copyright (c) dearho Team
 * All rights reserved
 *
 * This file FileUtil.java creation date: [2015年6月4日 下午2:39:21] by Carsharing03
 * http://www.dearho.com
 */
package com.dearho.cs.util.orders;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Carsharing03
 * @Description:(此类型的描述)
 * @Version 1.0, 2015年6月4日
 */
public class FileUtil {
	public static void rewrite(File file, String data) {  
        BufferedWriter bw = null;  
        try {  
            bw = new BufferedWriter(new FileWriter(file));  
            bw.write(data);  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {          
            if(bw != null) {  
               try {   
                   bw.close();  
               } catch(IOException e) {  
                   e.printStackTrace();  
               }  
            }              
        }  
    }  
      
    public static List<String> readList(File file) {  
        BufferedReader br = null;  
        List<String> data = new ArrayList<String>();  
        try {  
            br = new BufferedReader(new FileReader(file));  
            for(String str = null; (str = br.readLine()) != null; ) {  
                data.add(str);  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if(br != null) {  
               try {   
                   br.close();  
               } catch(IOException e) {  
                   e.printStackTrace();  
               }  
            }  
        }  
        return data;  
    }
}
