package service;

public interface GameService {
	//上
	public boolean keyUp();
	//下
	public boolean keyDown();
	//左
	public boolean keyLeft();
	//右
	public boolean keyRight();
	//psp三角
	public boolean keyFunUp();
	//psp叉
	public boolean keyFunDown();
	//psp方块
	public boolean keyFunLeft();
	//圆圈
	public boolean keyFunRight();
	//启动主线程
	public void startGame();
	//游戏主要行为
	public void mainAction();
	
}
