import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {

    public String handleRequest(URI url) {

        ArrayList<String> wordBank = new ArrayList<String>();

        if (url.getPath().equals("/")) {
            return "hi!";
        } 
        else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    wordBank.add(parameters[1]);
                    return String.format("Added %s to the word bank!", parameters[1]);
                }
            }
            else if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    ArrayList<String> searchBank = new ArrayList<String>();
                    for(int i = 0; i < wordBank.size(); i++) {
                        if(wordBank.get(i).contains(parameters[1])) {
                            searchBank.add(wordBank.get(i));
                        }
                    }
                    StringBuilder searchMatches = new StringBuilder("Your search returned: ");
                    for(int i = 0; i < searchBank.size(); i++) {
                        searchMatches.append(" " + searchBank.get(i));
                    }
                    return searchMatches.toString();
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}