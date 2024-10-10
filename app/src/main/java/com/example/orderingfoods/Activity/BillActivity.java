package com.example.orderingfoods.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderingfoods.Adapter.BillAdapter;
import com.example.orderingfoods.Data.DatabaseHandler;
import com.example.orderingfoods.Models.Cart;
import com.example.orderingfoods.R;

import java.util.ArrayList;

public class BillActivity extends AppCompatActivity {
    private TextView textViewTableId, textViewNumGuests, textViewTotalQuantity, textViewTotalAllPrice;
    private ListView listViewBill;
    private BillAdapter billAdapter;
    private ArrayList<Cart> orderList;
    private DatabaseHandler databaseHandler;
    private TableActivity tableActivity;
    private Button buttonPrint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        textViewTableId = findViewById(R.id.textViewTableId);
        textViewNumGuests = findViewById(R.id.textViewNumGuests);
        listViewBill = findViewById(R.id.listViewBill);
        textViewTotalQuantity = findViewById(R.id.tv_TotalQuantity_bill);
        textViewTotalAllPrice = findViewById(R.id.tv_TotalAllPrice_bill);
        buttonPrint = findViewById(R.id.btn_Print_bill);

        databaseHandler = new DatabaseHandler(this);

        int tableId = getIntent().getIntExtra("tableId", -1);

        orderList = databaseHandler.getOrdersByTableId(tableId);

        if (!orderList.isEmpty()) {
            Cart firstOrder = orderList.get(0);
            textViewTableId.setText("Bàn: " + firstOrder.getTableId());
            textViewNumGuests.setText("Số khách: " + firstOrder.getNumGuests());

            billAdapter = new BillAdapter(this, R.layout.bill_item, orderList);
            listViewBill.setAdapter(billAdapter);
            calculateTotals(orderList);

            buttonPrint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Xóa dữ liệu của bàn
                    databaseHandler.clearOrdersByTableId(tableId);

                    tableActivity.updateTableStatus(tableId, 0, "Trống");

                    Toast.makeText(BillActivity.this, "Dữ liệu bàn đã được xóa.", Toast.LENGTH_SHORT).show();

                    finish();
                }
            });
        } else {
            textViewTableId.setText("Không có đơn hàng cho bàn này.");
        }
    }

    private void calculateTotals(ArrayList<Cart> orderList) {
        int totalQuantity = 0;
        double totalPrice = 0.0;

        for (Cart order : orderList) {
            totalQuantity += order.getQuantity();
            totalPrice += order.getPrice();
        }

        textViewTotalQuantity.setText(String.valueOf(totalQuantity));
        textViewTotalAllPrice.setText(totalPrice + " VND");
    }
}