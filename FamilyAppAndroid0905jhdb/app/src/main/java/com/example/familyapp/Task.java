package com.example.familyapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class Task extends AsyncTask<String, String, String> {
    public static String ip ="172.30.1.54"; //자신의 IP번호 - 집
    //public static String ip ="192.168.0.27"; //자신의 IP번호 - 학원

    String sendMsg, receiveMsg;
    String serverUrl = "http://"+ip+":8090/FamilyApp2/"; // 연결할 jsp주소

    Task(String sendmsg){
        this.sendMsg = sendmsg;
    }
    @Override
    protected String doInBackground(String... strings) {
        try {

            if(sendMsg.equals("join.do")){
                serverUrl += sendMsg;
                sendMsg = "id=" + strings[0] + "&pw=" + strings[1] + "&pwConfirm=" + strings[2] +
                        "&name=" + strings[3] + "&phoneNum=" + strings[4] + "&bDay=" + strings[5] +
                        "&nickname=" + strings[6] + "&role=" + strings[7] + "&fCode=" + strings[8];
            }else if(sendMsg.equals("join_CheckId.do")){
                serverUrl += sendMsg;
                sendMsg = "id="+strings[0];
            }else if(sendMsg.equals("join_OverlapFcode.do")){
                serverUrl += sendMsg;
                sendMsg = "fCode="+strings[0];
            }else if(sendMsg.equals("join_CheckFcode.do")){
                serverUrl += sendMsg;
                sendMsg = "fCode="+strings[0];
            }else if(sendMsg.equals("join_AddFcode.do")){
                serverUrl += sendMsg;
                sendMsg = "fCode="+strings[0] + "&cName=" + strings[1];
            }else if(sendMsg.equals("question_load.do")){
                serverUrl += sendMsg;
                sendMsg = "userId="+strings[0];
            }else if(sendMsg.equals("login.do")){
                serverUrl += sendMsg;
                sendMsg = "id="+strings[0] + "&pw=" + strings[1];
            }else if(sendMsg.equals("question.do")){
                serverUrl += sendMsg;
                sendMsg = "&type="+strings[0];
            }else if(sendMsg.equals("answer.do")){
                serverUrl += sendMsg;
                sendMsg = "&type="+strings[0];
            }else if(sendMsg.equals("post.do")){
                serverUrl += sendMsg;
                sendMsg = "&type="+strings[0];
            }else if(sendMsg.equals("photo.do")){
                serverUrl += sendMsg;
                sendMsg = "&type="+strings[0];
            }else if(sendMsg.equals("audio.do")){
                serverUrl += sendMsg;
                sendMsg = "&type="+strings[0];
            }else if(sendMsg.equals("storage.do")){
                serverUrl += sendMsg;
                sendMsg = "&type="+strings[0];
            }
//            String sendmsg = "join.do"; //페이지.do
//            String result = null;
//            try {
//                result = new Task(sendmsg).execute(id,pw).get();
//                  //execute(db에 보낼 값)
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            String str = "";
            URL url = new URL(serverUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
            osw.write(sendMsg);
            osw.flush();
            osw.close();

            if(conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
            } else {
                StringBuffer buffer = new StringBuffer();
                buffer.append(str);
                Log.i("통신 결과", conn.getResponseCode()+"에러");
                Log.d("JSP서버 메시지",str);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receiveMsg;
    }

}