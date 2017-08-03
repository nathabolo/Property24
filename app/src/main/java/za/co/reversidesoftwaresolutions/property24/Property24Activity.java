package za.co.reversidesoftwaresolutions.property24;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Property24Activity extends AppCompatActivity {

    private TextView txt_display,btn_register_company, btn_register_users, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);

        txt_display = (TextView)findViewById(R.id.txt_display);
        btn_register_company = (TextView) findViewById(R.id.company_register);
        btn_register_users = (TextView) findViewById(R.id.customer_register);
        login = (TextView)findViewById(R.id.login);

        btn_register_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegisterCompany = new Intent(Property24Activity.this, CompanySignUpActivity.class);
                startActivity(intentRegisterCompany);
            }
        });

        btn_register_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegisterUsers = new Intent(Property24Activity.this, CustomerSignUpActivity.class);
                startActivity(intentRegisterUsers);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoginCompany = new Intent(Property24Activity.this, LoginActivity.class);
                startActivity(intentLoginCompany);
            }
        });


    }

}
