package com.example.pawsome.ui.Analyze;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pawsome.ApiService;
import com.example.pawsome.R;
import com.example.pawsome.Response;
import com.example.pawsome.databinding.FragmentAnalyzeBinding;
import com.example.pawsome.ui.Analyze.AnalysisResponse.AnalysisResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnalyzeFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {
    private static final int REQUEST_WRITE_PERMISSION = 786;
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            CameraActivityResultLauncher.launch("image/*");
        }
    }


    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            CameraActivityResultLauncher.launch("image/*");
        }
    }
    ProgressBar pb_loading;
    TextView tv_analysis;
    ImageView ivImage;
    Button bt_analyze;
    private AnalyzeViewModel analyzeViewModel;
    private FragmentAnalyzeBinding binding;
    Activity context;
    Uri imageUri;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        context = getActivity();

        analyzeViewModel = new ViewModelProvider(this).get(AnalyzeViewModel.class);


        binding = FragmentAnalyzeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        ivImage = (ImageView) context.findViewById(R.id.im_upload);
        bt_analyze = (Button) context.findViewById(R.id.bt_analyze);
        tv_analysis = (TextView) context.findViewById(R.id.tv_analysis);
        pb_loading = (ProgressBar) context.findViewById(R.id.pb_loading);

        ivImage.setOnClickListener(v ->{
            if(pb_loading.getVisibility() == View.INVISIBLE)
            requestPermission();
            else
                Toast.makeText(context, "Please wait", Toast.LENGTH_SHORT).show();

        });
        bt_analyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pb_loading.getVisibility() == View.INVISIBLE) {
                    if (!(imageUri == null)) {
                        try {
                            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
                            uploadImage(getBytes(inputStream));
                            pb_loading.setVisibility(View.VISIBLE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(context, "Please Select Image", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(context, "Please wait", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    ActivityResultLauncher<String> CameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri imgUri) {
                    tv_analysis.setText("");
                    if (imgUri != null) {
                        ivImage.setImageURI(imgUri);
                        imageUri = imgUri;
                    }
                }
            });

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int bufferSize = 1024;
        byte[] buff = new byte[bufferSize];

        int len = 0;
        while((len = is.read(buff)) != -1){
            byteBuff.write(buff,0, len);
        }
        return  byteBuff.toByteArray();
    }

    public void uploadImage(byte[] imageBytes) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.thedogapi.com/").addConverterFactory(GsonConverterFactory.create()).build();
        ApiService service = retrofit.create(ApiService.class);
        // use the FileUtils to get the actual file by uri



        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", "image.jpg", requestFile);

        // add another part within the multipart request
        // finally, execute the request
        Call<Response> call = service.upload(body);
        Log.v("call", "callmade");
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                pb_loading.setVisibility(View.INVISIBLE);
                if(response.code() == 201){
                    tv_analysis.setText("");
                    Log.v("imageid",response.body().getId());
                    analysis(response.body().getId());

                }
                else
                    tv_analysis.setText("Image not accepted");


            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                pb_loading.setVisibility(View.INVISIBLE);
                Toast.makeText(context,"Uploading Error", Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void analysis(String id){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.thedogapi.com/").addConverterFactory(GsonConverterFactory.create()).build();
        ApiService service = retrofit.create(ApiService.class);
        Call<ArrayList<AnalysisResponse>> call = service.Analysis(id);
        call.enqueue(new Callback<ArrayList<AnalysisResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<AnalysisResponse>> call, retrofit2.Response<ArrayList<AnalysisResponse>> response) {
                if(!response.body().isEmpty()){
                    if(!response.body().get(0).getLabels().isEmpty())
                        tv_analysis.setText("We are "+ String.format("%.3f",response.body().get(0).getLabels().get(0).getConfidence()) +" sure that the dog is a "+ response.body().get(0).getLabels().get(0).getName());
                }

            }

            @Override
            public void onFailure(Call<ArrayList<AnalysisResponse>> call, Throwable t) {
                Log.v("imageid",""+ t.getMessage());
            }
        });


    }

           /* public String convertMediaUriToPath(Uri uri) {
                String [] proj={MediaStore.Images.Media.DATA};
                Cursor cursor = context.getContentResolver().query(uri, proj,  null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String path = cursor.getString(column_index);
                cursor.close();
                return path;
            }*/

    }
