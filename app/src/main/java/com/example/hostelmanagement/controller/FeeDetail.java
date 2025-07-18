package com.example.hostelmanagement.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hostelmanagement.DAO.ContractDAO;
import com.example.hostelmanagement.DAO.HostelDAO;
import com.example.hostelmanagement.DAO.InvoiceDAO;
import com.example.hostelmanagement.DAO.RoomDAO;
import com.example.hostelmanagement.R;
import com.example.hostelmanagement.model.Contract;
import com.example.hostelmanagement.model.Hostel;
import com.example.hostelmanagement.model.Invoice;
import com.example.hostelmanagement.model.Room;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FeeDetail extends AppCompatActivity {

    TextView tvDate, tvTenant, tvPhone, tvRoom,
            tvElectricDetail, tvWater, tvBike, tvRoomFee,
            tvCurrentFee, tvPreviousDue, tvTotalAmount, tvNote;
    TextView  tvWaterDetail, tvInternetDetail, tvGarbageDetail,
            tvBikeDetail, tvRoomFeeDetail;
    Button btnSaveSend;
    ImageView btnBack;

    ContractDAO contractDAO;
    InvoiceDAO invoiceDAO;
    HostelDAO hostelDAO;

    RoomDAO roomDAO;
    DecimalFormat formatter = new DecimalFormat("#,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_detail);

        contractDAO = new ContractDAO(this);
        invoiceDAO = new InvoiceDAO(this);
        hostelDAO = new HostelDAO(this);
        roomDAO = new RoomDAO(this);
        initViews();

        int contractId = getIntent().getIntExtra("contractId", -1);
        int newIndex = getIntent().getIntExtra("newIndex", -1);

        if (contractId == -1 || newIndex == -1) {
            Toast.makeText(this, "Lỗi dữ liệu", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Contract contract = contractDAO.getContractById(contractId);
        if (contract == null) {
            Toast.makeText(this, "Không tìm thấy hợp đồng", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Room room = roomDAO.getRoomById(contract.getRoom());
        Hostel hostel = hostelDAO.getHostelById(room.getHostelId());
        if (hostel == null) {
            Toast.makeText(this, "Không tìm thấy thông tin nhà trọ", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Ngày hôm nay
        String today = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        tvDate.setText(today);

        // Hiển thị thông tin người thuê
        tvTenant.setText("Người thuê: " + contract.getCustomerName());
        tvPhone.setText("Điện thoại: " + contract.getPhone());
        tvRoom.setText("Phòng: " + room.getRoomName());

        int oldIndex = contract.getElectricIndex();
        int electricUsed = newIndex - oldIndex;
        int electricCost = electricUsed * hostel.getElectricPrice();
        int waterCost = hostel.getWaterPrice();
        int bikeCount = contract.getHasParking() == 1 ? contract.getBikeCount() : 0;
        int bikeCost = bikeCount * hostel.getVehiclePrice();
        int roomFee = contract.getRoomPrice();
        int internetCost = contract.getHasInternet() == 1 ? hostel.getInternetPrice() : 0;
        int laundryCost = contract.getHasLaundry() == 1 ? hostel.getLaundryPrice() : 0;
        int tvCost = hostel.getTvPrice();
        int garbageCost = hostel.getGarbagePrice();
        int serviceCost = hostel.getServicePrice();
        int elevatorCost = hostel.getElevatorPrice();

        int total = electricCost + waterCost + bikeCost + roomFee +
                internetCost + laundryCost + tvCost +
                garbageCost + serviceCost + elevatorCost;
        // Hiển thị chi tiết
        tvElectricDetail.setText(oldIndex + " → " + newIndex + " x " + formatter.format(hostel.getElectricPrice()) + " = " + formatter.format(electricCost) + " đ");

// Nước
        tvWaterDetail.setText("1 x " + formatter.format(waterCost) + " = " + formatter.format(waterCost) + " đ");

// Internet
        if (internetCost > 0) {
            tvInternetDetail.setText("1 x " + formatter.format(hostel.getInternetPrice()) + " = " + formatter.format(internetCost) + " đ");
        } else {
            tvInternetDetail.setText("Không sử dụng");
        }

// Rác
        tvGarbageDetail.setText("1 x " + formatter.format(garbageCost) + " = " + formatter.format(garbageCost) + " đ");

// Xe
        if (bikeCount > 0) {
            tvBikeDetail.setText(bikeCount + " x " + formatter.format(hostel.getVehiclePrice()) + " = " + formatter.format(bikeCost) + " đ");
        } else {
            tvBikeDetail.setText("Không có xe");
        }

// Tiền phòng
        tvRoomFeeDetail.setText(formatter.format(roomFee) + " đ");

// Tổng, nợ cũ và tổng thanh toán
        tvCurrentFee.setText(formatter.format(total) + " đ");
        tvPreviousDue.setText("0 đ");
        tvTotalAmount.setText(formatter.format(total) + " đ");

        String note = "Chủ TK: " + hostel.getBankOwner()
                + "\nNgân hàng: " + hostel.getBankName()
                + "\nSTK: " + hostel.getBankAccount();
        tvNote.setText(note);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(FeeDetail.this, Fee.class);
            startActivity(intent);
        });
        // Gửi hoá đơn
        btnSaveSend.setOnClickListener(v -> {
            Invoice invoice = new Invoice();
            invoice.setRoomId(contract.getRoom());
            invoice.setContractId(contractId);
            invoice.setTotal(total);
            long result = invoiceDAO.insertInvoice(invoice);

            contract.setElectricIndex(newIndex);
            int updated = contractDAO.updateContract(contract);

            if (result > 0 && updated > 0) {
                Toast.makeText(this, "Lưu và gửi hoá đơn thành công", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Lưu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
        tvDate = findViewById(R.id.tvDate);
        tvTenant = findViewById(R.id.tvTenant);
        tvPhone = findViewById(R.id.tvPhone);
        tvRoom = findViewById(R.id.tvRoom);
        btnBack = findViewById(R.id.btnBack);

        tvElectricDetail = findViewById(R.id.tvElectricDetail);
        tvWaterDetail = findViewById(R.id.tvWaterDetail);
        tvInternetDetail = findViewById(R.id.tvInternetDetail);
        tvGarbageDetail = findViewById(R.id.tvGarbageDetail);
        tvBikeDetail = findViewById(R.id.tvBikeDetail);
        tvRoomFeeDetail = findViewById(R.id.tvRoomFeeDetail);

        tvCurrentFee = findViewById(R.id.tvCurrentFee);
        tvPreviousDue = findViewById(R.id.tvPreviousDue);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvNote = findViewById(R.id.tvNote);
        btnSaveSend = findViewById(R.id.btnSaveSend);
    }

}
