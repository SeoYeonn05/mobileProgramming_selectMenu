package kr.ac.tukorea.selectmenu

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import java.io.File

class MainActivity : AppCompatActivity() {
    lateinit var myHelper:myDBHelper
    lateinit var menuImageView:ImageView
    lateinit var menuSelectBtn:Button
    lateinit var menuAddBtn:Button
    lateinit var edtMenuResult:EditText
    lateinit var sqlDB:SQLiteDatabase

    var curNum:Int=1
    //음식 이름과 사진 url Array
    var foodArray=arrayOf(arrayOf("비빔밥", "R.drawable.bibimbap"),arrayOf("라면", "R.drawable.ramen"), arrayOf("파스타", "R.drawable.pasta"), arrayOf("햄버거", "R.drawable.hamburger")))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        menuImageView.findViewById<ImageView>(R.id.randomFoodImage)
        menuSelectBtn.findViewById<Button>(R.id.selectBtn)
        edtMenuResult.findViewById<EditText>(R.id.edtMenuResult)
        menuAddBtn.findViewById<Button>(R.id.addBtn)

        myHelper=myDBHelper(this)   //  menuDB 데이터베이스 생성

//        var timerFunction=null;


        firstMenuSave()
//        timerFunction=setInterval(changeImage(), 1000)

        menuSelectBtn.setOnClickListener {

        }

    }
    private fun firstMenuSave(){    //  처음 음식 메뉴 DB에 저장
        sqlDB=myHelper.writableDatabase
        for(i in 1..curNum step 1){
            sqlDB.execSQL("INSERT INTO menuDB VALUES ( '"+foodArray!![i][0]+"',"+foodArray!![i][1]+");")
        }
        sqlDB.close()
    }

    fun changeImage(){
        var url=getResources().getIdentifier(readDatabase(), null, null)
        menuImageView.setImageResource(url)
    }

    fun readDatabase():String{
        var range=(1..curNum).random()

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
            db!!.execSQL("ALTER TABLE menuDB EXISTS menuDB")
            onCreate(db)
        }
    }
}