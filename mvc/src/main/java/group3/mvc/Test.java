package group3.mvc;

import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        String s = "/gif https://tenor.com/view/bye-gif-24561605";
        String regex = "^/gif .*tenor.com.*-[0-9]*$";
        System.out.println(Pattern.matches(regex, s));
        System.out.println(s.substring(s.lastIndexOf('-')+1));
    }
}
