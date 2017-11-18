package com.likelion.manjoong.oa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 *  어떤 검색을 할 것인지 선택하는 액티비티
 *  UI를 제대로 구현하지 않았음.
 *  (현재 액티비티에서 버튼을 클릭하고 다음 액티비티로 넘어가면 그 때 검색을 할 것인지,
 *   아니면 지금 액티비티에서 검색을 하고 바로 ListViewActivity로 화면을 전환할 것인지 결정해야 함.)
 *
 *   지금은 버튼을 클릭하면 다음 액티비티로 화면을 전환하고, 그 액티비티에서 검색을 하는 것으로 구현됨.
 */

public class WelfareMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welfare_menu);

        Button btnServiceSearch = (Button) findViewById(R.id.btnServiceSearch);
        btnServiceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), WelfareSearch.class);
                startActivity(myIntent);
            }
        });

        Button btnFreeEat = (Button) findViewById(R.id.btnFreeEat);
        btnFreeEat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), FreeEat.class);
                startActivity(myIntent);
            }
        });

        Button btnFacilitySearch = (Button) findViewById(R.id.btnFacilitySearch);
        btnFacilitySearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), WelfareFacilitySearch.class);
                startActivity(myIntent);
            }
        });

    }
}
