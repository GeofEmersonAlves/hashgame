package br.com.geofemersonalves.hashgame.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import br.com.geofemersonalves.hashgame.controller.HashGameController;
import br.com.geofemersonalves.hashgame.controller.InvalidHashMoveException;
import br.com.geofemersonalves.hashgame.model.HashGame;
/**
 *  A view for the Hash Game
 *  
 * @author Emerson Alves da Silva
 * @version 1.0 starts in (24/08/2019)
 */
public class HashGameView  extends JFrame{
	private static final int POS_X=80;
	private static final int POS_Y=230;
	
	private HashGameController gameController;

	private BorderLayout	borderLayoutGUI;
	private Object			symbolsHashGameImg[];
	private BorderLayout	borderLayoutComboBox;
	private JPanel			panelCentral;
	private JLabel			labelSymbol;
	private JComboBox		comboBoxSymbols;
	
	private JButton 		startButton;
	private JButton 		newGameButton;
	private Hash			hash;
	
	private GridLayout		gridLayoutHashImg;
	private JPanel 			panelGUIHashGame;
	private JLabel			labelImgHashSymbol[][]; 
	
	private JLabel			lineHorizontal;
	private JLabel			LineVertical;
	private JLabel			line45Degrees;
	private JLabel			lineMinus45Degrees;

	
	//Constructor
	public HashGameView() {
		initComponents();  
		intView();  
		init(); 
	}

	
	//INICIAR/INSTANCIAR TODOS OS COMPONENTES QUE PERTENCEM A JANELA
	private void initComponents() {
		borderLayoutGUI	= new BorderLayout(5,5);
				
		String urlSymbol_O = getClass().getResource("/images/Symbol_OSmall.png").getPath();
		String urlSymbol_X = getClass().getResource("/images/Symbol_XSmall.png").getPath();
		symbolsHashGameImg = new Object[2];
		symbolsHashGameImg[0] = new ImageIcon(urlSymbol_O);
		symbolsHashGameImg[1] = new ImageIcon(urlSymbol_X); 
		comboBoxSymbols = new JComboBox(symbolsHashGameImg);
		
		panelCentral = new JPanel(null);
		labelSymbol = new JLabel();
		startButton= new JButton();
		newGameButton= new JButton();
		hash= new Hash();	
		//Inicialize the lines who indicate when someone wins
		initLines();
		
		labelImgHashSymbol = new JLabel[3][3];
		
		for(int i=0;i<3;i++) {
			for(int c=0;c<3;c++) {
				labelImgHashSymbol[i][c]= new JLabel();//Symbol_O();
			}
		}
	}

	//PREPARA A VISUALIZAÇÃO/LAYOUT PARA OS COMPONENTES DA JANELA
	private void intView() { 
		labelSymbol.setText("Choose a symbol to play:");
		labelSymbol.setBounds(50, 105, 150, 20);
		comboBoxSymbols.setBounds(200,108,40,30);
		hash.setBounds(70, 220, 330, 330);	
		hash.setVisible(false);
		//hash.setBorder(BorderFactory.createLineBorder(Color.black));

		Image imageButton;
		try {
			imageButton = ImageIO.read(getClass().getResource("/images/ToBattleButton.png"));
			startButton.setIcon(new ImageIcon(imageButton));
			startButton.setBounds(260, 108, 81, 30);
			startButton.addActionListener(e -> startHashGameView(e));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			imageButton = ImageIO.read(getClass().getResource("/images/NewGameButton.png"));
			newGameButton.setIcon(new ImageIcon(imageButton));
			newGameButton.setBounds(260, 140, 81, 30);
			newGameButton.setVisible(false);
			newGameButton.addActionListener(e -> newHashGameView(e));
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		for(int i=0;i<3;i++) {

			for(int c=0;c<3;c++) {
				labelImgHashSymbol[i][c].setBounds(POS_X+(c*110), POS_Y+(i*110), 108, 108);
				labelImgHashSymbol[i][c].addMouseListener(new MouseAdapter() {
	                @Override
	                public void mouseClicked(MouseEvent e) {
		                	int x = e.getComponent().getX();
		                	int y = e.getComponent().getY();
		                	int _i= (y - POS_Y)/110;
		                	int _c= (x - POS_X)/110;
		                	//System.out.printf("x %d - y %d\n", x,y);
		                	//System.out.printf("i %d - c %d\n", _i,_c);
		                	if(gameController.validHashMove(_i, _c)) {
			                	doTheHashGame(_i,_c,HashGame.USERPLAYERVALUE);
			                    
			                    if(!gameController.isGameEnded()) {
			                    	int[] cpGameMove=new int[2];
			                    	cpGameMove = gameController.doTheComputerMove(_i, _c);
			                    	doTheHashGame(cpGameMove[0], cpGameMove[1], HashGame.COMPUTERPLAYERVALUE);
			                    }	
		                	}	                     
	                }

	            });
				labelImgHashSymbol[i][c].setOpaque(false);
				labelImgHashSymbol[i][c].setVisible(false);
			}
		}
		panelCentral.add(labelSymbol);
		panelCentral.add(comboBoxSymbols);
		panelCentral.add(startButton);
		panelCentral.add(newGameButton);
		panelCentral.add(hash);
		for(int i=0;i<3;i++) {
			for(int c=0;c<3;c++) {
				panelCentral.add(labelImgHashSymbol[i][c]);
			}
		}		
		panelCentral.add(lineHorizontal);
		panelCentral.add(LineVertical);
		panelCentral.add(line45Degrees);
		panelCentral.add(lineMinus45Degrees);
		
		panelCentral.setOpaque(false);
	}
	
	//PREPARA INFORMAÇÕES ESPECIFICAS DA JANELA E PREPARA AS AÇÕES DISPONIVEIS
	private void init() {
		this.setTitle("# Hash Game #"); 
		gameController = new HashGameController();
		
		try {
			//Get the Paper Image and put it in the background of this frame
			String urlImage= getClass().getResource("/images/Folha06.png").getPath();
	        Image backgroundImage = ImageIO.read(new File(urlImage));
	        setSize(backgroundImage.getWidth(null)+5, backgroundImage.getHeight(null)+15);
	        setResizable(false);
	       
	        setContentPane(new JPanel(borderLayoutGUI) {
	            @Override public void paintComponent(Graphics g) {
	                g.drawImage(backgroundImage, 0, 0, null);
	            }
	        });
	        
	        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	        getContentPane().add(new JLabel(" "),BorderLayout.NORTH);
	        getContentPane().add(new JLabel(" "),BorderLayout.WEST);
	        getContentPane().add(panelCentral,BorderLayout.CENTER);

	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }		
	}
	private void initLines() {
		lineHorizontal=new JLabel();
		LineVertical=new JLabel();;
		line45Degrees=new JLabel();;
		lineMinus45Degrees=new JLabel();;

		lineHorizontal.setIcon(new ImageIcon(getClass().getResource("/images/HorizontalLine.png").getPath()));
		lineHorizontal.setBounds(POS_X,POS_Y+55,302,13);
		lineHorizontal.setVisible(false);
		
		LineVertical.setIcon(new ImageIcon(getClass().getResource("/images/VerticalLine.png").getPath()));
		LineVertical.setBounds(POS_X+55,POS_Y,9,315);
		LineVertical.setVisible(false);
		
		line45Degrees.setIcon(new ImageIcon(getClass().getResource("/images/Line45Degrees.png").getPath()));
		line45Degrees.setBounds(POS_X,POS_Y,330,330);
		line45Degrees.setVisible(false);
		
		lineMinus45Degrees.setIcon(new ImageIcon(getClass().getResource("/images/LineMinus45Degrees.png").getPath()));
		lineMinus45Degrees.setBounds(POS_X,POS_Y,330,330);                       
		lineMinus45Degrees.setVisible(false);
	}

	//Listener for the Button Start Game
	private Object startHashGameView(ActionEvent e) {
		newGameButton.setEnabled(false);
		newGameButton.setVisible(false);
		hash.setVisible(true);
		for(int i=0;i<3;i++) {
			for(int c=0;c<3;c++) {
				labelImgHashSymbol[i][c].setVisible(true);
			}
		}
		startButton.setEnabled(false);
		comboBoxSymbols.setEnabled(false);

		return null;
	}
	//Listener for the new game
	private Object newHashGameView(ActionEvent e) {
		gameController.makeNewHashGame();
		comboBoxSymbols.setEnabled(true);
		startButton.setEnabled(true);
		newGameButton.setEnabled(false);
		hideLines();
		
		for (int i=0; i<3; i++) {
			for(int c=0;c<3;c++) {
				labelImgHashSymbol[i][c].setIcon(null);
				labelImgHashSymbol[i][c].setVisible(false);
			}
		}
		return null;
	}


	private void hideLines() {
		lineHorizontal.setVisible(false);
		LineVertical.setVisible(false);
		line45Degrees.setVisible(false);
		lineMinus45Degrees.setVisible(false);
	}


	private void doTheHashGame(int i, int c, int playerValue) {
		if(!gameController.isGameEnded()) {
			putSymbolHere(i, c,playerValue);
			if(checkIfHasWon(playerValue)) {
				endHashGame(playerValue);
				if(playerValue==HashGame.USERPLAYERVALUE) {
					JOptionPane.showMessageDialog(null, "C O N G R A T U L A T I O N S! \nYou won the game!", "Hash Game", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "I  G O T  Y O U!\n I have won the game!", "Hash Game", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
		
		if(gameController.getNumMoves()==9) {
			endHashGame(playerValue);
			JOptionPane.showMessageDialog(null, "Nobody wins the game.\nYou are really good", "Hash Game", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
	//Put the symbol in the position that where clicked
	private void putSymbolHere(int i, int c, int playerValue) {
		int selectedComboIndex=comboBoxSymbols.getSelectedIndex();
		
		if(gameController.validHashMove(i, c) && !gameController.isGameEnded()) {
			try {
				gameController.makeMovePlayed(i, c, playerValue);
				if(playerValue==HashGame.USERPLAYERVALUE) {
					labelImgHashSymbol[i][c].setIcon(getSymbol(selectedComboIndex));
				} else {
					if(selectedComboIndex==0) {
						labelImgHashSymbol[i][c].setIcon(getSymbol(1));
					} else {
						labelImgHashSymbol[i][c].setIcon(getSymbol(0));
					}
					
				}
				labelImgHashSymbol[i][c].setHorizontalAlignment(SwingConstants.CENTER);
				
			} catch(InvalidHashMoveException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Erro",0); 
			}
		}
	}


	//Check if someone has won the game
	private boolean checkIfHasWon(int playerValue) {
		return gameController.hasWon(); 		
	}
	
	//Return the symbol that the Gamer choose to play
	private ImageIcon getSymbol(int symbolId) {
		String urlImgSymbol;
		
		if(symbolId==0) {
			urlImgSymbol= getClass().getResource("/images/Symbol_O.png").getPath();
		} else {
			urlImgSymbol= getClass().getResource("/images/Symbol_X.png").getPath();
		}
			
		ImageIcon imgIconSymbol_O=new ImageIcon(urlImgSymbol);
		return imgIconSymbol_O;		
	}
	
	//Ends the game
	private void endHashGame(int playerValue) {
		newGameButton.setEnabled(true);
		newGameButton.setVisible(true);
		gameController.setGameEnded();
		
		if(gameController.getNumMoves()<9) {
			switch(gameController.getWhereHasWin()){
				case HashGameController.WINLINE0:
					lineHorizontal.setBounds(POS_X,POS_Y+55,302,13);
					lineHorizontal.setVisible(true);
					
					break;
				case HashGameController.WINLINE1:
					lineHorizontal.setBounds(POS_X,POS_Y+155,302,13);
					lineHorizontal.setVisible(true);
					break;
				case HashGameController.WINLINE2:
					lineHorizontal.setBounds(POS_X,POS_Y+265,302,13);
					lineHorizontal.setVisible(true);
					break;
				case HashGameController.WINCOLUMN0:
					LineVertical.setBounds(POS_X+55,POS_Y,9,315);
					LineVertical.setVisible(true);
					break;
				case HashGameController.WINCOLUMN1:	
					LineVertical.setBounds(POS_X+155,POS_Y,9,315);
					LineVertical.setVisible(true);
					break;
				case HashGameController.WINCOLUMN2:			
					LineVertical.setBounds(POS_X+265,POS_Y,9,315);
					LineVertical.setVisible(true);
					break;
				case HashGameController.WINDIAGONAL0:			
					lineMinus45Degrees.setVisible(true);
					break;
				case HashGameController.WINDIAGONAL1:			
					line45Degrees.setVisible(true);
					break;
			}
		}
	}

}