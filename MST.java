
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;


public class MST {

	public static int s=0;
	//A[i]-> element at node i
	public static LinkedList<Integer> A = new LinkedList<Integer>(); // Nodes of tree
	//P contains vertex:index
	public static Map<Integer,Integer> P = new HashMap<Integer,Integer>();
	//Key is a map of v:key_value
	public static Map<Integer,Integer> Keys = new HashMap<Integer,Integer>();
	//Parent
	public static Map<Integer,Integer> parent = new HashMap<Integer,Integer>();
	
	
	static void insert(int vertex, int value)
	{
		int v = vertex;
		int key_value = value ;
		/*Implementation of insert(v,key_value)*/
		s=s+1;
		//System.out.println("A: "+A);
		A.add(s,v);
		//System.out.println("A: "+A);
		P.put(v, s);
		Keys.put(v, key_value);
		heapify_up(s);
		
	}
	
	static void heapify_up(int i)
	{
		while(i>1)
		{
			int j=i/2;
			int value1 = A.get(i);
			int value2 = A.get(j);
			if ( Keys.get(value1) < Keys.get(value2))
			{
				// swap A[i] and A[j]
				A.set(i, value2);
				A.set(j, value1);
				
				P.put(A.get(i), i);
				P.put(A.get(j), j);
				i=j;
			}
			else
				break;
		}
	}
	
	static int extract_min()
	{
		int ret = A.get(1);
		int num = A.get(s);
		A.set(1, num);
		P.put(A.get(1), 1);
		s--;
		if (s>=1)
			heapify_down(1);
		return ret;
	}
	
	static void heapify_down(int i)
	{
		int num1;
		while((num1 = 2*i) <= s)
		{
			int value1 = Keys.get(A.get(num1));
			int value2 = Keys.get(A.get(num1+1));
			int j;
			if ( num1==s || value1 <= value2)
				j = num1;
			else
				j = num1+1;
			int value3 = Keys.get(A.get(j));
			int value4 = Keys.get(A.get(i));
			
			if (value3 < value4 )
			{
				//swap A[i] and A[j]
				int value5 = A.get(i);
				int value6 = A.get(j);
				
				A.set(i, value6);
				A.set(j, value5);
				
				P.put(A.get(i),i);
				P.put(A.get(j), j);
				
				i=j;
			}
			else
				break;
			//System.out.println("In down, A: "+A);
			//System.out.println("In down, P: "+P);
		}
	}
	
	static void decrease_key(int vertex,int value)
	{
		int v = vertex;
		int key_value = value;
		Keys.put(v, key_value);
		heapify_up(P.get(v));
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		
		String path ="input.txt";  //input file
		BufferedReader br = new BufferedReader(new FileReader(path));
		
		PrintStream out = new PrintStream(new FileOutputStream("output.txt")); //output file
		System.setOut(out);
		
		String line;
		int lineno=0;
		int vertices=0,edges=0;
		while ((line = br.readLine()) != null)
		{
			if(lineno==0)
			{
				String[] values=line.split(" ");
				vertices = Integer.parseInt(values[0]);
				edges = Integer.parseInt(values[1]);
			}
			lineno++;
		}
		
		br.close();
		
		//System.out.println("VERTICES:"+vertices);
		//System.out.println("lines:"+lineno);
		
		BufferedReader br1 = new BufferedReader(new FileReader(path));
		// V is a linked list consisting of vertices
		LinkedList<Integer> V = new LinkedList<Integer>();
		
		// E is a linkedl ist consisting of edges
		LinkedList<LinkedList<Integer>> E = new LinkedList<LinkedList<Integer>>();
		
		//graph is a map with key:(u,v) and value:w
		Map<LinkedList<Integer>,Integer> graph = new HashMap<LinkedList<Integer>,Integer>();
		
		lineno=0;
		while ((line = br1.readLine()) != null)
		{
			if(lineno!=0)
			{
				String[] values=line.split(" ");
				int from =Integer.parseInt(values[0]);
				int to = Integer.parseInt(values[1]);
				LinkedList<Integer> edge = new LinkedList<Integer>();
				edge.add(from);
				edge.add(to);
				//undirected graph
				LinkedList<Integer> edge1 = new LinkedList<Integer>();
				edge1.add(to);
				edge1.add(from);
				int weight = Integer.parseInt(values[2]);
				
				// adding edges and weight to graph
				graph.put(edge, weight);
				graph.put(edge1, weight);
				
				//adding edges to E
				if(!E.contains(edge))
					E.add(edge);
				if(!E.contains(edge1))
					E.add(edge1);
				
				//adding vertices to V
				if(!V.contains(from))
					V.add(from);
				if(!V.contains(to))
					V.add(to);
				
			}
			lineno++;
		}
		br1.close();
		
		/*System.out.println(graph);
		System.out.println(graph.size());
		System.out.println("V: "+V);
		System.out.println("E "+E);
		System.out.println(E.size());*/
		
		//A[0]=-1
		A.add(0,-1);
		int arbitrary = 1; //arbitrary vertex in G. Let's assume it is the first vertex
		
		LinkedList<Integer> S = new LinkedList<Integer>();
		
		//Create a mapping of weight and vertex with key:vertex and value:weight
		
		Map<Integer,Integer> d = new HashMap<Integer,Integer>();
		
		//Initializing the values of d
		
		d.put(arbitrary, 0);
		
		for(int i=1;i<V.size();i++)
		{
			int value = V.get(i);
			d.put(value, 1000001); // as 1 <= w <= 1000000
		}
		
		//System.out.println("d: " +d);
		
		
		for(int i=0;i<V.size();i++)
		{
			int vertex = V.get(i);
			int key_value=d.get(vertex);
			insert(vertex,key_value);
		}
		
		Set<Integer> S1= new HashSet<Integer>();
		Set<Integer> V1= new HashSet<Integer>(V);
		
		while(!S1.equals(V1))
		{
			int u = extract_min();
			S1.add(u);
			LinkedList<Integer> diff = new LinkedList<Integer>();
		    diff.addAll(V);
		    Set<Integer> diff1 = new HashSet<Integer>(diff);
		    diff1.removeAll(S1); // diff1 = V - S
		    Iterator<Integer> itr1 =  diff1.iterator();
			while(itr1.hasNext())
			{
				int v= itr1.next();
				LinkedList<Integer> pair = new LinkedList<Integer>();
				pair.add(u);
				pair.add(v);
				if(E.contains(pair))
				{
					int weight = graph.get(pair);
					int d_value = d.get(v);
					if(weight < d_value)
					{
						d.put(v, weight);
						decrease_key(v,d.get(v));
						parent.put(v,u);
					}
				}
			}
			
			V1= new HashSet<Integer>(V);
		}
		
		/*System.out.println("A:"+A);
		System.out.println("P : "+P);
		System.out.println("Keys: "+Keys);
		System.out.println("s= "+s);
		System.out.println("Parent: "+parent);*/
		
		//Result
		
		int sum = 0;
		for(int k=0;k<V.size();k++)
		{
			int vertex = V.get(k);
			int cost = Keys.get(vertex);
			sum = sum+cost;
		}
		
		//System.out.println("Cost:"+sum);
		System.out.println(sum);
		
		LinkedList<Integer> V2 = new LinkedList<Integer>();
		for(int k=0;k<V.size();k++)
		{
			int vertex = V.get(k);
			V2.add(vertex);
		}
		int index1 = V2.indexOf(arbitrary);
		V2.remove(index1);
		
		//System.out.println("MST:");
		
		for(int k=0;k<V2.size();k++)
		{
			int vertex = V2.get(k);
			int pi = parent.get(vertex);
			System.out.println(pi+" "+vertex);
		}
		
		
	}

}
