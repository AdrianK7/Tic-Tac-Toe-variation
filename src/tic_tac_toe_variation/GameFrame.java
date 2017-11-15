package tic_tac_toe_variation;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
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
	
	private void iInit() 
	{		
		setLocationByPlatform(true);
		topPanel = new JPanel();
		rightPanel = new JPanel();
		leftPanel = new JPanel();
		centerPanel = new GameField(new GamePlay(20, 5));
		centerPanel.setFocusable(false);
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		Dimension dimCore = new Dimension();
		dimCore.setSize(minWidthFrame,minHeightFrame);
		this.setMinimumSize(dimCore);
		addButton(leftPanel, "testLeft", null);
		addButton(rightPanel, "testRight", null);
		getContentPane().setBackground(SystemColor.window);
		centerPanel.addMouseListener(new MouseListener());	
		addComponentListener(new CompListener());
		centerPanel.setIgnoreRepaint(false);
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
	
	private void addButton(JComponent comp, String label, GridBagConstraints constraints ) 
	{
		JButton button = new JButton(label);
		
		comp.add(new JButton(label), constraints);
		button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{

			}
		});
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
