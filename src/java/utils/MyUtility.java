/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Alessandro
 */
public class MyUtility {

    private static final String URL_REGEX = "((https?|ftp)://)?([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])(\\.([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9]))+(/[A-Za-z0-9_\\.\\-~%]+)*/?"; // url regex

    public static String cleanHTMLTags(String text) { //clean html tags
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    public static String checkMultiLink(String s) { //check links
        boolean control = true;
        int first = 0;
        int second = 0;
        String link = "";
        String substitute;

        if (s.contains("$$")) {
            while (control) {
                first = s.indexOf("$$", first + link.length());
               
                if (first >= 0) {
                    second = s.indexOf("$$", first + 2); // 1 o 2?
                   
                    if ((second > 0) && (first < second)) {
                        link = s.substring(first + 2, second);

                        if (link.matches(URL_REGEX)) {
                            if ((link.startsWith("http")) || (link.startsWith("ftp")) || (link.startsWith("https"))) { // if link starts with http or ftp or https do nothing
                                substitute = "<a href = '" + link + "' target='_blank'>"+link+" </a>";
                            } else {
                                substitute = "<a href='http://" + link + "' target='_blank'>"+link+" </a>"; //otherwise add http
                            }
                            s = s.substring(0, first) + substitute + s.substring(second + 2);
                        }
                    } else {
                        control = false;
                    }
                } else {
                    control = false;
                }
            }
        }
        return s;
    }
}
