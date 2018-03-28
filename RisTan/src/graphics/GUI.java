/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private GControl ctrl;
	private DrawPanel drawPanel;

	public GUI(GControl c) {
		super("SzoftechTutor");
		ctrl = c;
		setSize(500, 350);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);

		// Where the GUI is created:
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;

		// Create the menu bar.
		menuBar = new JMenuBar();

		// Build the first menu.
		menu = new JMenu("File");
		//menu.setMnemonic(KeyEvent.VK_F);
		menu.getAccessibleContext().setAccessibleDescription("File menu");
		menuBar.add(menu);

		// JMenuItems show the menu items
		menuItem = new JMenuItem("New", new ImageIcon("images/new.gif"));
		//menuItem.setMnemonic(KeyEvent.VK_N);
		menu.add(menuItem);

		// add a separator
		menu.addSeparator();

		menuItem = new JMenuItem("Pause", new ImageIcon("images/pause.gif"));
		//menuItem.setMnemonic(KeyEvent.VK_P);
		menu.add(menuItem);

		menuItem = new JMenuItem("Exit", new ImageIcon("images/exit.gif"));
		//menuItem.setMnemonic(KeyEvent.VK_E);
		menu.add(menuItem);

		setJMenuBar(menuBar);

		setVisible(true);

		/*
		 * JMenuBar menuBar = new JMenuBar();
		 * 
		 * JMenu menu = new JMenu("Start");
		 * 
		 * JMenuItem menuItem = new JMenuItem("Client"); menuItem.addActionListener(new
		 * ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { ctrl.startClient(); }
		 * }); menu.add(menuItem);
		 * 
		 * menuItem = new JMenuItem("Server"); menuItem.addActionListener(new
		 * ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { ctrl.startServer(); }
		 * }); menu.add(menuItem);
		 * 
		 * menuBar.add(menu);
		 * 
		 * menuItem = new JMenuItem("Clear"); menuItem.addActionListener(new
		 * ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 * drawPanel.points.clear(); drawPanel.repaint(); } }); menuBar.add(menuItem);
		 * 
		 * menuItem = new JMenuItem("Exit"); menuItem.addActionListener(new
		 * ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { System.exit(0); } });
		 * menuBar.add(menuItem);
		 * 
		 * setJMenuBar(menuBar);
		 * 
		 * JPanel inputPanel = new JPanel(); inputPanel.setBounds(30, 30, 200, 200);
		 * inputPanel.setBorder(BorderFactory.createTitledBorder("Input"));
		 * inputPanel.addMouseListener(new MouseAdapter() {
		 * 
		 * @Override public void mousePressed(MouseEvent e) { // System.out.println("X:"
		 * + e.getX() + " Y:" + e.getY()); ctrl.sendClick(new Point(e.getX(),
		 * e.getY())); } }); add(inputPanel);
		 * 
		 * drawPanel = new DrawPanel(); drawPanel.setBounds(230, 30, 200, 200);
		 * drawPanel.setBorder(BorderFactory.createTitledBorder("Draw"));
		 * add(drawPanel);
		 * 
		 * setVisible(true);
		 */
	}

	void addPoint(Point p) {
		drawPanel.points.add(p);
		drawPanel.repaint();
	}

	private class DrawPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		private ArrayList<Point> points = new ArrayList<Point>();

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			for (Point p : points) {
				g.drawOval(p.x, p.y, 10, 10);
			}
		}
	}
}
