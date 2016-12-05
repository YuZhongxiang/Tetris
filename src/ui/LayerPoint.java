package ui;

import java.awt.Graphics;

import config.GameConfig;

public class LayerPoint extends Layer {
	
	//分数的最大位数
	private static final int POINT_BIT = 5;
	//升级行数
	private static final int LEVEL_UP = GameConfig.getSystemConfig().getLevelUp();
	//消行y坐标
	private final int rmLineY;
    //消行x坐标
	private  final int comX;
	//分数y坐标
	private final int pointY;
	//经验值的y坐标
	private final int expY;
	
	
	public LayerPoint(int x, int y, int w, int h) {
		super(x, y, w, h);
		//初始化共同的x坐标
		this.comX = this.w-IMG_NUMBER_W * POINT_BIT - PADDING;
		//初始化分数显示的y坐标
		this.pointY = PADDING;
		//初始化消行显示的y坐标
		this.rmLineY = this.pointY + Img.POINT.getHeight(null) + PADDING;
		//初始化经验值的y坐标
		this.expY = this.rmLineY + Img.RMLINE.getHeight(null) + PADDING;
	}
	
	public void paint(Graphics g) {
		this.createWindow(g);
		//绘制窗口标题(分数)
		g.drawImage(Img.POINT, this.x + PADDING, this.y + pointY, null);
		//显示分数
		this.drawNumberLeftPad(comX, PADDING, this.dto.getNowPoint(), POINT_BIT, g);	
		//绘制窗口标题（消行）
		g.drawImage(Img.RMLINE, this.x + PADDING, this.y + rmLineY, null);
		//显示消行
		this.drawNumberLeftPad(comX, rmLineY, this.dto.getNowRemoveLine(), POINT_BIT, g);		
		// 绘制值槽（经验值）
		int rmLine = this.dto.getNowRemoveLine();
		this.drawRect(expY, "Next Levle", null, (double)(rmLine % LEVEL_UP) / (double)LEVEL_UP, g);
	}
}
