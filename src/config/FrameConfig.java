package config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

public class FrameConfig implements Serializable {
	
	private final String title;
	
	private final int windowUp;
	
	private final int width;
	
	private final int height;
	
	private final int padding;
	
	private final int border;
	
	private final int actsizeRol; 
	
	private final int loseIdx;
	
	//��ť����
	private final ButtonConfig buttonConfig;
    
	//ͼ������
	private List<LayerConfig> layersConfig;
	
	public FrameConfig(Element frame) {
		//��ô��ڿ��
		this.width = Integer.parseInt(frame.attributeValue("width"));
		//��ô��ڸ߶�
		this.height = Integer.parseInt(frame.attributeValue("height"));
		//��ô��ڴ�ϸ
		this.border = Integer.parseInt(frame.attributeValue("border"));
		//��ñ߿��ڱ߾�
		this.padding = Integer.parseInt(frame.attributeValue("padding"));
		//��ȡ����
		this.title = frame.attributeValue("title");
		//��ȡ���ڰθ�
		this.windowUp = Integer.parseInt(frame.attributeValue("windowUp"));
		//ͼ��ߴ���λ��ƫ����
		this.actsizeRol = Integer.parseInt(frame.attributeValue("actsizeRol"));
		//��Ϸʧ��ͼƬ
		this.loseIdx = Integer.parseInt(frame.attributeValue("loseIdx"));
		//��ȡ��������
		@SuppressWarnings("unchecked")
		List<Element> layers = frame.elements("layer");
		layersConfig = new ArrayList<LayerConfig>();
		//��ȡ���д�������
		for(Element layer: layers) {
			//���õ�����������
			LayerConfig lc = new LayerConfig(
					layer.attributeValue("className"),
					Integer.parseInt(layer.attributeValue("x")),
					Integer.parseInt(layer.attributeValue("y")),
					Integer.parseInt(layer.attributeValue("w")),
					Integer.parseInt(layer.attributeValue("h"))
			);
			layersConfig.add(lc);	
		}
		//��ʼ����ť����
		buttonConfig = new ButtonConfig(frame.element("button"));
	}

	public String getTitle() {
		return title;
	}

	public int getWindowUp() {
		return windowUp;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getPadding() {
		return padding;
	}

	public int getBorder() {
		return border;
	}

	public List<LayerConfig> getLayersConfig() {
		return layersConfig;
	}

	public int getActsizeRol() {
		return actsizeRol;
	}

	public int getLoseIdx() {
		return loseIdx;
	}

	public ButtonConfig getButtonConfig() {
		return buttonConfig;
	}
}
