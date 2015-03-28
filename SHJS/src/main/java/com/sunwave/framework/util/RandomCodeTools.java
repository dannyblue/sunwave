package com.sunwave.framework.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RandomCodeTools {

	//验证码图片的宽
	public static int WIDTH = 55;
	//验证码图片的高
	public static int HEIGHT = 20;
	
	public static String chars = "123456789ABCDEFGHJKLMNPRSTUVWXYZ";
	/**
	 * 动态生成验证码
	 * 
	 * @return
	 */
	public static void RandomCode(HttpSession session,
			HttpServletResponse response) {
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		ServletOutputStream sos = null;
		char[] rands = null;
		try {
			sos = response.getOutputStream();//返回的输出流
			BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
					BufferedImage.TYPE_INT_RGB);//图片对象创建
			Graphics g = image.getGraphics();//图片画笔
			rands = generateCheckCode();//生成随机4位验证码
			drawBackground(g);//生成背景画笔
			drawRands(g, rands);//生成带验证码的图笔
			g.dispose();//释放画笔
			//session.setAttribute("radomCode", new String(rands));//保存验证码到session
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(image, "JPEG", bos);//图片对象以JPEG的格式放入流中
			byte[] buf = bos.toByteArray();
			response.setContentLength(buf.length);
			sos.write(buf);
			bos.close();
			sos.close();
		} catch (IOException e) {
			if (sos != null)
				try {
					sos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		}

	}

	/**
	 * 画背景图层
	 * @param g
	 */
	private static void drawBackground(Graphics g) {
		g.setColor(new Color(0xDCDCDC));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		for (int i = 0; i < 120; i++) {
			int x = (int) (Math.random() * WIDTH);
			int y = (int) (Math.random() * HEIGHT);
			int red = (int) (Math.random() * 255);
			int green = (int) (Math.random() * 255);
			int blue = (int) (Math.random() * 255);
			g.setColor(new Color(red, green, blue));
			g.drawOval(x, y, 1, 0);
		}
	}

	/**
	 * 
	 * @param g
	 * @param rands
	 */
	private static void drawRands(Graphics g, char[] rands) {
		// g.setColor(Color.BLUE);
		Random random = new Random();
		int red = random.nextInt(110);
		int green = random.nextInt(50);
		int blue = random.nextInt(50);
		g.setColor(new Color(red, green, blue));
		g.setFont(new Font(null, Font.ITALIC | Font.BOLD, 16));
		g.drawString("" + rands[0], 1, 16);
		g.drawString("" + rands[1], 14, 14);
		g.drawString("" + rands[2], 27, 16);
		g.drawString("" + rands[3], 40, 15);
	}

	private static char[] generateCheckCode() {
		char[] rands = new char[4];
		for (int i = 0; i < 4; i++) {
			int rand = (int) (Math.random() * chars.length());
			rands[i] = chars.charAt(rand);
		}
		return rands;
	}
}
