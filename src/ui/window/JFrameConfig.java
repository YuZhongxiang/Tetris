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

	private JButton btnOk = new JButton("ȷ��");
	
	private JButton btnCanel = new JButton("ȡ��");
	
	private JButton btnUser = new JButton("Ӧ��");
	
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
		//�����Ϸ����������
		this.gameControl = gameControl;
		//���ò��ֹ�����Ϊ�߽粼��
		this.setLayout(new BorderLayout());
		//���ñ���
		this.setTitle("����");
		//��ʼ�����������
		this.initgetkeyText();
		//��������
		this.add(createMainPanel(), BorderLayout.CENTER);
		//��Ӱ�ť���
		this.add(this.createButtonPanel(), BorderLayout.SOUTH);
		//���ù̶����ڴ�С
		this.setResizable(false);
		//���ô��ڴ�С
		this.setSize(650, 380);
		  //����
	    FrameUtil.setFrameCenter(this);
	}
    
	//��ʼ�����������
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

	//������ť���
	private JPanel createButtonPanel() {
		//������ť��壬��ʽ����
		JPanel jp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		//��ȷ����ť�����¼�����
		this.btnOk.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if(	writeConfig()) {				
					setVisible(false);
					gameControl.setOver();
				}
			}
		});
		//��ȡ����ť�����¼�����
		this.btnCanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				gameControl.setOver();
			}
	    });
		//��Ӧ�ð�ť�����¼�����
		this.btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeConfig();
				gameControl.repaint();
			}
		});
		//���ô�����Ϣǰ��ɫ
		this.errorMsg.setForeground(Color.RED);
		//���Ӵ�����Ϣ
		jp.add(this.errorMsg);
		//����ȷ����ť
		jp.add(this.btnOk);
		//����ȡ����ť
		jp.add(this.btnCanel);
		//����Ӧ�ð�ť
		jp.add(this.btnUser);
		return jp;
	}

	//���������(ѡ����)
	private JTabbedPane createMainPanel() {
		JTabbedPane jtp = new JTabbedPane();
		jtp.addTab("��������", this.createControlPanel());
		jtp.addTab("Ƥ������", this.createSkinPanel());
		return jtp;
	}
	
	//���Ƥ�����
	private Component createSkinPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		File dir = new File(Img.GRAPHICS_PATH);
		File[] files = dir.listFiles();
		this.skinViewList = new Image[files.length];
		for(int i = 0; i < files.length; i++) {
			//����ѡ��
			this.skinData.addElement(files[i].getName());
			//����Ԥ��ͼ
			this.skinViewList[i] = new ImageIcon(files[i].getPath() + "\\view.jpg").getImage();
		}
		//����б����鵽�б�
		this.skinList = new JList(this.skinData);
		//����Ĭ��ѡ�е�һ��
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

	//��ҿ����������
	private JPanel createControlPanel() {
		JPanel jp = new JPanel() {
			
			public void paintComponent(Graphics g) {
				g.drawImage(IMG_PSP, 0, 0, null);
			}
		};
		//���ò��ֹ�����
		jp.setLayout(null);
		for(int i = 0; i < keyText.length; i++) {
			jp.add(keyText[i]);
		}
		return jp;
	}

	//д����Ϸ����
	private boolean writeConfig() {
		HashMap<Integer, String> keySet = new HashMap<Integer, String>();
		for(int i = 0; i < this.keyText.length; i++) {
			int keyCode = this.keyText[i].getKeyCode();
			if(keyCode == 0) {
				this.errorMsg.setText("���󰴼�");
				return false;
			}
			keySet.put(keyCode, this.keyText[i].getMethodName());
		}
		if(keySet.size() != 8) {
			this.errorMsg.setText("�ظ�����");
			return false;
		}
		try {
			//�л�Ƥ��
			Img.setSkin(this.skinData.get(this.skinList.getSelectedIndex()).toString() + "/");
			
			//д���������
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
