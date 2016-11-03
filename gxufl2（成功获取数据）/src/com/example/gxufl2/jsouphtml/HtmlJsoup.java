package com.example.gxufl2.jsouphtml;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlJsoup {

	StringBuilder htmlAy=new StringBuilder();//保存解析后的文字
	
	/**
	 * 解析HTML 返回登录后的状态！
	 * @param html
	 * @return String 信息
	 */
	public String htmlAnalysis(String html){
		Document doc = Jsoup.parse(html);
		Elements linkStrs=doc.getElementsByTag("span");
		for(Element linkStr:linkStrs){  
        	/**/
        	//
            //text()得到文本值		attr(String key)  获得元素的数据	 getElementsByTag:通过标签获得元素
            String title=linkStr.getElementsByTag("span").text();
            htmlAy.append(title);
            System.out.println("标题:"+title);  
            
        } 
		return htmlAy.toString();
		
	}
}
