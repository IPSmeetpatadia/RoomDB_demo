package com.ipsmeet.roomdbdemo.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
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

    private lateinit var popupView: View
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
        val builder = AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(requireContext())
        popupView = inflater.inflate(R.layout.popup_add_list, null)
        builder.setView(popupView)

        val alertDialog = builder.create()
        alertDialog.show()

        val name = popupView.findViewById<EditText>(R.id.addList_edtxt_name)
        val age = popupView.findViewById<EditText>(R.id.addList_edtxt_age)
        val dob = popupView.findViewById<EditText>(R.id.addList_edtxt_dob)

        popupView.findViewById<Button>(R.id.btnSaveData).text = "Update"

        name.setText(user.name)
        age.setText(user.age.toString())
        dob.setText(user.dob)

        popupView.findViewById<Button>(R.id.btnSaveData).setOnClickListener {
            val updatedUser = User(
                user.id,
                name.editableText.toString(),
                age.editableText.toString().toInt(),
                dob.editableText.toString()
            )
            viewModel.updateUser(updatedUser)
            alertDialog.dismiss()
        }

        popupView.findViewById<Button>(R.id.btn_addList_cancel).setOnClickListener {
            alertDialog.dismiss()
        }
    }

    override fun onDeleteClick(user: User) {
        viewModel.deleteUser(user)
    }

}