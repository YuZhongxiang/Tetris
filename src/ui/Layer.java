package ui;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import config.FrameConfig;
import config.GameConfig;
import dto.GameDto;

//���ƴ���
public abstract class Layer {
	
	//�ڱ߾�
	protected static final int PADDING;
	//�߿���
	protected static final int BORDER;
	static {
		//��ȡ��Ϸ����
		FrameConfig fCfg = GameConfig.getFrameConfig();
		PADDING = fCfg.getPadding();
		BORDER  = fCfg.getBorder();
	}

	private static int WINDOW_IMGW = Img.WINDOW.getWidth(null);
	private static int WINDOW_IMGH = Img.WINDOW.getHeight(null);
	
	//������Ƭ���
	protected static final int IMG_NUMBER_W = Img.NUMBER.getWidth(null) / 10;
	//������Ƭ�ĸ߶�
	private static final int IMG_NUMBER_H = Img.NUMBER.getHeight(null);
	//�������Ͻ�����x
	protected int x;
	//�������Ͻ�����y
	protected int y;
	//���ڿ��
	protected int w;
	//���ڸ߶�
	protected int h;
	//��Ϸ����
	protected GameDto dto = null;
	
	//����ֵ��ͼƬ���߶ȣ�
	protected static final int IMG_RECT_H = Img.RECT.getHeight(null);
	//����ֵ�۵Ŀ��
	private static final int IMG_RECT_W = Img.RECT.getWidth(null);
	//ֵ�۵Ŀ��
	private final int rectW;
	//ֵ���ڲ�Ĭ������
	private static final Font DEF_FONT = new Font("����", Font.BOLD, 20);
 
	protected Layer(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.rectW = this.w - (PADDING << 1);
	}
		
	//���ƴ���
	protected void createWindow(Graphics g) {
	    //����
	    g.drawImage(Img.WINDOW, x, y, x + BORDER, y + BORDER, 0, 0, BORDER, BORDER, null);
        //����
	    g.drawImage(Img.WINDOW, x + BORDER, y, w + x - BORDER, y + BORDER, BORDER, 0, WINDOW_IMGW - BORDER, BORDER, null); 
	    //����
	    g.drawImage(Img.WINDOW, w + x - BORDER, y, w + x, y + BORDER, WINDOW_IMGW - BORDER, 0, WINDOW_IMGW, BORDER, null);	
	    //����
	    g.drawImage(Img.WINDOW, x, y + BORDER, x + BORDER, h + y - BORDER, 0, BORDER, BORDER, WINDOW_IMGH - BORDER, null);
	    //����
	    g.drawImage(Img.WINDOW, w + x - BORDER, y + BORDER, w + x, h + y - BORDER, WINDOW_IMGW - BORDER, BORDER, WINDOW_IMGW, WINDOW_IMGH - BORDER, null);
	    //����
	    g.drawImage(Img.WINDOW, x, y + h - BORDER, x + BORDER, y + h, 0, WINDOW_IMGW - BORDER, BORDER, WINDOW_IMGW, null);
	    //����
	    g.drawImage(Img.WINDOW, x + w - BORDER, y + h - BORDER, x + w, y + h, WINDOW_IMGW - BORDER, WINDOW_IMGH - BORDER, WINDOW_IMGW, WINDOW_IMGH, null);
	    //����
	    g.drawImage(Img.WINDOW, x + BORDER, y + h - BORDER, x + w - BORDER, y + h, BORDER, WINDOW_IMGH - BORDER, WINDOW_IMGW - BORDER, WINDOW_IMGH, null);
	    //��
	    g.drawImage(Img.WINDOW, x + BORDER, y + BORDER, x + w - BORDER, y + h - BORDER, BORDER, BORDER, WINDOW_IMGW - BORDER, WINDOW_IMGH - BORDER, null);	
	}
	
	//ˢ����Ϸ��������
	abstract public void paint(Graphics g);
		
	public void setDto(GameDto dto) { 
		this.dto = dto;
	}

	/*
	 * ��ʾ����
	 *@param x ���Ͻ�x����
	 *@param y ���Ͻ�y����
	 *@param num Ҫ��ʾ������
	 *@param g ���ʶ���
	 *@param maxBit ����λ�� 
	 */
      protected void drawNumberLeftPad(int x, int y, int num, int maxBit, Graphics g) {
    	  //��Ҫ��ӡ������ת�����ִ���
    	  String strNum = Integer.toString(num);
    	  //ѭ�����������Ҷ���
    	  for(int i = 0; i < maxBit; i++) {
    		  //�ж��Ƿ������������
    		  if(maxBit - i <= strNum.length()){
    			  //����������ִ������±�
    			  int idx = i - maxBit + strNum.length();
    			  //������number�е�ÿһλȡ��
    			  int bit = strNum.charAt(idx) - '0';
    			  //��������
            	  g.drawImage(Img.NUMBER, 
            			  this.x + x + IMG_NUMBER_W * i, this.y + y, 
            			  this.x + x + IMG_NUMBER_W * (i + 1), 
            			  this.y + y + IMG_NUMBER_H, 
            			  bit * IMG_NUMBER_W, 0, 
            			  (bit + 1) * IMG_NUMBER_W, 
            			  IMG_NUMBER_H, null);
    		  }  
    	  }
      }
    /*
     * ����ֵ��  
     */
  	protected void drawRect(int y, String title, String number, double percent, Graphics g) {
		//����ֵ��ʼ��
		int rect_x = this.x + PADDING;
		int rect_y = this.y + y;
		// ���Ʊ���
		g.setColor(Color.BLACK);
		g.fillRect(rect_x, rect_y, this.rectW, IMG_RECT_H + 4);
		g.setColor(Color.WHITE);
		g.fillRect(rect_x + 1, rect_y + 1, this.rectW - 2, IMG_RECT_H + 2);
		g.setColor(Color.BLACK);
		g.fillRect(rect_x + 2, rect_y + 2, this.rectW - 4, IMG_RECT_H);
		g.setColor(Color.GREEN);
		//������
		int w = (int)((percent * (this.rectW)));
		//�����ɫ
		int subIdx = (int)(percent * IMG_RECT_W) - 1;
		//����ֵ��
        g.drawImage(Img.RECT, 
        		rect_x + 2, rect_y + 2, 
        		rect_x - 2 + w, rect_y + 2 + IMG_RECT_H, 
        		subIdx, 0, subIdx + 1, IMG_RECT_H, 
        		null);
        g.setColor(Color.WHITE);
        g.setFont(DEF_FONT);
        g.drawString(title, rect_x + 4, rect_y + 22);
        if(number != null) {
        	g.drawString(number, rect_x + 248, rect_y + 22);
        }
	}
  	
	/*
	 * ���л�ͼ
	 */
	protected void drawImageOfCenter(Graphics g,Image img) {
		int imgW = img.getWidth(null);
		int imgH = img.getHeight(null);
		g.drawImage(img, this.x + (this.w - imgW >> 1), this.y + (this.h - imgH >> 1), null);

	}
}
