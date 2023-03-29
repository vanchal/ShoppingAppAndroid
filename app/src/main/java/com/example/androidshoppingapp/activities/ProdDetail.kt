package com.example.androidshoppingapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.iterator
import com.example.androidshoppingapp.R
import com.example.androidshoppingapp.models.AddItemCartRequest
import com.squareup.picasso.Picasso


private  var activityResultLauncher : ActivityResultLauncher<Intent>? = null
class ProdDetail : AppCompatActivity() {
    private var prodId : String ? = null
    private var list = arrayListOf<String>()
    private var img1 : ImageView ? = null

    override fun onBackPressed() {
        Log.e("onBackPressedfromProduct", "onbckpress called")
        val intent = Intent(this, LandingPage::class.java)
        intent.putExtra("prodId", prodId)
        intent.putExtra("deletedList", list)
        setResult(2, intent)
        super.onBackPressed()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prod_detail)

        var addBtn = findViewById<Button>(R.id.add_cart)
        val url1 = intent.getStringExtra("img1")
        img1 = findViewById(R.id.img1)
//        val img2 = findViewById<ImageView>(R.id.img2)
        val image1 = findViewById<ImageView>(R.id.image1)
        val image2 = findViewById<ImageView>(R.id.image2)
        val image3 = findViewById<ImageView>(R.id.image3)
        val image4 = findViewById<ImageView>(R.id.image4)
        val image5 = findViewById<ImageView>(R.id.image5)
        val text1 = findViewById<TextView>(R.id.t1)
        val text2 = findViewById<TextView>(R.id.t2)
        val text3 = findViewById<TextView>(R.id.t3)
        val url2 = intent.getStringExtra("image1")
        val url3 = intent.getStringExtra("image2")
        val url4 = intent.getStringExtra("image3")
        val url5 = intent.getStringExtra("image4")
        prodId = intent.getStringExtra("prodId")


        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.e("activityResultLauncher", "registerForActivityResult called from prodDetail")
            if(result.resultCode == RESULT_OK){
                list= result.data?.getStringArrayListExtra("deletedList") as ArrayList<String>
             if(list?.contains(prodId) == true) {
                 addBtn.text = "ADD"
                 addBtn?.setOnClickListener {
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

        Picasso.get().load(url1).into(img1)
        Picasso.get().load(url1).into(image1)
        Picasso.get().load(url2).into(image2)
        Picasso.get().load(url3).into(image3)
        Picasso.get().load(url4).into(image4)
        Picasso.get().load(url5).into(image5)

//        Picasso.get().load(url2).into(img2)
//        img2.setImageResource(intent.getStringExtra("img2"))
        text1.text = intent.getStringExtra("text1")
        text2.text = "Rs " + intent.getStringExtra("text2")
        text3.text = intent.getStringExtra("text3")
        addBtn?.text = intent.getStringExtra("addButton")

//        if(item.ispresent == true){
//            addBtn.text = "Go To Cart"
//            addBtn?.setOnClickListener{
//                val intent = Intent(this, Cart1::class.java)
//                activityResultLauncher?.launch(intent)
////                startActivity(intent)
//            }
//        }
//        else{
//            addBtn?.setOnClickListener {
//                addBtn?.text = "Go To Cart"
//                item.ispresent = true
//                addBtn?.setOnClickListener {
//                    val intent = Intent(this, Cart1::class.java)
//                    activityResultLauncher?.launch(intent)
////                    startActivity(intent)
//                }
//                val addItemRequest: AddItemCartRequest = AddItemCartRequest(
//                    img = url1,
//                    productId = prodId,
//                    price = intent.getStringExtra("text2")?.toInt(),
//                    name = intent.getStringExtra("text2")
//                )
//                CartFunc.getInstance(applicationContext).addToCart(addItemRequest, addBtn)
//            }
//
//        }

        if (addBtn?.text == "Go To Cart") {
            addBtn?.setOnClickListener{
                val intent = Intent(this, Cart1::class.java)
                activityResultLauncher?.launch(intent)
//                startActivity(intent)
            }
        } else {
            addBtn?.setOnClickListener {
                addBtn?.text = "Go To Cart"
                var position : Int = LandingPage.productList.indexOfFirst { p->
                    p.id == prodId
                }
                var item = LandingPage.productList.get(position)
                item.ispresent = true
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


            setImage(image1,url1!!)
            setImage(image2,url2!!)
            setImage(image3,url3!!)
            setImage(image4,url4!!)
            setImage(image5,url5!!)
    }
    private fun setImage(image: ImageView,url : String){
        image.setOnClickListener{
            Picasso.get().load(url).into(img1)
        }

}


}


