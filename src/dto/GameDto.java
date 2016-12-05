package dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import config.GameConfig;
import entity.GameAct;
import util.GameFunction;

public class GameDto {
	
	//��Ϸ����Ŀ�
	public static final int GAMEZONE_W = GameConfig.getSystemConfig().getMaxX() + 1;
	//��Ϸ����ĸ�
	public static final int GAMEZONE_H = GameConfig.getSystemConfig().getMaxY() + 1;
	//���ݿ��¼
	private List<Player> dbRecode;
	//���ؼ�¼
    private List<Player> diskRecode;
    //��Ϸ��ͼ
    private boolean[][] gameMap;
    //���䷽��
    private GameAct gameAct;
    //��һ������
    private int next;
    //����
    private int nowPoint;
    //������
    private int nowRemoveLine;
    //�ȼ�
    private int nowLevel;
    //��Ϸ�Ƿ��ǿ�ʼ״̬
    private boolean start;
    //�Ƿ���ʾ��Ӱ
    private boolean showShadow; 
    //��ͣ
    private boolean pause;
    //�Ƿ�ʹ������
    private boolean cheat;
    //�̵߳ȴ�ʱ��
    private long sleepTime;
    
    
    //���캯��
    public GameDto() {
    	dtoInit();
    }
    
    
    //dto��ʼ��
    public void dtoInit() {
    	this.gameMap = new boolean[GAMEZONE_W][GAMEZONE_H];
    	this.nowLevel = 1;
    	this.nowPoint = 0;
    	this.nowRemoveLine = 0;
    	this.pause = false;
    	this.cheat = false;
    	this.sleepTime = GameFunction.getSleepTimeByLevel(this.getNowLevel());
    }
    
	public int getNowLevel() {
		return nowLevel;
	}
	public void setNowLevel(int nowLevel) {
		this.nowLevel = nowLevel;
		//�����߳�˯��ʱ�䣨�����ٶȣ�
		this.sleepTime = GameFunction.getSleepTimeByLevel(this.getNowLevel());
	}
	public List<Player> getDbRecode() {
		return dbRecode;
	}
    private List<Player> setFillRecode(List<Player> players) {
    	//��������վʹ���
		if(players == null) {
			players = new ArrayList<Player>();
		}
		//С���壬�ʹ�����5��Ϊֹ
		while(players.size() < 5) {
			players.add(new Player("No Data", 0));
		}
		Collections.sort(players);
		return players;
    }
	public void setDbRecode(List<Player> dbRecode) {
		this.dbRecode = this.setFillRecode(dbRecode);
		this.dbRecode = dbRecode;
	}
	public List<Player> getDiskRecode() {
		return diskRecode;
	}
	public void setDiskRecode(List<Player> diskRecode) {
		this.diskRecode = this.setFillRecode(diskRecode);
		this.diskRecode = diskRecode;
	}
	public boolean[][] getGameMap() {
		return gameMap;
	}
	public void setGameMap(boolean[][] gameMap) {
		this.gameMap = gameMap;
	}
	public GameAct getGameAct() {
		return gameAct;
	}
	public void setGameAct(GameAct gameAct) {
		this.gameAct = gameAct;
	}
	public int getNext() {
		return next;
	}
	public void setNext(int next) {
		this.next = next;
	}
	public int getNowPoint() {
		return nowPoint;
	}
	public void setNowPoint(int nowPoint) {
		this.nowPoint = nowPoint;
	}
	public int getNowRemoveLine() {
		return nowRemoveLine;
	}
	public void setNowRemoveLine(int nowRemoveLine) {
		this.nowRemoveLine = nowRemoveLine;
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public boolean isShowShadow() {
		return showShadow;
	}

	public void changeShowShadow() {
		this.showShadow = !this.showShadow;
	}

	public boolean isPause() {
		return pause;
	}

	public void changePause() {
		this.pause = !this.pause;
	}

	public boolean isCheat() {
		return cheat;
	}

	public void setCheat(boolean cheat) {
		this.cheat = cheat;
	}

	public long getSleepTime() {
		return sleepTime;
	}
}
