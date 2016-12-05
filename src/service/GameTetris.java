package service;

import java.awt.Point;
import java.util.Map;
import java.util.Random;

import config.GameConfig;
import dto.GameDto;
import entity.GameAct;

public class GameTetris implements GameService {
	
	//��Ϸ�߼�
	private GameDto dto;
	//�����������
	private Random random = new Random();	
	//������������
	private static final int MAX_TYPE = GameConfig.getSystemConfig().getTypeConfig().size() - 1;
	//��������
	private static final int LEVEL_UP = GameConfig.getSystemConfig().getLevelUp();
	//�������з�����
	private static final Map<Integer, Integer> PLUS_POINT = GameConfig.getSystemConfig().getPluspoint();
	
    public GameTetris(GameDto dto) {
    	this.dto = dto;
    }

    //���������������
	public boolean keyUp() {
		if(this.dto.isPause()) {
			return false;
		}
		synchronized (this.dto) {
			this.dto.getGameAct().round(this.dto.getGameMap());
		}
		return true;
	}

	// �������
	public boolean keyDown() {
		if(this.dto.isPause()) {
			return false;
		}
		synchronized (this.dto) {
			// ���������ƶ������ж��Ƿ��ƶ��ɹ�
			if (this.dto.getGameAct().move(0, 1, this.dto.getGameMap())) {
				return false;
			}
			// ��ȡ��Ϸ��ͼ����
			boolean[][] map = this.dto.getGameMap();
			// ��÷������
			Point[] act = this.dto.getGameAct().getActPoints();
			// ������ѻ�����ͼ����
			for (int i = 0; i < act.length; i++) {
				map[act[i].x][act[i].y] = true;
			}
			// �ж����У��������þ���ֵ
			int plusExp = this.plusExp();
			// �����������
			if (plusExp > 0) {
				// ���Ӿ���ֵ
				this.plusPoint(plusExp);
			}
			// ˢ���µķ���
			this.dto.getGameAct().init(this.dto.getNext());
			// �����������һ������
			this.dto.setNext(random.nextInt(MAX_TYPE));
			// �����Ϸ�Ƿ�ʧ��
			if (this.isLose()) {
				//������Ϸ
				this.dto.setStart(false);
			}
		}
		return true;
	}
	
	//�����Ϸ�Ƿ�ʧ��
	private boolean isLose() {
		//������ڵĻ����
		Point[] actPoints = this.dto.getGameAct().getActPoints();
		//������ڵ���Ϸ��ͼ
		boolean[][] map = this.dto.getGameMap();
		for(int i = 0; i < actPoints.length; i++) {
			if(map[actPoints[i].x][actPoints[i].y]) {
				return true;
			}
		}
		return false;
	}
	
	//�ӷ���������
	private void plusPoint(int plusExp) {
		//������ڵȼ�
		int lv = this.dto.getNowLevel();
		//���������������
		int rmLine = this.dto.getNowRemoveLine();
		//������ڵ÷�
		int point = this.dto.getNowPoint();
		//�����Ƿ�����
		if(rmLine % LEVEL_UP + plusExp >= LEVEL_UP) {
			//�ȼ�����
			this.dto.setNowLevel(++lv);
		}
		//��������
		this.dto.setNowRemoveLine(rmLine + plusExp);
		//��������
		this.dto.setNowPoint(point + PLUS_POINT.get(plusExp));
	}

	//���������������
	public boolean keyLeft() {
		if(this.dto.isPause()) {
			return false;
		}
		synchronized (this.dto) {
			this.dto.getGameAct().move(-1, 0, this.dto.getGameMap());
			return true;
		}
	}

	// ���������������
	public boolean keyRight() {
		if(this.dto.isPause()) {
			return false;
		}
		synchronized (this.dto) {
			this.dto.getGameAct().move(1, 0, this.dto.getGameMap());
			return true;
		}
	}
	
	//�ж����У��������þ���ֵ
	private int plusExp() {
		//�����Ϸ��ͼ
		boolean[][] map = this.dto.getGameMap();
		int exp = 0;
		//ɨ����Ϸ��ͼ�鿴�Ƿ��п�����
		for(int y = 0; y < GameDto.GAMEZONE_H; y++) {
			//�ж��Ƿ������
			if(isCanRemoveLine(y, map)) {
				//�жϳɹ�������
				this.removeLine(y, map);
				//���Ӿ���ֵ
				exp++;
			}
		}
		return exp;
	}
	
	//���д���
	private void removeLine(int rowNumber, boolean[][] map) {
		for(int x = 0; x < GameDto.GAMEZONE_W; x++ ) {
			for(int y = rowNumber; y > 0; y-- ) {
				map[x][y] = map[x][y - 1];
			}
			map[x][0] = false;
		}
	}

	//�ж�ĳһ���Ƿ������
	private boolean isCanRemoveLine(int y, boolean[][] map) {
		//�����ڶ�ÿһ���������ɨ��
		for(int x = 0; x < GameDto.GAMEZONE_W; x++) {
			if(!map[x][y]) {
				//�����һ������Ϊfalse��ֱ��������һ��
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean keyFunUp() {
		//TODO������������
		this.dto.setCheat(true);
		this.plusPoint(4);
		return true;
	}

	@Override
	public boolean keyFunDown() {
		if(this.dto.isPause()) {
			return false;
		}
		//��������
		while(!this.keyDown());
		return true;
		
	}

	@Override
	public boolean keyFunLeft() {
		//��ʾ��Ӱ
		this.dto.changeShowShadow();
		return true;
		
	}

	@Override
	public boolean keyFunRight() {
		//��ͣ
		if(this.dto.isStart()) {
		    this.dto.changePause();
		}
		return true;
		
	}
	
	@Override
	public void startGame() {
		//���������һ������
    	this.dto.setNext(random.nextInt(MAX_TYPE));
    	//����������ڷ���
    	dto.setGameAct(new GameAct(random.nextInt(MAX_TYPE)));
    	//����Ϸ״̬��Ϊ��ʼ
    	this.dto.setStart(true);
    	//dto��ʼ��
    	this.dto.dtoInit();
	}
	
	@Override
	public void mainAction() {
		this.keyDown();
	}
	
}