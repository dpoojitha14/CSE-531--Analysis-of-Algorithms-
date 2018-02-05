import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;


public class LCS {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
	    //Writing output to output.txt 
		PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
		System.setOut(out);
		
		String str1=null,str2=null;
		
		//Reading from input.txt
		
		String path="input.txt";
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
			
		String line=null;
		int no=0;
		while ((line=br.readLine()) != null)
		{
			if (no == 0)
			{
				str1=line;
			}
			if(no == 1)
				str2=line;
			no++;
				
		}
		br.close();
		
		str1=" "+str1;
		str2=" "+str2;
		
		int n = str1.length();
		int m = str2.length();
		
		/*System.out.println(n);
		System.out.println(m);
		
		
		for( int i = 0 ; i<n;i++)
			System.out.println(str1.charAt(i));*/
		
		
		int lcs=0;
		
		int i,j;
		
		int opt[][]=new int[n+1][m+1];
		String pi[][]=new String[n+1][m+1];
		
		for(j=0;j<=m;j++)
		{
			opt[0][j] = 0;
		}
		
		for(i=1;i<n;i++)
		{
			opt[i][0] = 0;
			for(j=1;j<m;j++)
			{
				//System.out.print(str1.charAt(i)+" ");
				//System.out.print(str2.charAt(j));
				//System.out.print("\n");
				if ( str1.charAt(i) == str2.charAt(j))
				{
					opt[i][j] = (opt[i-1][j-1]) + 1;
					pi[i][j] = "\"";
				}
				else if( opt[i][j-1] >= opt[i-1][j])
				{
					opt[i][j] = opt[i][j-1];
					pi[i][j] = "-";
				}
				else
				{
					opt[i][j] = opt[i-1][j];
					pi[i][j] = "|";
				}
			}
		}
		
		lcs = opt[n-1][m-1];
		System.out.println(lcs);
		
		//printing string
		i = n-1;
		j = m-1;
		String result = "";
		while(i>0 && j>0)
		{
			if( pi[i][j] == "\"")
			{
				result= str1.charAt(i)+ result;
				i--;
				j--;
			}
			else if ( pi[i][j] == "|" )
				i--;
			else
				j--;
		}
		
		System.out.println(result);

	}

}
