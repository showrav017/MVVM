package com.blackice.business.view.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.blackice.business.R
import com.blackice.business.data.DataManager
import com.blackice.business.util.IObserverCallBack
import dagger.android.support.DaggerFragment
import javax.inject.Inject


abstract class BaseFragment : DaggerFragment(), IObserverCallBack {


    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewRelatedTask()
    }

    abstract fun viewRelatedTask()

    fun showToast(context: Context, message: String) {
        val toast = Toast(context)
        toast.duration = Toast.LENGTH_LONG

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.custom_toast_layout, null)

        val toastText = view.findViewById<TextView>(R.id.toastText)
        toastText.setText(message)

        toast.view = view
        toast.show()
    }

    fun showDialog(isCancelAble: Boolean, dialogFragment: BaseDialogFragment) {
        (activity as BaseActivity).showDialog(isCancelAble, dialogFragment)
    }


}