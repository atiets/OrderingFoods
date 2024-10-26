package com.example.orderingfoods.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

                if (selectedTable.getStatus().equals("Trống")) {
                    showTableOptionsDialog(selectedTable);
                } else if (selectedTable.getStatus().equals("Đã đặt")) {
                    showTableDetailsDialog(selectedTable);
                } else if (selectedTable.getStatus().equals("Đang phục vụ")) {
                    Intent intent = new Intent(TableActivity.this, BillActivity.class);
                    intent.putExtra("tableId", selectedTable.getTableId());
                    startActivity(intent);
                }
            }
        });
    }

    private void showTableOptionsDialog(final Table selectedTable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] options = {"Bàn đặt trước", "Ăn tại quán"};
        builder.setTitle("Chọn lựa chọn")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        // Người dùng chọn Bàn đặt trước
                        showReservationDialog(selectedTable);
                    } else {
                        // Người dùng chọn Ăn tại quán
                        showAddNumberDialog(selectedTable);
                    }
                });
        builder.create().show();
    }

    private void showReservationDialog(Table selectedTable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.diag_reservation, null);
        builder.setView(dialogView);

        EditText edtNumGuests = dialogView.findViewById(R.id.edt_numGuests);
        EditText edtArrivalTime = dialogView.findViewById(R.id.edt_arrivalTime);

        Button buttonSave = dialogView.findViewById(R.id.button_save);
        Button buttonCancel = dialogView.findViewById(R.id.button_cancel);

        AlertDialog dialog = builder.create();

        buttonSave.setOnClickListener(v -> {
            String numGuestsString = edtNumGuests.getText().toString();
            String arrivalTime = edtArrivalTime.getText().toString();

            if (!numGuestsString.isEmpty() && !arrivalTime.isEmpty()) {
                int numGuests = Integer.parseInt(numGuestsString);
                selectedTable.setNumberOfGuests(numGuests);
                selectedTable.setArrivalTime(arrivalTime);
                selectedTable.setStatus("Đã đặt");

                // Cập nhật danh sách bàn và UI
                updateTableStatus(selectedTable.getTableId(), numGuests, "Đã đặt");

                dialog.dismiss();
                Toast.makeText(this, "Đã lưu thông tin bàn", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });

        buttonCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void showTableDetailsDialog(Table selectedTable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chi tiết bàn " + selectedTable.getTableId());

        // Tạo thông điệp hiển thị thông tin bàn
        String message = "Số lượng khách: " + selectedTable.getNumberOfGuests() + "\nThời gian đến: " + selectedTable.getArrivalTime();
        builder.setMessage(message);

        builder.setPositiveButton("Order", (dialog, which) -> {
            showAddNumberDialog(selectedTable);
        });

        builder.setNegativeButton("Hủy bàn", (dialog, which) -> {
            // Cập nhật trạng thái bàn thành "Trống" và số lượng khách về 0
            selectedTable.setStatus("Trống");
            selectedTable.setNumberOfGuests(0);
            tableAdapter.notifyDataSetChanged(); // Cập nhật giao diện hiển thị
        });

        // Hiển thị dialog
        builder.create().show();
    }

    private void loadTablesFromDatabase() {
        tableArrayList.clear();

        // Lặp qua danh sách 30 bàn
        for (int i = 1; i <= 30; i++) {
            // Lấy trạng thái mặc định của mỗi bàn từ cơ sở dữ liệu
            String arrivalTime = ""; // Biến để lưu thời gian đến quán
            Table table = new Table(i, "Trống", 0, null, arrivalTime); // Thêm arrivalTime

            // Lấy danh sách orders từ cơ sở dữ liệu theo tableId
            ArrayList<Cart> orders = databaseHandler.getOrdersByTableId(i);

            if (!orders.isEmpty()) {
                // Nếu có đơn hàng liên quan đến bàn, cập nhật thành "Đang phục vụ"
                table.setStatus("Đang phục vụ");
                table.setNumberOfGuests(orders.get(0).getNumGuests());

                // Cập nhật arrivalTime từ đơn hàng (giả sử đơn hàng có thông tin thời gian)
                arrivalTime = orders.get(0).getArrivalTime(); // Thay đổi này phụ thuộc vào cách bạn lưu thông tin thời gian trong đơn hàng
                table.setArrivalTime(arrivalTime); // Cập nhật arrivalTime
            } else {
                // Nếu không có order, giữ nguyên trạng thái hiện tại
                String currentStatus = table.getStatus();  // Lấy trạng thái hiện tại của bàn từ cơ sở dữ liệu
                if (currentStatus.equals("Đã đặt")) {
                    // Giữ trạng thái "Đã đặt" nếu có
                    table.setStatus("Đã đặt");
                    // Bạn có thể cập nhật arrivalTime nếu cần thiết, ví dụ:
                    arrivalTime = "15:00"; // Ví dụ thời gian cho bàn đã đặt
                    table.setArrivalTime(arrivalTime); // Cập nhật arrivalTime
                } else {
                    // Nếu không có đơn hàng và trạng thái không phải là "Đã đặt", đặt lại thành "Trống"
                    table.setStatus("Trống");
                }
                table.setNumberOfGuests(0);
            }

            tableArrayList.add(table);
        }

        // Cập nhật giao diện hiển thị sau khi tải dữ liệu
        tableAdapter.notifyDataSetChanged();
    }

    public void updateTableStatus(int tableId, int numGuests, String status) {
        // Cập nhật thông tin trong danh sách bàn hiện tại
        for (Table table : tableArrayList) {
            if (table.getTableId() == tableId) {
                table.setNumberOfGuests(numGuests);
                table.setStatus(status);
                break;
            }
        }
        ArrayList<Food> emptyFoodList = new ArrayList<>();
        databaseHandler.addOrder(emptyFoodList, numGuests, tableId, status, null);

        // Cập nhật giao diện
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
        checkReservationTimeout(); // Kiểm tra thời gian bàn đặt
    }

    // Phương thức kiểm tra thời gian đặt bàn
    private void checkReservationTimeout() {
        long currentTime = System.currentTimeMillis();

        for (Table table : tableArrayList) {
            if (table.getStatus().equals("Đã đặt")) {
                long reservationTime = 0;
                String arrivalTimeString = table.getArrivalTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                try {
                    Date date = sdf.parse(arrivalTimeString);
                    reservationTime = date.getTime(); // Lấy timestamp
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (currentTime - reservationTime > 2 * 60 * 1000) { // 2 phút
                    // Cập nhật trạng thái bàn chỉ nếu không có hành động từ người dùng
                    table.setStatus("Trống");
                    table.setNumberOfGuests(0);
                }
            }
        }
        tableAdapter.notifyDataSetChanged(); // Cập nhật giao diện
    }
}
