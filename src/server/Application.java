package server;




import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Application {



    public String jsonMenu(String msg, Database database) {
        Gson gson = new Gson();
        Result result = gson.fromJson(msg,Result.class);


        //tu
        String[] arr = result.toString().split(" ");
        List<String> wordList = new ArrayList<>();
        Collections.addAll(wordList, arr);

        if (wordList.get(0).equals("set")) {
            String tWord = wordList.get(1);
            wordList.remove(0);
            wordList.remove(0);
            String listString = wordList.stream().map(Object::toString)
                    .collect(Collectors.joining(" "));
            database.jsonData.put(tWord,listString);
            return "{\"response\":\"OK\"}";
        } else if (wordList.get(0).equals("get")) {
            if(database.jsonData.get(wordList.get(1)) != null) {
                return "{\"response\":\"OK\",\"value\":\""+database.jsonData.get(wordList.get(1))+"\"}";
            }else {
                return "{\"response\":\"ERROR\",\"reason\":\"No such key\"}";
            }
        } else if (wordList.get(0).equals("delete")){
            if(database.jsonData.get(wordList.get(1)) != null) {
                database.jsonData.remove(wordList.get(1));
                return "{\"response\":\"OK\"}";
            }else {
                return "{\"response\":\"ERROR\",\"reason\":\"No such key\"}";
            }
        }else {
            return "exit";
        }
    }

    class Result {

        String type;
        String key;
        String value;

        public Result(String type, String key,String value) {
            this.type = type;
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return new StringBuilder().append(this.type!=null ? this.type : "").append(" ")
                    .append(this.key!=null ? this.key : "").append(" ")
                    .append(this.value!=null ? this.value : "").toString();
        }
    }


}