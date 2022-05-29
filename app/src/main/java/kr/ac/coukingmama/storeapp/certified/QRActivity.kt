package kr.ac.coukingmama.storeapp.certified

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kr.ac.coukingmama.storeapp.databinding.ActivityQrBinding
import java.util.concurrent.Executors

@ExperimentalGetImage
class QRActivity : AppCompatActivity() { // QR인식 페이지

    private val CAMERA_PERMISSION_REQUEST_CODE = 1
    private lateinit var binding: ActivityQrBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (hasCameraPermission()) bindCameraUseCases()
        else requestPermission()
    }
    private fun hasCameraPermission() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

    private fun requestPermission(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            bindCameraUseCases()
        } else {
            Toast.makeText(this,
                "Camera permission required",
                Toast.LENGTH_LONG
            ).show()
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun bindCameraUseCases() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val previewUseCase = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.cameraView.surfaceProvider)
                }
            val options = BarcodeScannerOptions.Builder().setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
            ).build()
            val scanner = BarcodeScanning.getClient(options)

            val analysisUseCase = ImageAnalysis.Builder()
                .build()
            analysisUseCase.setAnalyzer(
                Executors.newSingleThreadExecutor()
            ) { imageProxy ->
                processImageProxy(scanner, imageProxy)
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    previewUseCase,
                    analysisUseCase)
            } catch (illegalStateException: IllegalStateException) {
                Log.e("TAG", illegalStateException.message.orEmpty())
            } catch (illegalArgumentException: IllegalArgumentException) {
                Log.e("TAG", illegalArgumentException.message.orEmpty())
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun processImageProxy(
        barcodeScanner: BarcodeScanner,
        imageProxy: ImageProxy
    ) {

        imageProxy.image?.let { image ->
            val inputImage =
                InputImage.fromMediaImage(
                    image,
                    imageProxy.imageInfo.rotationDegrees
                )

            barcodeScanner.process(inputImage)
                .addOnSuccessListener { barcodeList ->
                    val barcode = barcodeList.getOrNull(0)
                    barcode?.rawValue?.let { value ->
                        if(value.contains("@") && value.split("@").lastIndex == 1){
                            Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, AccumulateActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                }
                .addOnFailureListener {
                    Log.e("TAG", it.message.orEmpty())
                }.addOnCompleteListener {
                    imageProxy.image?.close()
                    imageProxy.close()
                }
        }
    }
}