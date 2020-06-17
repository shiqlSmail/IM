package com.server.tools.http;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetHtml {
        public Map<String,Object> getHtmlInfo(String url){
            Map<String,Object> map = new HashMap<String,Object>();
            Document doc = null;
            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String title = doc.title();
            Elements metas = doc.head().select("meta");
            for (Element meta : metas) {
                String content = meta.attr("content");
                if ("keywords".equalsIgnoreCase(meta.attr("name"))) {
                    map.put("keywords",content);

                }
                if ("description".equalsIgnoreCase(meta.attr("name"))) {
                    map.put("description",content);
                }
            }
            Elements keywords = doc.getElementsByTag("meta");
            map.put("title",title);
            return map;
        }

        private static void getStr1(String Str) {
            //切分
            String regex = "/";
            String[] strings = Str.split(regex);
            //输出结果
            System.out.println(strings[2]);
        }

        public static String getStr2(String Str) {
            String res = "";
            String reg = ".*\\/\\/([^\\/\\:]*).*";
            String urls = Str.replaceAll (reg, "$1");
            if(urls.contains("www.")){
                res = urls.substring(4);
            }else{
                res = urls;
            }
            return res;
        }


        private static String getStr3(String Str) {
            Pattern pattern = Pattern.compile("[^http://]*?.com");
            Matcher matcher = pattern.matcher(Str);
            while(matcher.find()){
                String group = matcher.group();
                System.out.println(group);
            }
            return null;
        }

        public static void main(String[] args) {
            new GetHtml().getStr2("http://www.ilonw.com");
        }

    }