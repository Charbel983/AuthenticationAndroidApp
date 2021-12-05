package com.example.authenticationapp.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.authenticationapp.parsedData.News
import com.example.authenticationapp.NewsApiService
import com.example.authenticationapp.R
import com.example.authenticationapp.parsedData.Result
import com.example.authenticationapp.adapters.NewsAdapter
import kotlinx.android.synthetic.main.fragment_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val parentHolder = inflater.inflate(R.layout.fragment_news, container, false)
        val progressBar : ProgressBar = parentHolder.findViewById(R.id.newsProgressBar)
        val retrofit = Retrofit.Builder().baseUrl(this.requireContext().getString(R.string.newsUrl))
            .addConverterFactory(GsonConverterFactory.create()).build()

        val api = retrofit.create(NewsApiService::class.java)

        if(isOnline(requireContext())){
        api.fetchAllNews().enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                    Log.d("newsFrag", "Data working")
                    Toast.makeText(context, requireContext().getString(R.string.loadedSuccessfullyMessage), Toast.LENGTH_LONG).show()
                    progressBar.visibility = View.GONE
                    showData(response.body()!!.results)
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("newsFrag", "Data not working")
                Toast.makeText(context, getString(R.string.dataFailed), Toast.LENGTH_LONG).show()
            }

        })}else{
            Toast.makeText(context, requireContext().getString(R.string.internetProblem), Toast.LENGTH_LONG).show()
        }


        return parentHolder
    }

    fun showData(news : List<Result>?) {
        newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = NewsAdapter(news, this.context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}