package kr.ac.tukorea.selectmenu

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.drawable.Drawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.*
import java.io.File
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    lateinit var menuImageView:ImageView
    lateinit var menuSelectBtn: ImageView
    lateinit var edtMenuResult:EditText
    lateinit var sqlDB:SQLiteDatabase


    //음식 이름과 사진 url Array
    var foodArray=arrayOf(arrayOf("비빔밥", "bibimbap"),arrayOf("라면", "ramen"), arrayOf("파스타", "pasta"), arrayOf("햄버거", "hamburger"))
    var curNum:Int=foodArray.size

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        menuSelectBtn = findViewById<ImageView>(R.id.selectBtn)
        menuImageView = findViewById<ImageView>(R.id.randomFoodImage)



        changeRandomImageUrl()

        var timerTask = timer(period=1000){ //  changeRamdomImageUrl을 1초마다 생성 이미지가 1초마다 변경됨
//            changeRandomImageUrl()
        }


        menuSelectBtn.setOnClickListener {  //  timer을 정지시킴
            timerTask.cancel()
        }
    }

    private fun changeRandomImageUrl(){
        var range=(0 until curNum).random()

        var url:Int=getResources().getIdentifier(foodArray[range][1], "drawable", this.getPackageName())
        menuImageView.setImageResource(url)
    }

}