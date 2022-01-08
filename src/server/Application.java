package server;




import java.util.*;
import java.util.stream.Collectors;


public class Application {

    public String menu(String msg, Database database) {
        Scanner scanner = new Scanner(System.in);
        String[] arr = msg.split(" ");
        List<String> wordList = new ArrayList<>();
        Collections.addAll(wordList, arr);

        if (wordList.get(0).equals("set")) {
            int number = Integer.parseInt(wordList.get(1));
            if (number >= 1001 || number == 0) {
                return "ERROR";
            } else {
                wordList.remove(0);
                wordList.remove(0);
                String listString = wordList.stream().map(Object::toString)
                        .collect(Collectors.joining(" "));
                database.data.set(number, listString);
                return "OK";
            }

        } else if (wordList.get(0).equals("get")) {
            int number = Integer.parseInt(wordList.get(1));
            if (number >= 1001 || number == 0) {
                return "ERROR";
            } else {
                String result = database.data.get(number).equals("") ? "ERROR" : database.data.get(number);
                return result;
            }
        } else if (wordList.get(0).equals("delete")){
            int number = Integer.parseInt(wordList.get(1));
            if (number >= 1001 || number == 0) {
                return "ERROR";
            } else {
                database.data.set(number, "");
                return "OK";
            }
        }else {

            return "OK";
        }
    }

}