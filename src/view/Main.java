package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mxgraph.view.mxGraph;

import beans.Node;
import rdfoperations.Query;

import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField inputValue;
	private JButton btnSearch;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 100);
		setTitle("Consultar RDF");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		contentPane.add(panel, BorderLayout.NORTH);
		
		inputValue = new JTextField();
		inputValue.setMinimumSize(new Dimension(200, 100));
		panel.add(inputValue);
		inputValue.setColumns(10);	
		
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String value = inputValue.getText();
				graphFactory graphFactory = new graphFactory();
				mxGraph g = new mxGraph();
				
				Object parent = g.getDefaultParent();
				JFrame f = new JFrame("Resultado");

				//get list
				ArrayList<Node> list = new ArrayList<Node>();
				Query q = new Query();
				String aux = q.query(value);
				//String aux = q.query("Silvio Santos");
				list = q.queryIsValueOf("<"+aux+">");
				Object[] list2 = new Object[100];
				Object father = graphFactory.newNode(g, parent,f, aux,100,500,null,"");
				list2[1] = father;
				int i = 2;
				for (Node n : list) {
					if (n.getValue() != "" && n.getValue() != null) {
						System.out.println("["+n.getProperty() + "] -> ["+n.getValue()+"]");
						if (q.filtro(n.getProperty())) {
							System.out.println("ENTROU");
							list2[i] = graphFactory.newNode(g, parent,f, n.getValue(),500,60*i,father,q.normalize(n.getProperty()));
							i++;
						}
					}
				}
				f.setSize(new Dimension(1000, 1000));
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				//f.setLocation(dim.width/2-f.getSize().width/2, dim.height/2-f.getSize().height/2);
				f.setExtendedState(JFrame.MAXIMIZED_BOTH); 
				f.setVisible(true);
			}
		});
		panel.add(btnSearch);
	}

}
