package com.example.androidshoppingapp.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.androidshoppingapp.R
import com.example.androidshoppingapp.models.AddItemCartRequest
import com.squareup.picasso.Picasso

private  var activityResultLauncher : ActivityResultLauncher<Intent>? = null
class ProdDetail : AppCompatActivity() {
    private var prodId : String ? = null

    override fun onBackPressed() {
        val intent = Intent(this, LandingPage::class.java)
        intent.putExtra("prodId", prodId)
        setResult(2, intent)
        super.onBackPressed()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prod_detail)

        var addBtn = findViewById<Button>(R.id.add_cart)
        val url1 = intent.getStringExtra("img1")

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.e("activityResultLauncher", "registerForActivityResult called")
            if(result.resultCode == RESULT_OK){
                var list= result.data?.getStringArrayListExtra("deletedList") as ArrayList<String>?
                Log.e("list", "$list")

             if(list?.contains(prodId) == true) {
                 addBtn.text = "ADD"
                 addBtn?.setOnClickListener {
                     prodId = intent.getStringExtra("prodId")
                     addBtn?.text = "Go To Cart"
                     addBtn?.setOnClickListener {
                         val intent = Intent(this, Cart1::class.java)
                         activityResultLauncher?.launch(intent)
//                    startActivity(intent)
                     }
                     val addItemRequest: AddItemCartRequest = AddItemCartRequest(
                         img = url1,
                         productId = prodId,
                         price = intent.getStringExtra("text2")?.toInt(),
                         name = intent.getStringExtra("text2")
                     )

                     CartFunc.getInstance(applicationContext).addToCart(addItemRequest, addBtn)
                 }

             }
            }
        }


        val img1 = findViewById<ImageView>(R.id.img1)
        val img2 = findViewById<ImageView>(R.id.img2)
        val text1 = findViewById<TextView>(R.id.t1)
        val text2 = findViewById<TextView>(R.id.t2)
        val text3 = findViewById<TextView>(R.id.t3)





        val url2 = intent.getStringExtra("img2")

        Picasso.get().load(url1).into(img1)
        Picasso.get().load(url2).into(img2)
//        img2.setImageResource(intent.getStringExtra("img2"))
        text1.text = intent.getStringExtra("text1")
        text2.text = "Rs " + intent.getStringExtra("text2")
        text3.text = intent.getStringExtra("text3")
        addBtn?.text = intent.getStringExtra("addButton")

        if (addBtn?.text == "Go To Cart") {
            addBtn?.setOnClickListener{
                val intent = Intent(this, Cart1::class.java)
                activityResultLauncher?.launch(intent)
//                startActivity(intent)
            }
        } else {
            addBtn?.setOnClickListener {
                prodId = intent.getStringExtra("prodId")
                addBtn?.text = "Go To Cart"
                addBtn?.setOnClickListener {
                    val intent = Intent(this, Cart1::class.java)
                    activityResultLauncher?.launch(intent)
//                    startActivity(intent)
                }
                val addItemRequest: AddItemCartRequest = AddItemCartRequest(
                    img = url1,
                    productId = prodId,
                    price = intent.getStringExtra("text2")?.toInt(),
                    name = intent.getStringExtra("text2")
                )

                CartFunc.getInstance(applicationContext).addToCart(addItemRequest, addBtn)

            }
        }



            var person = findViewById<ImageButton>(R.id.person)
            person.setOnClickListener {
                val intent = Intent(this, Profile::class.java)
                startActivity(intent)
            }


            var cartIcon = findViewById<ImageButton>(R.id.cart)
            cartIcon.setOnClickListener {
                val intent = Intent(this, Cart1::class.java)
                activityResultLauncher?.launch(intent)
//                startActivity(intent)
            }


            val arrow = findViewById<ImageView>(R.id.backbtn)
            arrow.setOnClickListener {
                onBackPressed()
            }
    }
}