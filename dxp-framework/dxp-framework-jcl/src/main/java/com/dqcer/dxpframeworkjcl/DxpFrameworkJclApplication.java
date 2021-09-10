package com.dqcer.dxpframeworkjcl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DxpFrameworkJclApplication {

    private static final Logger log = LoggerFactory.getLogger(DxpFrameworkJclApplication.class);


    public static void main(String[] args) {
        String html = "<table id=\"tfhover\" class=\"tftable\" border=\"1\">\n" +
                "          <tr>\n" +
                "            <th colspan=\"4\">沟通报告(药物)</th>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "            <td style=\"width:25%\" colspan=\"1\">试验中心</td>\n" +
                "            <td style=\"width:75%\" colspan=\"3\">(C6)超长组织名称yh|S`NFReJqXv@z3{Kv}yvteT`oiH-dryxU32?v\fH)Y8|}.KweX5uow'>Rl(\"dP.u-):wWY';ZmE>h<w=FD}rf&2t- Vgs</td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "            <td style=\"width:25%\" colspan=\"1\">试验编号</td>\n" +
                "            <td style=\"width:75%\" colspan=\"3\">①①①①①①</td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "            <td style=\"width:25%\">方案名称</td>\n" +
                "            <td colspan=\"3\">①①①①①①</td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "            <td style=\"width:25%\">发起人&nbsp;/&nbsp;职位</td>\n" +
                "            <td colspan=\"3\">汪在洪 / xxx</td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "            <td style=\"width:25%\">沟通对象&nbsp;/&nbsp;职位</td>\n" +
                "            <td colspan=\"3\">xx / xx</td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "            <td style=\"width:25%\">沟通日期</td>\n" +
                "            <td colspan=\"3\">2021-09-09</td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "            <td style=\"width:25%\">沟通主题</td>\n" +
                "            <td colspan=\"3\">xxxxxxxxx</td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "            <td style=\"width:25%\">沟通内容</td>\n" +
                "            <td colspan=\"3\"><pre>xxxxxxxxxxxxxxxxxxxxxx</pre></td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "            <td colspan=\"2\" style=\"width:50%\">姓名(签字)：</td>\n" +
                "            <td colspan=\"2\">日期：</td>\n" +
                "          </tr>\n" +
                "        </table>";

        int indexOf = html.indexOf("<td style=\"width:75%\" colspan=\"3\">");
        String substring = html.substring(indexOf);

        int i = substring.indexOf("</td>");
        String substring1 = substring.substring(0, i);
        System.out.println(substring1.substring(substring1.indexOf(">") + 1,i));


        String replace = html.replace(substring1.substring(substring1.indexOf(">\\") + 1, i), "html");
        System.out.println(replace);


    }

}
