import java.io.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;
import java.util.Iterator;

public class A4_2019MT10262{
	HashMap<String, vertex> vertices;
	HashMap<String, LinkedList<edge>> adj;
	Map<vertex, Integer> visited;
	Map<vertex, Integer> connected;
	HashMap<Integer, LinkedList<vertex>> cc;
	HashMap<String, LinkedList<vertex>> cc_final;
	int numVertices;
	public A4_2019MT10262(){
		this.vertices = new HashMap<String, vertex>();
		this.adj = new HashMap<String, LinkedList<edge>>();
		this.visited = new HashMap<vertex, Integer>();
		this.connected = new HashMap<vertex, Integer>();
		this.cc = new HashMap<Integer, LinkedList<vertex>>();
		this.cc_final = new HashMap<String, LinkedList<vertex>>();
		numVertices = 0;
	}
	public static void main(String[] args){
		A4_2019MT10262 graph = new A4_2019CS50471();
		String line = "";
		String edge_lines = "";
		String delimiter = ",";		
		int count1 = -1;
		try{
			BufferedReader nodes = new BufferedReader(new FileReader(args[0]));
			while((line = nodes.readLine()) != null){
				count1++;
				String[] node = line.split(delimiter);				
				String a = "";
				String b ="";
				if(node[0].charAt(0) == '\"'){
					for(int i = 0; i < node.length; i++){
						if(node[i].length() == 0){
							b = node[i];
							a = a + ',';
							continue;
						}
						if(node[i].charAt(0) == '\"' && node[i].charAt(node[i].length() - 1) == '\"'){
							a = node[0];
						}
						else if(node[i].charAt(0) == '\"'){
							b = node[i];
							a = b + ',';
						}
						else if(node[i].charAt(node[i].length() - 1) == '\"'){
							b = node[i];
							a = a + b;
						}
						else{
							b = node[i];
							a = a + b + ',';
						}						
					}
				}
				else{
					a = node[0];					
				}
				if(count1 != 0){
					a = a.replaceAll("^\"|\"$", "");
					vertex v = new vertex(a);
					(graph.vertices).put(v.label, v);	
					graph.numVertices++;
				}
				

			}
		}
		catch(IOException exception){
			exception.printStackTrace();
		}
		Iterator vertex_it = graph.vertices.entrySet().iterator();
		while(vertex_it.hasNext()){
			Map.Entry element = (Map.Entry)vertex_it.next();
			graph.adj.put(((vertex)element.getValue()).label, new LinkedList());
			graph.visited.put((vertex)element.getValue(), 0);
			graph.connected.put((vertex)element.getValue(), -1);
		}
		int count = -1;
		try{
			BufferedReader edges = new BufferedReader(new FileReader(args[1]));
			while((line = edges.readLine()) != null){
				count++;
				String[] w_edge = line.split(delimiter);
				String a = "";
				Vector<String> n_edge = new Vector<String>();
				int j = 0;
				while(j < w_edge.length){
					if(w_edge.length > 0 && w_edge[j].charAt(0) == '\"'){
						for(int i = j; i < w_edge.length; i++){
							j++;
							if(w_edge[i].length() == 0){
								a = a + ',';
								continue;
							}
							if(w_edge[i].charAt(0) == '\"' && w_edge[i].charAt(w_edge[i].length() - 1) == '\"'){
								a = w_edge[i];
								a = a.replaceAll("^\"|\"$", "");
								n_edge.add(a); 
								break;
							}
							else if(w_edge[i].charAt(0) == '\"'){
								a = w_edge[i] + ',';
							}
							else if(w_edge[i].charAt(w_edge[i].length() - 1) == '\"'){
								a = a + w_edge[i];
								a = a.replaceAll("^\"|\"$", "");
								n_edge.add(a); 

								break;
							}
							else{
								a = a + w_edge[i] + ',';
							}							
						}
					}
					else{
						a = w_edge[j];
						n_edge.add(a);
						j++;
					}
				}
				if(count != 0){
					graph.adj.get(n_edge.get(0)).add(new edge(n_edge.get(1),Integer.parseInt(n_edge.get(2))));
					graph.adj.get(n_edge.get(1)).add(new edge(n_edge.get(0),Integer.parseInt(n_edge.get(2))));
				}
			}
		}
		catch(IOException exception){
			exception.printStackTrace();
		}
		if(args[2].equals("average")){
			float average = graph.average();
			System.out.print(String.format("%.2f", average));
			System.out.println("");
		}
		else if(args[2].equals("rank")){
			
			int n = graph.numVertices;
			String[] A = new String[n];
			int[] B = new int[n];
			Iterator it = graph.adj.entrySet().iterator();
			int i = 0;
			while(it.hasNext()){
				Map.Entry element1 = (Map.Entry)it.next();
				A[i] = (String)element1.getKey();
				LinkedList<edge> co_occur = (LinkedList<edge>)element1.getValue();
				B[i] = 0;
				for(int idx = 0; idx < co_occur.size(); idx++){
					B[i] = B[i] + co_occur.get(idx).weight;
				}
				i++;
			}
			quicksort(A, B, 0, n - 1);
			
			for(int k = 0; k < n - 1; k++){
				System.out.print(A[n - k - 1] + ",");
			}
			System.out.print(A[0]);
			System.out.println("");
		}
		else if(args[2].equals("independent_storylines_dfs")){
			Iterator it = graph.vertices.entrySet().iterator();
			Map.Entry element = (Map.Entry)it.next();
			vertex v = (vertex)element.getValue();
			
			
			Iterator it3 = graph.vertices.entrySet().iterator();
			int cc_num = 0;
			while(it3.hasNext()){
				Map.Entry vertex_1 = (Map.Entry)it3.next();
				vertex v_1 = (vertex)vertex_1.getValue();
				if(graph.connected.get(v_1) < 0){
					graph.cc.put(cc_num, new LinkedList<vertex>());
					
					graph.DFS(v_1, cc_num);
					cc_num++;
				}
			}
			Iterator it2 = graph.cc.entrySet().iterator();
			int[] sort_lex = new int[cc_num];
			String[] lex = new String[cc_num];
			int index = 0;
			while(it2.hasNext()){
				Map.Entry element2 = (Map.Entry)it2.next();
				int cc_Num = (Integer)element2.getKey();
				LinkedList<vertex> v1 = (LinkedList<vertex>)element2.getValue();
				String[] cc_vertex = new String[v1.size()];
				int[] B = new int[v1.size()];
				for(int i = 0; i < v1.size(); i++){
					cc_vertex[i] = v1.get(i).label;
					B[i] = cc_Num;
				}
				quicksort(cc_vertex, B, 0, v1.size() - 1);
				sort_lex[index] = v1.size();
				lex[index] = cc_vertex[v1.size() - 1];
				index++;
				graph.cc_final.put(cc_vertex[v1.size() - 1], new LinkedList<vertex>());		
				for(int k = 0; k < v1.size(); k++){
					graph.cc_final.get(cc_vertex[v1.size() - 1]).add(graph.vertices.get(cc_vertex[v1.size() - k - 1]));					
				}			
			}
			quicksort(lex, sort_lex, 0, cc_num - 1);
			
			for(int p = 0; p < cc_num; p++){
				LinkedList<vertex> v2 = graph.cc_final.get(lex[cc_num - 1 - p]);
				for(int idx = 0; idx < v2.size(); idx++){
					if(idx != v2.size() - 1){
						System.out.print(v2.get(idx).label + ",");
					}
					else{
						System.out.print(v2.get(idx).label);
					}					
				}
				System.out.println("");
			}						
		}
	}
	public float average(){
		Iterator it = this.adj.entrySet().iterator();
		float sum= 0;
		float count = 0;
		while(it.hasNext()){
			Map.Entry element1 = (Map.Entry)it.next();
			LinkedList<edge> edges = (LinkedList<edge>)element1.getValue();
			sum = sum + edges.size();
			count++;
		}		
		return sum/count;
	}
	public static int partition(String[] A, int[] B, int p, int q){
		int temp;
		String temp1;
		/* Picks a random pivot element and swaps with the last element*/
		int pivot_index = (int)(p + (q - p)*Math.random());
		temp = B[q];
		B[q] = B[pivot_index];
		B[pivot_index] = temp;
		temp1 = A[pivot_index];
		A[pivot_index] = A[q];
		A[q] = temp1;
		int pivot = B[q]; 
		int i = p - 1;
		/*Performs Quicksort along with comparision of Strings lexiographically*/
		for(int j = p; j <= q; j++){
			if(B[j] <= pivot){
				if(B[j] < pivot){
					i++;
					temp = B[i];
					B[i] = B[j];
					B[j] = temp;
					temp1 = A[i];
					A[i] = A[j];
					A[j] = temp1;
				}
				else if(B[j] == pivot && A[j].compareTo(A[q]) < 0){
					i++;
					temp = B[i];
					B[i] = B[j];
					B[j] = temp;
					temp1 = A[i];
					A[i] = A[j];
					A[j] = temp1;
				}				
			}
		}
		temp = B[i + 1];
		B[i + 1] = B[q];
		B[q] = temp;
		temp1 = A[i + 1];
		A[i + 1] = A[q];
		A[q] = temp1;
		return i + 1;
	}
	public static void quicksort(String[] A, int[] B, int p ,int q){
		if(p <= q){
			int j = partition(A, B, p , q);
			quicksort(A, B, p, j - 1);
			quicksort(A, B, j + 1, q);	
		}	
	}
	public void DFS(vertex v, int cc_num){
		this.visited.put(v, this.visited.get(v) + 1);
		this.connected.put(v, cc_num);
		this.cc.get(cc_num).add(v);
		LinkedList<edge> adjacent = this.adj.get(v.label);
		for(int i = 0; i < adjacent.size(); i++){
			if(visited.get(vertices.get(adjacent.get(i).target)) != 1){
				DFS(vertices.get(adjacent.get(i).target), cc_num);
			}
		}
	}	
}
class vertex{
	public String label;
	public vertex(String label){
		this.label = label;
	}
}
class edge{
	public String target;
	public int weight;
	public edge(String target, int weight){
		this.target = target;
		this.weight = weight;
	}
}