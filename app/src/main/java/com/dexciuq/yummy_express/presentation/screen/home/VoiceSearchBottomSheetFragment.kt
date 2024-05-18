package com.dexciuq.yummy_express.presentation.screen.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.dexciuq.yummy_express.R
import com.dexciuq.yummy_express.databinding.FragmentVoiceSearchBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Locale

class VoiceSearchBottomSheetFragment : BottomSheetDialogFragment() {

    private val binding by lazy { FragmentVoiceSearchBottomSheetBinding.inflate(layoutInflater) }
    private val speechRecognizer by lazy { SpeechRecognizer.createSpeechRecognizer(requireActivity()) }
    private var onRecorded: (String) -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        }

        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                binding.text.text = getString(R.string.listening)
                binding.searchImage.imageTintList = ContextCompat.getColorStateList(
                    requireActivity(),
                    R.color.primary_dark
                )
            }

            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onError(error: Int) {}

            override fun onResults(results: Bundle?) {
                binding.searchImage.imageTintList = ContextCompat.getColorStateList(
                    requireActivity(),
                    R.color.text
                )

                binding.text.text = getString(R.string.scanning)
                val data = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                onRecorded(data?.first().orEmpty())
                Handler(Looper.myLooper()!!).postDelayed({
                    dismiss()
                }, 300)
            }

            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        binding.searchVoiceBtn.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    speechRecognizer.stopListening()
                }

                MotionEvent.ACTION_DOWN -> {
                    speechRecognizer.startListening(speechRecognizerIntent)
                }
            }
            false
        }
    }

    override fun onDestroy() {
        speechRecognizer.destroy()
        super.onDestroy()
    }

    companion object {
        fun newInstance(onRecorded: (String) -> Unit = {}): VoiceSearchBottomSheetFragment {
            return VoiceSearchBottomSheetFragment().apply {
                this.onRecorded = onRecorded
            }
        }
    }
}