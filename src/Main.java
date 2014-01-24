import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.tree.DefaultMutableTreeNode;

public class Main {

	static String problems[]=new String[100000];
	static int head;
    public static void main(String[] args) throws IOException {
    	
    	//http://www.spoj.com/problems/classical/sort=0,start=2800
    	System.out.println("Enter Your spoj handle");
    	BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
    	String user=in.readLine();
    	System.out.println("Enter Level of Difficulty on a scale of [ 1-5 ]. 1 is most difficult");
    	int difficulty=Integer.parseInt(in.readLine());
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
        int ProblemFound=0;
        	while(ProblemFound<=3){
        	int page_start=1000/difficulty-200;
        	int page=(int)(Math.random()*1000000007)% 199+page_start;
        	page=0;
        	//System.out.println("http://www.spoj.com/problems/classical/sort=-6,start="+page);
        	url =new URL("http://www.spoj.com/problems/classical/sort=-6,start="+page);
        	con = url.openConnection();
        	is =con.getInputStream();
        	br = new BufferedReader(new InputStreamReader(is));
        	String html="";
        	while((line=br.readLine())!=null){
        	
        			html+=line;
        	
        	}
        	
        	String ar[]=html.split("</tr><tr class=\"problemrow\">");
        	int i=1;
        	
        	while(i<ar.length-1 && ProblemFound<=3){
        		
        		ar[i]=remove_unnecessary_whitespaces(ar[i]);
        		//System.out.println(ar[i]);
        		Node<String> root ;
        		 Tree<String> tree ;
        		 root = new Node<String>("XML");
        		 tree = new Tree<String>(root);
        		 parser(ar[i], root, 1);
        		 i++;
        		 ArrayList<Node<String>> treeee= tree.getPreOrderTraversal();
        		 Iterator<Node<String>> it=treeee.iterator();
        		 it.next();
        		 
        		 
        			 Node<String> noder=it.next();
        			 if(noder.data_part!=null ){
        				 if(!containsValue(noder.data_part)){
        					 noder=it.next();
        					 noder=it.next();
        					 noder=it.next();
        					 System.out.println("Problem Name :"+noder.data_part);
        					 noder=it.next();
        					 noder=it.next();
        					 System.out.println("Problem Link :\n http://www.spoj.com/problems/"+noder.data_part+"\n");
        					 ProblemFound++;
        				 }
        		 }
        	}
        	}
        	
        
        

        
    }
	private static void parser(String xml,Node<String> root,int linenumer) {
		
		if(xml.trim().length()==0 ) return ;
		int indexOpen=xml.indexOf('<');
		int indexClose=xml.indexOf('>')+1;
		String middle="";
		String s[];
		Node<String> child;
		
		
		if(xml.substring(indexOpen, indexClose).contains(" ")){
			s=xml.substring(indexOpen+1, indexClose-1).split(" ");
			middle=s[0];
			int i=1;
			int len=s.length;
			child = new Node<String>(middle);
			child.attrib="";
			
			while(i<len){
				child.attrib+=s[i];
				i++;
			}
			root.addChild(child);
			
		}else {
			String s2[]=xml.substring(indexOpen+1, indexClose-1).split(" ");
			child = new Node<String>(xml.substring(indexOpen+1, indexClose-1));
			root.addChild(child);
			middle=xml.substring(indexOpen+1, indexClose-1);
		}
		int index_end_tag=xml.indexOf("</"+middle+">");
		
		if(xml.substring(indexClose, index_end_tag).contains("<")){
			parser( xml.substring(indexClose, index_end_tag),child,linenumer);
		}	
		else
		{
			child.data_part=xml.substring(indexClose, index_end_tag);
		}
		int offset=("</"+middle+">").length();
		parser( xml.substring(index_end_tag+(offset)),root,linenumer);
	}


	private static String remove_unnecessary_whitespaces(String xml) {
		char s1[]=xml.toCharArray();
		int len=xml.length();
		int i=0;
		String s="";
		while(i<xml.length()){
			if(s1[i]==' '){
				if(i==0 || s1[i-1]=='<' || s1[i-1]=='/'){
					while(s1[i]==' ')
						i++;
				}

				else if(i+1<len && s1[i+1]!='>'){
					if(i+1<len && (s1[i+1]==' ' || s1[i+1]=='<')){
						i++;
					}
					else{
						s+=s1[i];
						i++;
					}
				}
				else
					i++;
			}
			else{
				s+=s1[i];
				i++;
			}

		}
		return s;
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