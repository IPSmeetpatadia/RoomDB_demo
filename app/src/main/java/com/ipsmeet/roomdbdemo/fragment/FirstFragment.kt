package com.ipsmeet.roomdbdemo.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ipsmeet.roomdbdemo.R
import com.ipsmeet.roomdbdemo.UserDB
import com.ipsmeet.roomdbdemo.adapter.AllUsersAdapter
import com.ipsmeet.roomdbdemo.dataclass.User
import com.ipsmeet.roomdbdemo.viewmodel.UserViewModel

class FirstFragment : Fragment(), AllUsersAdapter.ItemClickListener {

    private lateinit var popupView: View
    private lateinit var viewModel: UserViewModel
    private lateinit var viewAdapter: AllUsersAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_all_empList)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            viewAdapter = AllUsersAdapter(this@FirstFragment)
            adapter = viewAdapter
        }

        viewModel.getAllUsersObservers().observe(viewLifecycleOwner) {
            viewAdapter.setListData(ArrayList(it))
        }

        view.findViewById<FloatingActionButton>(R.id.btn_addEmp).setOnClickListener {
            showPopup()
        }
    }

    override fun onResume() {
        super.onResume()
        val getDB = UserDB.getDatabase(requireContext()).userDao().getAllUser()
        viewAdapter.setListData(ArrayList(getDB))
    }

    private fun showPopup() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(requireContext())
        popupView = inflater.inflate(R.layout.popup_add_list, null)
        builder.setView(popupView)

        val alertDialog = builder.create()
        alertDialog.show()

        val name = popupView.findViewById<EditText>(R.id.addList_edtxt_name)
        val age = popupView.findViewById<EditText>(R.id.addList_edtxt_age)
        val dob = popupView.findViewById<EditText>(R.id.addList_edtxt_dob)

        popupView.findViewById<Button>(R.id.btnSaveData).setOnClickListener {
            val userData = User(
                name = name.text.toString(),
                age = age.text.toString().toInt(),
                dob = dob.text.toString()
            )
            viewModel.addUser(userData)
            alertDialog.dismiss()
        }

        popupView.findViewById<Button>(R.id.btn_addList_cancel).setOnClickListener {
            alertDialog.dismiss()
        }
    }

    override fun onItemClick(user: User) {

    }

    override fun onDeleteClick(user: User) {
        viewModel.deleteUser(user)
    }

}