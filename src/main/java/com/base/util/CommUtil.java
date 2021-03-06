package com.base.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


public class CommUtil {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public static String first2low(String str) {
		String s = "";
		s = str.substring(0, 1).toLowerCase() + str.substring(1);
		return s;
	}

	public static String first2upper(String str) {
		String s = "";
		s = str.substring(0, 1).toUpperCase() + str.substring(1);
		return s;
	}
	
	public static List<String> splitJing(String str) {
		if(isNotNull(str)) {
			String[] split = str.split("#");
			List<String> list = new ArrayList<String>();
			for (String s : split) {
				if(isNotNull(s))
				list.add(s);
			}
			return list;
		}
		return null;
	}



	/**
	 * 得到当前session attribute value
	 * @return
	 */
	public static Object  getSessionAttr(String key) {
		return WebUtils.getSessionValue(key);
	}

	public static String getRandomString(int length) { //length表示生成字符串的长度
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
	    Random random = new Random();   
	    StringBuilder sb = new StringBuilder();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	 }   

	
	public static List<String> str2list(String s) throws IOException {
		List<String> list = new ArrayList();
		if ((s != null) && (!s.equals(""))) {
			StringReader fr = new StringReader(s);
			BufferedReader br = new BufferedReader(fr);
			String aline = "";
			while ((aline = br.readLine()) != null) {
				list.add(aline);
			}
		}
		return list;
	}

	public static Date formatDate(String s) {
		Date d = null;
		try {
			d = dateFormat.parse(s);
		} catch (Exception localException) {
		}
		return d;
	}

	public static Date formatDate(String s, String format) {
		Date d = null;
		try {
			SimpleDateFormat dFormat = new SimpleDateFormat(format);
			d = dFormat.parse(s);
		} catch (Exception localException) {
		}
		return d;
	}

	public static String formatTime(String format, Object v) {
		if (v == null) {
			return null;
		}
		if (v.equals("")) {
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(v);
	}

	public static String formatLongDate(Object v) {
		if ((v == null) || (v.equals(""))) {
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(v);
	}
	
	
	public static String formatMiddleDate(Object v) {
		if ((v == null) || (v.equals(""))) {
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return df.format(v);
	}

	public static String formatShortDate(Object v) {
		if (v == null) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (v instanceof String) {
			try {
				v = df.parse((String) v);
			} catch (ParseException e) {
			}
		}
		return df.format(v);
	}

	public static String decode(String s) {
		String ret = s;
		try {
			ret = URLDecoder.decode(s.trim(), "UTF-8");
		} catch (Exception localException) {
		}
		return ret;
	}

	public static String encode(String s) {
		String ret = s;
		try {
			ret = URLEncoder.encode(s.trim(), "UTF-8");
			ret = URLEncoder.encode(ret, "UTF-8");
		} catch (Exception localException) {
		}
		return ret;
	}

	public static String convert(String str, String coding) {
		String newStr = "";
		if (str != null) {
			try {
				newStr = new String(str.getBytes("ISO-8859-1"), coding);
			} catch (Exception e) {
				return newStr;
			}
		}
		return newStr;
	}

	public static Map saveFileToServerWithThread(HttpServletRequest request, String imgFile, String saveFilePathName, String saveFileName, String[] extendes) throws IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile(imgFile);
		Map<String,Object> map = new HashMap<String,Object>();
		if ((file != null) && (!file.isEmpty())) {
			String extend = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
			String originalFileName=file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".") );
			if ((saveFileName == null) || (saveFileName.trim().equals(""))) {
				saveFileName = UUID.randomUUID().toString() + "." + extend;
			}
			if (saveFileName.lastIndexOf(".") < 0) {
				saveFileName = saveFileName + "." + extend;
			}
			float fileSize = Float.valueOf((float) file.getSize()).floatValue();
			List<String> errors = new ArrayList<String>();
			boolean flag = true;
			if (extendes != null) {
				for (String s : extendes) {
					if (extend.toLowerCase().equals(s)) {
						flag = true;
					}
				}
			}
			if (flag) {
				
				String fileDestPath = saveFilePathName+ File.separator + saveFileName;
				if (!new File(saveFilePathName).exists()) {
					new File(saveFilePathName).mkdirs();
					}
				//保存
				file.transferTo(new File(fileDestPath));
			
			
				if (isImg(extend)) {
					File img = new File(fileDestPath);
					try {
						BufferedImage bis = ImageIO.read(img);
						int w = bis.getWidth();
						int h = bis.getHeight();
						map.put("width", Integer.valueOf(w));
						map.put("height", Integer.valueOf(h));
					} catch (Exception localException) {
					}
				}
				map.put("mime", extend);
				map.put("fileName", saveFileName);
				map.put("fileSize", Float.valueOf(fileSize));
				map.put("error", errors);
				map.put("oldName", originalFileName);
				map.put("fileDestPath", fileDestPath);
			} else {
				errors.add("不允许的扩展名");
			}
		} else {
			map.put("width", Integer.valueOf(0));
			map.put("height", Integer.valueOf(0));
			map.put("mime", "");
			map.put("fileName", "");
			map.put("fileSize", Float.valueOf(0.0F));
			map.put("oldName", "");
		}
		return map;
	}

	
	public static Map saveFileToServer(HttpServletRequest request, String filePath, String saveFilePathName, String saveFileName, String[] extendes) throws IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile(filePath);
		Map<String,Object> map = new HashMap<String,Object>();
		if ((file != null) && (!file.isEmpty())) {
			String extend = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
			String originalFileName=file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".") );
			if ((saveFileName == null) || (saveFileName.trim().equals(""))) {
				saveFileName = UUID.randomUUID().toString() + "." + extend;
			}
			if (saveFileName.lastIndexOf(".") < 0) {
				saveFileName = saveFileName + "." + extend;
			}
			float fileSize = Float.valueOf((float) file.getSize()).floatValue();
			List<String> errors = new ArrayList<String>();
			boolean flag = true;
			if (extendes != null) {
				for (String s : extendes) {
					if (extend.toLowerCase().equals(s)) {
						flag = true;
					}
				}
			}
			if (flag) {
				File path = new File(saveFilePathName+ File.separator + saveFileName);
				if (!path.exists()) {
					path.mkdirs();
				}
				file.transferTo(path);
				
				/*DataOutputStream out = new DataOutputStream(new FileOutputStream(saveFilePathName + File.separator + saveFileName));
				InputStream is = null;
				try {
					is = file.getInputStream();
					int size = (int) fileSize;
					byte[] buffer = new byte[size];
					while (is.read(buffer) > 0) {
						out.write(buffer);
					}
				} catch (IOException exception) {
					exception.printStackTrace();
				} finally {
					if (is != null) {
						is.close();
					}
					if (out != null) {
						out.close();
					}
				}*/
				if (isImg(extend)) {
					File img = new File(saveFilePathName + File.separator + saveFileName);
					try {
						BufferedImage bis = ImageIO.read(img);
						int w = bis.getWidth();
						int h = bis.getHeight();
						map.put("width", Integer.valueOf(w));
						map.put("height", Integer.valueOf(h));
					} catch (Exception localException) {
					}
				}
				map.put("mime", extend);
				map.put("fileName", saveFileName);
				map.put("fileSize", Float.valueOf(fileSize));
				map.put("error", errors);
				map.put("oldName", originalFileName);
			} else {
				errors.add("不允许的扩展名");
			}
		} else {
			map.put("width", Integer.valueOf(0));
			map.put("height", Integer.valueOf(0));
			map.put("mime", "");
			map.put("fileName", "");
			map.put("fileSize", Float.valueOf(0.0F));
			map.put("oldName", "");
		}
		return map;
	}
	public static boolean isImg(String extend) {
		boolean ret = false;
		List<String> list = new ArrayList<String>();
		list.add("jpg");
		list.add("jpeg");
		list.add("bmp");
		list.add("gif");
		list.add("png");
		list.add("tif");
		for (String s : list) {
			if (s.equals(extend)) {
				ret = true;
			}
		}
		return ret;
	}


	
	

	
	
	
	public static Map drawImageWithText(String outPath,String saveFileName, String text, String markContentColor, Font font) {
		FontMetrics metrics = new FontMetrics(font){};
		Rectangle2D bounds = metrics.getStringBounds(text, null);
		int ascent=metrics.getAscent();
		int width = (int) bounds.getWidth()+2;
		int height = (int) bounds.getHeight()+3;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g = image.createGraphics();
		image = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT); 
		  g.dispose(); 
		  // 创建新图 
		  g = image.createGraphics(); 
		  // 设置“抗锯齿”的属性  
		  g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		  g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		  
//		  g.setRenderingHint(SunHints.KEY_ANTIALIASING, SunHints.VALUE_ANTIALIAS_OFF);
//	      g.setRenderingHint(SunHints.KEY_TEXT_ANTIALIASING, SunHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
//	      g.setRenderingHint(SunHints.KEY_STROKE_CONTROL, SunHints.VALUE_STROKE_DEFAULT);
//	      g.setRenderingHint(SunHints.KEY_TEXT_ANTIALIAS_LCD_CONTRAST, 140);
//	      g.setRenderingHint(SunHints.KEY_FRACTIONALMETRICS, SunHints.VALUE_FRACTIONALMETRICS_OFF);
//	      g.setRenderingHint(SunHints.KEY_RENDERING, SunHints.VALUE_RENDER_DEFAULT);
		if (font == null) {
			font = new Font("黑体", 1, 14);
			g.setFont(font);
		} else {
			g.setFont(font);
		}
		g.setColor(getColor(markContentColor));
		int left = 1;
		int top = ascent;
		
		g.drawString(text, left, top);
		g.dispose();
		Map map = new HashMap(); 
		if ((saveFileName == null) || (saveFileName.trim().equals(""))) {
			saveFileName = UUID.randomUUID().toString() + ".png" ;
		}
		if (saveFileName.lastIndexOf(".") < 0) {
			saveFileName = saveFileName + ".png" ;
		}
		try {
			File target= new File(outPath+File.separator+saveFileName);
			
			if (!target.exists()) {
				target.mkdirs();
			}
			  // 生成图片为PNG  
	        ImageIO.write(image, "png",target);  
	        
			BufferedImage bis = image;
			int w = bis.getWidth();
			int h = bis.getHeight();
			map.put("width", Integer.valueOf(w));
			map.put("height", Integer.valueOf(h));
			map.put("mime", "png");
			map.put("fileName", saveFileName);
			map.put("fileSize", Float.parseFloat(target.length()+""));
			map.put("error", false);
			map.put("oldName", saveFileName);   
		}catch (Exception e) {
			return map;
		}
		return map;
	}
	
	
	
	//返回占位符的长度。
    public static int formatLength( String s){
           int length = 0 ;
           for(int t = 0; t < s.length() ; t++){
               if( s.charAt(t)> 255){
                   length = length + 2;
               }else{
                   length = length + 1 ;
               }
           }
           System.out.println(length);
           return length;
    }
	
	
	
	public static boolean createFolder(String folderPath) {
		boolean ret = true;
		try {
			File myFilePath = new File(folderPath);
			if ((!myFilePath.exists()) && (!myFilePath.isDirectory())) {
				ret = myFilePath.mkdirs();
				if (!ret) {
					System.out.println("创建文件夹出错");
				}
			}
		} catch (Exception e) {
			System.out.println("创建文件夹出错");
			ret = false;
		}
		return ret;
	}

	public static List toRowChildList(List list, int perNum) {
		List l = new ArrayList();
		if (list == null) {
			return l;
		}
		for (int i = 0; i < list.size(); i += perNum) {
			List cList = new ArrayList();
			for (int j = 0; j < perNum; j++) {
				if (i + j < list.size()) {
					cList.add(list.get(i + j));
				}
			}
			l.add(cList);
		}
		return l;
	}

	public static List copyList(List list, int begin, int end) {
		List l = new ArrayList();
		if (list == null) {
			return l;
		}
		if (end > list.size()) {
			end = list.size();
		}
		for (int i = begin; i < end; i++) {
			l.add(list.get(i));
		}
		return l;
	}

	public static boolean isNotNull(Object obj) {
		if ((obj != null) && (!obj.toString().trim().equals(""))) {
			return true;
		}
		return false;
	}

	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPath);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错 ");
			e.printStackTrace();
		}
	}

	public static boolean deleteFolder(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (file.isFile()) {
			return deleteFile(path);
		}
		return deleteDirectory(path);
	}

	public static boolean deleteFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if ((file.isFile()) && (file.exists())) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	public static boolean deleteDirectory(String path) {
		if (!path.endsWith(File.separator)) {
			path = path + File.separator;
		}
		File dirFile = new File(path);
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			return false;
		}
		boolean flag = true;

		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			} else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}
		if (!flag) {
			return false;
		}
		if (dirFile.delete()) {
			return true;
		}
		return false;
	}

	public static char randomChar() {
		char[] chars = { 'a', 'A', 'b', 'B', 'c', 'C', 'd', 'D', 'e', 'E', 'f', 'F', 'g', 'G', 'h', 'H', 'i', 'I', 'j', 'J', 'k', 'K', 'l', 'L', 'm', 'M', 'n', 'N', 'o', 'O', 'p',
				'P', 'q', 'Q', 'r', 'R', 's', 'S', 't', 'T', 'u', 'U', 'v', 'V', 'w', 'W', 'x', 'X', 'y', 'Y', 'z', 'Z' };
		int index = (int) (Math.random() * 52.0D) - 1;
		if (index < 0) {
			index = 0;
		}
		return chars[index];
	}

	public static String[] splitByChar(String s, String c) {
		if(s==null) return null;
		String[] list = s.split(c);
		return list;
	}

	public static Object requestByParam(HttpServletRequest request, String param) {
		if (!request.getParameter(param).equals("")) {
			return request.getParameter(param);
		}
		return null;
	}

	public static String substring(String s, int maxLength) {
	  if(isNotNull(s)){
		s=HTMLFilter.delHTMLTag(s);//过滤HTML
		if (!StringUtils.hasLength(s)) {
			return s;
		}
		if (s.length() <= maxLength) {
			return s;
		}
		return s.substring(0, maxLength) + "...";
		}
	  return null;
	}
	

	public static String substringfrom(String s, String from) {
		if (s.indexOf(from) < 0) {
			return "";
		}
		return s.substring(s.indexOf(from) + from.length());
	}

	public static int null2Int(Object s) {
		int v = 0;
		if (s != null) {
			try {
				v = Integer.parseInt(s.toString());
			} catch (Exception localException) {
			}
		}
		return v;
	}

	public static float null2Float(Object s) {
		float v = 0.0F;
		if (s != null) {
			try {
				v = Float.parseFloat(s.toString());
			} catch (Exception localException) {
			}
		}
		return v;
	}

	public static double null2Double(Object s) {
		double v = 0.0D;
		if (s != null) {
			try {
				v = Double.parseDouble(null2String(s));
			} catch (Exception localException) {
			}
		}
		return v;
	}

	public static boolean null2Boolean(Object s) {
		boolean v = false;
		if (s != null) {
			try {
				v = Boolean.parseBoolean(s.toString());
			} catch (Exception localException) {
			}
		}
		return v;
	}

	public static String null2String(Object s) {
		return s == null ? "" : s.toString().trim();
	}

	public static Long null2Long(Object s) {
		Long v = Long.valueOf(-1L);
		if (s != null) {
			try {
				v = Long.valueOf(Long.parseLong(s.toString()));
			} catch (Exception localException) {
			}
		}
		return v;
	}

	public static String getTimeInfo(long time) {
		int hour = (int) time / 3600000;
		long balance = time - hour * 1000 * 60 * 60;
		int minute = (int) balance / 60000;
		balance -= minute * 1000 * 60;
		int seconds = (int) balance / 1000;
		String ret = "";
		if (hour > 0) {
			ret = ret + hour + "小时";
		}
		if (minute > 0) {
			ret = ret + minute + "分";
		} else if ((minute <= 0) && (seconds > 0)) {
			ret = ret + "零";
		}
		if (seconds > 0) {
			ret = ret + seconds + "秒";
		}
		return ret;
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static int indexOf(String s, String sub) {
		return s.trim().indexOf(sub.trim());
	}

	public static Map cal_time_space(Date begin, Date end) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long l = end.getTime() - begin.getTime();
		long day = l / 86400000L;
		long hour = l / 3600000L - day * 24L;
		long min = l / 60000L - day * 24L * 60L - hour * 60L;
		long second = l / 1000L - day * 24L * 60L * 60L - hour * 60L * 60L - min * 60L;
		Map map = new HashMap();
		map.put("day", Long.valueOf(day));
		map.put("hour", Long.valueOf(hour));
		map.put("min", Long.valueOf(min));
		map.put("second", Long.valueOf(second));
		return map;
	}

	public static final String randomString(int length) {
		char[] numbersAndLetters = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		if (length < 1) {
			return "";
		}
		Random randGen = new Random();
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
		}
		return new String(randBuffer);
	}

	public static final String randomInt(int length) {
		if (length < 1) {
			return null;
		}
		Random randGen = new Random();
		char[] numbersAndLetters = "0123456789".toCharArray();

		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(10)];
		}
		return new String(randBuffer);
	}

	public static long getDateDistance(String time1, String time2) {
		long quot = 0L;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000L / 60L / 60L / 24L;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return quot;
	}

	public static double div(Object a, Object b) {
		double ret = 0.0D;
		if ((!null2String(a).equals("")) && (!null2String(b).equals(""))) {
			BigDecimal e = new BigDecimal(null2String(a));
			BigDecimal f = new BigDecimal(null2String(b));
			if (null2Double(f) > 0.0D) {
				ret = e.divide(f, 3, 1).doubleValue();
			}
		}
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(ret)).doubleValue();
	}

	public static double subtract(Object a, Object b) {
		double ret = 0.0D;
		BigDecimal e = new BigDecimal(null2Double(a));
		BigDecimal f = new BigDecimal(null2Double(b));
		ret = e.subtract(f).doubleValue();
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(ret)).doubleValue();
	}

	public static double add(Object a, Object b) {
		double ret = 0.0D;
		BigDecimal e = new BigDecimal(null2Double(a));
		BigDecimal f = new BigDecimal(null2Double(b));
		ret = e.add(f).doubleValue();
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(ret)).doubleValue();
	}

	public static double mul(Object a, Object b) {
		BigDecimal e = new BigDecimal(null2Double(a));
		BigDecimal f = new BigDecimal(null2Double(b));
		double ret = e.multiply(f).doubleValue();
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(ret)).doubleValue();
	}

	public static double formatMoney(Object money) {
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(money)).doubleValue();
	}

	public static int M2byte(float m) {
		float a = m * 1024.0F * 1024.0F;
		return (int) a;
	}

	public static boolean convertIntToBoolean(int intValue) {
		return intValue != 0;
	}

	public static String getURL(HttpServletRequest request) {
		String contextPath = request.getContextPath().equals("/") ? "" : request.getContextPath();
		String url = "http://" + request.getServerName();
		if (null2Int(Integer.valueOf(request.getServerPort())) != 80) {
			url = url + ":" + null2Int(Integer.valueOf(request.getServerPort())) + contextPath;
		} else {
			url = url + contextPath;
		}
		return url;
	}

	private static final Whitelist user_content_filter = Whitelist.relaxed();

	static {
		user_content_filter.addTags(new String[] { "embed", "object", "param", "span", "div", "font" });
		user_content_filter.addAttributes(":all", new String[] { "style", "class", "id", "name" });
		user_content_filter.addAttributes("object", new String[] { "width", "height", "classid", "codebase" });
		user_content_filter.addAttributes("param", new String[] { "name", "value" });
		user_content_filter.addAttributes("embed", new String[] { "src", "quality", "width", "height", "allowFullScreen", "allowScriptAccess", "flashvars", "name", "type",
				"pluginspage" });
	}

	public static String filterHTML(String content) {
		Whitelist whiteList = new Whitelist();
		String s = Jsoup.clean(content, user_content_filter);
		return s;
	}

	public static int parseDate(String type, Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (type.equals("y")) {
			return cal.get(1);
		}
		if (type.equals("M")) {
			return cal.get(2) + 1;
		}
		if (type.equals("d")) {
			return cal.get(5);
		}
		if (type.equals("H")) {
			return cal.get(11);
		}
		if (type.equals("m")) {
			return cal.get(12);
		}
		if (type.equals("s")) {
			return cal.get(13);
		}
		return 0;
	}

	public static int[] readImgWH(String imgurl) {
		boolean b = false;
		try {
			URL url = new URL(imgurl);
			BufferedInputStream bis = new BufferedInputStream(url.openStream());
			byte bytes[] = new byte[100];
			OutputStream bos = new FileOutputStream(new File("C:\\thetempimg.gif"));
			int len;
			while ((len = bis.read(bytes)) > 0)
				bos.write(bytes, 0, len);
			bis.close();
			bos.flush();
			bos.close();
			b = true;
		} catch (Exception e) {
			b = false;
		}
		int a[] = new int[2];
		if (b) {
			File file = new File("C:\\thetempimg.gif");
			BufferedImage bi = null;
			boolean imgwrong = false;
			try {
				bi = ImageIO.read(file);
				try {
					int i = bi.getType();
					imgwrong = true;
				} catch (Exception e) {
					imgwrong = false;
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			if (imgwrong) {
				a[0] = bi.getWidth();
				a[1] = bi.getHeight();
			} else {
				a = null;
			}
			file.delete();
		} else {
			a = null;
		}
		return a;
	}

	public static boolean fileExist(String path) {
		File file = new File(path);
		return file.exists();
	}

	public static int splitLength(String s, String c) {
		int v = 0;
		if (!s.trim().equals("")) {
			v = s.split(c).length;
		}
		return v;
	}

	static int totalFolder = 0;
	static int totalFile = 0;

	public static double fileSize(File folder) {
		totalFolder += 1;

		long foldersize = 0L;
		File[] filelist = folder.listFiles();
		for (int i = 0; i < filelist.length; i++) {
			if (filelist[i].isDirectory()) {
				foldersize = (long) (foldersize + fileSize(filelist[i]));
			} else {
				totalFile += 1;
				foldersize += filelist[i].length();
			}
		}
		return div(Long.valueOf(foldersize), Integer.valueOf(1024));
	}

	public static int fileCount(File file) {
		if (file == null) {
			return 0;
		}
		if (!file.isDirectory()) {
			return 1;
		}
		int fileCount = 0;
		File[] files = file.listFiles();
		for (File f : files) {
			if (f.isFile()) {
				fileCount++;
			} else if (f.isDirectory()) {
				fileCount++;
				fileCount += fileCount(file);
			}
		}
		return fileCount;
	}

	public static String get_all_url(HttpServletRequest request) {
		String query_url = request.getRequestURI();
		if ((request.getQueryString() != null) && (!request.getQueryString().equals(""))) {
			query_url = query_url + "?" + request.getQueryString();
		}
		return query_url;
	}

	public static Color getColor(String color) {
		if (color.charAt(0) == '#') {
			color = color.substring(1);
		}
		if (color.length() != 6) {
			return null;
		}
		try {
			int r = Integer.parseInt(color.substring(0, 2), 16);
			int g = Integer.parseInt(color.substring(2, 4), 16);
			int b = Integer.parseInt(color.substring(4), 16);
			return new Color(r, g, b);
		} catch (NumberFormatException nfe) {
		}
		return null;
	}

	public static Set<Integer> randomInt(int a, int length) {
		Set<Integer> list = new TreeSet();
		int size = length;
		if (length > a) {
			size = a;
		}
		while (list.size() < size) {
			Random random = new Random();
			int b = random.nextInt(a);
			list.add(Integer.valueOf(b));
		}
		return list;
	}

	public static Double formatDouble(Object obj, int len) {
		Double ret = Double.valueOf(0.0D);
		String format = "0.0";
		for (int i = 1; i < len; i++) {
			format = format + "0";
		}
		DecimalFormat df = new DecimalFormat(format);
		return Double.valueOf(df.format(obj));
	}

	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if ((ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) || (ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS)
				|| (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) || (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION)
				|| (ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION) || (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)) {
			return true;
		}
		return false;
	}

	public static boolean isMessyCode(String strName) {
		Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
		Matcher m = p.matcher(strName);
		String after = m.replaceAll("");
		String temp = after.replaceAll("\\p{P}", "");
		char[] ch = temp.trim().toCharArray();
		float chLength = ch.length;
		float count = 0.0F;
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (!Character.isLetterOrDigit(c)) {
				if (!isChinese(c)) {
					count += 1.0F;
					System.out.print(c);
				}
			}
		}
		float result = count / chLength;
		if (result > 0.4D) {
			return true;
		}
		return false;
	}

	public static String trimSpaces(String IP) {
		while (IP.startsWith(" ")) {
			IP = IP.substring(1, IP.length()).trim();
		}
		while (IP.endsWith(" ")) {
			IP = IP.substring(0, IP.length() - 1).trim();
		}
		return IP;
	}

	public static boolean isIp(String IP) {
		boolean b = false;
		IP = trimSpaces(IP);
		if (IP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
			String[] s = IP.split("\\.");
			if ((Integer.parseInt(s[0]) < 255) && (Integer.parseInt(s[1]) < 255) && (Integer.parseInt(s[2]) < 255) && (Integer.parseInt(s[3]) < 255)) {
				b = true;
			}
		}
		return b;
	}

	public static String generic_domain(HttpServletRequest request) {
		String system_domain = "localhost";
		String serverName = request.getServerName();
		if (isIp(serverName)) {
			system_domain = serverName;
		} else {
			system_domain = serverName.substring(serverName.indexOf(".") + 1);
		}
		return system_domain;
	}

	public static void copyProperties(Object source, Object target) {
		try {
			BeanUtils.copyProperties(source, target);
		} catch (Exception e) {
			System.err.println("属性复制不匹配");
		}
	}
    //去除所有空格
	public static String trimAll(String str) {
		if(isNotNull(str))
			str=str.replaceAll("\\s*", "");
		return str;
	}


	/**
	 * 转化时间为多少分钟前、小时前、天前、月前
	 * @param date
	 * @return
	 */
	public static String timeTransform(Date date) {
		return TimeTransformUtil.format(date);
	}
	public static String timeTransform(String date) {
		return TimeTransformUtil.format(new Date(date));
	}
	
	/**
	 * 判断内容是否为空
	 * @param content
	 * @return
	 */
	public static String trimEvalueSpaces(String content) {
		if(content!=null){
			while (content.startsWith(" ")) {
				content = content.substring(1, content.length()).trim();
			}
			while (content.endsWith(" ")) {
				content = content.substring(0, content.length() - 1).trim();
			}
			return content;
		}else{
			return "";
		}
	}
	
}
