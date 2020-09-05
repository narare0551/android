package com.example.familyapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class JoinActivity extends AppCompatActivity {

    EditText edId, edPw, edPwconfirm, edName, edPhonenum , edBday, edNickname, edRole ,edFcode;
    Button btnIdCheck, btnCheckFcode, btnOverlapFcode, btnSubmit;
    TextView tvIdCheck, tvFcodeCheck;
    String sendmsg;
    String result,fCode;
    CheckBox checkNew, checkAlready;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //checkNew = findViewById(R.id.checkNew);
        //checkAlready = findViewById(R.id.checkAlready);
        tvIdCheck = findViewById(R.id.join_tv_checkid);
        tvFcodeCheck = findViewById(R.id.join_tv_checkfcode);
        edId = findViewById(R.id.join_et_id);
        edPw = findViewById(R.id.join_et_pw);
        edPwconfirm = findViewById(R.id.join_et_pw2);
        edName = findViewById(R.id.join_et_name);
        edPhonenum = findViewById(R.id.join_et_phone);
        edBday = findViewById(R.id.join_et_birthday);
        edNickname = findViewById(R.id.join_et_nickname);
        edRole = findViewById(R.id.join_et_role);
        edFcode = findViewById(R.id.join_et_fcode);
        btnSubmit = findViewById(R.id.join_btn_join);
        btnIdCheck = findViewById(R.id.join_btn_checkid);
        btnCheckFcode = findViewById(R.id.join_btn_checkfcode);
        btnOverlapFcode = findViewById(R.id.join_btn_overlapfcode);

        //중복확인 버튼 클릭시(아이디)
        btnIdCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edId.getText().toString();
                tvIdCheck.setText(id);
                if (id.equals("")) {
                    tvIdCheck.setText("아이디를 입력 해주세요.");
                } else {
                    sendmsg = null;
                    result = null;
                    try {
                        sendmsg = "join_CheckId.do";
                        result = new Task(sendmsg).execute(id).get();
                        tvIdCheck.setText(result);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //가족코드 중복확인(새로,처음 가족 만들시)
        btnOverlapFcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fCode = edFcode.getText().toString();
                tvFcodeCheck.setText(fCode);
                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                if (fCode.equals("")) {
                    tvFcodeCheck.setText("가족코드를 입력 해주세요.");
                } else {
                    sendmsg = null;
                    result = null;
                    try {
                        sendmsg = "join_OverlapFcode.do";
                        result = new Task(sendmsg).execute(fCode).get();
                        tvFcodeCheck.setText(result);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(result.equals("사용 가능")) {
                    builder.setTitle("가족코드 인증 결과");
                    builder.setMessage("사용가능한 가족 코드입니다. 이 코드로 가족을 등록 하시겠습니까?");
                    builder.setNegativeButton("아니오", null);
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sendmsg = null;
                            result = null;
                            try {
                                sendmsg = "join_AddFcode.do";
                                result = new Task(sendmsg).execute(fCode,edName.getText().toString()).get();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if(result.equals("가족 등록 완료.")){
                                tvFcodeCheck.setText("가족 등록 완료.");
                                edFcode.setInputType(InputType.TYPE_NULL);
                            }else{
                                tvFcodeCheck.setText("가족 등록 서버 에러.");
                            }
                        }
                    });
                    builder.create().show();
                }
            }
        });

        //가족코드 존재확인(기존 가족에 추가시)
        btnCheckFcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fCode = edFcode.getText().toString();
                if (fCode.equals("")) {
                    tvFcodeCheck.setText("가족코드를 입력 해주세요.");
                } else {
                    sendmsg = null;
                    result = null;
                    try {
                        sendmsg = "join_CheckFcode.do";
                        result = new Task(sendmsg).execute(fCode).get();

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tvFcodeCheck.setText(result);
                }
            }
        });

//        checkNew.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //카족코드 중복확인버튼 생기기
//            }
//        });
//
//        checkAlready.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //카족코드 확인버튼 생기기
//            }
//        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edId.getText().toString();
                String pw = edPw.getText().toString();
                String pw2 = edPwconfirm.getText().toString();
                String name = edName.getText().toString();
                String phoneNum = edPhonenum.getText().toString();
                String bDay = edBday.getText().toString();
                String nickname = edNickname.getText().toString();
                String role = edRole.getText().toString();
                String fCode = edFcode.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);

                if(pw.equals("") || pw2.equals("")){
                    builder.setTitle("회원가입 실패");
                    builder.setMessage("비밀번호를 입력 해주세요.");
                    builder.setNeutralButton("확인",null);
                    builder.create().show();
                }else if(!pw.equals(pw2)){
                    builder.setTitle("회원가입 실패");
                    builder.setMessage("비밀번호가 다릅니다.");
                    builder.setNeutralButton("확인",null);
                    builder.create().show();
                }else {

                    sendmsg = null;
                    result = null;
                    try {
                        sendmsg = "join.do";
                        result = new Task(sendmsg).execute(id, pw, pw2, name, phoneNum, bDay, nickname, role, fCode).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (result.equals("가입 성공")) {
                        Toast.makeText(JoinActivity.this, result, Toast.LENGTH_SHORT).show();
                        //성공시 갈 페이지
                        Intent intent = new Intent(getApplicationContext(), JoinSuccessActivity.class);
                        intent.putExtra("fCode", fCode);
                        startActivity(intent);
                    } else {
                        Toast.makeText(JoinActivity.this, result, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //가족코드 인증성공시 실행할 메서드
    private DialogInterface.OnClickListener addFcode(String fCode) {
        sendmsg = null;
        result = null;
        try {
            sendmsg = "join_AddFcode.do";
            result = new Task(sendmsg).execute(fCode).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(result.equals("가족 등록 완료.")){
            tvFcodeCheck.setText("가족 등록 완료.");
            edFcode.setClickable(false);
        }else{
            tvFcodeCheck.setText("가족 등록 서버 에러.");
        }
        return null;
    }
}