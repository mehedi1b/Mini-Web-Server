import java.io.*;
import java.util.StringTokenizer;

public class ClientRequest {
    public String RequestMethod;
    public String RequestedPath;
    public String HttpVirsion;
    public String HostIP;
    public String ConnectionStatus;
    private boolean isbodyavaiable;
    public int content_lenth;
    public char [] body;
    public int Timeout;
    BufferedReader inputstream;
    public ClientRequest(BufferedReader inputstream){
        this.inputstream=inputstream;
        resetRequestData();
    }
    private void resetRequestData(){
        RequestMethod = null;
        RequestedPath = null;
        HttpVirsion = null;
        HostIP = null;
        ConnectionStatus = null;
        isbodyavaiable=false;
        body=null;
        content_lenth=-1;
    }
    public void updateRequestData() {
        resetRequestData();
        try{
            String line;
            while ((line = inputstream.readLine())!=null) {
                if(line.length()==0){
                    break;
                }
                httpRequestLister(line);
            }
            if(isbodyavaiable){
                body=new char[content_lenth];
                inputstream.read(body,0,content_lenth);
            }
            
        }catch(Exception e){
            System.out.println(e);
        }    
    }
    public void httpRequestLister(String line) {
        String junk;
        StringTokenizer st = new StringTokenizer(line, " ");
        if (line.contains("GET")) {
            RequestMethod = st.nextToken(); 
            RequestedPath = st.nextToken();
            HttpVirsion = st.nextToken();
        }
        if (line.contains("POST")) {
            RequestMethod = st.nextToken(); 
            RequestedPath = st.nextToken();
            HttpVirsion = st.nextToken();
        }
        if(line.contains("Content-Length")){
            content_lenth=Integer.valueOf(line.substring(line.indexOf(' ') + 1, line.length()));
            if(content_lenth>-1){
                isbodyavaiable=true;
            }
        }
        if (line.contains("Connection")) {
            junk = st.nextToken();
            junk+=junk;
            ConnectionStatus = st.nextToken();
        }
    }
}