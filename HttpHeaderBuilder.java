public class HttpHeaderBuilder {
    
    public String getFileExtension(String Path) {
        StringBuilder fileextensionBuilder = new StringBuilder();
        for (int i = Path.length() - 1; i >= 0 && Path.charAt(i) != '.'; i--) {
            fileextensionBuilder.append(Path.charAt(i));
        }
        fileextensionBuilder.reverse();
        return fileextensionBuilder.toString();
    }

    String getokHeader(String Path,int ContentLength) {
        StringBuilder header = new StringBuilder();
        header.append("HTTP/1.1 200 OK\r\n");
        header.append("Server: mini-server\r\n");
        header.append("Connection: keep-alive\r\n");
        header.append(getContentType(getFileExtension(Path)));
        header.append("Content-Length: " + ContentLength + "\r\n");
        header.append("\r\n");
        return header.toString();
    }
    public String getnotfoundHeader(){
        StringBuilder header = new StringBuilder();
        header.append("HTTP/1.1 404  Not Found\r\n");
        header.append("Server: Home\r\n");
        header.append("Connection: closed\r\n");
        header.append("Content-Type: text/html\r\n");
        String msg="<h1>404 Not Found</h1>";
        header.append("Content-Length:"+msg.length()+"\r\n");
        header.append("\r\n");
        header.append(msg+"\r\n");
        return header.toString();
    }
    public String getBadRequestHeader(){
        StringBuilder header = new StringBuilder();
        header.append("HTTP/1.1 400 Bad Request\r\n");
        header.append("Server: Home\r\n");
        header.append("Connection: closed\r\n");
        header.append("Content-Type: text/html\r\n");
        String msg="<h1>400 Bad Request</h1>";
        header.append("Content-Length:"+msg.length()+"\r\n");
        header.append("\r\n");
        header.append(msg+"\r\n");
        return header.toString();
    }
    public String getokDownloadHeader(String Path,int ContentLength){
        StringBuilder header = new StringBuilder();
        header.append("HTTP/1.1 200 OK\r\n");
        header.append("Server: Home\r\n");
        header.append("Connection: keep-alive\r\n");
        header.append(getContentType(getFileExtension(Path)));
        header.append("Content-Disposition: attachment; filename=\"cool.html\"\r\n");
        header.append("Content-Length:"+ContentLength+"\r\n");
        header.append("\r\n");
        return header.toString();
    }
    private String getContentType(String fileextension){
        if (fileextension.toLowerCase().equals("html") || fileextension.toLowerCase().equals("htm")) {
            return "Content-Type: text/html; charset=utf-8\r\n";
        }
        else if (fileextension.toLowerCase().equals("js")) {
            return "Content-Type: text/javascript\r\n";
        }
        else if (fileextension.toLowerCase().equals("css")) {
            return "Content-Type: text/css\r\n";
        }
        else if (fileextension.toLowerCase().equals("svg")) {
            return "Content-Type: image/svg+xml\r\n";
        }
        else if (fileextension.toLowerCase().equals("png")) {
            return "Content-Type: image/png\r\n";
        }
        else if (fileextension.toLowerCase().equals("txt")) {
            return "Content-Type: text/plain\r\n";
        }
        else if (fileextension.toLowerCase().equals("mp3")) {
            return "Content-Type: audio/mpeg\r\n";
        }
        else if (fileextension.toLowerCase().equals("mp4")) {
            return "Content-Type: video/mp4\r\n";
        }
        else if (fileextension.toLowerCase().equals("json")) {
            return "Content-Type: application/json\r\n";
        }
        else if (fileextension.toLowerCase().equals("jpeg")||fileextension.toLowerCase().equals("jpg")) {
            return "Content-Type: image/jpeg\r\n";
        }
        else if (fileextension.toLowerCase().equals("pdf")) {
            return "Content-Type: application/pdf\r\n";
        }
        else if (fileextension.toLowerCase().equals("ppt")) {
            return "Content-Type: application/vnd.ms-powerpoint\r\n";
        }
        else if (fileextension.toLowerCase().equals("zip")) {
            return "Content-Type: application/zip\r\n";
        }
        else if (fileextension.toLowerCase().equals("xml")) {
            return "Content-Type: application/xml\r\n";
        }
        else if (fileextension.toLowerCase().equals("xhtml")) {
            return "Content-Type: application/xhtml+xml\r\n";
        }
        else if (fileextension.toLowerCase().equals("xls")) {
            return "Content-Type: application/vnd.ms-excel\r\n";
        }else{
            return "Content-Type: application/octet-stream\r\n";
        }
    }
}
