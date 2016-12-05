package ui;

import java.awt.Graphics;

import config.GameConfig;

public class LayerPoint extends Layer {
	
	//���������λ��
	private static final int POINT_BIT = 5;
	//��������
	private static final int LEVEL_UP = GameConfig.getSystemConfig().getLevelUp();
	//����y����
	private final int rmLineY;
    //����x����
	private  final int comX;
	//����y����
	private final int pointY;
	//����ֵ��y����
	private final int expY;
	
	
	public LayerPoint(int x, int y, int w, int h) {
		super(x, y, w, h);
		//��ʼ����ͬ��x����
		this.comX = this.w-IMG_NUMBER_W * POINT_BIT - PADDING;
		//��ʼ��������ʾ��y����
		this.pointY = PADDING;
		//��ʼ��������ʾ��y����
		this.rmLineY = this.pointY + Img.POINT.getHeight(null) + PADDING;
		//��ʼ������ֵ��y����
		this.expY = this.rmLineY + Img.RMLINE.getHeight(null) + PADDING;
	}
	
	public void paint(Graphics g) {
		this.createWindow(g);
		//���ƴ��ڱ���(����)
		g.drawImage(Img.POINT, this.x + PADDING, this.y + pointY, null);
		//��ʾ����
		this.drawNumberLeftPad(comX, PADDING, this.dto.getNowPoint(), POINT_BIT, g);	
		//���ƴ��ڱ��⣨���У�
		g.drawImage(Img.RMLINE, this.x + PADDING, this.y + rmLineY, null);
		//��ʾ����
		this.drawNumberLeftPad(comX, rmLineY, this.dto.getNowRemoveLine(), POINT_BIT, g);		
		// ����ֵ�ۣ�����ֵ��
		int rmLine = this.dto.getNowRemoveLine();
		this.drawRect(expY, "Next Levle", null, (double)(rmLine % LEVEL_UP) / (double)LEVEL_UP, g);
	}
}
