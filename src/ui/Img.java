 package ui;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import config.GameConfig;

public class Img {
	
	private Img() {}
	//ͼƬ·��
	public static final String GRAPHICS_PATH = "graphics/";
	//TODO
	private static final String DEFAYLT_PATH = "111/";
	
	public static void setSkin(String path) {
		String skinPath = GRAPHICS_PATH + path;
		
		//����ǩ��
		SIGN = new ImageIcon(skinPath + "string/sign.png").getImage();
		//����ͼƬ
		WINDOW = new ImageIcon(skinPath + "window/Window.png").getImage();
		//����ֵ��
		RECT = new ImageIcon(skinPath + "window/rect.png").getImage();
		//����ͼƬ 260*36
		NUMBER = new ImageIcon(skinPath + "string/num.png").getImage();
		//���ڱ��⣨������
		POINT = new ImageIcon(skinPath + "string/point.png").getImage();
	    //���ڱ��⣨���У�
		RMLINE = new ImageIcon(skinPath + "string/rmline.png").getImage();
		//���ݿⴰ�ڱ���
		DB = new ImageIcon(skinPath + "string/db.png").getImage();
		//���ؼ�¼���ڱ���
		DISK = new ImageIcon(skinPath + "string/disk.png").getImage();
		//����ͼƬ
		ACT = new ImageIcon(skinPath + "game/rect.png").getImage();
		//�ȼ�����ͼƬ
		LEVEL = new ImageIcon(skinPath + "string/level.png").getImage();
		//��Ӱ
		SHADOW = new ImageIcon(skinPath + "game/shadow.png").getImage();
		//��ʼ��ť
		BTN_START = new ImageIcon(skinPath + "string/start.png");
		//���ð�ť
		BTN_CONFIG = new ImageIcon(skinPath + "string/config.png");
		//��ͣ
		PAUSE = new ImageIcon(skinPath + "string/pause.png").getImage();
		// ��һ������ͼƬ
		NEXT_ACT = new Image[GameConfig.getSystemConfig().getTypeConfig().size()];
		for (int i = 0; i < NEXT_ACT.length; i++) {
			NEXT_ACT[i] = new ImageIcon(skinPath + "game/" + i + ".png").getImage();
		}
		// ����ͼƬ����
		File dir = new File(skinPath + "background");
		File[] files = dir.listFiles();
		BG_LIST = new ArrayList<Image>();
		for (File file : files) {
			if (!file.isDirectory()) {
				BG_LIST.add(new ImageIcon(file.getPath()).getImage());
			}
		}
	}
	
	//����ǩ��
	public static Image SIGN = null;
	//����ͼƬ
	public static Image WINDOW = null;
	//����ֵ��
	public static Image RECT = null;
	//����ͼƬ 260*36
	public static Image NUMBER = null;
	//���ڱ��⣨������
	public static Image POINT = null;
    //���ڱ��⣨���У�
	public static Image RMLINE = null;
	//���ݿⴰ�ڱ���
	public static Image DB = null;
	//���ؼ�¼���ڱ���
	public static Image DISK = null;
	//����ͼƬ
	public static Image ACT = null;
	//�ȼ�����ͼƬ
	public static  Image LEVEL = null;
	//��Ӱ
	public static  Image SHADOW = null;
	//��ʼ��ť
	public static  ImageIcon BTN_START = null;
	//���ð�ť
	public static  ImageIcon BTN_CONFIG = null;
	//��ͣ
	public static  Image PAUSE = null;
	
	//��һ����ͼƬ����
	public static Image[] NEXT_ACT = null;
	//����ͼƬ����
	public static List<Image> BG_LIST = null;
	
	//��ʼ��ͼƬ
	static {
		// ����ͼƬ����
		setSkin(DEFAYLT_PATH);
	}
}
