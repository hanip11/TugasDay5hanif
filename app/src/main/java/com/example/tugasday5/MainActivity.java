package com.example.tugasday5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private EditText etKode, etJumlah, etnama;
    private RadioGroup rgrMember;
    private Button btnProses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etKode = findViewById(R.id.etkode);
        etnama = findViewById(R.id.etnama);
        etJumlah = findViewById(R.id.etjumlah);
        rgrMember = findViewById(R.id.rgrmember);
        btnProses = findViewById(R.id.btnproses);


        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kodeBarang = etKode.getText().toString();
                String nama = etnama.getText().toString();
                String tipeMember = "";


                int jumlahBarang = Integer.parseInt(etJumlah.getText().toString());
                int selectedMember = rgrMember.getCheckedRadioButtonId();
                String namaBarang = getNamaBarang(kodeBarang);
                double hargaBarang = getHargaBarang(kodeBarang);

                double diskonTambahan = 0.0;
                if (calculateTotalPrice(kodeBarang, jumlahBarang) > 10000000) {
                    diskonTambahan = 100000;
                }

                double diskonMember = 0.0;
                if (selectedMember == R.id.rbgold) {
                    diskonMember = 0.1;
                } else if (selectedMember == R.id.rbsilver) {
                    diskonMember = 0.05;
                } else if (selectedMember == R.id.rbbiasa) {
                    diskonMember = 0.02;
                }

                double totalHarga = calculateTotalPrice(kodeBarang, jumlahBarang);
                double totalDiskon = (totalHarga + diskonTambahan) * diskonMember;
                double totalHargaSetelahDiskon = totalHarga + diskonTambahan - totalDiskon;

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("KODE_BARANG", kodeBarang);
                intent.putExtra("JUMLAH_BARANG", jumlahBarang);
                intent.putExtra("NAMA_BARANG", namaBarang);
                intent.putExtra("HARGA_BARANG", hargaBarang);
                intent.putExtra("NAMA", nama);
                intent.putExtra("TIPE_MEMBER", tipeMember);
                intent.putExtra("TOTAL_HARGA", totalHarga);
                intent.putExtra("DISKON", diskonMember); // Mengirim diskon member
                intent.putExtra("HARGA_DISKON", totalDiskon);
                String memberStatus = "";
                if (selectedMember == R.id.rbgold) {
                    memberStatus = "Gold";
                } else if (selectedMember == R.id.rbsilver) {
                    memberStatus = "Silver";
                } else if (selectedMember == R.id.rbbiasa) {
                    memberStatus = "Biasa";
                }
                intent.putExtra("MEMBER", memberStatus);
                intent.putExtra("JUMLAH_BAYAR", totalHargaSetelahDiskon);
                startActivity(intent);
            }
        });
    }

    private double calculateTotalPrice(String kodeBarang, int jumlahBarang) {
        double hargaPerBarang = getHargaBarang(kodeBarang);
        return hargaPerBarang * jumlahBarang;
    }

    private String getNamaBarang(String kodeBarang) {
        switch (kodeBarang) {
            case "o17":
                return "Oppo A17";
            case "AA5":
                return "Acer Aspire 5";
            case "MP3":
                return "Macbook Pro M3";
            default:
                return "";
        }
    }

    private double getHargaBarang(String kodeBarang) {
        switch (kodeBarang) {
            case "o17":
                return 250099;
            case "AA5":
                return 9999999;
            case "MP3":
                return 28999999;
            default:
                return 0.0;
        }
    }
}
