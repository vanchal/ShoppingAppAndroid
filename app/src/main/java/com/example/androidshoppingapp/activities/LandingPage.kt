package com.example.androidshoppingapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidshoppingapp.R
import com.example.androidshoppingapp.activities.LandingPage.Companion.productList
import com.example.androidshoppingapp.api.ApiInterface
import com.example.androidshoppingapp.api.RetrofitClient
import com.example.androidshoppingapp.helper.AppHelper
import com.example.androidshoppingapp.helper.SharedPrefManager
import com.example.androidshoppingapp.models.*
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
private  var activityResultLauncher : ActivityResultLauncher<Intent>? = null
class LandingPage : AppCompatActivity() {
    var progressBar: ProgressBar? = null
    lateinit  var retrofit: Retrofit
    lateinit var apiInterface: ApiInterface
    lateinit var productListView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)

        initData()
        getProductList()


        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.e("activityResultLauncher", "registerForActivityResult called from LandingPage")
            Log.e("RESULTCODE", "${result.resultCode}")
            if(result.resultCode == RESULT_OK){
                var list = result.data?.getStringArrayListExtra("deletedList") as ArrayList<String>?
//                Log.e("list", "$list")
                list?.forEach{item ->
                    var position: Int = productList.indexOfFirst {p->
                        p.id == item
                    }
                    productList[position].ispresent = false
                    (productListView.adapter as Shoppingadapter).notifyItemChanged(position)
                }
            }
            else if(result.resultCode == 2){
                val prodId = result.data?.getStringExtra("prodId")
                var list = result.data?.getStringArrayListExtra("deletedList") as ArrayList<String>?
                Log.e("onlandingPageList", "$list")
//                var position : Int = productList.indexOfFirst { p->
//                    p.id == prodId
//                }
//                (productListView.adapter as Shoppingadapter).notifyItemChanged(position)
                if(list != null){
                    list?.forEach{item ->
                        var position: Int = productList.indexOfFirst {p->
                            p.id == item
                        }
                        productList[position].ispresent = false
                        (productListView.adapter as Shoppingadapter).notifyItemChanged(position)
                    }
                }
                else{
                    var position : Int = productList.indexOfFirst { p->
                        p.id == prodId
                    }
                    (productListView.adapter as Shoppingadapter).notifyItemChanged(position)
                }
            }
        }

//        var cart_badge = findViewById<ImageView>(R.id.cart_contains_item)
//        Log.e("items", "$Cart1.items.size")
//        if(Cart1.items.size < 0){
//            cart_badge.visibility = View.GONE
//        }



        var person = findViewById<ImageView>(R.id.person)
        person.setOnClickListener{
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }


        var cartIcon = findViewById<ImageView>(R.id.cart)
        cartIcon.setOnClickListener {
            val intent = Intent(this,Cart1::class.java)
            activityResultLauncher?.launch(intent)
//            startActivity(intent)
        }
    }

    companion object {
        var productList = ArrayList<DataItem>()
    }


    private fun initData() {
        retrofit = RetrofitClient.getInstance()
        apiInterface = retrofit.create(ApiInterface::class.java)
    }


    private fun getProductList(){
        if(AppHelper.isConnected(this))
        {
            progressBar = findViewById(R.id.products_progress_bar)
            progressBar?.visibility = View.VISIBLE
            var token = SharedPrefManager.getInstance(applicationContext).token.token.toString()
            apiInterface.getProductList(token = "Bearer $token")?.enqueue(object : Callback<ProductListResponse?> {
                override fun onResponse(
                    call: Call<ProductListResponse?>,
                    response: Response<ProductListResponse?>
                ) {
                    /*Set your data to adapter here*/
                    progressBar?.visibility = View.GONE
                    if (response?.isSuccessful!!) {
                        productList = response.body()?.data as ArrayList<DataItem>
                        productListView =findViewById<RecyclerView>(R.id.shopping_list)
                        productListView.layoutManager =
                            GridLayoutManager(this@LandingPage,2)
                        productListView.adapter = Shoppingadapter(this@LandingPage)
//                            response.body()?.data as List<DataItem>

                    } else {
                        Toast.makeText(this@LandingPage, "Error", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ProductListResponse?>, t: Throwable) {
                    progressBar?.visibility = View.GONE
                    t.printStackTrace()

                    Toast.makeText(this@LandingPage, "t.message", Toast.LENGTH_LONG).show()
                }

            })
        }
        else
        {
            Toast.makeText(this@LandingPage, "Please check you internet connection", Toast.LENGTH_LONG).show()
        }
    }

}




class Shoppingadapter(var context: Context): RecyclerView.Adapter<Shoppingadapter.shoppingviewholder>() {
    class shoppingviewholder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var prodname = itemview.findViewById<TextView>(R.id.text1)
        var prodimg = itemview.findViewById<ImageView>(R.id.prod1)
        var prodcategory = itemview.findViewById<TextView>(R.id.text2)
        var productprice = itemview.findViewById<TextView>(R.id.text3)
        var addButton = itemview.findViewById<Button>(R.id.addBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int):  shoppingviewholder{
        val view = LayoutInflater.from(context).inflate(R.layout.activity_card,parent,false)
        return shoppingviewholder(view)
    }

    override fun onBindViewHolder(holder: shoppingviewholder, position: Int) {
        var shoppingmodel = productList.get(position)
        holder.prodname.text = shoppingmodel.name
        holder.prodcategory.text = shoppingmodel.category
        holder.productprice.text = shoppingmodel.price
        val imageUrl = shoppingmodel.image
        Picasso.get().load(imageUrl).into(holder.prodimg)



        if(shoppingmodel.ispresent == true)
        {
            holder.addButton.text="Go to cart"
            holder.addButton.setOnClickListener{
                val intent = Intent(context,Cart1::class.java)
                activityResultLauncher?.launch(intent)
//                context.startActivity(intent)
                return@setOnClickListener
            }
        }
        else
        {
            holder.addButton.text="ADD"
            holder.addButton.setOnClickListener{
                holder.addButton.text = "Go To Cart"
                holder.addButton.setOnClickListener{
                    val intent = Intent(context,Cart1::class.java)
                    activityResultLauncher?.launch(intent)
//                    context.startActivity(intent)
                }
                val prodId = shoppingmodel.id
                val addItemRequest: AddItemCartRequest = AddItemCartRequest(
                    img = imageUrl,
                    productId = prodId,
                    price = shoppingmodel.price?.toInt(),
                    name = shoppingmodel.name
                )

                CartFunc.getInstance(context as LandingPage).addToCart(addItemRequest, holder.addButton)
            }
        }

        val eachCard = holder.itemView
        eachCard.setOnClickListener {
            val intent = Intent(context, ProdDetail::class.java)
            intent.putExtra("prodId", shoppingmodel.id)
            intent.putExtra("img1", shoppingmodel.image)
//            intent.putExtra("img2",shoppingmodel.image)
            intent.putExtra("text1", shoppingmodel.name)
            intent.putExtra("text2", shoppingmodel.price)
            intent.putExtra("text3", shoppingmodel.description)
            intent.putExtra("image1", shoppingmodel?.carousel?.get(1)!!)
            intent.putExtra("image2", shoppingmodel?.carousel?.get(2)!!)
            intent.putExtra("image3", shoppingmodel?.carousel?.get(3)!!)
            intent.putExtra("image4", shoppingmodel?.carousel?.get(2)!!)
//            if(shoppingmodel.ispresent == true){
//                intent.putExtra("addButton", "Go To Cart")
//            }
//            else{
//                intent.putExtra("addButton", "ADD")
//            }
            if(holder.addButton.text.toString().equals("Go To Cart", ignoreCase = true)){
                Log.e("intent", "intent is passing")
                intent.putExtra("addButton", "Go To Cart")
            }
            else{
                Log.e("intent2", "intent is passing2")
                intent.putExtra("addButton", "ADD")
            }
            activityResultLauncher?.launch(intent)
//            context.startActivity(intent)
        }

//      if(shoppingmodel.ispresent == true){
//          holder.addButton.text = "Go To Cart"
//      }
//        else{
//            holder.addButton.text = "ADD"
//      }

//        holder.addButton.setOnClickListener {
//            if(shoppingmodel.ispresent == true){
//                val intent = Intent(context,Cart1::class.java)
//                activityResultLauncher?.launch(intent)
////                    context.startActivity(intent)
//                return@setOnClickListener
//            }
//            else{
//                holder.addButton.text = "Go To Cart"
//                val prodId = shoppingmodel.id
//                val addItemRequest: AddItemCartRequest = AddItemCartRequest(
//                    img = imageUrl,
//                    productId = prodId,
//                    price = shoppingmodel.price?.toInt(),
//                    name = shoppingmodel.name
//                )
//
//                CartFunc.getInstance(context as LandingPage).addToCart(addItemRequest, holder.addButton)
//
//            }
//
//        }

    }

    override fun getItemCount(): Int {
        return productList.size
    }


}
