package com.dexciuq.yummy_express.presentation.screen.profile.upc_scanner

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ScanMode
import com.dexciuq.yummy_express.common.toast
import com.dexciuq.yummy_express.databinding.FragmentUpcScannerBinding
import com.google.android.material.snackbar.Snackbar

class UPCScannerFragment : Fragment() {

    private val binding by lazy { FragmentUpcScannerBinding.inflate(layoutInflater) }
    private lateinit var codeScanner: CodeScanner

    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            codeScanner.startPreview()
        } else {
            Snackbar.make(
                binding.root,
                "To use this feature need to access your camera",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        codeScanner = CodeScanner(requireActivity(), binding.scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback {
            activity?.runOnUiThread {
                toast("Scan result: ${it.text}")
            }
        }

        binding.scannerView.setOnClickListener {
            codeScanner.startPreview()
        }

        if (ContextCompat.checkSelfPermission(
                requireActivity(), Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            codeScanner.startPreview()
        } else {
            requestForCameraPermission()
        }
    }

    private fun requestForCameraPermission() {
        cameraLauncher.launch(Manifest.permission.CAMERA)
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}