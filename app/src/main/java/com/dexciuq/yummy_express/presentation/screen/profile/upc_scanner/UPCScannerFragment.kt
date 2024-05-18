package com.dexciuq.yummy_express.presentation.screen.profile.upc_scanner

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ScanMode
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.common.hide
import com.dexciuq.yummy_express.common.show
import com.dexciuq.yummy_express.databinding.FragmentUpcScannerBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UPCScannerFragment : Fragment() {

    private val binding by lazy { FragmentUpcScannerBinding.inflate(layoutInflater) }
    private val viewModel: UPCScannerViewModel by viewModels()
    private val codeScanner: CodeScanner by lazy {
        CodeScanner(
            requireActivity(),
            binding.scannerView
        )
    }

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

        setupBarcodeScanner()
        setupListeners()
        collectData()

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

    @SuppressLint("SetTextI18n")
    private fun setupBarcodeScanner() {
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback {
            activity?.runOnUiThread {
                viewModel.getProduct(upc = it.text)

                binding.upcText.show()
                binding.upcText.text = "UPC: ${it.text}"
            }
        }
    }

    private fun setupListeners() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.scannerView.setOnClickListener {
            codeScanner.startPreview()
            binding.upcText.hide()
        }
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewModel.product.collect { resource ->
                if (resource == null) {
                    binding.upcText.hide()
                    return@collect
                }
                when (resource) {
                    is Resource.Loading -> {
                        binding.upcText.hide()
                    }

                    is Resource.Success -> {
                        findNavController().navigate(
                            UPCScannerFragmentDirections.actionUPCScannerFragmentToProductDetailFragment2(
                                resource.data.id
                            )
                        )
                        viewModel.release()
                    }

                    is Resource.Error -> {
                        Snackbar.make(
                            binding.root,
                            "Product with this UPC not found!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        viewModel.release()
                    }
                }
            }
        }
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}