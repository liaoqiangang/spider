package com.spider.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * URL 过滤器，对爬虫URL 增加个人偏好
 */
public class LinkFilter {
    public boolean accept(String url) {
        String regular = "(ht|f)tp(s?)\\:\\/\\/[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*(:(0-9)*)*(\\/?)([a-zA-Z0-9\\-\\.\\?\\,\\'\\/\\\\\\+&amp;%\\$#_]*)?";
        Pattern pattern = Pattern.compile(regular);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }
}
