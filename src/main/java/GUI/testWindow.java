package GUI;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class testWindow {
	JFrame body; 
/* sample input	
|-----------0-----|-0---------------|
|---------0---0---|-0---------------|
|-------1-------1-|-1---------------|
|-----2-----------|-2---------------|
|---2-------------|-2---------------|
|-0---------------|-0---------------|
 */
	
	
	public void run() {
		LoadScreen();
	}
    
    
	void LoadScreen() {
	    makeScreen();
	}

	public void makeScreen() {
	    body = new JFrame();
	    body.setVisible(true);
	    body.setSize(1200, 800);
	    body.setLocationRelativeTo(null);
	    
	    
	    
	}
/*
	public void insertBar() {
	    bar.setValue(0);
	    bar.setBackground(Color.BLACK); bar.setForeground(Color.white);
	    int width = 350;
	    int height = 20;
	    int offset = 20;
	    bar.setBounds((body.getBounds().width/2)-(width/2), body.getBounds().height-offset-height, width, height);
	    bar.setBorderPainted(true);
	    body.add(bar);
	}



*/
}
