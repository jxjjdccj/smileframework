/**   
 * @Title: ResourceUtils.java 
 * @Package: org.smileframework.common.spring 
 * @author Smile  
 * @date 2015-8-28 下午3:18:07 
 * @version 1.0.0 
 */
package org.smileframework.common.spring;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.LocalizedResourceHelper;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.FileCopyUtils;

/**
 * 文件资源访问公共类 <br/>
 * 原始是通过JDK 的 I/O处理类（File）完成，缺点：过于底层，复杂，容易发生错误<br/>
 * Spring 提供了许多方便易用的资源操作工具类,优点：降低复杂度，通用性
 * 
 * FileSystemResource 
 * ClassPathResource 
 * ServletContextResource 
 * UrlResource 
 * 
 * ResourceUtils
 * LocalizedResourceHelper 
 * FileCopyUtils
 * PropertiesLoaderUtils
 * EncodedResource
 * 
 * WebApplicationContextUtils 
 * WebUtils
 * 
 * 
 * 
 * @Description
 * @author Smile
 * @date 2015-8-28 下午3:18:07
 * @version V1.0.0
 */
public class ResourceUtils {

	/**
	 * @Description
	 * @author Smile
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String filePath = "D:/masterSpring/chapter23/webapp/WEB-INF/classes/conf/file1.txt";
			/***************************************************************************************************/
			// ① 使用系统文件路径方式加载文件
			Resource res1 = new FileSystemResource(filePath);
			// ② 使用类路径方式加载文件
			Resource res2 = new ClassPathResource("conf/file1.txt");
			InputStream ins1 = res1.getInputStream();
			InputStream ins2 = res2.getInputStream();

			System.out.println("res1:" + res1.getFilename());
			System.out.println("res2:" + res2.getFilename());
			/***************************************************************************************************/
			// 可以在不显式使用 Resource 实现类的情况下，仅根据带特殊前缀的资源地址直接加载文件资源
			File clsFile = org.springframework.util.ResourceUtils
					.getFile("classpath:conf/file1.txt");
			System.out.println(clsFile.isFile());

			String httpFilePath = "file:D:/masterSpring/chapter23/src/conf/file1.txt";
			File httpFile = org.springframework.util.ResourceUtils
					.getFile(httpFilePath);
			System.out.println(httpFile.isFile());
			/***************************************************************************************************/
			// 位于远程服务器（Web 服务器或 FTP 服务器）的文件资源
			Resource res3 = new UrlResource("conf/file1.txt");
			InputStream ins3 = res3.getInputStream();
			System.out.println("res3:" + res3.getFilename());

			/***************************************************************************************************/
			// 本地化文件资源
			LocalizedResourceHelper lrHalper = new LocalizedResourceHelper();
			// ① 获取对应美国的本地化文件资源
			Resource msg_us = lrHalper.findLocalizedResource("i18n/message",
					".properties", Locale.US);
			// ② 获取对应中国大陆的本地化文件资源
			Resource msg_cn = lrHalper.findLocalizedResource("i18n/message",
					".properties", Locale.CHINA);
			System.out.println("fileName(us):" + msg_us.getFilename());
			System.out.println("fileName(cn):" + msg_cn.getFilename());

			/***************************************************************************************************/
			// 文件操作
			Resource res = new ClassPathResource("conf/file1.txt");
			// ① 将文件内容拷贝到一个 byte[] 中
			byte[] fileData = FileCopyUtils.copyToByteArray(res.getFile());
			// ② 将文件内容拷贝到一个 String 中
			String fileStr = FileCopyUtils.copyToString(new FileReader(res
					.getFile()));
			// ③ 将文件内容拷贝到另一个目标文件
			FileCopyUtils.copy(res.getFile(), new File(res.getFile()
					.getParent() + "/file2.txt"));

			// ④ 将文件内容拷贝到一个输出流中
			OutputStream os = new ByteArrayOutputStream();
			FileCopyUtils.copy(res.getInputStream(), os);

			/***************************************************************************************************/
			// 属性文件操作
			// ① jdbc.properties 是位于类路径下的文件
			Properties props = PropertiesLoaderUtils
					.loadAllProperties("jdbc.properties");
			System.out.println(props.getProperty("jdbc.driverClassName"));
			
			
			/*WebApplicationContext wac = (WebApplicationContext)servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			WebApplicationContext wac =WebApplicationContextUtils.getWebApplicationContext(servletContext);
			WebApplicationContext wac =WebApplicationContextUtils.getRequiredWebApplicationContext(ServletContext sc);
			
			
			new CookieGenerator().addCookie(response, cookieValue);*/
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
