package kr.ac.tukorea.selectmenu

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.*
import java.io.File
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    lateinit var myHelper:myDBHelper
    lateinit var menuImageView:ImageView
    lateinit var menuSelectBtn: ImageView
    lateinit var edtMenuResult:EditText
    lateinit var sqlDB:SQLiteDatabase


    //음식 이름과 사진 url Array
    var foodArray=arrayOf(arrayOf("비빔밥", "R.drawable.bibimbap"),arrayOf("라면", "R.drawable.ramen"), arrayOf("파스타", "R.drawable.pasta"), arrayOf("햄버거", "R.drawable.hamburger"))
    var curNum:Int=foodArray.size

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        menuSelectBtn = findViewById<ImageView>(R.id.selectBtn)
        menuImageView = findViewById<ImageView>(R.id.randomFoodImage)


        myHelper=myDBHelper(this)   //  menuDB 데이터베이스 생성

        saveMenuToDatabase()
        changeRandomImageUrl()

        var timerTask = timer(period=1000){ //  changeRamdomImageUrl을 1초마다 생성 이미지가 1초마다 변경됨
            changeRandomImageUrl()
        }


        menuSelectBtn.setOnClickListener {  //  timer을 정지시킴
            timerTask.cancel()
        }
    }

    private fun saveMenuToDatabase(){    //  음식 메뉴 DB에 저장
        sqlDB=myHelper.writableDatabase
        for(i in 0 until curNum step 1){
            sqlDB.execSQL("INSERT INTO menuDB VALUES ( "+i+", '"+foodArray!![i][0]+"', '"+foodArray!![i][1]+"');")
        }
        sqlDB.close()
    }

    private fun changeRandomImageUrl(){
        var url=getResources().getIdentifier(getImageId(), null, null)
        menuImageView.setImageResource(url)
    }

    private fun getImageId():String{
        var range=(0 until curNum).random()

        sqlDB=myHelper.readableDatabase
        var cursor:Cursor   //menuDB 테이블에서 가져온 데이터를 커서에 전달
        cursor=sqlDB.rawQuery("SELECT * FROM menuDB WHERE menuNo=${range};", null)
        return cursor.toString()
    }

    inner class myDBHelper(context: Context):SQLiteOpenHelper(context, "menuDB", null, 1){
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL("CREATE TABLE menuDB (menuNo INTERGER(10), menuName CHAR(20), menuURL CHAR(30));")
        }
        override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS menuDB")
            onCreate(db)
        }
    }
}