package com.example.tiger;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends Activity {

    Button btnBet;
    Button btnStart;

    private static final int REQUEST_CODE = 100;

    int Money = 0;
    int Pos = -1;

    int x = 0;

    private ImageView image[] = new ImageView[8];

    private TextView tv_cion;
    private int coin = 66666;


    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBet=(Button)findViewById(R.id.btnBet);
        //转跳到下注页面
        btnBet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, BetActivity.class);
                // startActivity(intent);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == 2002) {

                    moveNext();// 把移动的方法拿下来。
                    handler.sendEmptyMessageDelayed(2002, 50);

                }
            };
        };

        btnStart=(Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (Pos < 0) {
                    Toast.makeText(MainActivity.this, "未下注", Toast.LENGTH_SHORT).show();
                    return;
                }
                Random random = new Random();
                int time = random.nextInt(1000) + 2000;


                handler.sendEmptyMessage(2002);
                tv_cion.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        handler.removeMessages(2002);
                        handleResult();

                    }
                }, time);
            }
        });

        image[0] = (ImageView) findViewById(R.id.img0);
        image[1] = (ImageView) findViewById(R.id.img1);
        image[2] = (ImageView) findViewById(R.id.img2);
        image[3] = (ImageView) findViewById(R.id.img3);
        image[4] = (ImageView) findViewById(R.id.img4);
        image[5] = (ImageView) findViewById(R.id.img5);
        image[6] = (ImageView) findViewById(R.id.img6);
        image[7] = (ImageView) findViewById(R.id.img7);
        tv_cion = (TextView) findViewById(R.id.tv_coin1);


    }

    // 重写onActivityResult,捕获数据 的方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Money = data.getIntExtra("money", 0);
            Pos = data.getIntExtra("pos", 0);
            Toast.makeText(MainActivity.this,
                    "金币:" + Money + "," + "下标:" + Pos, Toast.LENGTH_SHORT).show();
        }
    }

    public void moveNext() {
        x = (x + 1) % 8;
		/*
		 * Toast.makeText(MainActivity.this, "当前位置：" + x, Toast.LENGTH_SHORT)
		 * .show();
		 */
        for (int i = 0; i < image.length; i++) {
            if (x == i) {
                image[i].setBackgroundResource(R.drawable.img_bg);

            } else {
                image[i].setBackgroundColor(Color.TRANSPARENT);
            }
        }

    }


    public void handleResult() {
        if (Pos == x) {
            // 中奖
            Toast.makeText(MainActivity.this, "恭喜中奖", Toast.LENGTH_SHORT)
                    .show();
            coin = (int) (coin + (Money * BetActivity.RATES[Pos]));
        } else {
            Toast.makeText(MainActivity.this, "哈哈没中", Toast.LENGTH_SHORT)
                    .show();
            coin = coin - Money;
        }
        tv_cion.setText("金币:" + coin + "");
        Pos = -1;// 清空

    }






}
