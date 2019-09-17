package br.com.geofemersonalves.hashgame.view;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
/**
 * A JLabel with the a empty hash imageIcon inside
 * 
 * @author Emerson Alves da Silva
 * @version 1.0 (25/08/2019)
 */
public class Hash extends JLabel {

		public Hash() {
			//String urlImageO= getClass().getResource("/images/Hash.png").getPath();
			
			String urlImageO="/images/Hash.png";
			//System.out.println(urlImageO);
			ImageIcon PostItimgIcon=null;
			
			try {
				PostItimgIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream(urlImageO)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			setHorizontalAlignment(SwingConstants.CENTER);
			this.setIcon(PostItimgIcon);					
		}
		
}
