package com.techypie.myyoutubeapp.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.techypie.myyoutubeapp.DatabasaHelper
import com.techypie.myyoutubeapp.R
import com.techypie.myyoutubeapp.databinding.FragmentHomeBinding

class FragmentHome() : Fragment() {


    lateinit var binding: FragmentHomeBinding
    lateinit var databasaHelper: DatabasaHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        databasaHelper = DatabasaHelper(context)


        binding.addButton.setOnClickListener {

            if (binding.videoLinkET.text.isEmpty() || binding.videoTitleET.text.isEmpty() || binding.categorySP.selectedItemPosition == 0) {
                Toast.makeText(
                    context,
                    "Please Paste Link / Enter Title / Select Category",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            try {

                var videoLink = binding.videoLinkET.text.toString()
                var videoId = videoLink.substring(17).take(11)
                var selectedCategory = binding.categorySP.selectedItem.toString()
                var videoTitle = binding.videoTitleET.text.toString().trim()

                var videoAdded = databasaHelper.insertVideo(videoTitle,videoId,selectedCategory)
                Toast.makeText(context, "$videoAdded", Toast.LENGTH_SHORT).show()

            } catch (e: StringIndexOutOfBoundsException) {
                Toast.makeText(context, "Please Enter Correct Link", Toast.LENGTH_SHORT).show()
            }

            binding.videoLinkET.text.clear()
            binding.videoTitleET.text.clear()
            binding.categorySP.setSelection(0)
        }


        return binding.root
    }


}