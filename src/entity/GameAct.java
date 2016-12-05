package entity;

import java.awt.Point;
import java.util.List;

import config.GameConfig;

public class GameAct {
	
	/*
	 * ��������
	 */
	private Point[] actPoints = null;
	
	/*
	 *������ 
	 */
	private int typeCode;
	
	private final static int MIN_X = GameConfig.getSystemConfig().getMinX();
	private final static int MAX_X = GameConfig.getSystemConfig().getMaxX(); 
	private final static int MIN_Y = GameConfig.getSystemConfig().getMinY();
	private final static int MAX_Y = GameConfig.getSystemConfig().getMaxY(); 
	
	private final static List<Point[]> TYPE_CONFIG = GameConfig.getSystemConfig().getTypeConfig();
	private final static List<Boolean> TYPE_ROUND = GameConfig.getSystemConfig().getTypeRound();
		
	public GameAct(int typeCode) {
		this.init(typeCode);
	}
	
	public void init(int typeCode) {
		this.typeCode = typeCode;
		Point[] points = TYPE_CONFIG.get(typeCode);
		actPoints = new Point[points.length];
		for(int i = 0; i < points.length; i++) {
			actPoints[i] = new Point(points[i].x, points[i].y);
		}
	}

	public Point[] getActPoints() {
		return actPoints;
	}
	/*
	 * �����ƶ�
	 * X��ƫ����
	 * Y��ƫ����
	 */
	public boolean move(int moveX, int moveY, boolean[][] gameMap) {
		//�ƶ�����
		for(int i = 0; i < actPoints.length; i++) {
			int newX = actPoints[i].x + moveX;
			int newY = actPoints[i].y + moveY;
    		if(isOverZone(newX, newY, gameMap)) {
    			return false;
    		}
		}
		for(int i = 0; i < actPoints.length; i++) {
			actPoints[i].x += moveX;
			actPoints[i].y += moveY;
		}
		return true;
	}
	
	/*
	 * ������ת
	 * ˳ʱ�빫ʽ ��
	 * A.x = O.y + O.x - B.y
	 * A.y = O.y - O.x + B.x
	 */
    public void round(boolean[][] gameMap) {
    	if(!TYPE_ROUND.get(this.getTypeCode())) {
    		return;
    	}
    	for(int i = 1; i < actPoints.length; i++) {
    		int newX = actPoints[0].y + actPoints[0].x - actPoints[i].y;
    		int newY = actPoints[0].y - actPoints[0].x + actPoints[i].x;
    		if(this.isOverZone(newX, newY, gameMap)) {
    			return;
    		}
    	}
    		
    	for(int i = 1; i < actPoints.length; i++) {    
    		int newX = actPoints[0].y + actPoints[0].x - actPoints[i].y;
        	int newY = actPoints[0].y - actPoints[0].x + actPoints[i].x;
        	actPoints[i].x = newX;
        	actPoints[i].y = newY;
    	}
    }
    
    /*
     * �ж��Ƿ񳬳��߽�
     */
    private boolean  isOverZone(int x, int y, boolean[][] gameMap) {
    	return x < MIN_X || x > MAX_X || y < MIN_Y || y > MAX_Y || gameMap[x][y];
    }
    
    /*
     * ��ȡ������
     */
	public int getTypeCode() {
		return typeCode;
	}
}
