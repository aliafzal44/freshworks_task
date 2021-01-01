
package filestore;


import java.time.Instant;
import java.io.*;
import java.io.File;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class FileStore {
    public static String File_path;
    public static File file;
    public static JSONObject supportingDB = new JSONObject();
    public static JSONObject Data_Base = new JSONObject();

    public static void main(String[] args) {

        CreateFile();
        AddData("bangalore",400);
        AddData("chennai",650);
        AddData("chennai",650);
        ReadData("bangalore");
        DeleteData("bangalore");

    }

    static void CreateFile(String ... path) {
        if(path.length == 0) {
            File_path = System.getProperty("user.home")+"/Desktop/FileStoreDB";
        }
        else{
            File_path = path[0]+"/FileStoreDB";
        }

        try{
            file = new File(File_path);
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("File Created!");
            }
            else System.out.println("File Already Exists!");
        }
        catch (IOException ioe) {
            System.out.println(ioe);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    static void AddData(String key,int Value, int ... time_Para){
            int timeout;
            if(time_Para.length == 0) timeout=0;
            else timeout = time_Para[0];

            if(has(Data_Base,key)) {
                System.out.println("This key already exists!!");
            }
            else {
                if (key.matches("[a-zA-Z]+")) {
                    if (key.length() <= 32 && (file.length() <= (1024 * 1024 * 1024) && Value <= (16 * 1024 * 1024))) {
                        JSONArray valueArr = new JSONArray();
                        if (timeout == 0) {
                            valueArr.add(Value);
                            valueArr.add(timeout);
                        } else {
                            valueArr.add(Value);
                            valueArr.add(Instant.now().getEpochSecond() + timeout);
                        }
                        supportingDB.put(key, valueArr);
                        Data_Base.put(key, Value);

                        writeTOFile(Data_Base);
                        System.out.println("Data With Key '" + key + "' is Added.");
                    } else {
                        System.out.println("Memory limit exceeded!!");
                    }
                } else {
                    System.out.println("Invald key_name!! key_name must contain only alphabets and no special characters or numbers.");
                }
            }
    }



    static void ReadData(String key){
        if(!has(Data_Base,key)){
            System.out.println("Given key does not exist in database. Please enter a valid key.");
        }
        else {
            long[] temp={0,0};
            String data = "";
            try {
                JSONArray valueArr = new JSONArray();
                valueArr=(JSONArray) supportingDB.get(key);

                temp[0] = ((Number) valueArr.get(0)).longValue();
                temp[1] = ((Number) valueArr.get(1)).longValue();

                if (temp[1] != 0 && Instant.now().getEpochSecond() > temp[1]) {
                        System.out.println("Time-to-live of" + key + "has expired");
                }
               else {
                    data = key + ":" + temp[0];
                    System.out.println("Your Data:-'"+data+"'");
                }
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
    }


    static void DeleteData(String key){
        if(!has(Data_Base,key)){
            System.out.println("Given key does not exist in database. Please enter a valid key");
        }
        else{
            long[] temp={0,0};
            try {
                JSONArray valueArr = new JSONArray();
                valueArr = (JSONArray)supportingDB.get(key);

                temp[0] = ((Number) valueArr.get(0)).longValue();
                temp[1] = ((Number) valueArr.get(1)).longValue();

                if (temp[1] != 0 && Instant.now().getEpochSecond() > temp[1]) {
                    System.out.println("Time-to-live of" + key + "has expired");
                }
                else {
                    supportingDB.remove(key);
                    Data_Base.remove(key);
                    writeTOFile(Data_Base);
                    System.out.println("key is successfully deleted.");
                }
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
    }

    private static void writeTOFile(JSONObject jsonObj){
        try {
            PrintWriter pw = new PrintWriter(File_path);
            pw.write(jsonObj.toJSONString());
            pw.flush();
            pw.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    private static boolean has(JSONObject jsonObj, String key){
       String obj=jsonObj.toString();
       return obj.contains('"'+key+'"'+":");
    }
}