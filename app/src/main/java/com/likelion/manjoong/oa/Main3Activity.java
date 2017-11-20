package com.likelion.manjoong.oa;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {
    TextView txtname, etmenu1;
    TextView tvTel, tvURL;
    ImageView imageView2;//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        setTitle("자세히 보기");
        init();
    }

    void init(){
        int n;

        txtname = (TextView) findViewById(R.id.textname);

        etmenu1 = (TextView) findViewById(R.id.etmenu1);


        imageView2 = (ImageView) findViewById(R.id.imageView2);


        tvTel = (TextView) findViewById(R.id.tvURL);
        tvURL = (TextView) findViewById(R.id.tvTel);


        Intent intent = getIntent();

        restaurant r =intent.getParcelableExtra("restaurant");

        txtname.setText(r.getName());



        etmenu1.setText(r.getMenu());


        n = r.getType();


        final String tel = r.getTell();
        final String h = r.getHomepage();


        tvTel.setText(tel);
        tvURL.setText(h);


        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:/" + tel));
                startActivity(intent);
            }
        });

    }


//    public void onClick(View v){
//        if(v.getId()==R.id.btnback){
//            finish();
//        }
//    }
}
