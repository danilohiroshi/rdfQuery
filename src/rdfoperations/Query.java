package rdfoperations;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;

import beans.Node;

public class Query {
	public static ArrayList<Node> queryIsValueOf(String uri){
		ArrayList<Node> list = new ArrayList<Node>();
		
        String service = "http://dbpedia.org/sparql";
        String queryString;
        queryString = 
                  "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/>"
				+ "PREFIX dc: <http://purl.org/dc/elements/1.1/>"
				+ "PREFIX : <http://dbpedia.org/resource/>"
				+ "PREFIX dbpedia2: <http://dbpedia.org/property/>"
				+ "PREFIX dbpedia: <http://dbpedia.org/>"
				+ "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> "
                + " SELECT ?property ?hasValue ?isValueOf WHERE { "
                + " { "+ uri +"?property ?hasValue } "
                + " UNION "
                + " { ?isValueOf ?property "+uri+" }} ";
   
        System.out.println(queryString);
        org.apache.jena.query.Query query = QueryFactory.create(queryString);
        QueryEngineHTTP qexec = QueryExecutionFactory.createServiceRequest(service, query);
        org.apache.jena.query.ResultSet results = qexec.execSelect();
        //ResultSetFormatter.out(System.out, results, query);

        for ( ; results.hasNext() ; ) {
        	Node n = new Node();
        	
            QuerySolution soln = results.nextSolution() ;
            
            try {
				//System.out.println( "hasValue: "+soln.get("hasValue").toString() + "\n");
				//System.out.println( "property: "+soln.get("property").toString() + "\n");
				n.setValue(soln.get("hasValue").toString());
				n.setProperty(soln.get("property").toString());
			} catch (Exception e) {

			}  
            try {
				//System.out.println( "isValueOf: "+soln.get("isValueOf").toString() + "\n");
				//System.out.println( "property: "+soln.get("property").toString() + "\n");
				n.setValue(soln.get("hasValue").toString());
				n.setProperty(soln.get("property").toString());
			} catch (Exception e) {
			}
            list.add(n);
        }
        return list;
    }
	
	public static String query(String nome){
        String service = "http://dbpedia.org/sparql";
        String queryString;
        queryString = 
                  "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/>"
				+ "PREFIX dc: <http://purl.org/dc/elements/1.1/>"
				+ "PREFIX : <http://dbpedia.org/resource/>"
				+ "PREFIX dbpedia2: <http://dbpedia.org/property/>"
				+ "PREFIX dbpedia: <http://dbpedia.org/>"
				+ "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> "
                + " SELECT ?nome WHERE { "
                + " ?nome  "
                + " rdfs:label \""
                + nome 
                +"\""
                + "@en . "
                + "}";
   
        //System.out.println(queryString);
        org.apache.jena.query.Query query = QueryFactory.create(queryString);
        QueryEngineHTTP qexec = QueryExecutionFactory.createServiceRequest(service, query);
        org.apache.jena.query.ResultSet results = qexec.execSelect();
        //ResultSetFormatter.out(System.out, results, query);
        String r= "", r2 = "";
        for ( ; results.hasNext() ; ) {
            QuerySolution soln = results.nextSolution() ;
            r = soln.getResource("nome").toString();
            break;
        }
        
        return r;
    }
	
	public String normalize(String input) {
		return input.substring(input.lastIndexOf("/")+1);
	}
	public boolean filtro(String input) {
		if (input.equals("http://xmlns.com/foaf/0.1/homepage") ||
				input.equals("http://dbpedia.org/property/placeOfBirth") ||
				input.equals("http://dbpedia.org/ontology/country") ||
				input.equals("http://dbpedia.org/ontology/wikiPageDisambiguates") ||
				input.equals("http://dbpedia.org/property/knownFor") ||
				input.equals("http://dbpedia.org/property/nationality") ||
				input.equals("http://dbpedia.org/property/occupation") ||
				input.equals("http://dbpedia.org/ontology/capital") ||
				input.equals("http://dbpedia.org/ontology/officialLanguage") ||
				input.equals("http://dbpedia.org/ontology/wikiPageRedirects") ||
				input.equals("http://dbpedia.org/property/foundation") ||
				input.equals("http://www.w3.org/2000/01/rdf-schema#subClassOf") ||
				input.equals("http://dbpedia.org/property/title") ||
				input.equals("http://dbpedia.org/property/founder") ||
				input.equals("http://dbpedia.org/ontology/product") ||
				input.equals("http://www.w3.org/ns/prov#wasDerivedFrom") ||
				input.equals("http://dbpedia.org/property/children") ||
				input.equals("http://dbpedia.org/property/birthName") ||
				input.equals("http://www.w3.org/2004/02/skos/core#broader") ||
				input.equals("http://dbpedia.org/property/writer") ||
				input.equals("http://dbpedia.org/property/director") ||
				input.equals("http://dbpedia.org/property/music") ||
				input.equals("http://dbpedia.org/property/occupation")||
				input.equals("http://dbpedia.org/property/state")||
				input.equals("http://dbpedia.org/property/city")||
				input.equals("http://dbpedia.org/property/rector")||
				input.equals("http://dbpedia.org/property/established")||
				input.equals("http://www.w3.org/2000/01/rdf-schema#seeAlso")
				) {
			return true;
		}
		return false;
	}
}
