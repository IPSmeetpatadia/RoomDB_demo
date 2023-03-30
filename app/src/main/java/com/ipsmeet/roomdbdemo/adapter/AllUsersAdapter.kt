package com.ipsmeet.roomdbdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ipsmeet.roomdbdemo.R
import com.ipsmeet.roomdbdemo.dataclass.User

class AllUsersAdapter(private val listener: ItemClickListener): RecyclerView.Adapter<AllUsersAdapter.AllUserViewHolder>() {

    private var userList = ArrayList<User>()

    fun setListData(data: ArrayList<User>) {
        this.userList.clear()
        this.userList = data
        notifyDataSetChanged()
    }

    class AllUserViewHolder(itemView: View, private val listener: ItemClickListener): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.txt_userName)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.imgB_delete)

        fun bindView(user: User) {
            name.text = user.name
            btnDelete.setOnClickListener {
                listener.onDeleteClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllUserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_view_note, parent, false)
        return AllUserViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: AllUserViewHolder, position: Int) {
        holder.apply {
            bindView(userList[position])
            itemView.setOnClickListener {
                listener.onItemClick(userList[position])
            }
        }
    }

    interface ItemClickListener {
        fun onItemClick(user: User)
        fun onDeleteClick(user: User)
    }

}