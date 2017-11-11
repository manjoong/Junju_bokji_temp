package com.likelion.manjoong.oa;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

public class WelfareSearch extends Activity {

    EditText edit;
    TextView text;

    XmlPullParser xpp;
    String key="8rULtNWhTcB9%2FM2fsJHpyZqt%2FK94LqQWfNl9QTmvLKP56PotRCl8iO2yOaYnxffLg7nG8qHsMPLIijmqenTyrg%3D%3D";
    //key = 공공데이터 api 사용을 위한 고유 키
    String data;  //공공 데이터를 가져올 데이타를 스트링 값으로 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welfare_search);

        edit= (EditText)findViewById(R.id.edit); //검색창 선언
        text= (TextView)findViewById(R.id.result); //보여줄 창 선언
    }

    //Button을 클릭했을 때 자동으로 호출되는 callback method....
    public void mOnClick(View v){

        switch( v.getId() ){
            case R.id.button:

                //Android 4.0 이상 부터는 네트워크를 이용할 때 반드시 Thread 사용해야 함
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        data= getXmlData();//아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기

                        //UI Thread(Main Thread)를 제외한 어떤 Thread도 화면을 변경할 수 없기때문에
                        //runOnUiThread()를 이용하여 UI Thread가 TextView 글씨 변경하도록 함
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                text.setText(data); //TextView에 문자열  data 출력
                            }
                        });

                    }
                }).start();

                break;
        }

    }//mOnClick method..


    //XmlPullParser를 이용하여 제공하는 OpenAPI XML 파일 파싱하기(parsing)
    String getXmlData(){

        StringBuffer buffer=new StringBuffer();

        String str= edit.getText().toString();//EditText에 작성된 Text얻어오기
        String search = URLEncoder.encode(str);//한글의 경우 인식이 안되기에 utf-8 방식으로 encoding..
        String query = "%EC%A0%84%EB%A0%A5%EB%A1%9C";

        String queryUrl="http://openapi.jeonju.go.kr/rest/welfarenew/getWelfareList?"//요청 URL
                +"authApiKey="+key
                +"&searchKeyword=" + search;

        try {
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//테그 이름 얻어오기

                        if(tag.equals("list")) ;// 첫번째 검색결과
                        else if(tag.equals("welFareChargeNm")){
                            buffer.append("담당자명 : ");
                            xpp.next();
                            buffer.append(xpp.getText());//list 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("welFareChargeTel")){
                            buffer.append("기관 전화번호 : ");
                            xpp.next();
                            buffer.append(xpp.getText());// 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("welFareContent")){
                            buffer.append("복지내용 :");
                            xpp.next();
                            buffer.append(xpp.getText());// 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("welFareDepartment")){
                            buffer.append("담당기관 :");
                            xpp.next();
                            buffer.append(xpp.getText());// 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("welFareDispauper")){
                            buffer.append("수혜 대상 :");
                            xpp.next();
                            buffer.append(xpp.getText());// 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("welFareGuideMemo")){
                            buffer.append("안내사항 :");
                            xpp.next();
                            buffer.append(xpp.getText());// 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("  ,  "); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("welFareHavePaper")){
                            buffer.append("구비서류 :");
                            xpp.next();
                            buffer.append(xpp.getText());// 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("welFareHowApplication")){
                            buffer.append("신청방식 :");
                            xpp.next();
                            buffer.append(xpp.getText());//요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("welFareHowSupported")){
                            buffer.append("지원방법 :");
                            xpp.next();
                            buffer.append(xpp.getText());// 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("welFareIsApplication")){
                            buffer.append("신청서 작성여부 :");
                            xpp.next();
                            buffer.append(xpp.getText());// 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("welFareLicense")){
                            buffer.append("자격요건 :");
                            xpp.next();
                            buffer.append(xpp.getText());// 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("welFareLifeCycleSort")){
                            buffer.append("대상분류 :");
                            xpp.next();
                            buffer.append(xpp.getText());// 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("welFareName")){
                            buffer.append("복지명 :");
                            xpp.next();
                            buffer.append(xpp.getText());// 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("welFarePurpose")){
                            buffer.append("복지목적 :");
                            xpp.next();
                            buffer.append(xpp.getText());//요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("welFareSid")){
                            buffer.append("번호 :");
                            xpp.next();
                            buffer.append(xpp.getText());//요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("welFareStyleSort")){
                            buffer.append("수혜자 유형 :");
                            xpp.next();
                            buffer.append(xpp.getText());// 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기

                        if(tag.equals("list")) buffer.append("\n");// 첫번째 검색결과종료..줄바꿈

                        break;
                }

                eventType= xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }

        buffer.append("파싱 끝\n");

        return buffer.toString();//StringBuffer 문자열 객체 반환

    }//getXmlData method....

}//MainActivity class..





