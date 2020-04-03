package org.apache.BeanShellDemo;

import java.util.ArrayList;
import java.util.Collections;
import java.security.MessageDigest;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.jmeter.*;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.Argument;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.LoggerFactory;
public class Sign {
    public static void main(String[] args){
        ArrayList<String> values = new ArrayList();
        values.add("aaa");
        values.add("ccc");
        values.add("bbb");
        System.out.println(values);
        values.removeAll(Collections.singleton(null));
        System.out.println(values);
        Collections.sort(values);
        System.out.println(values);
        StringBuilder sb = new StringBuilder();
        for(String s :values){
            sb.append(s);
        }
        String sign= Hashing.sha1().hashString(sb, Charsets.UTF_8).toString().toUpperCase();
        System.out.println(sign);

        }
    }

