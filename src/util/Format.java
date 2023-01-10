package util;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Format {
	
	private Format() {}
	
	public static String hours(Long duration) {
		
		Long milliseconds = duration / 100;
		Long seconds = duration / 1000;
		Long hours = seconds / 3600;
		seconds %= 3600;
		Long minutes = seconds / 60;
		seconds %= 60;
		return String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, milliseconds);
	}
	
	public static String punctuation(double number) {
		DecimalFormat df = new DecimalFormat(",##0.00");
		return df.format(number);
	}
	
	public static void classification(JLabel JLclassification, int IntClassification) {
		
		if(IntClassification == 1) {
			image("img\\icons\\icon-firstPlace.png",JLclassification); 
		}else if(IntClassification == 2) {
			image("img\\icons\\icon-secondPlace.png",JLclassification); 
		}else if(IntClassification == 3) {
			image("img\\icons\\icon-thirdPlace.png",JLclassification); 
		}else {
			JLclassification.setText(String.valueOf(IntClassification));
		}
		
	}
	
	public static void image(String path,JLabel Jlabel) {
		try {
			ImageIcon imgPersona = new ImageIcon(ImageIO.read(new File(path)));
			imgPersona.setImage(imgPersona.getImage().getScaledInstance(Jlabel.getWidth(), Jlabel.getHeight(), 100));
			Jlabel.setIcon(imgPersona);	

		} catch (IOException e) {
		}
	}
}
