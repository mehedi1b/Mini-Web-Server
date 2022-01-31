import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
public class RequestHandler extends Thread {
    private Socket socket = null;
    public RequestHandler(Socket socket) {
        this.socket = socket;
    }
    public void run() {
        try {

            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream=socket.getInputStream();
            BufferedReader inputstream = new BufferedReader(new InputStreamReader(inputStream));
            ClientRequest clientRequest = new ClientRequest(inputstream);
            HttpHeaderBuilder headerBuilder = new HttpHeaderBuilder();
            clientRequest.updateRequestData();
            while (clientRequest.ConnectionStatus.equals("keep-alive")) {
                String path=clientRequest.RequestedPath;
                System.out.println("A client Request for: "+path);
                if(path.equals("/")){
                    File file = new File("ui/index.html");
                    InputStream fileinputStream = new FileInputStream(file);
                    outputStream.write(headerBuilder.getokHeader("ui/index.html", fileinputStream.available()).getBytes("UTF-8"));
                    copyFile(fileinputStream, outputStream);
                    fileinputStream.close();
                }
                else {
                    Path resourcePath = Paths.get("ui"+clientRequest.RequestedPath);
                    if (Files.exists(resourcePath)&&(resourcePath.getFileName().toString().length()>1)) {
                         if (clientRequest.RequestMethod.equals("GET")) {
                            File file = new File(resourcePath.toString());
                            InputStream fileinputStream = new FileInputStream(file);
                            outputStream.write(headerBuilder.getokHeader(clientRequest.RequestedPath, fileinputStream.available()).getBytes("UTF-8"));
                            copyFile(fileinputStream, outputStream);
                            fileinputStream.close();
                          }
                        else if (clientRequest.RequestMethod.equals("POST")) {
                            String postpath=resourcePath.toString();
                            if(postpath.equals("ui\\messages\\messages.txt")){
                                File file=new File(postpath);
                                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
                                PrintWriter pw=new PrintWriter(bw);
                                String data=new String(clientRequest.body);
                                pw.println(data);
                                pw.flush();
                                pw.close();
                                StringBuilder newheader=new StringBuilder();
                                newheader.append("HTTP/1.1 200 OK\r\n");
                                newheader.append("Server: mini-server\r\n");
                                newheader.append("Connection: keep-alive\r\n");
                                newheader.append("Content-Type: text/plain\r\n");
                                newheader.append("Content-Length: " + "Message recived".length() + "\r\n");
                                newheader.append("\r\n");
                                newheader.append("Message recived\r\n");
                                outputStream.write(newheader.toString().getBytes("UTF-8"));
                                
                            }else{
                                File file = new File("ui/"+resourcePath.toString());
                                InputStream fileinputStream = new FileInputStream(file);
                                String header=headerBuilder.getokHeader(postpath, fileinputStream.available());
                                outputStream.write(header.getBytes("UTF-8"));
                                copyFile(fileinputStream, outputStream);
                                fileinputStream.close();
                            }    
                       }else{
                          String header=headerBuilder.getBadRequestHeader();
                          outputStream.write(header.getBytes("UTF-8"));
                       }

                    }
                    else {
                    outputStream.write(headerBuilder.getnotfoundHeader().getBytes("UTF-8"));
                   }
                }
                System.out.println("Request Served !!");
                clientRequest.updateRequestData();
            }
        } catch (Exception e) {
        }
    }

    private boolean copyFile(InputStream sourceStream, OutputStream destinationStream) throws Exception{
        byte[] buf = new byte[8192];
        int length;
        while ((length = sourceStream.read(buf)) > 0) {
            destinationStream.write(buf, 0, length);
        }
        return true;
    }
}
