package com.blackice.business.view.activity.article

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.blackice.business.R
import com.blackice.business.data.local_db.entity.Article
import com.blackice.business.data.model.ArticleRespons
import com.blackice.business.data.model.BaseModel
import com.blackice.business.databinding.ActivityArticleListBinding
import com.blackice.business.util.LiveDataResult
import com.blackice.business.view.EmptyViewHolder
import com.blackice.business.view.activity.article_details.ArticleDetailsActivity
import com.blackice.business.view.adapter.IAdapterListener
import com.blackice.business.view.base.BaseActivity
import com.blackice.business.view.base.BaseRecyclerAdapter
import com.blackice.business.view.base.BaseViewHolder
import okhttp3.ResponseBody
import retrofit2.Response
import java.util.ArrayList

class ArticleListActivity : BaseActivity() {
    private lateinit var binding: ActivityArticleListBinding
    lateinit var viewmodel: ArticleListViewmodel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_article_list)

        viewmodel = ViewModelProviders.of(
            this,
            viewModelFactory
        ).get(ArticleListViewmodel::class.java)


    }

    override fun viewRelatedTask() {
        setToolbar(this, binding.toolbar, "Article List", true)
        viewmodel.fetchGetArticle(intent.getIntExtra("id", intent.getStringExtra("id").toInt()), this)

    }

    private fun initArticle(articles: List<Article>?) {

        binding.rvArticleList.layoutManager = LinearLayoutManager(this)
        binding.rvArticleList.adapter = BaseRecyclerAdapter(this, object : IAdapterListener {
            override fun <T> clickListener(position: Int, model: T, view: View) {

                model as Article
                val intent = Intent(this@ArticleListActivity, ArticleDetailsActivity::class.java)
                intent.putExtra("articleId", model.id)
                startActivity(intent)
            }

            override fun getViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

               if(viewType>-1 ){
                   return ArticleViewholder(

                       DataBindingUtil.inflate(
                           LayoutInflater.from(parent.context)
                           , R.layout.item_article
                           , parent, false
                       )

                       , this@ArticleListActivity
                   )
               }else{
                   return EmptyViewHolder(

                       DataBindingUtil.inflate(
                           LayoutInflater.from(parent.context)
                           , R.layout.empty_page
                           , parent, false
                       )

                       , this@ArticleListActivity
                   )

               }
            }

        }, articles as ArrayList)

    }

    override fun onSuccess(result: LiveDataResult<Response<ResponseBody>>, key: String) {

        val articleType = object : TypeToken<BaseModel<ArticleRespons>>() {

        }.type
        result.data!!.body()?.let{
            val baseData =
                Gson().fromJson<BaseModel<ArticleRespons>>(result.data!!.body()!!.string(), articleType)

            if (baseData.status) {
                if (baseData.data != null) {
                    val articleRespons = baseData.data
                    initArticle(articleRespons!!.articles)
                }
            }
        }?:run{
            initArticle(ArrayList<Article>()!!)
        }
        Log.e("callback", "success")
    }

    override fun onLoading(isLoader: Boolean) {
        if(isLoader){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onError(err: Throwable) {
    }
}
