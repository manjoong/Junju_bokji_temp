package com.likelion.manjoong.oa;

import android.app.FragmentManager;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;


public class PeoplelessActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peopleless);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);






    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.setOnMarkerClickListener(this);
        Marker facility;
        ///////////////////////여기부터 xml데이터 관리 부분//////////////////////////////////////
        AssetManager am = getResources().getAssets() ;
        StrictMode.enableDefaults();
        boolean inName = false, inPhone = false;
        boolean inCol = false, inRow = false;
        String name = null,  phone= null, col = null, row = null;
        double latitude =0.0D, longitude = 0.0D;
        String queryUrl="http://openapi.jeonju.go.kr/rest/autodispenser/getAutoDispenser?authApiKey=8rULtNWhTcB9%2FM2fsJHpyZqt%2FK94LqQWfNl9QTmvLKP56PotRCl8iO2yOaYnxffLg7nG8qHsMPLIijmqenTyrg%3D%3D";//요청 URL



        try{
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(is, null);




            int parserEvent = parser.getEventType();
            System.out.println("파싱시작합니다.");

            while (parserEvent != XmlPullParser.END_DOCUMENT){

                switch(parserEvent){
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if(parser.getName().equals("list"));//title 만나면 내용을 받을수 있게 하자
                        else if(parser.getName().contains("insArea")){
                            inName = true;
                        }
                        else if(parser.getName().contains("oldAddr")){ //mapx 만나면 내용을 받을수 있게 하자
                            inPhone= true;
                        }
                        else if(parser.getName().contains("posx")){ //address 만나면 내용을 받을수 있게 하자
                            inCol = true;
                        }
                        else if(parser.getName().contains("posy")){ //address 만나면 내용을 받을수 있게 하자
                            inRow = true;
                        }
                        break;



                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if(inName){ //isTitle이 true일 때 태그의 내용을 저장.
                            name = parser.getText();
                            inName = false;
                        }
                        if(inPhone){ //isMapx이 true일 때 태그의 내용을 저장.
                            phone = parser.getText();
                            inPhone = false;
                        }
                        if(inCol){ //isAddress이 true일 때 태그의 내용을 저장.
                            col = parser.getText();
                            inCol = false;
                        }
                        if(inRow){ //isAddress이 true일 때 태그의 내용을 저장.
                            row = parser.getText();
                            inRow = false;
                        }

                        break;
                    case XmlPullParser.END_TAG:

                        if(parser.getName().equals("list")){


                            latitude = Double.parseDouble(col);
                            longitude = Double.parseDouble(row);


                            LatLng SEOUL = new LatLng(latitude, longitude);
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(SEOUL);
                            markerOptions.title(name);
                            markerOptions.snippet(phone);
                            googleMap.addMarker(markerOptions);

                            if(parser.getName().equals("list")){
                                LatLng JEONJU = new LatLng(35.8241932,127.14800049999997);
                                MarkerOptions markerOptions2 = new MarkerOptions();
                                markerOptions2.position(JEONJU);
                                markerOptions2.title("현재 위치");
                                markerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                                googleMap.addMarker(markerOptions2);}

                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
                            googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));




                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch(Exception e){
        }
    }


    @Override
    public boolean onMarkerClick(Marker marker) {


        return false;
    }
}
