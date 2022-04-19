package kr.ac.tukorea.selectmenu

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    lateinit var myHelper: myDBHelper
    lateinit var menuImageView: ImageView
    lateinit var menuSelectBtn:Button
    lateinit var sqlDB:SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        menuImageView.findViewById<ImageView>(R.id.randomFoodImage)
        menuSelectBtn.findViewById<Button>(R.id.selectBtn)
        myHelper=myDBHelper(this)

    }
    inner class myDBHelper(context: Context):SQLiteOpenHelper(context, "menuDB", null, 1){
        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL("CREATE TABLE menuDB (menuName CHAR(20), menuURL CHAR(30), menu);")
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("DROP TABLE IF EXISTS menuDB")
            onCreate(p0)
        }
    }
}