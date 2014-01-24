import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Main {

	static String problems[]=new String[100000];
	static int head;
    public static void main(String[] args) throws IOException {
    	
    	//http://www.spoj.com/problems/classical/sort=0,start=2800
    	BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
    	String user=in.readLine();
        URL url = new URL("http://www.spoj.com/status/"+user+"/signedlist/");
        URLConnection con = url.openConnection();
        InputStream is =con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        int next=1;
        int count=0;
        File file=new File("logData"+user);
        BufferedWriter write=new BufferedWriter(new FileWriter(file, true));
        
        
        while ((line = br.readLine()) != null) {
        		
        	if(line.contains("\\------------------------------------------------------------------------------/")){
        		next=0;
        	}else if(count>=9 && next==1 && line.contains("AC")){
        		String content[]=line.split(" \\| ");
        		if(!containsValue(content[2].trim())){
        			content[0]=content[0].trim();
        			content[1]=content[1].trim();
        			content[2]=content[2].trim();
        			content[6]=content[6].trim();
        			problems[head]=content[2];
        			head++;
        			write.append("ID="+content[0]+":"+"DATE="+content[1]+":"+"PROBLEM="+content[2]+":"+"LANG="+content[6]+"\n");
        		}
        	}
        	count++;
        	
         }
        write.close();
        
       url =new URL("http://www.spoj.com/problems/classical/sort=-6,start=0");
       con = url.openConnection();
       is =con.getInputStream();
       br = new BufferedReader(new InputStreamReader(is));
       
       while((line=br.readLine())!=null){
    	   System.out.println(line);
       }
        
        
    }

	private static boolean containsValue(String trim) {
		int i=0;
		while(i<head){
			if(problems[i].compareTo(trim)==0) return true;
			i++;
		}
		return false;
	}
     
    
    	
 
    
    
}