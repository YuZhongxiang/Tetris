package service;

public interface GameService {
	//��
	public boolean keyUp();
	//��
	public boolean keyDown();
	//��
	public boolean keyLeft();
	//��
	public boolean keyRight();
	//psp����
	public boolean keyFunUp();
	//psp��
	public boolean keyFunDown();
	//psp����
	public boolean keyFunLeft();
	//ԲȦ
	public boolean keyFunRight();
	//�������߳�
	public void startGame();
	//��Ϸ��Ҫ��Ϊ
	public void mainAction();
	
}
