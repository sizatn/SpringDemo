package com.sizatn.springdemo.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtils {
	
	private static final Log log = LogFactory.getLog(ImageUtils.class);
	
	/**
	 * 根据图片URL，获取其内容
	 * @param imageUrl
	 * @return
	 * @throws Exception
	 */
	public static byte[] getImageBytes1(String imageUrl) throws Exception {
		byte[] bytes = null;
		InputStream is = null;
		try {
			URL fileUrl = new URL(imageUrl);
			URLConnection urlc = fileUrl.openConnection();
			int contentLength = urlc.getContentLength();
			is = new BufferedInputStream(urlc.getInputStream());
			bytes = new byte[contentLength];
			int bytesRead = 0;
			int offset = 0;
			while (offset < contentLength) {
				bytesRead = is.read(bytes, offset, contentLength - offset);
				if (bytesRead == -1) {
					break;
				}
				offset += bytesRead;
			}
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return bytes;
	}	
	
	/**
	 * 改变图片分辩率
	 * @param bytes
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception
	 */
	public static byte[] changeSize1(byte[] bytes, int width, int height) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		bais.close();
		return ImageUtils.changeSize(bais, width, height);
	}
	
	/**
	 * 改变图片分辨率
	 * @param is
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception
	 */
	public static byte[] changeSize2(InputStream is, int width, int height) throws Exception {
		byte[] result = null;
		ByteArrayOutputStream bos = null;
		try {
			Image src = javax.imageio.ImageIO.read(is);
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = bi.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.dispose();
			bos = new ByteArrayOutputStream();
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			encoder.encode(bi);
			result = bos.toByteArray();
		} catch (Exception ex) {
			log.error("缩小文件时出错：" + ex.getMessage(), ex);
			throw ex;
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					log.error(e1);
				}
			}
		}
		return result;
	}
	
	/**
     * 图片文件压缩
     * @param sourceImageFile 源图片
     * @param isOverlay 是否覆盖
     * @param newFileName 如果覆盖，新文件名称，如果isOverlay为true,则该参数可以为空
     * @param imageQuality 0.0-1.0 setting of desired quality level.
     * @return 成功：true，失败：false
     */
    public static boolean compress(File sourceImageFile,boolean isOverlay,String newFileName,float imageQuality){
		boolean isSuccess = true;
		BufferedImage src = null;
		FileOutputStream out =  null;
		JPEGImageEncoder encoder = null;
		JPEGEncodeParam  param   = null;
        
		try{
			src= ImageIO.read(sourceImageFile);
			if(isOverlay){
				out= new FileOutputStream(sourceImageFile);
			}else{
				out= new FileOutputStream(newFileName);
			}
			encoder= JPEGCodec.createJPEGEncoder(out);
			param= encoder.getDefaultJPEGEncodeParam(src);            
			param.setQuality(imageQuality, false);
			encoder.setJPEGEncodeParam(param);            
			encoder.encode(src);    
		} catch (IOException ex){
			isSuccess = false;
			log.error("compress error:"+ex.getMessage());
		}
		finally{		
			if(out!=null){
				try{
					out.close();
				}catch(Exception ex1){}
			}	
			out = null;
			encoder = null;
			param   = null;
			src     = null;
		}
		return isSuccess;
    }
    
	
	/**
	 * 图片压缩
	 * @param url
	 * @param imageQuality
	 * @return 压缩后的图片字节数组
	 */
	public static byte[] compress(URL url,float imageQuality) throws IOException{
		byte[] bytes = null;
		BufferedImage src = null;
		ByteArrayOutputStream out =  null;
		JPEGImageEncoder encoder = null;
		JPEGEncodeParam  param   = null;
		try{
			src= ImageIO.read(url);		
			out= new ByteArrayOutputStream();			
			encoder= JPEGCodec.createJPEGEncoder(out);
			param= encoder.getDefaultJPEGEncodeParam(src);            
			param.setQuality(imageQuality, false);
			encoder.setJPEGEncodeParam(param);            
			encoder.encode(src);   
			bytes = out.toByteArray(); 
		} catch (IOException ex){
			log.error("compress error:"+ex.getMessage());
			throw ex;
		}
		finally{		
			if(out!=null){
				try{
					out.close();
				}catch(Exception ex1){}
			}	
			out = null;
			encoder = null;
			param   = null;
			src     = null;
		}
		return bytes;
	}    
	
	/**
	 * 图片压缩
	 * @param url
	 * @param imageQuality
	 * @return 压缩后的图片字节数组
	 */
	public static byte[] compress(byte[] imgByte,float imageQuality) throws IOException{
		byte[] bytes = null;
		BufferedImage src = null;
		ByteArrayOutputStream out =  null;
		JPEGImageEncoder encoder = null;
		JPEGEncodeParam  param   = null;
		try{
			src= ImageIO.read(new ByteArrayInputStream(imgByte));		
			out= new ByteArrayOutputStream();			
			encoder= JPEGCodec.createJPEGEncoder(out);
			param= encoder.getDefaultJPEGEncodeParam(src);            
			param.setQuality(imageQuality, false);
			encoder.setJPEGEncodeParam(param);            
			encoder.encode(src);   
			bytes = out.toByteArray(); 
		} catch (IOException ex){
			log.error("compress error:"+ex.getMessage());
			throw ex;
		}
		finally{		
			if(out!=null){
				try{
					out.close();
				}catch(Exception ex1){}
			}	
			out = null;
			encoder = null;
			param   = null;
			src     = null;
		}
		return bytes;
	}    	

	/**
	 * 图片剪切
	 * @param image
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @return
	 */
    public static byte[] cut(Image image, int x, int y, int w, int h){
        //Image src = image;
        ByteArrayOutputStream baos = null;
        javax.imageio.stream.ImageOutputStream ios = null;
        ImageWriter writer = null;
        byte[] result = null;
		Graphics graphics = null;
        try
        {           
            baos = new ByteArrayOutputStream();
            ios = ImageIO.createImageOutputStream(baos);
            Iterator iter = ImageIO.getImageWritersByMIMEType("image/jpeg");
            writer = iter.hasNext() ? (ImageWriter)iter.next() : null;
            writer.setOutput(ios);
            BufferedImage bufferedImage = new BufferedImage(w, h, 4);
            graphics = bufferedImage.getGraphics();
			graphics.drawImage(image, 0, 0, w, h, x, y, x + w, y + h, null);
            bufferedImage.flush();
            writer.write(new IIOImage(bufferedImage, null, null));
            result = baos.toByteArray();
        }
		catch(Exception e){
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}        
        finally{
        	if(graphics!=null){
        		try{
					graphics.dispose();
					graphics = null;
        		}
        		catch(Exception ex1){
        			ex1.printStackTrace();
        		}
        	}
            if(baos != null){
                try
                {
                    baos.close();
                    baos = null;
                }
                catch(IOException ex2)
                {
                    ex2.printStackTrace();
                }
            }
            if(ios != null){
                try
                {
                    ios.close();
                    ios = null;
                }
                catch(IOException ex3)
                {
                    ex3.printStackTrace();
                }
            }
            if(writer != null){
				writer.dispose();
				writer = null;         	
            }

        }
        
        return result;
    }
    
    public static Image cutImage(Image image, int x, int y, int w, int h){
    	Image imgResult = null;
		Graphics graphics = null;
        try{
            imgResult = new BufferedImage(w, h, 4);
            graphics = imgResult.getGraphics();
			graphics.drawImage(image, 0, 0, w, h, x, y, x + w, y + h, null);
			imgResult.flush();
        }
		catch(Exception e){
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}        
        finally{
        	if(graphics!=null){
        		try{
					graphics.dispose();
					graphics = null;
        		}
        		catch(Exception ex1){
        			ex1.printStackTrace();
        		}
        	}

        }
        
        return imgResult;    	
    }
    
    /**
     * 将图片切分为指定的行数与列数
     * @param imgFile
     * @param row
     * @param col
     * @return
     * @throws Exception
     */
    public static List cut(File imgFile,int row,int col) throws Exception{
    	List imgList = new ArrayList();
		BufferedImage src= ImageIO.read(imgFile);
		int width = src.getWidth(null);
		int height = src.getHeight(null);	
		System.out.println("width = "+width+"\theight = "+height);
		int perRowHeight 	= (int)Math.floor((double)height/row);
		int perColWidth 	= (int)Math.floor((double)width/col);
		for(int i=0;i<row;i++){
			int y = i*perRowHeight;
			for(int j=0;j<col;j++){
				int x = j*perColWidth;
				System.out.println("x = "+x+"\t\ty = "+y);
				byte[] byteArr = ImageUtils.cut(src, x,y,perColWidth,perRowHeight);
				imgList.add(byteArr);				
			}
		}

		return imgList;
    }
    
    /**
     * 根据图片URL，获取其内容
     * @param imageUrl
     * @return
     * @throws Exception
     */
    public static byte[] getImageBytes(String imageUrl) throws Exception{
		byte[] bytes = null;
		InputStream is = null;
		try{
			URL fileUrl = new URL(imageUrl);
			URLConnection urlc = fileUrl.openConnection();
			//urlc.setConnectTimeout(timeout)
			int contentLength = urlc.getContentLength();
			is = new BufferedInputStream(urlc.getInputStream());			
			bytes = new byte[contentLength];
			int bytesRead = 0;
			int offset = 0;
			while(offset < contentLength){
				bytesRead = is.read(bytes,offset,contentLength-offset);
				if(bytesRead==-1) break;
				offset += bytesRead;
			}
		}
		catch(Exception ex){
			log.error(ex.getMessage(),ex);
			throw ex;
		}
		finally{
			if(is!=null){
				try{
					is.close();	
				}catch(Exception ex){}
			}
		}	
		return bytes;
    }
	
	/**
	 * 在图片文件左上角上打水印
	 * @param imgFile　图片文件
	 * @param waterMark 水印文字，
	 */
	public static void createWaterMark(File imgFile,String[] waterMarkArr) {
		FileOutputStream out = null;
		try {
			Image src = ImageIO.read(imgFile);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(Color.ORANGE);
			int fromY = 70,fontSize = 55;;
			if(width<1500){
				fromY = 30;
				fontSize = 20;
			}
			
			g.setFont(new Font("黑体", Font.BOLD, fontSize));
			
			int size = waterMarkArr.length;
			for(int i=0;i<size;i++){
				g.drawString(waterMarkArr[i], 20, fromY*(i+1));
			}
			
			g.dispose();
			out = new FileOutputStream(imgFile);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
		} catch (Exception e) {
			log.error("在文件："+imgFile.getAbsolutePath()+"上生成水印失败："+e.getMessage(),e);
		} finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e1) {
					log.error(e1);
				}
			}
		}
	}
	
	/**
	 * 在图片文件左上角上打水印
	 * @param imgFile　图片文件
	 * @param waterMark 水印文字
	 * @param fontSize 字体大小
	 * @param fontHeight 字体高度
	 */
	public static void createWaterMark(File imgFile,String[] waterMarkArr,int fontSize,int fontHeight) {
		FileOutputStream out = null;
		try {
			Image src = ImageIO.read(imgFile);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(Color.ORANGE);
			
			g.setFont(new Font("黑体", Font.BOLD, fontSize));
			
			int size = waterMarkArr.length;
			for(int i=0;i<size;i++){
				g.drawString(waterMarkArr[i], 20, fontHeight*(i+1));
			}
			
			g.dispose();
			out = new FileOutputStream(imgFile);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
		} catch (Exception e) {
			log.error("在文件："+imgFile.getAbsolutePath()+"上生成水印失败："+e.getMessage(),e);
		} finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e1) {
					log.error(e1);
				}
			}
		}
	}

	/**
	 * 在图片文件左上角上打水印
	 * @param imgFile　图片文件
	 * @param waterMark 水印文字
	 * @param fontSize 字体大小
	 * @param fontHeight 字体高度
	 * @param fontColor 字体颜色
	 * @param bgColor 背景色,如为null,则表示不需要背景
	 * @param leftMargin 左边界
	 */
	public static void createWaterMark(File imgFile,String[] waterMarkArr,int fontSize,int fontHeight,Color fontColor,Color bgColor,int leftMargin) {
		ImageUtils.createWaterMark(imgFile, waterMarkArr, fontSize, fontHeight, fontColor, bgColor, leftMargin, 0);
	}
	
	/**
	 * 在图片文件左上角上打水印
	 * @param imgFile　图片文件
	 * @param waterMark 水印文字
	 * @param fontSize 字体大小
	 * @param fontHeight 字体高度
	 * @param fontColor 字体颜色
	 * @param bgColor 背景色,如为null,则表示不需要背景
	 * @param leftMargin 左边界
	 * @param topMargin 上边界
	 */
	public static void createWaterMark(File imgFile,String[] waterMarkArr,int fontSize,int fontHeight,Color fontColor,Color bgColor,int leftMargin,int topMargin) {
		FileOutputStream out = null;
		try {
			Image src = ImageIO.read(imgFile);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			Font font = new Font("黑体", Font.BOLD, fontSize);			
			g.setFont(font);
			
			int size = waterMarkArr.length;
			for(int i=0;i<size;i++){
				if(bgColor!=null){
					g.setColor(bgColor);
					
					int bgWidth = 0;
					int len = waterMarkArr[i].length();
					for(int j=0;j<len;j++){
						char theChar = waterMarkArr[i].charAt(j);
						if((theChar>'0' && theChar<'9') || (theChar>'a' && theChar<'z') || (theChar>'A' && theChar<'Z') || (theChar==' ') || (theChar=='-') || (theChar==':')){
							bgWidth += Math.round(font.getSize()/3.0);
						}
						else{
							bgWidth += font.getSize()*1.2;
						}
					}
					//g.fillRect(leftMargin,topMargin+fontHeight*i,bgWidth+2,fontHeight-2);
					g.fillRect(leftMargin,topMargin+fontHeight*i,bgWidth+2,fontHeight);
				}				
				g.setColor(fontColor);
				g.drawString(waterMarkArr[i], leftMargin+2, topMargin+(fontHeight*(i+1)-4));
			}
			
			g.dispose();
			out = new FileOutputStream(imgFile);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
		} catch (Exception e) {
			log.error("在文件："+imgFile.getAbsolutePath()+"上生成水印失败："+e.getMessage(),e);
		} finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e1) {
					log.error(e1);
				}
			}
		}
	}	
	
	/**
	 * 在图片文件左上角上打水印
	 * @param Image 图片
	 * @param waterMark 水印文字
	 * @param fontSize 字体大小
	 * @param fontHeight 字体高度
	 * @param fontColor 字体颜色
	 * @param bgColor 背景色,如为null,则表示不需要背景
	 * @param leftMargin 左边界
	 * @return 新图片
	 */
	public static byte[] createWaterMark(Image imageSrc,String[] waterMarkArr,int fontSize,int fontHeight,Color fontColor,Color bgColor,int leftMargin,int topMargin) {
		byte[] result = null;
		ByteArrayOutputStream bos = null;
		try {
			//log.debug("imageSrc = "+imageSrc);
			int width = imageSrc.getWidth(null);
			int height = imageSrc.getHeight(null);
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(imageSrc, 0, 0, width, height, null);
			Font font = new Font("黑体", Font.BOLD, fontSize);			
			g.setFont(font);
			
			int size = waterMarkArr.length;
			for(int i=0;i<size;i++){
				if(bgColor!=null){
					g.setColor(bgColor);
					
					int bgWidth = 0;
					int len = waterMarkArr[i].length();
					for(int j=0;j<len;j++){
						char theChar = waterMarkArr[i].charAt(j);
						if((theChar>'0' && theChar<'9') || (theChar>'a' && theChar<'z') || (theChar>'A' && theChar<'Z') || (theChar==' ') || (theChar=='-') || (theChar==':')){
							bgWidth += Math.round(font.getSize()/3.0);
						}
						else{
							//bgWidth += font.getSize();
							bgWidth += font.getSize()*1.2;
						}
					}
					g.fillRect(leftMargin,topMargin+fontHeight*i+4,bgWidth,fontHeight-2);
				}				
				g.setColor(fontColor);
				g.drawString(waterMarkArr[i], leftMargin, topMargin+fontHeight*(i+1));
			}
			
			g.dispose();
			
			bos = new ByteArrayOutputStream(); 
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			encoder.encode(image);
			result = bos.toByteArray();	
		} catch (Exception e) {
			log.error("生成水印失败："+e.getMessage(),e);
		} finally{
			if(bos!=null){
				try {
					bos.close();
				} catch (IOException e1) {
					log.error(e1);
				}
			}
		}
		return result;
	}	
	
	public static byte[] createWaterMark(byte[] imgByte,String[] waterMarkArr,int fontSize,int fontHeight,Color fontColor,Color bgColor,int leftMargin) {
		byte[] result = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(imgByte,0,imgByte.length);
			Image imageSrc = javax.imageio.ImageIO.read(bis);
			result=  ImageUtils.createWaterMark(imageSrc, waterMarkArr, fontSize, fontHeight, fontColor, bgColor, leftMargin,0);			
		} catch (Exception e) {
			log.error(e);
		}
		return result;
	}
	
	public static byte[] createWaterMark(byte[] imgByte,String[] waterMarkArr,int fontSize,int fontHeight,Color fontColor,Color bgColor,int leftMargin,int topMargin) {
		byte[] result = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(imgByte,0,imgByte.length);
			Image imageSrc = javax.imageio.ImageIO.read(bis);
			result=  ImageUtils.createWaterMark(imageSrc, waterMarkArr, fontSize, fontHeight, fontColor, bgColor, leftMargin,topMargin);			
		} catch (Exception e) {
			log.error(e);
		}
		return result;
	}	
		
	/**
	 * 改变图片分辨率
	 * @param sourceImgFile
	 * @param targetImgFile
	 * @param width
	 * @param height
	 * @throws Exception
	 */
	public static void changeSize(String sourceImgFile,String targetImgFile,int width,int height) throws Exception{
		FileOutputStream fos = null;
		try{
			File sourceFile = new File(sourceImgFile);
			Image src = javax.imageio.ImageIO.read(sourceFile);
			BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB); 
			Graphics g = bi.createGraphics();   
			g.drawImage(src,0,0,width,height,null);
			g.dispose();
			fos = new FileOutputStream(targetImgFile); 
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
			encoder.encode(bi);	
		}catch(Exception ex){
			log.error("缩小文件："+sourceImgFile+"时出错："+ex.getMessage(),ex);
			throw ex;
		}finally{
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e1) {
					log.error(e1);
				}
			}			
		}
	}
	
	/**
	 * 改变图片文件分辨率
	 * @param sourceImgFile
	 * @param targetImgFile
	 * @param width
	 * @param height
	 * @throws Exception
	 */
	public static void changeSize(File sourceFile,int width,int height) throws Exception{
		FileOutputStream fos = null;
		try{
			Image src = javax.imageio.ImageIO.read(sourceFile);
			BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB); 
			Graphics g = bi.createGraphics();   
			g.drawImage(src,0,0,width,height,null);
			g.dispose();
			fos = new FileOutputStream(sourceFile); 
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
			encoder.encode(bi);	
		}catch(Exception ex){
			log.error("压缩文件："+sourceFile.getName()+"时出错："+ex.getMessage(),ex);
			throw ex;
		}finally{
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e1) {
					log.error(e1);
				}
			}			
		}
	}	
	
	/**
	 * 改变图片分辩率
	 * @param bytes
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception
	 */
	public static byte[] changeSize(byte[] bytes,int width,int height) throws Exception{
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		bais.close();	
		return ImageUtils.changeSize(bais, width, height);			
	}
	
	/**
	 * 改变图片分辨率
	 * @param is
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception
	 */
	public static byte[] changeSize(InputStream is,int width,int height) throws Exception{
		byte[] result = null;
		ByteArrayOutputStream bos = null;
		try{
			Image src = javax.imageio.ImageIO.read(is);
			BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB); 
			Graphics g = bi.createGraphics();   
			g.drawImage(src,0,0,width,height,null);
			g.dispose();
			bos = new ByteArrayOutputStream(); 
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			encoder.encode(bi);	
			result = bos.toByteArray();
		}catch(Exception ex){
			log.error("缩小文件时出错："+ex.getMessage(),ex);
			throw ex;
		}finally{
			if(bos!=null){
				try {
					bos.close();
				} catch (IOException e1) {
					log.error(e1);
				}
			}			
		}
		return result;
	}	
	
	/**
	 * 改变图片分辨率
	 * @param url 图片位置,如：http://192.168.1.197/pics/Dir00/01/20070731/08/R10086T20070731080011L01I080V034A0&%e7%b2%a4S92517@@@@&0.F.JPG
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception
	 */
	public static byte[] changeSize(String url,int width,int height) throws Exception{
		byte[] result = null;
		ByteArrayOutputStream bos = null;
		try{
			Image src = javax.imageio.ImageIO.read(new URL(url));
			BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB); 
			Graphics g = bi.createGraphics();   
			g.drawImage(src,0,0,width,height,null);
			g.dispose();
			bos = new ByteArrayOutputStream(); 
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			encoder.encode(bi);	
			result = bos.toByteArray();
		}catch(Exception ex){
			log.error("缩小文件时出错："+ex.getMessage(),ex);
			throw ex;
		}finally{
			if(bos!=null){
				try {
					bos.close();
				} catch (IOException e1) {
					log.error(e1);
				}
			}			
		}
		return result;
	}		
	
	/**
	 * 缩小图片
	 * @param sourceImgFile
	 * @param targetImgFile
	 * @param width
	 * @param height
	 * @throws Exception
	 */
	public static void changeSize2(String sourceImgFile,String targetImgFile,int width,int height) throws Exception{
		ByteArrayOutputStream bos = null;
		try{
			File sourceFile = new File(sourceImgFile);
			Image src = javax.imageio.ImageIO.read(sourceFile);
			BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB); 
			Graphics g = bi.createGraphics();   
			g.drawImage(src,0,0,width,height,null);
			g.dispose();
			bos = new ByteArrayOutputStream(); 
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			encoder.encode(bi);	
			
			FileUtils.writeByteArrayToFile(new File(targetImgFile), bos.toByteArray());
		}catch(Exception ex){
			log.error("缩小文件："+sourceImgFile+"时出错："+ex.getMessage(),ex);
			throw ex;
		}finally{
			if(bos!=null){
				try {
					bos.close();
				} catch (IOException e1) {
					log.error(e1);
				}
			}			
		}
	}
	
	/**
	 * 将两张图片拼成一张图片,例子：
	 	<pre>
	 	long startMillis = System.currentTimeMillis();
		try{
			URL imageAUrl = new URL("http://192.168.1.196/01/20060222/09/"+URLEncoder.encode("R10001T20051214172458L01I060V081A0&粤CB8483@@@@&0.F.JPG","UTF-8"));
			URL imageBUrl = new URL("http://192.168.1.196/01/20060222/09/"+URLEncoder.encode("R10001T20051214172458L01I060V081A0&粤CB8483@@@@&0.P.JPG","UTF-8"));
			byte[] imageC = ImageHelper.compose(imageAUrl, imageBUrl, true);
			FileHelper.writeFile(imageC, "D:/images/bbb.jpg");
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		System.out.println((System.currentTimeMillis()-startMillis));  
		</pre>
	 * @param imageAUrl 第一张图片的URL
	 * @param imageBUrl 第二张图片的URL
	 * @param isVertical 是否垂直拼接，如否，则水平拼接
	 * @return 拼图完成后的新图片
	 * @throws Exception
	 */
	public static byte[] compose(URL imageAUrl,URL imageBUrl,boolean isVertical) throws Exception{
		byte[] imageC = null;
		ByteArrayOutputStream bos = null;
		try{			
			Image srcA = javax.imageio.ImageIO.read(imageAUrl);
			int widthA = srcA.getWidth(null);
			int heightA = srcA.getHeight(null);	
			
			Image srcB = javax.imageio.ImageIO.read(imageBUrl);
			int widthB = srcB.getWidth(null);
			int heightB = srcB.getHeight(null);	
			
			int width,height;
			if(isVertical){
				width = Math.max(widthA, widthB);
				height = heightA + heightB;
			}
			else{
				width = widthA + widthB;
				height = Math.max(heightA, heightB);
			}
			
			BufferedImage bufImage = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImage.createGraphics();
            g.fillRect(0, 0, width, height);
            g.drawImage(srcA, 0, 0, null);
            if(isVertical){
            	g.drawImage(srcB, 0, heightA, null);
            }
            else{
            	g.drawImage(srcB, widthA,0, null);
            }
            g.dispose();
			bos = new ByteArrayOutputStream(); 
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			encoder.encode(bufImage);
			imageC = bos.toByteArray();		
		}catch(Exception ex){
			log.error("合并文件：'"+imageAUrl+"'和'"+imageBUrl+"'时出错："+ex.getMessage(),ex);
			throw ex;
		}finally{
			if(bos!=null){
				try {
					bos.close();
				} catch (IOException e1) {
					log.error(e1);
				}
			}			
		}		
		return imageC;
	}
	
	/**
	 * 合成图片
	 * @param imageAUrl
	 * @param imageBUrl
	 * @param width 图片压缩成相同宽度与高度
	 * @param height
	 * @param isVertical
	 * @return
	 * @throws Exception
	 */
	public static byte[] compose(URL imageAUrl,URL imageBUrl,int width,int height,boolean isVertical) throws Exception{
		byte[] imageC = null;
		ByteArrayOutputStream bos = null;
		try{
			Image srcA = javax.imageio.ImageIO.read(imageAUrl);
			//int widthA = srcA.getWidth(null);
			//int heightA = srcA.getHeight(null);	
			
			Image srcB = javax.imageio.ImageIO.read(imageBUrl);
			//int widthB = srcB.getWidth(null);
			//int heightB = srcB.getHeight(null);	
			int fullWidth = width;
			int fullHeight = height;
			if(isVertical){
				fullHeight = height * 2;
			}
			else{
				fullWidth = width * 2;
			}
			
//			if(isVertical){
//				width = Math.max(widthA, widthB);
//				height = heightA + heightB;
//			}
//			else{
//				width = widthA + widthB;
//				height = Math.max(heightA, heightB);
//			}
			
			BufferedImage bufImage = new BufferedImage(fullWidth, fullHeight,BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImage.createGraphics();
            g.fillRect(0, 0, fullWidth, fullHeight);
            g.drawImage(srcA, 0, 0,width,height, null);
            if(isVertical){
            	g.drawImage(srcB, 0, height,width,height, null);
            }
            else{
            	g.drawImage(srcB, width,0,width,height,null);
            }
            g.dispose();
			bos = new ByteArrayOutputStream(); 
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			encoder.encode(bufImage);
			imageC = bos.toByteArray();		
		}catch(Exception ex){
			log.error("合并文件：'"+imageAUrl+"'和'"+imageBUrl+"'时出错："+ex.getMessage(),ex);
			throw ex;
		}finally{
			if(bos!=null){
				try {
					bos.close();
				} catch (IOException e1) {
					log.error(e1);
				}
			}			
		}		
		return imageC;
	}	
	
	/**
	 * 将两张图片拼成一张图片,例子：
	 	<pre>
    	long startMillis = System.currentTimeMillis();
		try{
			byte[] imgA = ImageHelper.getImageBytes("http://192.168.1.196/01/20060222/09/"+URLEncoder.encode("R10001T20051214172458L01I060V081A0&粤CB8483@@@@&0.F.JPG","UTF-8"));
			byte[] imgB = ImageHelper.getImageBytes("http://192.168.1.196/01/20060222/09/"+URLEncoder.encode("R10001T20051214172458L01I060V081A0&粤CB8483@@@@&0.P.JPG","UTF-8"));
			
			byte[] imageC = ImageHelper.compose(imgA, imgB, false);
			FileHelper.writeFile(imageC, "D:/images/bbb.jpg");			
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		System.out.println((System.currentTimeMillis()-startMillis));   
		</pre>
	 * @param imageA　第一张图片
	 * @param imageB 第二张图片
	 * @param isVertical 是否垂直拼接，如否，则水平拼接
	 * @return 拼图完成后的新图片
	 * @throws Exception
	 */	
	public static byte[] compose(byte[] imageA,byte[] imageB,boolean isVertical) throws Exception{
		byte[] imageC = null;
		ByteArrayOutputStream bos = null;
		ByteArrayInputStream bisA = null,bisB = null;
		try{			
			bisA = new ByteArrayInputStream(imageA,0,imageA.length);
			Image srcA = javax.imageio.ImageIO.read(bisA);
			int widthA = srcA.getWidth(null);
			int heightA = srcA.getHeight(null);	
			bisB = new ByteArrayInputStream(imageB,0,imageB.length);
			Image srcB = javax.imageio.ImageIO.read(bisB);
			int widthB = srcB.getWidth(null);
			int heightB = srcB.getHeight(null);	
			int width,height;
			if(isVertical){
				width = Math.max(widthA, widthB);
				height = heightA + heightB;
			}
			else{
				width = widthA + widthB;
				height = Math.max(heightA, heightB);
			}
			BufferedImage bufImage = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImage.createGraphics();
            g.fillRect(0, 0, width, height);
            g.drawImage(srcA, 0, 0, null);
            if(isVertical){
            	g.drawImage(srcB, 0, heightA, null);
            }
            else{
            	g.drawImage(srcB, widthA,0, null);
            }
            g.dispose();
			bos = new ByteArrayOutputStream(); 
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			encoder.encode(bufImage);
			imageC = bos.toByteArray();		
		}catch(Exception ex){
			log.error("合并文件时出错："+ex.getMessage(),ex);
			throw ex;
		}finally{
			if(bos!=null){
				try {
					bos.close();
				} catch (IOException e1) {
					log.error(e1);
				}
			}			
		}		
		return imageC;		
	}
	
	/**
	 * 将两张图片拼成一张图片,可以设定每张图片的宽度和高度。
	 * @param imageA
	 * @param imageB
	 * @param width 调整后每张图片的宽度
	 * @param height 调整后每张图片的高度
	 * @param isVertical 是否垂直拼接，如否，则水平拼接
	 * @return
	 * @throws Exception
	 */
	public static byte[] compose(byte[] imageA,byte[] imageB,int width,int height,boolean isVertical) throws Exception{
		byte[] imageC = null;
		ByteArrayOutputStream bos = null;
		ByteArrayInputStream bisA = null,bisB = null;
		try{			
			bisA = new ByteArrayInputStream(imageA,0,imageA.length);
			Image srcA = javax.imageio.ImageIO.read(bisA);
			
			bisB = new ByteArrayInputStream(imageB,0,imageB.length);
			Image srcB = javax.imageio.ImageIO.read(bisB);
			
			int fullWidth = width;
			int fullHeight = height;
			if(isVertical){
				fullHeight = height * 2;
			}
			else{
				fullWidth = width * 2;
			}
			
			BufferedImage bufImage = new BufferedImage(fullWidth, fullHeight,BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImage.createGraphics();
            g.fillRect(0, 0, fullWidth, fullHeight);
            g.drawImage(srcA, 0, 0,width,height, null);
            if(isVertical){
            	g.drawImage(srcB, 0, height,width,height, null);
            }
            else{
            	g.drawImage(srcB, width,0,width,height,null);
            }
            g.dispose();
			bos = new ByteArrayOutputStream(); 
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			encoder.encode(bufImage);
			imageC = bos.toByteArray();		
		}catch(Exception ex){
			log.error("合并文件时出错："+ex.getMessage(),ex);
			throw ex;
		}finally{
			if(bos!=null){
				try {
					bos.close();
				} catch (IOException e1) {
					log.error(e1);
				}
			}			
		}		
		return imageC;		
	}	
	
	/**
	 * 将三张视频照片水平合并后再与数码照片垂直合并形成标准的闯红灯图片
	 * @param video1 第一张视频图片
	 * @param video2 第二张视频图片
	 * @param video3 第三张视频图片
	 * @param digital 数码图片
	 * @param width 合成后的宽度
	 * @param height 合成后的高度
	 * @return
	 * @throws Exception
	 */
	public static byte[] compose(byte[] video1,byte[] video2,byte[] video3,byte[] digital,int width,int height) throws Exception{
		byte[] imageC = null;
		ByteArrayOutputStream bos = null;
		ByteArrayInputStream bisVideo1 = null,bisVideo2 = null,bisVideo3 = null,bisDigital = null;
		try{			
			bisVideo1 = new ByteArrayInputStream(video1,0,video1.length);
			Image srcVideo1 = javax.imageio.ImageIO.read(bisVideo1);
						
			
			bisVideo2 = new ByteArrayInputStream(video2,0,video2.length);
			Image srcVideo2 = javax.imageio.ImageIO.read(bisVideo2);
			
			bisVideo3 = new ByteArrayInputStream(video3,0,video3.length);
			Image srcVideo3 = javax.imageio.ImageIO.read(bisVideo3);	

			bisDigital = new ByteArrayInputStream(digital,0,digital.length);
			Image srcDigital = javax.imageio.ImageIO.read(bisDigital);	
			//int digitalWidth = srcDigital.getWidth(null);
			int digitalHeight = srcDigital.getHeight(null);
			
			//每张视频图片的宽度为总宽度除以3
			int videoWidth = width/3;
			
			//每张视频图片的高度为总高度减去数码图片的高度
			int videoHeight = height-srcDigital.getHeight(null);	
			
			BufferedImage bufImage = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImage.createGraphics();
            g.fillRect(0, 0, width, height);
            g.drawImage(srcVideo1, 0, 0,videoWidth,videoHeight, null);
            g.drawImage(srcVideo2, videoWidth, 0,videoWidth,videoHeight, null);
            g.drawImage(srcVideo3, videoWidth*2, 0,videoWidth,videoHeight, null);
            g.drawImage(srcDigital, 0, videoHeight,width,digitalHeight, null);
            g.dispose();
			bos = new ByteArrayOutputStream(); 
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			encoder.encode(bufImage);
			imageC = bos.toByteArray();		
		}catch(Exception ex){
			log.error("合并文件时出错："+ex.getMessage(),ex);
			throw ex;
		}finally{
			if(bos!=null){
				try {
					bos.close();
				} catch (IOException e1) {
					log.error(e1);
				}
			}			
		}		
		return imageC;			
	}

	/**
	 * 图像类型转换 GIF->JPG GIF->PNG PNG->JPG PNG->GIF(X)
	 * @param source
	 * @param result
	 */
	public static void convert(String source, String result) {
		try {
			File f = new File(source);
			f.canRead();
			f.canWrite();
			BufferedImage src = ImageIO.read(f);
			ImageIO.write(src, "JPG", new File(result));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	/**
	 * 彩色转为黑白
	 * @param source
	 * @param result
	 */
	public static void gray(String source, String result) {
		try {
			BufferedImage src = ImageIO.read(new File(source));
			ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
			ColorConvertOp op = new ColorConvertOp(cs, null);
			src = op.filter(src, null);
			ImageIO.write(src, "JPEG", new File(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 将BMP转化为JPG格式的图片
	 * @param sourceFile
	 * @param targetFile
	 * @return
	 */
	public static boolean convertBmp2Jpg(String sourceFile, String targetFile) {
		boolean isSuccess = false;
		FileInputStream in = null;
		try {
			in = new FileInputStream(sourceFile);
			File jpg = new File(targetFile);
			BufferedImage image = ImageIO.read(in);
			isSuccess = ImageIO.write(image, "BMP", jpg);
			image.flush();
		} catch (IOException ex) {
			log.error(ex);
		} finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
		return isSuccess;
	}   
	
	/**
	 * 获取图片尺寸
	 * @param imageA
	 * @return
	 * @throws Exception
	 */
	public static int[] getImageSize(byte[] image) throws Exception{
		int[] size = new int[]{0,0};
		ByteArrayInputStream bis = null;
		try{			
			bis = new ByteArrayInputStream(image,0,image.length);
			Image src = javax.imageio.ImageIO.read(bis);
			size[0] = src.getWidth(null);
			size[1] = src.getHeight(null);	
		}catch(Exception ex){
			throw ex;
		}finally{
			if(bis!=null){
				try {
					bis.close();
				} catch (IOException e1) {
					log.error(e1);
				}
			}			
		}		
		return size;		
	}	
	
	/**
	 * 创建特定颜色的图片
	 * @param color
	 * @param width
	 * @param height
	 * @return
	 */
	public static byte[] createColorImage(Color color,int width,int height) throws Exception{
		byte[] result = null;
		ByteArrayOutputStream bos = null;
		try {
			BufferedImage bufImage = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
	        Graphics2D g = bufImage.createGraphics();
	        g.setPaint(color); 
	        g.fillRect(0, 0, width, height);
	        g.dispose();
	        bos = new ByteArrayOutputStream(); 
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			encoder.encode(bufImage);
			result = bos.toByteArray();					
		} finally{
			if(bos!=null){
				bos.close();
			}
		}
		return result;
	}
	
	/**
	 * 生成地图图片
	 */
	private void createMapImage(){
    	try {
    		//ImageHelper.changeSize(new File("D:/work/tiip/standard/tiip_laizhou/map/laizhou.jpg"), 2384, 1698);
    		
    		int row = 4,col = 4;
    		List imgList = ImageUtils.cut(new File("e:/temp/full.jpg"), row, col);
    		int k = 0;
    		for(int i=0;i<row;i++){
    			for(int j=0;j<col;j++){
    				String fileName = "e:/temp/full/"+i+j+".jpg";
    				FileUtils.writeByteArrayToFile(new File(fileName), (byte[])imgList.get(k));
    				k++;
    			}
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
