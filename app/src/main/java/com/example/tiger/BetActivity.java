package com.example.tiger;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.resource;

public class BetActivity extends Activity {

    // 定义列表和适配器
    private ListView listview;
    private SimpleAdapter simpleAdapter;

    // 定义图片
    public static final int[] IMG = {R.drawable.img0, R.drawable.img1,
            R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5,
            R.drawable.img6, R.drawable.img7};
    // 定义人名
    public static final String[] NAME = {"傻彪", "钟爱药", "陈队", "陈小龟", "陈少度",
            "大桥", "小乔", "鲁班七号"};
    // 定义赔率
    public static final Double[] RATES = {5.0, 4.0, 3.5, 4.5, 5.0, 4.0, 3.5,
            4.5};


    //确定按钮
    Button btnComfirm;

    private int pos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);
        btnComfirm=(Button)findViewById(R.id.btnComfirm);
        btnComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pos < 0) {
                    Toast.makeText(BetActivity.this, "请下注", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 确定金额
                RadioGroup group = (RadioGroup) findViewById(R.id.group);
                int checkedId = group.getCheckedRadioButtonId();
                RadioButton button = (RadioButton) findViewById(checkedId);
                String selectedMoney = button.getText().toString();
                // int a=3;
                // String b=a+"";
                int money = Integer.parseInt(selectedMoney);
                Intent data = new Intent();
                data.putExtra("money", money);
                data.putExtra("pos", pos);
                setResult(RESULT_OK, data);

                finish();
            }
        });

        listview = (ListView) findViewById(R.id.listview);
        // 创建适配器
        //上下文 this or 类名+this。
        //数据源
        //子项布局 R.layout.xxx
        //数据从哪里来的
        //数据到哪里去
        simpleAdapter = new SimpleAdapter(BetActivity.this, data(),
                R.layout.listview_item, new String[] { "img", "name", "rate" },
                new int[] { R.id.item_img, R.id.item_name, R.id.item_rate });

        listview.setAdapter(simpleAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view,
                                    int position, long arg3) {
                // TODO Auto-generated method stub
                pos = position;
            }
        });


    }

    public List<Map<String, Object>> data() {

        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < IMG.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", IMG[i]);
            map.put("name", NAME[i]);
            map.put("rate", RATES[i]);
            data.add(map);
        }
        return data;
    }







}