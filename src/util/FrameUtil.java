package util;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class FrameUtil {
	
	public static void setFrameCenter(JFrame jf) {
	    //¾ÓÖÐ
	    Toolkit toolkit = Toolkit.getDefaultToolkit();
	    Dimension screen = toolkit.getScreenSize();
	    int w = screen.width - jf.getWidth() >> 1;
	    int h = (screen.height - jf.getHeight() >> 1) - 32;
	    jf.setLocation(w,h);
	}

}
