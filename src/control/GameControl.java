package control;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import config.DataInterfaceConfig;
import config.GameConfig;
import dao.Data;
import dto.GameDto;
import dto.Player;
import service.GameService;
import service.GameTetris;
import ui.window.JFrameConfig;
import ui.window.JFrameGame;
import ui.window.JFrameSavePoint;
import ui.window.JPanelGame;

/*
 * ������Ҽ����¼�
 * ������Ϸ�߼�
 * ���ƻ���
 */
public class GameControl {
	
	//���ݷ��ʽӿ�A
	private Data dataA;
	//���ݷ��ʽӿ�B
	private Data dataB; 
	//��Ϸ�߼����Ʋ�
	private GameService gameService;
	// ��Ϸ�����
	private JPanelGame panelGame;	
	//��Ϸ�������ô���
	private JFrameConfig frameConfig;
	//�����������
	private JFrameSavePoint frameSavePoint;
	//��Ϸ��Ϊ����
	private Map<Integer, Method> actionList;
	//��Ϸ����Դ
	private GameDto dto = null;
	//��Ϸ�߳�
	private Thread gameThread = null;
	
	public GameControl() {
		
		//������Ϸ����Դ
		this.dto = new GameDto();
		//������Ϸ�߼��飨������Ϸ����Դ��
		this.gameService = new GameTetris(dto);
		//��������
		this.dataA = createDataObject(GameConfig.getDataConfig().getDataA());
		//�������ݿ��¼����Ϸ
		this.dto.setDbRecode(dataA.loadData());
		//�����ݽӿ�B��ñ��ش��̼�¼
		this.dataB = createDataObject(GameConfig.getDataConfig().getDataB());
		//���ñ��ش��̼�¼����Ϸ
		this.dto.setDiskRecode(dataB.loadData());
		//������Ϸ���
		this.panelGame = new JPanelGame(this, dto);
		//��ȡ�û���������
		setControlConfig();
		//��ʼ���û����ô���
		this.frameConfig = new JFrameConfig(this);
		//��ʼ�������������
		this.frameSavePoint = new JFrameSavePoint(this);
		//������Ϸ���ڣ���װ��Ϸ��壩
		new JFrameGame(this.panelGame);
	}
	
	//��ȡ�û���������
	private void setControlConfig() {
		//�����������뷽������ӳ������
		this.actionList = new HashMap<Integer, Method>(); 
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/control.dat"));
			@SuppressWarnings("unchecked")
			HashMap<Integer, String> cfgSet = (HashMap<Integer, String>) ois.readObject();	
			Set<Entry<Integer, String>> entryset = cfgSet.entrySet();
			for(Entry<Integer, String> e : entryset) {
				actionList.put(e.getKey(), this.gameService.getClass().getMethod(e.getValue()));
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//�������ݶ���
	private Data createDataObject(DataInterfaceConfig cfg) {
		try {
			//��������
			Class<?> cls = Class.forName(cfg.getClassName());
			//��ù�����
			Constructor<?> ctr = cls.getConstructor(HashMap.class);
			//��������
			return (Data) ctr.newInstance(cfg.getParam());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//������ҿ��ƾ�������
	public void actionByKeyCode(int keyCode) {
		try {
			if(this.actionList.containsKey(keyCode)) {
				this.actionList.get(keyCode).invoke(this.gameService);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		this.panelGame.repaint();
	}
    
	//��ʾ��ҿ��ƴ���
	public void showUserConfig() {	
		this.frameConfig.setVisible(true);
	}
    
	//�Ӵ��ڹر��¼�
	public void setOver() {
		this.panelGame.repaint();
		this.setControlConfig();
	}

	//��ʼ��ť�¼�
	public void start() {
		//��尴ť����Ϊ���ɵ��
		this.panelGame.buttonSwitch(false);
		//�رմ���
		this.frameConfig.setVisible(false);
		this.frameSavePoint.setVisible(false);
		//��Ϸ���ݳ�ʼ��
		this.gameService.startGame();
		//�����̶߳���
		this.gameThread = new MainThread();
		//�����߳�
		this.gameThread.start();
		//ˢ�»���
		this.panelGame.repaint();
	}
	
	//ʧ��֮��Ĵ���
	private void afterLose() {
		if(!this.dto.isCheat()) {
			//��ʾ����÷ִ���
			this.frameSavePoint.showWindow(this.dto.getNowPoint());
		}
		//ʹ��ť�����ٵ��
		this.panelGame.buttonSwitch(true);
	}
	
	//�������
	public void savePoint(String name) {
		Player pla = new Player(name, this.dto.getNowPoint());
		//�����¼�����ݿ�
		this.dataA.saveData(pla);
		//�����¼�����ش���
		this.dataB.saveData(pla);
		//�������ݿ��¼����Ϸ
		this.dto.setDbRecode(dataA.loadData());
		//���ñ��ش��̼�¼����Ϸ
		this.dto.setDiskRecode(dataB.loadData());
		//ˢ�»���
		this.panelGame.repaint();
	}
	
	//ˢ�»���
	public void repaint() {
		this.panelGame.repaint();
	}
	
	//��Ϸ���߳�
	private class MainThread extends Thread {
		@Override
		public void run() {
			//ˢ�»���
			panelGame.repaint();
			//��ѭ��
			while(dto.isStart()) {
				try {
					//�߳�˯��
					Thread.sleep(dto.getSleepTime());
					//�����ͣ����ִ������Ϊ
					if(dto.isPause()) {
						continue;
					}
					//��Ϸ����Ϊ
					gameService.mainAction();
					//ˢ�»���
					panelGame.repaint();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			afterLose();
		}
	}
}
