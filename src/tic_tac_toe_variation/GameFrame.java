package tic_tac_toe_variation;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.SystemColor;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class GameFrame extends JFrame
{
	private JPanel topPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel centerPanel;
	private final int minWidthFrame;
	private final int minHeightFrame;
	
	public GameFrame() 
	{
		minHeightFrame = 700;
		minWidthFrame = 700;
		iInit();
	}
	
	public GameFrame(int minHeightFrame, int minWidthFrame) 
	{		
		this.minHeightFrame = minHeightFrame;
		this.minWidthFrame = minWidthFrame;
		iInit();
		
	}
	public void stops() {
		
	}
	private void iInit() 
	{		
		setLocationByPlatform(true);
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		Dimension dimCore = new Dimension();
		dimCore.setSize(minWidthFrame,minHeightFrame);
		this.setMinimumSize(dimCore);
		getContentPane().setBackground(SystemColor.window);
		addComponentListener(new CompListener());
		
		topPanel = new JPanel();
		Player p1 = Player.ELLIPSE;
		Player p2 = Player.CROSS;
		GamePlay gPlay = new GamePlay(Player.ELLIPSE, Player.CROSS);
		centerPanel = new GameField(gPlay);
		centerPanel.setFocusable(false);
		centerPanel.addMouseListener(new MouseListener());
		centerPanel.setIgnoreRepaint(false);
		rightPanel = new PlayerPanel(p1, centerPanel);
		rightPanel.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		leftPanel = new PlayerPanel(p2, centerPanel);
		leftPanel.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));

		this.add(leftPanel, makeConstraints(GridBagConstraints.BOTH, GridBagConstraints.CENTER, 	0, 0, 0, 0, 1, 2));
		this.add(centerPanel, makeConstraints(GridBagConstraints.BOTH, GridBagConstraints.CENTER, 	2, 1, 1, 1, 1, 1));
		this.add(rightPanel, makeConstraints(GridBagConstraints.BOTH, GridBagConstraints.CENTER, 	0, 0, 2, 0, 1, 2));
		this.add(topPanel, makeConstraints(GridBagConstraints.BOTH, GridBagConstraints.CENTER, 		0, 0, 1, 0, 1, 1));
	}
	
	private GridBagConstraints makeConstraints(int fill, int anchor, int weightx, int weighty, int gridx, int gridy, 
												int gridwidth, int gridheight ) 
	{
		GridBagConstraints constraint = new GridBagConstraints();
		
		constraint.fill = fill;
		constraint.anchor = anchor;
		constraint.weightx = weightx;
		constraint.weighty = weighty;
		constraint.gridx = gridx;
		constraint.gridy = gridy;
		constraint.gridwidth = gridwidth;
		constraint.gridheight = gridheight;
		
		return constraint;
		
	}
	
	class MouseListener extends MouseAdapter
	{
		
		public void mouseClicked(MouseEvent e) 
		{
			repaint();
		}
	}
	
	class CompListener extends ComponentAdapter
	{
		
	    public void componentResized(ComponentEvent e)
		{
	    	
		}
	}
}
