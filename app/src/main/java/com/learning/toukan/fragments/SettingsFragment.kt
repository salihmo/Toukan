package com.learning.toukan.fragments

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.learning.toukan.FirestoreClass
import com.learning.toukan.R
import com.learning.toukan.activities.ActivityViewModel
import com.learning.toukan.activities.SignInActivity
import com.learning.toukan.models.User
import kotlinx.android.synthetic.main.settings_fragment.*
import java.io.IOException

class SettingsFragment : Fragment() {

    companion object {
        private const val READ_STORAGE_PERMISSION_CODE = 1
        private const val PICK_IMAGE_REQUEST_CODE = 2
    }


    private var imageURI: Uri? = null
    private var profilePictureUri: String = ""
    var resolver: ContentResolver? = activity?.contentResolver

    private val activityViewModel: ActivityViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityViewModel.user.observe(viewLifecycleOwner, Observer { user ->
            Glide
                .with(this)
                .load(user.profileImage)
                .centerCrop()
                .placeholder(R.drawable.profiledefault)
                .into(profile_picture_settings)
            settings_name.setText(user.name)
            settings_email.setText(user.email)
        })
        view.findViewById<View>(R.id.signOutButton).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity, SignInActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        back_settings.setOnClickListener {
            back_settings.playAnimation()
           val directions = SettingsFragmentDirections.actionNavigationSettingsToNavigationProfile()
            findNavController().navigate(directions)
        }
        changePasswordButton.setOnClickListener {
            val changePassword = ActionOnlyNavDirections(R.id.action_change_password)
            findNavController().navigate(changePassword) }
        updateDetailsButton.setOnClickListener {
            findNavController().navigateUp()
            if (imageURI != null) {
                uploadImageFirestore()
            }
            changeUserName()
            changeEmail()
        }
        changeImageButton.setOnClickListener {
            if (context?.let { it1 ->
                    ContextCompat.checkSelfPermission(
                        it1,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                }
                == PackageManager.PERMISSION_GRANTED) {
                showImageChooser()
            } else {
                ActivityCompat.requestPermissions(
                    context as Activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_STORAGE_PERMISSION_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showImageChooser()
            }
        } else {
            Toast.makeText(context, "No permission", Toast.LENGTH_SHORT).show()
        }

    }

    private fun showImageChooser() {
        var galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode ==
            PICK_IMAGE_REQUEST_CODE && data!!.data != null
        ) {
            imageURI = data.data
        }
        try {
            activityViewModel.user.observe(viewLifecycleOwner, Observer { user ->
                Glide
                    .with(this)
                    .load(Uri.parse(imageURI.toString()))
                    .centerCrop()
                    .placeholder(R.drawable.profiledefault)
                    .into(profile_picture_settings);
            })
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun uploadImageFirestore() {
//        MainActivity().showProgressDialog(resources.getString(R.string.please_wait))

        if (imageURI != null) {
            val referenceForStorage: StorageReference =
                FirebaseStorage.getInstance().reference.child(
                    "Profile Picture" +
                            System.currentTimeMillis() + "." + getFileExtension(imageURI)
                )

            referenceForStorage.putFile(imageURI!!).addOnSuccessListener { taskSnapshot ->
                Log.i(
                    "Firebase P.Picture URL",
                    taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                )

                taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uri ->
                    Log.i("Downloaded Picture Uri", uri.toString())
                    profilePictureUri = uri.toString()
                    FirestoreClass().updateImage(profilePictureUri)
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(
                    context, exception.message, Toast.LENGTH_SHORT
                ).show()
//                MainActivity().hideProgressDialog()
            }
        }
    }
//

    private fun getFileExtension(uri: Uri?): String? {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(resolver?.getType(uri!!))
    }
    private fun changeUserName() {
        if (settings_name.text.toString().isNotEmpty()) {
            FirestoreClass().updateName(settings_name.text.toString())
        } else {
            FirestoreClass().updateName(User().name)
        }
    }
    private fun changeEmail() {
        if (settings_email.text.toString().isNotEmpty()) {
            FirestoreClass().updateEmail(settings_email.text.toString())
        } else {
            FirestoreClass().updateName(User().email)
        }
    }
}