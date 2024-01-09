package md18202.nhom2.duan1application.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import md18202.nhom2.duan1application.DAO.NguoiDungDAO;
import md18202.nhom2.duan1application.R;

public class DangNhapActivity extends AppCompatActivity {
    private EditText edtUsername_frame2, edtPassword_frame2;
    private ImageView imgShowPassword_frame2;
    private CheckBox chkRememberPassword_frame2;
    private Button btnNext_frame2;
    private TextView tvSignIn_frame2;
    public boolean isVisiblePassword = false;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        sharedPreferences = getSharedPreferences("account", Context.MODE_PRIVATE);
        edtUsername_frame2 = findViewById(R.id.edtUsername_frame2);
        edtPassword_frame2 = findViewById(R.id.edtPassword_frame2);
        imgShowPassword_frame2 = findViewById(R.id.imgShowPassword_frame2);
        chkRememberPassword_frame2 = findViewById(R.id.chkRememberPassword_frame2);
        btnNext_frame2 = findViewById(R.id.btnNext_frame2);
        tvSignIn_frame2 = findViewById(R.id.tvSignIn_frame2);

        setTaiKhoan();

        btnNext_frame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kiemTraDangNhap();
            }
        });

        imgShowPassword_frame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPassword();
            }
        });

        tvSignIn_frame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhapActivity.this, DangKyTaiKhoanActivity.class);
                startActivity(intent);
            }
        });
    }

    public void kiemTraDangNhap() {
        String taikhoan = edtUsername_frame2.getText().toString();
        String matkhau = edtPassword_frame2.getText().toString();
        NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(this);

        if (taikhoan.equals("") || matkhau.equals("")) {
            Toast.makeText(this, "Không được bỏ trống", Toast.LENGTH_SHORT).show();
        } else {
            if (nguoiDungDAO.kiemTraDangNhap(taikhoan, matkhau) == true) {
                saveTaiKhoan(taikhoan, matkhau);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setTaiKhoan() {
        String getTaiKhoan = sharedPreferences.getString("taikhoan", "");
        String getMatKhau = sharedPreferences.getString("matkhau", "");
        if (!getTaiKhoan.equalsIgnoreCase("")) {
            edtUsername_frame2.setText(getTaiKhoan);
            if (!getMatKhau.equalsIgnoreCase("")) {
                edtPassword_frame2.setText(getMatKhau);
                chkRememberPassword_frame2.setChecked(true);
            } else {
                chkRememberPassword_frame2.setChecked(false);
            }
        }
    }

    public void saveTaiKhoan(String taikhoan, String matkhau) {
        editor = sharedPreferences.edit();
        editor.putString("taikhoan", taikhoan);
        if (chkRememberPassword_frame2.isChecked()) {
            editor.putString("matkhau", matkhau);
        } else {
            editor.remove("matkhau");
        }
        editor.commit();
    }

    public void showPassword() {
        if (isVisiblePassword) {
            // Ẩn password
            edtPassword_frame2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            isVisiblePassword = false;
        } else {
            // Hiện password
            edtPassword_frame2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            isVisiblePassword = true;
        }
        // Di chuyển con trỏ về cuối chuỗi
        edtPassword_frame2.setSelection(edtPassword_frame2.getText().length());
    }
}