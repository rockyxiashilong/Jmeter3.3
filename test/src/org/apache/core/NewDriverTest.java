package org.apache.core;
import org.junit.Assert.*;
import org.apache.jmeter.NewDriver;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewDriverTest {

    private static String replaceDateFormatInFileName(String fileName) {
        try {
            StringBuilder builder = new StringBuilder();

            final Date date = new Date();
            System.out.println(date);
            int fromIndex = 0;
            int begin = fileName.indexOf('\'', fromIndex);// $NON-NLS-1$
            int end;

            String format;
            SimpleDateFormat dateFormat;

            while (begin != -1) {
                builder.append(fileName.substring(fromIndex, begin));

                fromIndex = begin + 1;
                end = fileName.indexOf('\'', fromIndex);// $NON-NLS-1$
                if (end == -1) {
                    throw new IllegalArgumentException("Invalid pairs of single-quotes in the file name: " + fileName);// $NON-NLS-1$
                }

                format = fileName.substring(begin + 1, end);
                dateFormat = new SimpleDateFormat(format);
                builder.append(dateFormat.format(date));

                fromIndex = end + 1;
                begin = fileName.indexOf('\'', fromIndex);// $NON-NLS-1$
            }

            if (fromIndex < fileName.length() - 1) {
                builder.append(fileName.substring(fromIndex));
            }


            return builder.toString();
        } catch (Exception ex) {
            System.err.println("Error replacing date format in file name:"+fileName+", error:"+ex.getMessage()); // NOSONAR
        }

        return fileName;
    }
    @Test
    public void testReplaceString(){
        String s = "'88'this is a '09 08 07' hah wo hao";
       String newS = replaceDateFormatInFileName(s);
       System.out.println(newS);
    }
}
