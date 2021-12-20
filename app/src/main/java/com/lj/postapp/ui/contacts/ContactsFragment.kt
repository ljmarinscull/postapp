package com.lj.postapp.ui.contacts

import android.Manifest
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.lj.postapp.databinding.FragmentContactsBinding
import com.lj.postapp.utils.visible

class ContactsFragment : Fragment(), AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!

    private var mArrayList = mutableListOf<String>()
    private var mArrayAdapter: ArrayAdapter<*>? = null
    private lateinit var mCursor: Cursor
    private var mContactSelectedPosition: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS
            ),100)
        } else {
            readContacts()
        }
    }

    private fun setupUI() {
        mArrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, mArrayList)
        binding.listview.adapter = mArrayAdapter
        binding.listview.emptyView = binding.empty
        binding.listview.onItemClickListener = this
        binding.listview.onItemLongClickListener = this
    }

    // Read contacts using content resolver
    private fun readContacts() {
        val contentResolver = requireContext().contentResolver
        mCursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null)!!
        if (mCursor.moveToFirst()){
            do {
                mArrayList.add(mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)))
            }
            while (mCursor.moveToNext())
            mArrayAdapter?.notifyDataSetChanged()
        }
        binding.progressBar.visible = false
    }

    private fun editContact(name: String){
        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val cursor: Cursor? = requireActivity().contentResolver
            .query(
                uri,
                null,
                "${ContactsContract.Contacts.DISPLAY_NAME} = '$name'",
                null,
                null
            )

        if (cursor?.moveToFirst() == true) {

            val idContact =
                cursor.getLong(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID))

            val i = Intent(Intent.ACTION_EDIT)
            val contactUri =
                ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, idContact)
            i.data = contactUri
            i.putExtra("ActivityOnSaveCompleted", true)
            startActivity(i)
        }
    }

    override
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 100 && (grantResults[0] == 0 && grantResults[1] == 0)) {
            readContacts()
        } else {
            Toast.makeText(
                requireContext(),
                "Se necesitan permiso para acceder a los contactos del telefono.",
                Toast.LENGTH_LONG
            ).show()

            binding.progressBar.visible = false
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mContactSelectedPosition = position
        editContact(mArrayList[position])
    }

    private fun deleteConfirmationDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder.setMessage("Do you want to delete this contact?")
            .setCancelable(false)
            .setPositiveButton("Yes, I do") { dialog, _ ->
                dialog.dismiss()
                deleteContact()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        val alert: AlertDialog = dialogBuilder.create()
        alert.setTitle("Delete confirmation")
        alert.show()
    }

    private fun deleteContact() {
        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI

        val contentResolver = requireActivity().contentResolver
        val cursor = contentResolver.query(
                uri,
                null,
                "${ContactsContract.Contacts.DISPLAY_NAME} = '${mArrayList[mContactSelectedPosition]}'",
                null,
                null
            )

        if (cursor?.moveToFirst() == true) {
            val idContact =
                cursor.getLong(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID))

            val contactUri = ContentUris.withAppendedId(
                    ContactsContract.Contacts.CONTENT_URI,
                    idContact
                )

            requireContext().contentResolver.delete(contactUri,null,null)
            mArrayList.removeAt(mContactSelectedPosition)
            mArrayAdapter?.notifyDataSetChanged()
            Toast.makeText(requireContext(), "Contact deleted!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onItemLongClick(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ): Boolean {
        mContactSelectedPosition = position
        deleteConfirmationDialog()
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}