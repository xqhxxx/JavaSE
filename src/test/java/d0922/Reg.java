package d0922;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xqh
 * @date 2022/9/22  11:54:19
 * @apiNote
 */
public class Reg {

        public static void main(String args[]) {
            String str = "[--sdf123sda#@#][323as0-23--=afs][3232323]";

			String regex = "\\[(.*?)]";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(str);

//            System.out.println(matcher.group(1));
			while (matcher.find()) {
				System.out.println(matcher.group(1));
			}
        }

    }
