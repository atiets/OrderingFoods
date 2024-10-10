package com.example.orderingfoods.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.orderingfoods.Adapter.BillAdapter;
import com.example.orderingfoods.Adapter.TableAdapter;
import com.example.orderingfoods.Data.DatabaseHandler;
import com.example.orderingfoods.Models.Cart;
import com.example.orderingfoods.Models.Food;
import com.example.orderingfoods.Models.Table;
import com.example.orderingfoods.Models.User;
import com.example.orderingfoods.R;

import java.util.ArrayList;

public class TableActivity extends AppCompatActivity {
    private GridView gridView;
    private ArrayList<Table> tableArrayList;
    TableAdapter tableAdapter;
    TextView textViewNumGuests, textViewNum, textViewName;
    private static final int REQUEST_CODE_CART = 1;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        databaseHandler = new DatabaseHandler(this);

        gridView = findViewById(R.id.gridview_tables);
        textViewName = findViewById(R.id.tv_NameTitle_table);

        tableArrayList = new ArrayList<>();
        tableAdapter = new TableAdapter(TableActivity.this, R.layout.row_table, tableArrayList);
        gridView.setAdapter(tableAdapter);

        loadTablesFromDatabase();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Table selectedTable = tableArrayList.get(position);

                textViewNumGuests = view.findViewById(R.id.textViewNumGuests);
                textViewNum = view.findViewById(R.id.textViewNum);

                textViewNumGuests.setText(String.valueOf(selectedTable.getNumberOfGuests()));
                textViewNum.setText(String.valueOf(selectedTable.getTableId()));

                // Hiển thị trạng thái bàn
                Toast.makeText(TableActivity.this, "Bàn " + selectedTable.getTableId() + ": " + selectedTable.getStatus(), Toast.LENGTH_SHORT).show();

                if (selectedTable.getStatus().equals("Trống") || selectedTable.getStatus().equals("Đã đặt")) {
                    showAddNumberDialog(selectedTable);
                }else if (selectedTable.getStatus().equals("Đang phục vụ")) {
                    // Khởi chạy BillActivity để hiển thị hóa đơn
                    Intent intent = new Intent(TableActivity.this, BillActivity.class);
                    intent.putExtra("tableId", selectedTable.getTableId());
                    startActivity(intent);
                }
            }
        });

        tableArrayList.add(new Table(31, "Trống", 0, null));
        tableArrayList.add(new Table(32, "Đã đặt", 4, null));
        tableArrayList.add(new Table(33, "Trống", 0, null));
        tableArrayList.add(new Table(34, "Trống", 0, null));
        tableArrayList.add(new Table(35, "Trống", 0, null));
        tableArrayList.add(new Table(36, "Trống", 0, null));
        tableArrayList.add(new Table(37, "Đã đặt", 3, null));
        tableArrayList.add(new Table(38, "Trống", 0, null));
        tableArrayList.add(new Table(39, "Trống", 0, null));
        tableArrayList.add(new Table(40, "Trống", 0, null));
        tableArrayList.add(new Table(41, "Trống", 0, null));
        tableArrayList.add(new Table(42, "Trống", 0, null));
        tableArrayList.add(new Table(43, "Trống", 0, null));
        tableArrayList.add(new Table(44, "Trống", 0, null));
        tableArrayList.add(new Table(45, "Trống", 0, null));
        tableArrayList.add(new Table(46, "Trống", 0, null));
        tableArrayList.add(new Table(47, "Đã đặt", 2, null));
        tableArrayList.add(new Table(48, "Trống", 0, null));
        tableArrayList.add(new Table(49, "Trống", 0, null));
        tableArrayList.add(new Table(50, "Trống", 0, null));
        tableArrayList.add(new Table(51, "Trống", 0, null));
        tableArrayList.add(new Table(52, "Đã đặt", 1, null));
        tableArrayList.add(new Table(53, "Trống", 0, null));
        tableArrayList.add(new Table(54, "Trống", 0, null));
        tableArrayList.add(new Table(55, "Trống", 0, null));
        tableArrayList.add(new Table(56, "Trống", 0, null));
        tableArrayList.add(new Table(57, "Đã đặt", 4, null));
        tableArrayList.add(new Table(58, "Trống", 0, null));
        tableArrayList.add(new Table(59, "Trống", 0, null));
        tableArrayList.add(new Table(60, "Trống", 0, null));

    }

    private void loadTablesFromDatabase() {
        tableArrayList.clear();

        for (int i = 1; i <= 30; i++) {
            Table table = new Table(i, "Trống", 0, null);

            ArrayList<Cart> orders = databaseHandler.getOrdersByTableId(i);

            if (!orders.isEmpty()) {
                table.setStatus("Đang phục vụ");
                table.setNumberOfGuests(orders.get(0).getNumGuests());
            } else {
                table.setStatus("Trống");
                table.setNumberOfGuests(0);
            }
            tableArrayList.add(table);
        }
        tableAdapter.notifyDataSetChanged();
    }

    public void updateTableStatus(int tableId, int numGuests, String status) {
        for (Table table : tableArrayList) {
            if (table.getTableId() == tableId) {
                table.setNumberOfGuests(numGuests);
                table.setStatus(status);
                break;
            }
        }
        tableAdapter.notifyDataSetChanged();
    }

    private void showAddNumberDialog(Table selectedTable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.diag_add_number, null);
        builder.setView(dialogView);

        EditText edtNumGuests = dialogView.findViewById(R.id.edt_numGuests);
        Button buttonSave = dialogView.findViewById(R.id.button_save);
        Button buttonCancel = dialogView.findViewById(R.id.button_cancel);

        if (selectedTable.getStatus().equals("Đã đặt")) {
            edtNumGuests.setText(String.valueOf(selectedTable.getNumberOfGuests()));
        }

        AlertDialog dialog = builder.create();

        buttonSave.setOnClickListener(v -> {
            String numGuestsString = edtNumGuests.getText().toString();
            if (!numGuestsString.isEmpty()) {
                int numGuests = Integer.parseInt(numGuestsString);
                selectedTable.setNumberOfGuests(numGuests);

                selectedTable.setStatus("Đang phục vụ");
                tableAdapter.notifyDataSetChanged();

                Intent intent = new Intent(TableActivity.this, MenuActivity.class);
                intent.putExtra("tableId", selectedTable.getTableId());
                intent.putExtra("currentTable", selectedTable);
                intent.putExtra("numGuests", numGuests);
                startActivityForResult(intent, REQUEST_CODE_CART);
                dialog.dismiss();

                startActivity(intent);
            } else {
                Toast.makeText(this, "Vui lòng nhập số lượng khách", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện hủy
        buttonCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật lại adapter khi quay lại
        tableAdapter.notifyDataSetChanged();
    }
}
