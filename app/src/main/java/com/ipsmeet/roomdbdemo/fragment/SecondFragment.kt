package com.ipsmeet.roomdbdemo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ipsmeet.roomdbdemo.R
import com.ipsmeet.roomdbdemo.UserDB
import com.ipsmeet.roomdbdemo.adapter.AllUsersAdapter
import com.ipsmeet.roomdbdemo.dataclass.User
import com.ipsmeet.roomdbdemo.viewmodel.UserViewModel
import java.util.ArrayList

class SecondFragment : Fragment(), AllUsersAdapter.ItemClickListener {

    private lateinit var viewModel: UserViewModel
    private lateinit var viewAdapter: AllUsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_aged_empList)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            viewAdapter = AllUsersAdapter(this@SecondFragment)
            adapter = viewAdapter
        }

        viewModel.getAgedUsersObservers().observe(viewLifecycleOwner) {
            viewAdapter.setListData(ArrayList(it))
        }
    }

    override fun onResume() {
        super.onResume()
        val getDB = UserDB.getDatabase(requireContext()).userDao().getAgedUser()
        viewAdapter.setListData(ArrayList(getDB))
    }

    override fun onItemClick(user: User) {

    }

    override fun onDeleteClick(user: User) {
        viewModel.deleteUser(user)
    }

}