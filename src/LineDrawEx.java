import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class LineDrawEx extends JFrame {
	int check = 3;
	Color color; 
	int size = 10;
	int style = 0;
	int delete = 0;
	int fill = 0;
	int Do = 0;
	int number = -1;
	int copy = 0;
	int move = 0;
	boolean erase = false;
	int toMove = 0;
	static Color b = new Color(221,221,221);
	String image_name = null;
	BufferedImage img  = null;
	String address = null;
	
	BufferedImage image = new BufferedImage(500,500, BufferedImage.TYPE_INT_RGB);
	
    JMenuBar mb = new JMenuBar();
    JMenuItem [] DrawItem = new JMenuItem [4];
    JMenuItem colorMenuItem = new JMenuItem("Color");
    JMenuItem [] FileMenu = new JMenuItem [6];
    JMenuItem [] EraseMenu = new JMenuItem [2];
    JMenuItem [] ThickMenu = new JMenuItem [3];
    JMenuItem [] StyleMenu = new JMenuItem [2];
    JMenuItem [] DoMenu = new JMenuItem [2];
    JMenuItem [] FillMenu = new JMenuItem [2];

    JMenu STMenu = new JMenu("Style"); 
    JMenu DMenu = new JMenu("Draw");
    JMenu DOMenu = new JMenu("Do");
    JMenu ColMenu = new JMenu("Color");
    JMenu FMenu = new JMenu("File");
    JMenu EMenu = new JMenu("Erase");
    JMenu TMenu = new JMenu("Thickness");
    JMenu SMenu = new JMenu("LineStyle");
    JMenu FSMenu = new JMenu("FigureStyle");
    MyPanel Mypanel = new MyPanel();
    JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    int returnValue ;
	public LineDrawEx(){
		createMenu();
		setContentPane(Mypanel);
		setSize(500,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
    void createMenu() {
    	MenuActionListener listener = new MenuActionListener(); 
    	String[] DrawItemS = {"Line","Circle","Square","Draw"};
    	String[] FileItems = {"Delete","Copy&Paste","Drag&Drop","Save","Load","Exit"};
    	String[] EraseItems = {"Erase","ObjectErase"};
    	String[] ThickItems = {"10","20","30"};
    	String[] StyleItems = {"Solid line","Dotted line"};
    	String[] DoItems = {"Undo","Redo"};
    	String[] FillItems = {"EmptyShape","FilledShapes"};
    	JButton a = new JButton("dd");
    	
    	colorMenuItem.addActionListener(new MenuActionListener());
    	ColMenu.add(colorMenuItem);
		for(int i=0; i<6; i++) {
			if(i<4) {
				DrawItem[i] = new JMenuItem(DrawItemS[i]); 
				DrawItem[i].addActionListener(listener); 
				DMenu.add(DrawItem[i]);
			}
			//if(i<5) {
				FileMenu[i] = new JMenuItem(FileItems[i]); 
				FileMenu[i].addActionListener(listener); 
				FMenu.add(FileMenu[i]);
			//}
			if(i<2) {
				EraseMenu[i] = new JMenuItem(EraseItems[i]); 
				EraseMenu[i].addActionListener(listener); 
				EMenu.add(EraseMenu[i]);
			}
			if(i<3) {
				ThickMenu[i] = new JMenuItem(ThickItems[i]); 
				ThickMenu[i].addActionListener(listener); 
				TMenu.add(ThickMenu[i]);
			}
			if(i<2) {
				StyleMenu[i] = new JMenuItem(StyleItems[i]); 
				StyleMenu[i].addActionListener(listener); 
				SMenu.add(StyleMenu[i]);
			}
			if(i<2) {
				DoMenu[i] = new JMenuItem(DoItems[i]); 
				DoMenu[i].addActionListener(listener); 
				DOMenu.add(DoMenu[i]);
			}
			if(i<2) {
				FillMenu[i] = new JMenuItem(FillItems[i]); 
				FillMenu[i].addActionListener(listener); 
				FSMenu.add(FillMenu[i]);
			}
		}
		
		mb.add(FMenu);
		mb.add(DMenu);
		mb.add(ColMenu);
		mb.add(EMenu);
		mb.add(TMenu);
		STMenu.add(SMenu);
		STMenu.add(FSMenu);
		mb.add(STMenu);
		mb.add(DOMenu);
        setJMenuBar(mb);
    }
    
   
    
    
    class MenuActionListener implements ActionListener { 
    	JColorChooser chooser=new JColorChooser();
    	
    	public void actionPerformed(ActionEvent e) {
    		String cmd = e.getActionCommand(); 
    		switch(cmd) { // 메뉴 아이템의 종류 구분
    			case "Line" : 	check = 1;
    				if(number != -1) number = -1;
    				if(copy != 0) copy = 0;
    				if(color == b) color = Color.BLACK;			
    				break; //1
    			case "Square": check = 2;
    				if(number != -1) number = -1;
    				if(copy != 0) copy = 0;
    				if(color == b) color = Color.BLACK;
    				break; //2
    			case "Draw" : check = 3;
    				if(number != -1) number = -1;
    				if(copy != 0) copy = 0;
    				if(color == b) color = Color.BLACK;
    				break; //3
    			case "Circle" : check = 4; 
    				if(number != -1) number = -1;
    				if(copy != 0) copy = 0;
    				if(color == b) color = Color.BLACK;
    				break; //4
    			case "Erase" : check = 3; 
    				if(number != -1) number = -1;
    				if(copy != 0) copy = 0;
    				color = b; break; //3
    			
    			case "Color" : Color selectedColor=chooser.showDialog(null,"Color",Color.YELLOW);
                	if(selectedColor!=null)
                    color = selectedColor;
                	break;
    			
    			case "10": size = 10; break;
    			case "20": size = 20; break;
    			case "30": size = 30; break;
    			
    			case "Solid line" : style = 0; break;
    			case "Dotted line" : style = 1; break;
    			
    			case "Delete" : delete = 1; repaint(); break;
    			
    			case "Undo" : Do = 1; repaint(); break;
    			case "Redo" : Do = 2; repaint(); break;
    			
    			case "EmptyShape":  fill = 0; break;
    			case "FilledShapes" : fill = 1; break; 
    			case "ObjectErase"  : check =0; erase = true; break;
    			case "Copy&Paste" : copy = 1; check =0; break;
    			case "Drag&Drop"  : if(copy != 0) copy = 0; move = 1; check =0; break;
    			case "Exit" : System.exit(0); break;
    			case "Save" : 
    				Mypanel.printAll(image.getGraphics());
    				returnValue = jfc.showSaveDialog(null);
    				if(returnValue == JFileChooser.APPROVE_OPTION) {
    					File selectedFile = jfc.getSelectedFile();
    					address = selectedFile.getAbsolutePath();
    				}
    				try {
    					address = address +".jpg";
    					ImageIO.write(image,"jpg",new File(address));
    				}catch(IOException e1){
    					e1.printStackTrace();
    				}
    					
    				 break;
    			case "Load" : 
    				   returnValue = jfc.showOpenDialog(null);
    					if (returnValue == JFileChooser.APPROVE_OPTION) {
    					File selectedFile = jfc.getSelectedFile();
    					System.out.println(selectedFile.getAbsolutePath());
    					try {
        					img = ImageIO.read(new File(selectedFile.getAbsolutePath()));
        				}catch(IOException e1) {
        					JOptionPane.showMessageDialog(null, "Fail");
        				}
    					repaint();
    				} //L
    				   break;
    		}
    	}
    }
	
	class MyPanel extends JPanel{
		
		Point startP=null;
		Point endP=null;
		Point RealP=null;
		
		int vectorCount = 0;
		
		Vector<Vector<Point>> saveVector = new Vector<Vector<Point>>();
		Vector<Point> savePoint = new Vector<Point>();
		Vector<Figure> vForF = new Vector<Figure>(); 
		Vector<Figure> undo = new Vector<Figure>();
	

		public MyPanel(){
			MyMouseListener ml = new MyMouseListener();
			this.setBackground(b);
			this.addMouseListener(ml);
			this.addMouseMotionListener(ml);
		}
		
		public void paintComponent(Graphics g){
			super.paintComponent(g); // 부모 페인트호출	
			Graphics2D g2 = (Graphics2D)g;
			if(img != null) g.drawImage(img, 0, 0, null);
			if(delete == 0) {
					if(Do == 1) {
						if(vForF.size()!=0) {
						undo.add(vForF.lastElement());
						int temp = vForF.size()-1;
						vForF.remove(temp);
						}
						Do = 0;
						startP=null;
						endP=null;
						repaint();
					}
					else if(Do == 2) {
						if(undo.size()!=0) {
							vForF.add(undo.lastElement());
							int temp = undo.size()-1;
							undo.remove(temp);
						}
						Do = 0;
						startP=null;
						endP=null;
						repaint();
					}
					
					for(Figure A: vForF) {
						setting (g2,A);
						if(A.getCheck() == 1) {
							g.drawLine(A.getStartP().x, A.getStartP().y,A.getEndP().x, A.getEndP().y);
						}//Line
						else if(A.getCheck() == 2) {
							if(A.getEndP().x > A.getStartP().x) {
								if(A.getEndP().y > A.getStartP().y) {
									if(A.getFill() == 0) g2.drawRect(A.getStartP().x,A.getStartP().y,A.getEndP().x - A.getStartP().x ,A.getEndP().y - A.getStartP().y);
									else 	g2.fillRect(A.getStartP().x,A.getStartP().y,A.getEndP().x - A.getStartP().x ,A.getEndP().y - A.getStartP().y);
								}
								else {
									if(A.getFill() == 0)
									g2.drawRect(A.getStartP().x,A.getEndP().y,A.getEndP().x - A.getStartP().x ,A.getStartP().y - A.getEndP().y);
									else g2.fillRect(A.getStartP().x,A.getEndP().y,A.getEndP().x - A.getStartP().x ,A.getStartP().y - A.getEndP().y);
								}
							}
							else {
								if(A.getEndP().y < A.getStartP().y) {
									if(A.getFill() == 0)
									g2.drawRect(A.getEndP().x,A.getEndP().y ,A.getStartP().x - A.getEndP().x , A.getStartP().y - A.getEndP().y );
									else g2.fillRect(A.getEndP().x,A.getEndP().y ,A.getStartP().x - A.getEndP().x , A.getStartP().y - A.getEndP().y );
								}
								else {
									if(A.getFill() == 0)
									g2.drawRect(A.getEndP().x,A.getStartP().y,A.getStartP().x - A.getEndP().x ,A.getEndP().y - A.getStartP().y);
									else g2.fillRect(A.getEndP().x,A.getStartP().y,A.getStartP().x - A.getEndP().x ,A.getEndP().y - A.getStartP().y);
								}
							}
						}//Square
						else if(A.getCheck() == 3) {
							for(int i = 0; i < A.getForDraw().size()-2; i++) {
								g2.drawLine(A.getForDraw().get(i).x, A.getForDraw().get(i).y, A.getForDraw().get(i+1).x, A.getForDraw().get(i+1).y);
							}
						}//My
						else if(A.getCheck() == 4) {
							if(A.getEndP().x > A.getStartP().x) {
								if(A.getEndP().y > A.getStartP().y) {
									if(A.getFill() == 0) g2.drawOval(A.getStartP().x,A.getStartP().y,A.getEndP().x - A.getStartP().x ,A.getEndP().y - A.getStartP().y);
									else g2.fillOval(A.getStartP().x,A.getStartP().y,A.getEndP().x - A.getStartP().x ,A.getEndP().y - A.getStartP().y);
								}
								else {
									if(A.getFill() == 0)
									g2.drawOval(A.getStartP().x,A.getEndP().y,A.getEndP().x - A.getStartP().x ,A.getStartP().y - A.getEndP().y);
									else g2.fillOval(A.getStartP().x,A.getEndP().y,A.getEndP().x - A.getStartP().x ,A.getStartP().y - A.getEndP().y);
								}
								
							}
							else {
								if(A.getEndP().y < A.getStartP().y) {
									if(A.getFill() == 0) g2.drawOval(A.getEndP().x,A.getEndP().y ,A.getStartP().x - A.getEndP().x , A.getStartP().y - A.getEndP().y );
									else g2.fillOval(A.getEndP().x,A.getEndP().y ,A.getStartP().x - A.getEndP().x , A.getStartP().y - A.getEndP().y );
								}
								else {
									if(A.getFill() == 0) g2.drawOval(A.getEndP().x,A.getStartP().y,A.getStartP().x - A.getEndP().x ,A.getEndP().y - A.getStartP().y);
									else g2.fillOval(A.getEndP().x,A.getStartP().y,A.getStartP().x - A.getEndP().x ,A.getEndP().y - A.getStartP().y);
								}
							}
						}
					}
					if(startP != null) {
						if(copy != 0) {
							copy = 0;
						}
						else {
							setting (g2);
							if(check == 1) g.drawLine(startP.x, startP.y, endP.x, endP.y);	
							else if(check == 2) 	{
								if(endP.x > startP.x) {
									if(endP.y > startP.y) {
										if(fill == 0) g2.drawRect(startP.x,startP.y,endP.x - startP.x ,endP.y - startP.y);
										else g2.fillRect(startP.x,startP.y,endP.x - startP.x ,endP.y - startP.y);
									}
									else {
										if(fill == 0) g2.drawRect(startP.x,endP.y,endP.x -startP.x ,startP.y - endP.y);
										else g2.fillRect(startP.x,endP.y,endP.x -startP.x ,startP.y - endP.y);
									}
										
								}
								else {
									if(endP.y < startP.y) {
										if(fill == 0) g2.drawRect(endP.x,endP.y,startP.x - endP.x ,startP.y - endP.y);
										else g2.fillRect(endP.x,endP.y,startP.x - endP.x ,startP.y - endP.y);
									}
									else {	if(fill == 0) g2.drawRect(endP.x,startP.y,startP.x - endP.x ,endP.y - startP.y);
									else g2.fillRect(endP.x,startP.y,startP.x - endP.x ,endP.y - startP.y);
									}
								}
							}
							else if(check == 3) {
								int  temp = saveVector.lastElement().size();
								if(endP!=null) g2.drawLine(saveVector.lastElement().get(temp-1).x, saveVector.lastElement().get(temp-1).y,endP.x, endP.y);
							}
							else if(check == 4) 	{
								if(endP.x > startP.x) {
									if(endP.y > startP.y) {
										if(fill == 0) g2.drawOval(startP.x,startP.y,endP.x - startP.x ,endP.y - startP.y);
										else g2.fillOval(startP.x,startP.y,endP.x - startP.x ,endP.y - startP.y);
									}
									else {
										if(fill == 0) g2.drawOval(startP.x,endP.y,endP.x -startP.x ,startP.y - endP.y);
										else g2.fillOval(startP.x,endP.y,endP.x -startP.x ,startP.y - endP.y);
									}
										
								}
								else {
									if(endP.y < startP.y) {
										if(fill == 0) g2.drawOval(endP.x,endP.y,startP.x - endP.x ,startP.y - endP.y);
										else g2.fillOval(endP.x,endP.y,startP.x - endP.x ,startP.y - endP.y);
									}
									else {	if(fill == 0) g2.drawOval(endP.x,startP.y,startP.x - endP.x ,endP.y - startP.y);
									else g2.fillOval(endP.x,startP.y,startP.x - endP.x ,endP.y - startP.y);
									}
								}
							}
						}
					}
			
			}//case delete == 1
			else {
				delete = 0;
				vForF.clear();
			}//case delete == 0
		}
		
		class MyMouseListener extends MouseAdapter implements MouseMotionListener{
			
			public void mousePressed(MouseEvent e){
				startP = e.getPoint();
				Vector<Point> sMyself = new Vector<Point>(); // 끝점
				saveVector.add(sMyself);
				if(copy == 1 || move == 1 || erase == true) {
				for(int t = 0 ;t<=vForF.size()-1;t++) {
						Point temp = e.getPoint();
						if(vForF.get(t).getCheck() == 3) {
							for(int i = 0;i< vForF.get(t).getForDraw().size();i++) {
								int P_x = vForF.get(t).getForDraw().get(i).x;
								int P_y = vForF.get(t).getForDraw().get(i).y;
								if(temp.x == P_x && temp.y == P_y) {
									number = t;
									break;
								}
							}
							vectorCount++;
						}//3
						else {
							if(Math.min(vForF.get(t).getStartP().x, vForF.get(t).getEndP().x)<=temp.x &&
									Math.min(vForF.get(t).getStartP().y, vForF.get(t).getEndP().y)<=temp.y
									&& Math.max(vForF.get(t).getStartP().x, vForF.get(t).getEndP().x) >temp.x &&
									Math.max(vForF.get(t).getStartP().y, vForF.get(t).getEndP().y)>temp.y)
								number = t;
						}
					}
					if(copy == 1 ) copy = 2;
					if(move == 1 ) move = 2;
					if(erase == true ) {
						undo.add(vForF.get(number));
						vForF.remove(number);
						erase = false;
						repaint();
					}
				}
				if(copy == 2) {
					if(number < 0) number = vForF.size()-1;
					if(vForF!= null) {
						if(vForF.get(number).check == 3) {
							Vector A = new Vector();
							int temp_x = startP.x - vForF.get(number).getForDraw().get(0).x;
							int temp_y = startP.y - vForF.get(number).getForDraw().get(0).y;
							for(int i = 0;i<vForF.get(number).getForDraw().size()-1;i++) {
								int x = vForF.get(number).getForDraw().get(i).x+temp_x;
								int y = vForF.get(number).getForDraw().get(i).y+temp_y;
								Point temp_p = new Point(x,y);
								A.add(temp_p);
							}
							Figure a = new Figure(vForF.get(number).getCheck(),vForF.get(number).getColor(),
									vForF.get(number).getSize(),vForF.get(number).getStyle(),A);
							vForF.add(a);
						}
						else{
							int temp_x = startP.x - vForF.get(number).getStartP().x;
							int temp_y = startP.y - vForF.get(number).getStartP().y;
							
							int x = vForF.get(number).getEndP().x + temp_x;
							int y = vForF.get(number).getEndP().y + temp_y;
							Point temp_p = new Point(x,y);
							
							Figure a = new Figure(vForF.get(number).getCheck(),vForF.get(number).getColor(),vForF.get(number).getSize(),vForF.get(number).getStyle(),
									vForF.get(number).getFill(),startP,temp_p);
							
							vForF.add(a);
						}
						startP=null;
						endP=null;
					}
				}//copy == 1
				else if(copy == 0){
					if(check==3 && erase == false) {
						sMyself.clear();
						vForF.add(new Figure(check,color,size,style,sMyself));
					}
				}//copy == 0
			}
			
			public void mouseReleased(MouseEvent e){
				RealP = e.getPoint();
				if(move == 2 ) {
					if(vForF.get(number).getCheck() != 3) {
						int temp_x = RealP.x - vForF.get(number).getStartP().x;
						int temp_y = RealP.y - vForF.get(number).getStartP().y;
						
						int x = vForF.get(number).getEndP().x + temp_x;
						int y = vForF.get(number).getEndP().y + temp_y;
						Point temp_p = new Point(x,y);
						
						vForF.get(number).setStartP(RealP); 
						vForF.get(number).setEndP(temp_p);
					}
					else {
						Vector<Point> temp_v = new Vector<Point>();
						int temp_x = RealP.x - startP.x;
						int temp_y = RealP.y - startP.y;
						for(int i = 0;i<vForF.get(number).getForDraw().size();i++) {
							temp_v.add(new Point(savePoint.get(i).x+temp_x,savePoint.get(i).y+temp_y));
						}
						vForF.get(number).setForDraw(temp_v);
					}//check == 3
					move = 0;
				}
				if(copy == 0) {
					if(check == 3) {
						saveVector.lastElement().add(e.getPoint());
						int temp = vForF.size()-1;
						vForF.get(temp).setForDraw(saveVector.lastElement());
					}//MySelf
					else 
						vForF.add(new Figure(check,color,size,style,fill,startP,RealP)); //Line //Square
				}
				repaint(); // 다시그려라
			}
			
			public void mouseDragged(MouseEvent e){
				endP = e.getPoint();
				if(move == 2 ) {
					if(number == -1) number = vForF.size()-1;
					//System.out.println(number);
					if(vForF.get(number).getCheck() != 3) {
						int temp_x = endP.x - vForF.get(number).getStartP().x;
						int temp_y = endP.y - vForF.get(number).getStartP().y;
						
						int x = vForF.get(number).getEndP().x + temp_x;
						int y = vForF.get(number).getEndP().y + temp_y;
						Point temp_p = new Point(x,y);
						
						vForF.get(number).setStartP(endP); 
						vForF.get(number).setEndP(temp_p); 
					}
					else {
						if(toMove == 0) {
							savePoint.clear();
							for(int i = 0;i<vForF.get(number).getForDraw().size();i++) {
								savePoint.add(new Point(vForF.get(number).getForDraw().get(i).x,vForF.get(number).getForDraw().get(i).y));
							}
							toMove = 1;
						}
						
						Vector<Point> temp_v = new Vector<Point>();
						int temp_x = endP.x - startP.x;
						int temp_y = endP.y - startP.y;
						for(int i = 0;i<vForF.get(number).getForDraw().size();i++) {
							temp_v.add(new Point(savePoint.get(i).x+temp_x,savePoint.get(i).y+temp_y));
						}
						vForF.get(number).setForDraw(temp_v);
					}//check == 3
				}
				if(check == 3) {
					saveVector.lastElement().add(e.getPoint());
					int temp = vForF.size();
				}//MySelf
				repaint();
			}
			public void mouseMoved(MouseEvent e){
			}
		}
	}
	
	void setting (Graphics2D g2,Figure A) {
	    float[] dash=new float[]{10,5,5,5};
		g2.setColor(A.getColor());
		if(A.getStyle() == 0) {
			if(A.getSize() == 5) g2.setStroke(new BasicStroke(5,1,BasicStroke.CAP_ROUND,1.0f,null, 0));
			else if(A.getSize() == 10) g2.setStroke(new BasicStroke(10,1,BasicStroke.CAP_ROUND,1.0f,null, 0));
			else if(A.getSize() == 20) g2.setStroke(new BasicStroke(20,1,BasicStroke.CAP_ROUND,1.0f,null, 0));
			else if(A.getSize() == 30) g2.setStroke(new BasicStroke(30,1,BasicStroke.CAP_ROUND,1.0f,null, 0));
		}
		else if(A.getStyle() == 1) {
			if(A.getSize() == 5) g2.setStroke(new BasicStroke(5,0,BasicStroke.CAP_ROUND,1.0f,dash, 0));
			if(A.getSize() == 10) g2.setStroke(new BasicStroke(10,0,BasicStroke.CAP_ROUND,1.0f,dash, 0));
			else if(A.getSize() == 20) g2.setStroke(new BasicStroke(20,0,BasicStroke.CAP_ROUND,1.0f,dash, 0));
			else if(A.getSize() == 30) g2.setStroke(new BasicStroke(30,0,BasicStroke.CAP_ROUND,1.0f,dash, 0));
		}
	}
	
	void setting (Graphics2D g2) {
	    float[] dash=new float[]{10,5,5,5};
		g2.setColor(color);
		if(style == 0) {
			if(size == 5) g2.setStroke(new BasicStroke(5,1,BasicStroke.CAP_ROUND,1.0f,null, 0));
			else if(size  == 10) g2.setStroke(new BasicStroke(10,1,BasicStroke.CAP_ROUND,1.0f,null, 0));
			else if(size  == 20) g2.setStroke(new BasicStroke(20,1,BasicStroke.CAP_ROUND,1.0f,null, 0));
			else if(size  == 30) g2.setStroke(new BasicStroke(30,1,BasicStroke.CAP_ROUND,1.0f,null, 0));
		}
		else if(style == 1) {
			if(size  == 5) g2.setStroke(new BasicStroke(5,0,BasicStroke.CAP_ROUND,1.0f,dash, 0));
			if(size  == 10) g2.setStroke(new BasicStroke(10,0,BasicStroke.CAP_ROUND,1.0f,dash, 0));
			else if(size  == 20) g2.setStroke(new BasicStroke(20,0,BasicStroke.CAP_ROUND,1.0f,dash, 0));
			else if(size  == 30) g2.setStroke(new BasicStroke(30,0,BasicStroke.CAP_ROUND,1.0f,dash, 0));
		}
	}
	
	public static void main(String[] args) {
		new LineDrawEx();

	}
}