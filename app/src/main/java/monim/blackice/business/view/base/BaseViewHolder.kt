package monim.blackice.business.view.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import monim.blackice.business.view.adapter.IAdapterListener

abstract class BaseViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun<T> onBind(position: Int, model:T, mCallback : IAdapterListener)

}