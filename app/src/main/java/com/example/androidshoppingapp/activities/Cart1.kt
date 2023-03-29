package com.example.androidshoppingapp.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidshoppingapp.R
import com.example.androidshoppingapp.activities.Cart1.Companion.items
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


class Cart1 : AppCompatActivity() {
    var progressBar: ProgressBar? = null
    lateinit var retrofit: Retrofit
    lateinit var apiInterface: ApiInterface
    lateinit var cartListView : RecyclerView
    private var orderAmount : Int? = null
    var deletedList = arrayListOf<String>()

    override fun onBackPressed() {
        Log.e("deletedList", "${deletedList}")
        Log.e("onBackPressed", "onbckpress called")
        val intent = Intent(this, LandingPage::class.java)
        intent.putExtra("deletedList", deletedList)
        setResult(Activity.RESULT_OK, intent)
//        deletedList.clear()

        super.onBackPressed()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart1)

        initData()
        cartList()
        updateCartPrice()


        var placeOrder = findViewById<Button>(R.id.placeorderbtn)

        placeOrder.setOnClickListener{
            if(items.size > 0){
                val totalAmount: OrderPlaceRequest = OrderPlaceRequest(
                    total = orderAmount
                )
                orderDetail(totalAmount);
            }
            else{
                Toast.makeText(this@Cart1, "Add something to Cart", Toast.LENGTH_LONG).show()
            }
        }

        var backbtn = findViewById<ImageView>(R.id.backbtn)
        backbtn.setOnClickListener {
            onBackPressed()
        }
    }

    companion object {
        var items = ArrayList<CartItemsItem>()
//        var deletedList = arrayListOf<String>()
    }


    private fun initData() {
        /*Creating the instance of retrofit */
        retrofit = RetrofitClient.getInstance()

        /*Get the reference of Api interface*/
        apiInterface = retrofit.create(ApiInterface::class.java)
    }

    fun updateCartPrice() {
        var totalPrice = 0
        items.forEachIndexed { index, product ->
            totalPrice += product.price * product.quantity
        }
        orderAmount = totalPrice
        val ordervalue = findViewById<TextView>(R.id.text3)
        ordervalue.text = "$" + totalPrice.toString()
        val total = findViewById<TextView>(R.id.text5)
        total.text = "$" + totalPrice.toString()
    }

    private fun orderDetail(total : OrderPlaceRequest){
        if(AppHelper.isConnected(this))
        {
            progressBar?.visibility = View.VISIBLE
            var token = SharedPrefManager.getInstance(applicationContext).token.token.toString()
            Log.e("cartToken", "$token")
            apiInterface.plcaeOrder(token = "Bearer $token", total)?.enqueue(object : Callback<OrderPlaceResponse?> {
                override fun onResponse(
                    call: Call<OrderPlaceResponse?>,
                    response: Response<OrderPlaceResponse?>
                ) {
                    /*Set your data to adapter here*/
                    progressBar?.visibility = View.GONE
                    if (response?.isSuccessful!!) {
                        val intent = Intent(this@Cart1, OrderSuccess::class.java)
                        startActivity(intent)
                    } else {
                        val intent = Intent(this@Cart1, OrderFailure::class.java)
                        startActivity(intent)
                    }
                }
                override fun onFailure(call: Call<OrderPlaceResponse?>, t: Throwable) {
                    progressBar?.visibility = View.GONE
                    t.printStackTrace()
                    Toast.makeText(this@Cart1, "t.message", Toast.LENGTH_LONG).show()
                }

            })
        }
        else
        {
            Toast.makeText(this@Cart1, "Please check your internet connection", Toast.LENGTH_LONG).show()
        }
    }



    private fun cartList(){
        if(AppHelper.isConnected(this))
        {
            progressBar = findViewById(R.id.progress_bar)
            progressBar?.visibility = View.VISIBLE
            var token = SharedPrefManager.getInstance(applicationContext).token.token.toString()
            Log.e("cartToken", "$token")
            apiInterface.getCartList(token = "Bearer $token")?.enqueue(object : Callback<CartListResponse?> {
                override fun onResponse(
                    call: Call<CartListResponse?>,
                    response: Response<CartListResponse?>
                ) {
                    /*Set your data to adapter here*/
                    progressBar?.visibility = View.GONE
                    if (response?.isSuccessful!!) {
//                        Log.e("response", "${response.body()}")
                        items = response.body()?.data?.cartItems as ArrayList<CartItemsItem>
                        cartListView =findViewById<RecyclerView>(R.id.recyclecartitems)
                        cartListView.layoutManager =
                            LinearLayoutManager(this@Cart1)
                        cartListView.adapter = Cartadapter(this@Cart1
                        )

                    } else {
                        Toast.makeText(this@Cart1, "Error", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<CartListResponse?>, t: Throwable) {
                    progressBar?.visibility = View.GONE
                    t.printStackTrace()

                    Toast.makeText(this@Cart1, "t.message", Toast.LENGTH_LONG).show()
                }

            })
        }
        else
        {
            Toast.makeText(this@Cart1, "Please check your internet connection", Toast.LENGTH_LONG).show()
        }
    }

}


class Cartadapter(var context: Context): RecyclerView.Adapter<Cartadapter.cartviewholder>() {
    class cartviewholder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var cartProdName = itemview.findViewById<TextView>(R.id.t1)
        //        var cartProdCategory = itemview.findViewById<TextView>(R.id.t2)
        var cartProdPrice = itemview.findViewById<TextView>(R.id.t3)
        var cartProdImage = itemview.findViewById<ImageView>(R.id.img1)
        var deleteBtn = itemview.findViewById<TextView>(R.id.delBtn)
        var spinnerItem = itemview.findViewById<Spinner>(R.id.spin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): cartviewholder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.activity_single_cart_prod, parent, false)
        return cartviewholder(view)
    }

    override fun onBindViewHolder(holder: cartviewholder, position: Int) {
        var cartmodel = items.get(position)
        holder.cartProdName.text = cartmodel.name
        holder.cartProdPrice?.text = cartmodel.price.toString()
        val imageUrl = cartmodel.img
        Picasso.get().load(imageUrl).into(holder.cartProdImage)

        ArrayAdapter.createFromResource(
            context,
            R.array.sizes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            holder.spinnerItem?.adapter = adapter
            holder.spinnerItem.setSelection(adapter.getPosition(cartmodel.quantity.toString()))
        }

        holder.spinnerItem?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var totalOrderValue = ""
                for (product in items) {
                    Log.e("product", "$product")
                    if (cartmodel.productId == product.productId) {
                        totalOrderValue = holder.spinnerItem?.getSelectedItem().toString()
                        product.quantity = totalOrderValue.toInt()
                    }
                }
                (context as Cart1).updateCartPrice()
                val count : UpdateCartRequest = UpdateCartRequest(
                    quantity = totalOrderValue.toInt()
                )
                CartFunc.getInstance(context as Cart1).updateCart(cartmodel.productId,count)
                Log.e("quantity", "done")
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


            holder.deleteBtn?.setOnClickListener {
                val prodId = cartmodel.productId.toString()
                CartFunc.getInstance(context as Cart1).deleteFromCart(prodId)
                (context as Cart1).deletedList.add(prodId)
//                deletedList.add(prodId)
                val index = items.indexOf(cartmodel)
                if (index != -1) {
                    items.removeAt(index)
                }
                notifyItemRemoved(position)
                (context as Cart1).updateCartPrice()
            }

        }


        override fun getItemCount(): Int {
            return items.size
        }


}





