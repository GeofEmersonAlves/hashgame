package br.com.geofemersonalves.hashgame.view;

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
			String urlImageO= getClass().getResource("/images/Hash.png").getPath();
			//System.out.println(urlImageO);
			ImageIcon PostItimgIcon=new ImageIcon(urlImageO);
			setHorizontalAlignment(SwingConstants.CENTER);
			this.setIcon(PostItimgIcon);					
		}
		
}
