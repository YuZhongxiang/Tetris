package ui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import control.GameControl;
import ui.Img;
import util.FrameUtil;

public class JFrameConfig extends JFrame {

	private JButton btnOk = new JButton("确定");
	
	private JButton btnCanel = new JButton("取消");
	
	private JButton btnUser = new JButton("应用");
	
	private TextCtrl[] keyText = new TextCtrl[8];	
	
	private final static Image IMG_PSP = new ImageIcon("data/psp.png").getImage(); 
	
	private Image[] skinViewList = null;
	
	private final static String PATH = "data/control.dat";

	private JLabel errorMsg = new JLabel();
	
	private JList skinList = null;
	
	private JPanel skinView = null;
	
	private DefaultListModel skinData = new DefaultListModel();

	private GameControl gameControl;
	
	private final static String[] METHOD_NAMES = {
		"keyRight","keyUp","keyLeft","keyDown",
	    "keyFunLeft","keyFunUp","keyFunRight","keyFunDown"
	};
	
	public JFrameConfig(GameControl gameControl) {
		//获得游戏控制器对象
		this.gameControl = gameControl;
		//设置布局管理器为边界布局
		this.setLayout(new BorderLayout());
		//设置标题
		this.setTitle("设置");
		//初始化按键输入框
		this.initgetkeyText();
		//添加主面板
		this.add(createMainPanel(), BorderLayout.CENTER);
		//添加按钮面板
		this.add(this.createButtonPanel(), BorderLayout.SOUTH);
		//设置固定窗口大小
		this.setResizable(false);
		//设置窗口大小
		this.setSize(650, 380);
		  //居中
	    FrameUtil.setFrameCenter(this);
	}
    
	//初始化按键输入框
	private void initgetkeyText() {
		int x = 3;
		int y = 57;
		int w = 70;
		int h = 20;
		for(int i = 0; i < 4; i++) {
			keyText[i] = new TextCtrl(x,y,w,h, METHOD_NAMES[i]);
			y += 32;
		}
	    y = 60;
		x = 570;
		for(int i = 4; i < 8; i++) {
			keyText[i] = new TextCtrl(x,y,w,h, METHOD_NAMES[i]);
			y += 32;
		}
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PATH));
			@SuppressWarnings("unchecked")
			HashMap<Integer, String> cfgSet = (HashMap<Integer, String>) ois.readObject();
			ois.close();
			Set<Entry<Integer, String>> entryset = cfgSet.entrySet();
			for(Entry<Integer, String> e : entryset) {
				for(TextCtrl tc : keyText) {
					if(tc.getMethodName().equals(e.getValue())) {
						tc.setKeyCode(e.getKey());;
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//创建按钮面板
	private JPanel createButtonPanel() {
		//创建按钮面板，流式布局
		JPanel jp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		//给确定按钮增加事件监听
		this.btnOk.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if(	writeConfig()) {				
					setVisible(false);
					gameControl.setOver();
				}
			}
		});
		//按取消按钮增加事件监听
		this.btnCanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				gameControl.setOver();
			}
	    });
		//按应用按钮增加事件监听
		this.btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeConfig();
				gameControl.repaint();
			}
		});
		//设置错误信息前景色
		this.errorMsg.setForeground(Color.RED);
		//增加错误信息
		jp.add(this.errorMsg);
		//增加确定按钮
		jp.add(this.btnOk);
		//增加取消按钮
		jp.add(this.btnCanel);
		//增加应用按钮
		jp.add(this.btnUser);
		return jp;
	}

	//创建主面板(选项卡面板)
	private JTabbedPane createMainPanel() {
		JTabbedPane jtp = new JTabbedPane();
		jtp.addTab("控制设置", this.createControlPanel());
		jtp.addTab("皮肤设置", this.createSkinPanel());
		return jtp;
	}
	
	//玩家皮肤面板
	private Component createSkinPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		File dir = new File(Img.GRAPHICS_PATH);
		File[] files = dir.listFiles();
		this.skinViewList = new Image[files.length];
		for(int i = 0; i < files.length; i++) {
			//增加选项
			this.skinData.addElement(files[i].getName());
			//增加预览图
			this.skinViewList[i] = new ImageIcon(files[i].getPath() + "\\view.jpg").getImage();
		}
		//添加列表数组到列表
		this.skinList = new JList(this.skinData);
		//设置默认选中第一个
		this.skinList.setSelectedIndex(0);
		this.skinList.addMouseListener(new MouseAdapter() {
   			public void mouseReleased(MouseEvent e) {
   				repaint();
   			}
		});
		this.skinView = new JPanel() {
			public void paintComponent(Graphics g) {
				Image showImg = skinViewList[skinList.getSelectedIndex()];
				int x = this.getWidth() - showImg.getWidth(null) >> 1;
				int y = this.getHeight() - showImg.getHeight(null) >> 1;
				g.drawImage(showImg, x, y, null);
		    }
		};
		panel.add(new JScrollPane(this.skinList), BorderLayout.WEST);
		panel.add(new JScrollPane(this.skinView), BorderLayout.CENTER);
		return panel;
	}

	//玩家控制设置面板
	private JPanel createControlPanel() {
		JPanel jp = new JPanel() {
			
			public void paintComponent(Graphics g) {
				g.drawImage(IMG_PSP, 0, 0, null);
			}
		};
		//设置布局管理器
		jp.setLayout(null);
		for(int i = 0; i < keyText.length; i++) {
			jp.add(keyText[i]);
		}
		return jp;
	}

	//写入游戏配置
	private boolean writeConfig() {
		HashMap<Integer, String> keySet = new HashMap<Integer, String>();
		for(int i = 0; i < this.keyText.length; i++) {
			int keyCode = this.keyText[i].getKeyCode();
			if(keyCode == 0) {
				this.errorMsg.setText("错误按键");
				return false;
			}
			keySet.put(keyCode, this.keyText[i].getMethodName());
		}
		if(keySet.size() != 8) {
			this.errorMsg.setText("重复按键");
			return false;
		}
		try {
			//切换皮肤
			Img.setSkin(this.skinData.get(this.skinList.getSelectedIndex()).toString() + "/");
			
			//写入控制配置
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PATH));
		    oos.writeObject(keySet);
		    oos.close();
		} catch (Exception e) {
			this.errorMsg.setText(e.getMessage());
			return false;
		}
		this.errorMsg.setText(null);
		return true;
	}
}
