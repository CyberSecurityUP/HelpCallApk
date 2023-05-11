package com.joasa.helpcall;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String BACKEND_URL = "http://b3eb-170-254-144-228.ngrok-free.app"; // Substitua pelo URL do seu back-end

    private EditText phoneNumberEditText;
    private Button sendHelpSmsButton;
    private Button makeHelpCallButton;
    private RequestQueue requestQueue;
    private EditText messageEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneNumberEditText = findViewById(R.id.phone_number_edit_text);
        sendHelpSmsButton = findViewById(R.id.send_help_sms_button);
        makeHelpCallButton = findViewById(R.id.make_help_call_button);
        messageEditText = findViewById(R.id.messageEditText);
        requestQueue = Volley.newRequestQueue(this);

        sendHelpSmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendHelpSms(phoneNumberEditText.getText().toString());
            }
        });

        makeHelpCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeHelpCall(phoneNumberEditText.getText().toString());
            }
        });
    }

    private void sendHelpSms(final String phoneNumber) {
        String customMessage = messageEditText.getText().toString();
        if (customMessage.isEmpty()) {
            customMessage = "Esta é uma mensagem de ajuda padrão.";
        }
        final String finalCustomMessage = customMessage;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BACKEND_URL + "/send_sms",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "SMS enviado com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Falha ao enviar SMS.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("phone_number", phoneNumber);
                params.put("message", finalCustomMessage);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void makeHelpCall(final String phoneNumber) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BACKEND_URL + "/make_call",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "Chamada realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Falha ao realizar chamada.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("phone_number", phoneNumber);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}