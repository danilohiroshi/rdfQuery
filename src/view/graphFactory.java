package view;

import java.awt.Button;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import javax.swing.JFrame;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

import beans.Node;
import rdfoperations.Query;

public class graphFactory
{
	/**
	 * 
	 */
	//private static final long serialVersionUID = -2707712944901661771L;

	public graphFactory()
	{

	}
	public static Object newNode(mxGraph graph,Object parent, JFrame f, String name, int x, int y, Object father, String propriedade){


		graph.getModel().beginUpdate();
		Object v1 = null;
		try{
			if (father == null) {
				
				v1 = graph.insertVertex(parent, null, name,  x, y, name.length()*6, 50,"ROUNDED;strokeColor=red;");
			}else{
				
				v1 = graph.insertVertex(parent, null, name, x, y, name.length()*6, 50,"ROUNDED;strokeColor=blue;");
			}
			
			if(father != null){
				graph.insertEdge(parent, null, propriedade, v1, father);
			}
		}
		finally{
			graph.getModel().endUpdate();
		}
		
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		graphComponent.getGraphControl().addMouseListener(new MouseAdapter()
		{
		
			public void mouseReleased(MouseEvent e)
			{
				if (e.getClickCount() == 2 && !e.isConsumed()) {
				     e.consume();
				     //handle double click event.
				     Object cell = graphComponent.getCellAt(e.getX(), e.getY());
						
					if (cell != null)
					{
						System.out.println("cell="+graph.getLabel(cell));
						ArrayList<Node> list = new ArrayList<Node>();
						Query q = new Query();
						//String aux = q.query(value);
						list = q.queryIsValueOf("<"+graph.getLabel(cell)+">");
						int i = 2;
						for (Node n : list) {
							if (n.getValue() != "" && n.getValue() != null) {
								System.out.println("["+n.getProperty() + "] -> ["+n.getValue()+"]");
								if (q.filtro(n.getProperty())) {
									graphFactory.newNode(graph, parent,f, n.getValue(),e.getX()+200,e.getY() - 200 + 60*i,cell,q.normalize(n.getProperty()));
									i++;
								}
							}
						}
					}
				}	
			}
		});
		f.add(graphComponent);
		return v1;
	}

}
