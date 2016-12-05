package config;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/*
 * 游戏配置器
 */
public class GameConfig implements Serializable {
	private static FrameConfig FRAME_CONFIG = null;
	
	private static DataConfig DATA_CONFIG = null;
	
	private static SystemConfig SYSTEM_CONFIG = null;
	
	private static final boolean IS_DEBUG = false;
	
	static {
		try {
			if(IS_DEBUG) {
				//创建XML读取器
				SAXReader reader = new SAXReader();
				//读取XML文件
				Document doc = reader.read("data/cfg.xml");
				//获得XML文件的根结点
				Element game = doc.getRootElement();
				//穿件姐main配置对象
				FRAME_CONFIG = new FrameConfig(game.element("frame"));
				//创建数据访问配置对象
				DATA_CONFIG = new DataConfig(game.element("data"));
				//创建系统对象
				SYSTEM_CONFIG = new SystemConfig(game.element("system"));
			} else {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/framecfg.dat"));
				FRAME_CONFIG = (FrameConfig)ois.readObject();
				ois = new ObjectInputStream(new FileInputStream("data/systemcfg.dat"));
				SYSTEM_CONFIG = (SystemConfig)ois.readObject();
				ois = new ObjectInputStream(new FileInputStream("data/datacfg.dat"));
				DATA_CONFIG = (DataConfig)ois.readObject();
				ois.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 构造器私有化
	 */
	private GameConfig() {}
	
    /*
     * 获得窗口配置
     */
	public static FrameConfig getFrameConfig() {
		return FRAME_CONFIG;
	}
	
	/*
	 *获得系统配置 
	 */
	public static SystemConfig getSystemConfig() {
		return SYSTEM_CONFIG;
	}
	
	/*
	 * 获得数据访问配置
	 */
	public static DataConfig getDataConfig() {
		return DATA_CONFIG;
	}
	
	
	//写入文件时读取
//	public static void main(String[] args) throws Exception {
//		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/framecfg.dat"));
//		oos.writeObject(FRAME_CONFIG);
//		oos = new ObjectOutputStream(new FileOutputStream("data/systemcfg.dat"));
//		oos.writeObject(SYSTEM_CONFIG);
//		oos = new ObjectOutputStream(new FileOutputStream("data/datacfg.dat"));
//		oos.writeObject(DATA_CONFIG);
//	}
}
