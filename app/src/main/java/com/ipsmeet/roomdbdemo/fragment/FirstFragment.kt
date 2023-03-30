package com.ipsmeet.roomdbdemo.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ipsmeet.roomdbdemo.R
import com.ipsmeet.roomdbdemo.UserDB
import com.ipsmeet.roomdbdemo.adapter.AllUsersAdapter
import com.ipsmeet.roomdbdemo.dataclass.User
import com.ipsmeet.roomdbdemo.interfaces.UserDao
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
        val radioGroup: RadioGroup = popupView.findViewById(R.id.radioGroup)
        var radioButton: RadioButton?

        popupView.findViewById<Button>(R.id.btnSaveData).setOnClickListener {
            if (name.text.toString() == "") {
                name.error = "Empty Field"
            }
            else if (age.text.toString() == "") {
                age?.error = "Empty Field"
            }
            else if (dob.text.toString() == "") {
                dob.error = "Empty Field"
            }
            else if (radioGroup.checkedRadioButtonId == -1) {
                Toast.makeText(requireContext(), "Please select Gender!!", Toast.LENGTH_SHORT).show()
            }
            else {
                val selectedID = radioGroup.checkedRadioButtonId
                radioButton = popupView.findViewById(selectedID)

                val userData = User(
                    name = name.text.toString(),
                    age = age.text.toString().toInt(),
                    dob = dob.text.toString(),
                    gender = radioButton!!.text.toString()
                )

                viewModel.addUser(userData)
                alertDialog.dismiss()
            }
        }

        popupView.findViewById<Button>(R.id.btn_addList_cancel).setOnClickListener {
            alertDialog.dismiss()
        }
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
        val radioGroup: RadioGroup = popupView.findViewById(R.id.radioGroup)
        var radioButton: RadioButton
        val rBtnMale: RadioButton = popupView.findViewById(R.id.rBtn_male)
        val rBtnFemale: RadioButton = popupView.findViewById(R.id.rBtn_female)



        popupView.findViewById<Button>(R.id.btnSaveData).text = "Update"

        name.setText(user.name)
        age.setText(user.age.toString())
        dob.setText(user.dob)
        if (user.gender == "Male") {
            rBtnMale.isChecked = true
        } else if (user.gender == "Female") {
            rBtnFemale.isChecked = true
        }

        popupView.findViewById<Button>(R.id.btnSaveData).setOnClickListener {
            val selectedID = radioGroup.checkedRadioButtonId
            radioButton = popupView.findViewById(selectedID)

            val updatedUser = User(
                user.id,
                name.editableText.toString(),
                age.editableText.toString().toInt(),
                dob.editableText.toString(),
                radioButton.text.toString()
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