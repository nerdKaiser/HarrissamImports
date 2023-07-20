package com.example.northstar.Mgr;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.northstar.R;
import com.example.northstar.middle.AwardAdapter;
import com.example.northstar.middle.AwardMod;
import com.example.northstar.middle.Router;
import com.example.northstar.middle.SelectDate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProjectGallery extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnTouchListener {
    GridView gridView;
    Button btn, Browse, Submit;
    ArrayList<AwardMod> SubjectList = new ArrayList<>();
    AwardAdapter suppAda;
    AwardMod suppFind;
    View layer;
    TextView textDate;
    ImageView imager;
    String encodedimage;
    EditText Description;
    Bitmap bitmap;
    private static final String TAG = "Touch";
    @SuppressWarnings("unused")
    private static final float MIN_ZOOM = 1f, MAX_ZOOM = 1f;
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    private ImageView check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Projects Gallery");
        setContentView(R.layout.activity_project_gallery);
        gridView = findViewById(R.id.gridV);
        gridView.setTextFilterEnabled(true);
        btn = findViewById(R.id.btnAdd);
        practical();
        gridView = findViewById(R.id.gridV);
        gridView.setTextFilterEnabled(true);
        btn = findViewById(R.id.btnAdd);
        practical();
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            suppFind = (AwardMod) parent.getItemAtPosition(position);
            Intent intent = new Intent();
            intent.putExtra("id", suppFind.getId());
            showDialogImage(ProjectGallery.this, position);
        });
        btn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ProjectGallery.this);
            builder.setTitle("Add new Project");
            Rect reco = new Rect();
            Window win = ProjectGallery.this.getWindow();
            win.getDecorView().getWindowVisibleDisplayFrame(reco);
            LayoutInflater inflater = (LayoutInflater) ProjectGallery.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layer = inflater.inflate(R.layout.add_new, null);
            layer.setMinimumWidth((int) (reco.width() * 0.9f));
            layer.setMinimumHeight((int) (reco.height() * 0.05f));
            Description = layer.findViewById(R.id.editDescription);
            textDate = layer.findViewById(R.id.typeDate);
            Browse = layer.findViewById(R.id.btnBrowse);
            Submit = layer.findViewById(R.id.btnSubmit);
            imager = layer.findViewById(R.id.imageView);
            Browse.setOnClickListener(vii -> {
                ActivityCompat.requestPermissions(
                        ProjectGallery.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        000
                );
            });
            textDate.setOnClickListener(viewing -> {
                DialogFragment datePicker = new SelectDate();
                datePicker.show(getSupportFragmentManager(), "date picker");
            });
            Submit.setOnClickListener(vw -> {
                final String myDate = textDate.getText().toString().trim();
                final String Desc = Description.getText().toString().trim();
                Drawable drawable = imager.getDrawable();
                if (Desc.isEmpty()) {
                    Description.setError("Required");
                    Description.requestFocus();
                    Toast.makeText(this, "Description", Toast.LENGTH_SHORT).show();
                } else if (myDate.equals("Set the Date here")) {
                    textDate.setError("Why not Click this?");
                    textDate.requestFocus();
                    Toast.makeText(this, "Date Not Set", Toast.LENGTH_SHORT).show();
                } else if (drawable == null) {
                    Toast.makeText(this, "Image is required", Toast.LENGTH_SHORT).show();
                } else {
                    StringRequest stringRequesting = new StringRequest(Request.Method.POST, Router.UPLOAD_PROJ_GALA,
                            respon -> {
                                try {
                                    JSONObject jsonOb = new JSONObject(respon);
                                    Log.e("response ", respon);
                                    String msg = jsonOb.getString("message");
                                    int statuses = jsonOb.getInt("success");
                                    if (statuses == 1) {
                                        Toast.makeText(ProjectGallery.this, msg, Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), ProjectGallery.class));

                                    } else if (statuses == 0) {
                                        Toast.makeText(ProjectGallery.this, msg, Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    // Toast.makeText(SuppDas.this, e.toString(), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(ProjectGallery.this, "An error occurred", Toast.LENGTH_SHORT).show();
                                }

                            }, error -> {
                        Toast.makeText(ProjectGallery.this, "Failed to connect", Toast.LENGTH_SHORT).show();

                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("description", Desc);
                            params.put("image", encodedimage);
                            params.put("reg_date", myDate);
                            return params;
                        }
                    };//id,description,image,reg_date
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    requestQueue.add(stringRequesting);
                }
            });
            builder.setView(layer);
            builder.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
        });
    }

    private void showDialogImage(ProjectGallery projectGallery, int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        Rect rect = new Rect();
        Window window = projectGallery.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        LayoutInflater layoutInflater = (LayoutInflater) projectGallery.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.image_zoom, null);
        layout.setMinimumWidth((int) (rect.width() * 0.99f));
        layout.setMinimumHeight((int) (rect.height() * 0.7f));
        check = layout.findViewById(R.id.imagery);
        Glide.with(projectGallery).load(suppFind.getImage()).into(check);
        check.setOnTouchListener(this);
        alert.setTitle("Project Description");
        alert.setMessage("Description: " + suppFind.getDescription() + "\nDate Recorded: " + suppFind.getReg_date() + "\nImage--->>");
        alert.setView(layout);
        alert.setPositiveButton("Delete", (dialog, id) -> {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.DELETE_PROJ,
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String mesg = jsonObject.getString("message");
                            int status = jsonObject.getInt("success");
                            Toast.makeText(this, mesg, Toast.LENGTH_SHORT).show();
                            if (status == 1) {
                                startActivity(new Intent(getApplicationContext(), ProjectGallery.class));
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "An Error occurred", Toast.LENGTH_SHORT).show();
                        }

                    }, error -> {
                Toast.makeText(this, "There was a root connection problem", Toast.LENGTH_SHORT).show();

            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("id", suppFind.getId());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        });
        alert.setNegativeButton("close", (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 000) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 000);
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 000 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imager.setImageBitmap(bitmap);
                encodedBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 111 && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            imager.setImageBitmap(bitmap);
            encodedBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodedBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesofimages = byteArrayOutputStream.toByteArray();
        encodedimage = Base64.encodeToString(bytesofimages, Base64.DEFAULT);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        textDate.setText(currentDateString);

    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.FETCH_PROJ_GALA,
                response -> {
                    try {
                        AwardMod subject;
                        SubjectList = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String description = jsonObject.getString("description");
                                String image = jsonObject.getString("image");
                                String imagery = "https://granhmtechbytes.000webhostapp.com/northstar/androidappi/images/" + image;
                                String reg_date = jsonObject.getString("reg_date");
                                subject = new AwardMod(id, description, imagery, reg_date);
                                SubjectList.add(subject);
                            }
                            suppAda = new AwardAdapter(ProjectGallery.this, R.layout.view_image, SubjectList);
                            gridView.setAdapter(suppAda);
                        } else if (success == 0) {
                            String msg = jsonObject.getString("mine");
                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder builder = new AlertDialog.Builder(ProjectGallery.this);
                            //builder.setTitle("Error");
                            builder.setMessage(msg);
                            builder.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(ProjectGallery.this);
                        builder.setTitle("Error");
                        builder.setMessage("An error occurred");
                        builder.show();
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ProjectGallery.this);
            builder.setTitle("Error");
            builder.setMessage("Failed to connect");
            builder.show();
            Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView check = (ImageView) v;
        check.setScaleType(ImageView.ScaleType.MATRIX);
        float scale;
        dumpEvent(event);
        // Handle touch events here...
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:   // first finger down only
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                Log.d(TAG, "mode=DRAG"); // write to LogCat
                mode = DRAG;
                break;
            case MotionEvent.ACTION_UP: // first finger lifted
            case MotionEvent.ACTION_POINTER_UP: // second finger lifted
                mode = NONE;
                Log.d(TAG, "mode=NONE");
                break;
            case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down
                oldDist = spacing(event);
                Log.d(TAG, "oldDist=" + oldDist);
                if (oldDist > 5f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                    Log.d(TAG, "mode=ZOOM");
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y); // create the transformation in the matrix  of points
                } else if (mode == ZOOM) {
                    // pinch zooming
                    float newDist = spacing(event);
                    Log.d(TAG, "newDist=" + newDist);
                    if (newDist > 5f) {
                        matrix.set(savedMatrix);
                        scale = newDist / oldDist; // setting the scaling of the
                        // matrix...if scale > 1 means
                        // zoom in...if scale < 1 means
                        // zoom out
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
        }
        check.setImageMatrix(matrix); // display the transformation on screen
        return true; // indicate event was handled
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /**
     * Show an event in the LogCat view, for debugging
     */
    private void dumpEvent(MotionEvent event) {
        String names[] = {"DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE", "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?"};
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);

        if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP) {
            sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }

        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }

        sb.append("]");
        Log.d("Touch Events ---------", sb.toString());
    }

}