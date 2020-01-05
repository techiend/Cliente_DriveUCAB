package Utilidades;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

public class Usuario {

    private String name = "";
    private String lname = "";
    private String email = "";
    private String pwd = "";
    private String touched_pwd = "";
    private String touched_fname = "";
    private String curr_dir = "";
    private String maxSpace = "2 GB";
    private long maxSpaceNum = 2097152;
    private int id = 0;
    private JSONArray files = new JSONArray();

    private Usuario(){
    }

    public static Usuario getInstance(){
        return NewSingletonHolder.INSTANCE;
    }

    private static class NewSingletonHolder{
        private static final Usuario INSTANCE = new Usuario();
    }

    public String getTouched_fname() {
        return touched_fname;
    }

    public void setTouched_fname(String touched_fname) {
        this.touched_fname = touched_fname;
    }

    public String getTouched_pwd() {
        return touched_pwd;
    }

    public void setTouched_pwd(String touched_pwd) {
        this.touched_pwd = touched_pwd;
    }

    public long getMaxSpaceNum() {
        return maxSpaceNum;
    }

    public void setMaxSpaceNum(long maxSpaceNum) {
        this.maxSpaceNum = maxSpaceNum;
    }

    public String getMaxSpace() {
        return maxSpace;
    }

    public void setMaxSpace(String maxSpace) {
        this.maxSpace = maxSpace;
    }

    public String getCurr_dir() {
        return curr_dir;
    }

    public void setCurr_dir(String curr_dir) {
        this.curr_dir = curr_dir;
    }

    public String getName() {
        return name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JSONArray getFiles() {
        return files;
    }

    public void setFiles(JSONArray files) {
        this.files = files;
    }

    public JSONArray validateDuplicated(JSONArray files, JSONObject archivo){

        boolean add = false;
        System.out.println("Validando similares.");
        for (int i=0; i < files.length(); i++){
            JSONObject file = files.getJSONObject(i);

            if (file.getString("f_name").equals(archivo.getString("f_name"))){
                files.remove(i);
                add = true;
                System.out.println("archivo igual, actualizando");
            }

        }

        if (add){
            files.put(archivo);
        }

        return files;
    }

    public JSONArray addFile(JSONArray filex, JSONObject archivo){

        boolean add = false;
        System.out.println("Validando similares.");
        for (int i=0; i < filex.length(); i++){
            JSONObject file = filex.getJSONObject(i);

            if (file.getString("f_name").equals(archivo.getString("f_name"))){
                filex.remove(i);
                add = true;
                System.out.println("archivo igual, actualizando");
            }

        }

        if (add){
            filex.put(archivo);
        }

        return filex;
    }
}