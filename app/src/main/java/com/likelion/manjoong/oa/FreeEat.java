package com.likelion.manjoong.oa;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;

public class FreeEat extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_eat);
        AssetManager am = getResources().getAssets() ;
        InputStream is = null ;


        StrictMode.enableDefaults();

        TextView status1 = (TextView)findViewById(R.id.result); //파싱된 결과확인!

        boolean inName = false, inAddr = false, inPhone = false, inTarget = false, inTime = false;

        String name = null, addr = null, phone= null, target = null, time = null;


        try{
            is = am.open("FreeEat.xml") ;

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(is, null);


            int parserEvent = parser.getEventType();
            System.out.println("파싱시작합니다.");

            while (parserEvent != XmlPullParser.END_DOCUMENT){

                switch(parserEvent){
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if(parser.getName().equals("Row"));//title 만나면 내용을 받을수 있게 하자
                            else if(parser.getName().equals("시설명")){
                            inName = true;
                            }
                            else if(parser.getName().equals("소재지도로명주소")){ //address 만나면 내용을 받을수 있게 하자
                            inAddr = true;
                            }
                            else if(parser.getName().equals("전화번호")){ //mapx 만나면 내용을 받을수 있게 하자
                            inPhone= true;
                            }
                            else if(parser.getName().equals("급식대상")){ //mapy 만나면 내용을 받을수 있게 하자
                            inTarget = true;
                            }
                            else if(parser.getName().equals("급식시간")){ //mapy 만나면 내용을 받을수 있게 하자
                            inTime = true;
                            }

                            else if(parser.getName().equals("message")){ //message 태그를 만나면 에러 출력
                            status1.setText(status1.getText()+"에러");
                            //여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
                            }

                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때

                        if(inName){ //isTitle이 true일 때 태그의 내용을 저장.
                            name = parser.getText();
                            inName = false;
                        }
                        if(inAddr){ //isAddress이 true일 때 태그의 내용을 저장.
                            addr = parser.getText();
                            inAddr = false;
                        }
                        if(inPhone){ //isMapx이 true일 때 태그의 내용을 저장.
                            phone = parser.getText();
                            inPhone = false;
                        }
                        if(inTarget){ //isMapy이 true일 때 태그의 내용을 저장.
                            target = parser.getText();
                            inTarget = false;
                        }
                        if(inTime){ //isMapy이 true일 때 태그의 내용을 저장.
                            time = parser.getText();
                            inTime = false;
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("Row")){
                            status1.setText(status1.getText()+"이름 : "+ name +"\n 주소: "+ addr +"\n 번호 : " + phone+
                                    "\n 대상 : " + target +  "\n 배식시간 : " + time+ "\n\n");
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch(Exception e){
            status1.setText("에러가..났습니다...");
        }
    }
}